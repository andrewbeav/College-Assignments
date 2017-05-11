#!/usr/bin/env python
import rospy
import math
from geometry_msgs.msg import Twist
from assn3.msg import BallLocation

class PID:
    def __init__(self, goal, kp, ki, kd):
        self.goal = goal
        self.kp = kp
        self.ki = ki
        self.kd = kd
        self.previous_error = 0
        self.integral = 0
    def get_output(self, measurement):
        error = self.goal - measurement
        self.integral = self.integral + error
        derivative = error - self.previous_error
        output = self.kp*error + self.ki*self.integral + self.kd*derivative
        self.previous_error = error
        return output

class Robot:
    def __init__(self):
        self.pub = rospy.Publisher('/mobile_base/commands/velocity', Twist, queue_size=10)
        rospy.Subscriber("/ball_detector/ball_location", BallLocation, self.location_detected)
	self.state = 'search'
	self.bearing = -1
	self.distance = -1
        self.bearing_goal = 320 # where in the robot's field of view we want the ball to be
        self.distance_goal = 1.5 # ideal distance we want to be from the ball
        self.bearingPID = PID(self.bearing_goal, 0.005, 0.0, 0.0) 
        self.distancePID = PID(self.distance_goal, -0.2, 0.0, 0.0) 

    def get_vector(self, from_x, from_y, to_x, to_y):
        bearing = math.atan2(to_y - from_y, to_x - from_x)
        distance = math.sqrt((from_x - to_x)**2 + (from_y - to_y)**2)
        return (bearing, distance)

    def location_detected(self, msg):
        self.bearing = msg.bearing
        self.distance = msg.distance

    def run(self):
        rate = rospy.Rate(10)

        # Tolerance distances we are willing to accept
        bearing_epsilon = 20
        distance_epsilon = 0.15

        twist = Twist()
        while not rospy.is_shutdown():

            if self.state == 'search': 
		print 'search state'
                if self.bearing != -1 and self.distance != -1:
                    self.state = 'approach'
                else:
                    twist.angular.z = 1
                    twist.linear.x = 0

            # Using the PID controllers to approach the correct distance and bearing
            elif self.state == 'approach': 
		print 'approach state'
		print self.bearing
                if self.bearing == -1 or self.distance == -1.0:
                    self.state = 'search'
		    twist.angular.z = 0
		    twist.angular.x = 0
                elif self.bearing < self.bearing_goal + bearing_epsilon and self.bearing > self.bearing_goal - bearing_epsilon:
                    if self.distance < self.distance_goal + distance_epsilon and self.distance > self.distance_goal - distance_epsilon:
			self.state = 'intermediate_nav'
			self.event_time = rospy.get_time()
			self.state = 'kick'
                else:
                    twist.angular.z = self.bearingPID.get_output(self.bearing)
                    twist.linear.x = self.distancePID.get_output(self.distance)

            elif self.state == 'kick': 
		print 'kick state'
                twist.angular.z = 0
                twist.linear.x = 1.5
                if rospy.get_time() - self.event_time >= 1.7:
                    self.state = 'search'

            self.pub.publish(twist)

            rate.sleep()

rospy.init_node('robot_puppy')
robot = Robot()
robot.run()

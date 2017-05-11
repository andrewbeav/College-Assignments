#!/usr/bin/env python
import rospy
import math
from geometry_msgs.msg import Twist
from assn3.msg import BallLocation
from tf.transformations import euler_from_quaternion
from nav_msgs.msg import Odometry

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
        rospy.Subscriber('/odom', Odometry, self.handle_pose)
	self.state = 'search'
	self.bearing = -1
	self.distance = -1
        self.bearing_goal = 320 # where in the robot's field of view we want the ball to be
        self.distance_goal = 1.5 # ideal distance we want to be from the ball
        self.bearingPID = PID(self.bearing_goal, 0.005, 0.0, 0.0) 
        self.distancePID = PID(self.distance_goal, -0.2, 0.0, 0.0) 

	self.x = 0
	self.y = 0
	self.theta = 0

	self.intermediate_distancePID = PID(0, -0.3, 0, 0)
	self.thetaPID = PID(0, 1, 0, 0)

    def get_vector(self, from_x, from_y, to_x, to_y):
        bearing = math.atan2(to_y - from_y, to_x - from_x)
        distance = math.sqrt((from_x - to_x)**2 + (from_y - to_y)**2)
        return (bearing, distance)

    def location_detected(self, msg):
        self.bearing = msg.bearing
        self.distance = msg.distance

    def handle_pose(self, msg):
        self.x = msg.pose.pose.position.x
        self.y = msg.pose.pose.position.y
        q = (msg.pose.pose.orientation.x, msg.pose.pose.orientation.y,
        msg.pose.pose.orientation.z, msg.pose.pose.orientation.w)
        (_, _, self.theta) = euler_from_quaternion(q)

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

            elif self.state == 'approach': 
		print 'approach state'
		print self.bearing
                if self.bearing == -1 or self.distance == -1.0:
                    self.state = 'search'
		    twist.angular.z = 0
		    twist.angular.x = 0
                elif self.bearing < self.bearing_goal + bearing_epsilon and self.bearing > self.bearing_goal - bearing_epsilon:
                    if self.distance < self.distance_goal + distance_epsilon and self.distance > self.distance_goal - distance_epsilon:
                        # Calculating Intermedate and Final Goal coordinates
			self.intermediate_x = self.x + (1.5 * math.sqrt(2) * math.cos(self.theta - math.pi/4))
			self.intermediate_y = self.y + (1.5 * math.sqrt(2) * math.cos(self.theta - math.pi/4))
			self.final_x = self.x + (3 * math.cos(self.theta))
			self.final_y = self.y + (3 * math.sin(self.theta))
			self.intermediate_distancePID = PID(0, -0.2, 0, 0)
			self.thetaPID = PID(0, 1, 0, 0)
			self.state = 'intermediate_nav'
                else:
                    twist.angular.z = self.bearingPID.get_output(self.bearing)
                    twist.linear.x = self.distancePID.get_output(self.distance)

            elif self.state == 'intermediate_nav':
                print 'intermediate'
		(bearing, distance) = self.get_vector(self.x, self.y, self.intermediate_x, self.intermediate_y)
		#print 'self.theta: ' + str(self.theta)
		#print 'Goals: intermediate_x = ' + str(self.intermediate_x) + ' intermediate_y = ' + str(self.intermediate_y)
		#print '\n'

		#twist.linear.x = self.intermediate_distancePID.get_output(distance)
		#twist.angular.z = self.thetaPID.get_output(self.theta - bearing)
		
                if abs(self.x - self.intermediate_x) < 0.5 and abs(self.y - self.intermediate_y) < 0.5:
                    self.state = 'nav_to_kick'
                else:
		    twist.linear.x = self.intermediate_distancePID.get_output(distance) 
		    twist.angular.z = self.thetaPID.get_output(self.theta - bearing)
		    #print('theta output: ' + str(twist.angular.z))
            elif self.state == 'nav_to_kick':
                print 'nav to kick'
		(bearing, distance) = self.get_vector(self.x, self.y, self.final_x, self.final_y)
		if abs(self.x - self.final_x) < 0.1 and abs(self.y - self.final_y) < 0.1:
			self.state = 'line_up'
		else:
			twist.linear.x = self.intermediate_distancePID.get_output(distance)
			twist.angular.z = self.thetaPID.get_output(self.theta - bearing)
            elif self.state == 'line_up':
                print 'line_up'
		bear = 320 - self.bearing
		twist.angular.z = self.bearingPID.get_output(self.bearing)
		if abs(bear) < 5:
			self.event_time = rospy.get_time()
			self.state = 'kick'
		twist.linear.x = 0
            elif self.state == 'kick': 
		print 'kick state'
                twist.angular.z = 0
                twist.linear.x = 1.5
                if rospy.get_time() - self.event_time >= 1.7:
                    self.state = 'search'

            self.pub.publish(twist)

            rate.sleep()

rospy.init_node('dodgeball')
robot = Robot()
robot.run()

#!/usr/bin/env python
import rospy
import math
from geometry_msgs.msg import Twist
from assn3.msg import BallLocation
from tf.transformations import euler_from_quaternion
from nav_msgs.msg import Odometry
from kobuki_msgs.msg import BumperEvent
import tf2_ros

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

def get_vector(from_x, from_y, to_x, to_y):
    bearing = math.atan2(to_y - from_y, to_x - from_x)
    distance = math.sqrt((from_x - to_x)**2 + (from_y - to_y)**2)
    return (bearing, distance)

class Robot:
    def __init__(self):
        self.twist_pub = rospy.Publisher('/mobile_base/commands/velocity', Twist, queue_size=10)
        rospy.Subscriber('/ball_detector/ball_location', BallLocation, self.location_detected)
        rospy.Subscriber('/mobile_base/events/bumper', BumperEvent, self.bumped)
	rospy.Subscriber('/odom', Odometry, self.handle_pose)
	self.state = 'LOCATE_GOAL'
        self.listener = tf2_ros.Buffer()
        tf2_ros.TransformListener(self.listener)
        self.goal_x = self.goal_y = 0.0
	self.ball_distance = self.ball_bearing = -1
	
	self.x = 0
	self.y = 0
	self.theta = 0

    def handle_pose(self, msg):
        self.x = msg.pose.pose.position.x
        self.y = msg.pose.pose.position.y
        q = (msg.pose.pose.orientation.x, msg.pose.pose.orientation.y,
        msg.pose.pose.orientation.z, msg.pose.pose.orientation.w)
        (_, _, self.theta) = euler_from_quaternion(q)

    def location_detected(self, msg):
        self.ball_bearing = msg.bearing
        self.ball_distance = msg.distance

    def bumped(self, msg):
	if msg.state == BumperEvent.PRESSED:
	    self.event_time = rospy.get_time()
	    self.state = 'BACK_UP'

    def run(self):
        rate = rospy.Rate(10)

        twist = Twist()

        # Ball approaching PID stuff
        ball_bearing_goal = 320
        ball_distance_goal = 1.5
        ball_bearing_PID = PID(ball_bearing_goal, 0.005, 0.0, 0.0)
        ball_distance_PID = PID(ball_distance_goal, -0.2, 0.0, 0.0)

        # PIDs for approaching a point
        range_PID = PID(0, -0.3, 0, 0)
        theta_PID = PID(0, 1, 0, 0)

        while not rospy.is_shutdown():
            
	    # Calculating position of the goal(ar tag)
	    try:
	        trans = self.listener.lookup_transform('odom', 'ar_marker_0', rospy.Time())
	        self.goal_x = trans.transform.translation.x
	        self.goal_y = trans.transform.translation.y
	    except tf2_ros.LookupException:
	        pass
	    except tf2_ros.ConnectivityException:
	        pass
	    except tf2_ros.ExtrapolationException:
	        pass

            # Beginning of State machine
            if self.state == 'LOCATE_GOAL':
		print 'LOCATE_GOAL'

                if self.goal_x != 0 and self.goal_y != 0:
		    print str(self.goal_x) + ", " + str(self.goal_y)
                    self.state = 'SEARCH'

            elif self.state == 'SEARCH':
		print 'SEARCH'
                if self.ball_distance != -1 and self.ball_bearing != -1:
                    self.state = 'APPROACH'
                else:
                    twist.linear.x = 0
                    twist.angular.z = 1
            elif self.state == 'APPROACH':
		print 'APPROACH'
		if self.ball_distance == -1 or self.ball_bearing == -1:
		    self.state = 'SEARCH'
                if abs(self.ball_bearing - ball_bearing_goal) < 5 and abs(self.ball_distance - ball_distance_goal) < 0.2:
                    # Calculate the ball position
                    ball_x = self.x + (self.ball_distance * math.cos(self.theta))
                    ball_y = self.y + (self.ball_distance * math.sin(self.theta))
		    print str(ball_x) + ", " + str(ball_y)

                    # Calculate the kick position
                    (bearing, distance) = get_vector(self.goal_x, self.goal_y, ball_x, ball_y)
		    print "ball/goal vector: " + str(distance) + ", " + str(bearing)
                    self.kick_x = math.cos(bearing) * (1 + distance) + self.goal_x # Might have to change 1.2 to something else
                    self.kick_y = math.sin(bearing) * (1 + distance) + self.goal_y
		    print "kick coordinates" + str(self.kick_x) + ", " + str(self.kick_y)

                    if abs(self.x - self.kick_x) < abs(self.x - ball_x) and abs(self.y - self.kick_y) < abs(self.y - ball_y):
                        self.state = 'NAV_TO_KICK'
                    else:
			self.state = 'NAV_TO_KICK'
                        left_side_x = self.x + (1.5 * math.sqrt(2) * math.cos(self.theta - math.pi/4))
                        left_side_y = self.y + (1.5 * math.sqrt(2) * math.sin(self.theta - math.pi/4))
			right_side_x = self.x + (1.5 * math.sqrt(2) * math.cos(self.theta + math.pi/4))
			right_side_y = self.y + (1.5 * math.sqrt(2) * math.sin(self.theta + math.pi/4))
			if abs(left_side_x - self.kick_x) < abs(right_side_x - self.kick_x) and abs(left_side_y - self.kick_y) < abs(right_side_y - self.kick_y):
				self.side_x = left_side_x
				self.side_y = left_side_y
			else:
				self.side_x = right_side_x
				self.side_y = left_side_y
                        self.state = 'NAV_TO_INTERMEDIATE'

                else:
                    twist.linear.x = ball_distance_PID.get_output(self.ball_distance)
                    twist.angular.z = ball_bearing_PID.get_output(self.ball_bearing)
            elif self.state == 'NAV_TO_INTERMEDIATE':
		print 'NAV_TO_INTERMEDIATE'
                (bearing, distance) = get_vector(self.x, self.y, self.side_x, self.side_y)

                if abs(self.x - self.side_x) < 0.2 and abs(self.y - self.side_y) < 0.2:
                    self.state = 'NAV_TO_KICK'
		else:
		    twist.linear.x = range_PID.get_output(distance)
		    twist.angular.z = theta_PID.get_output(self.theta - bearing)


            elif self.state == 'NAV_TO_KICK':
		print 'NAV_TO_KICK'
                (bearing, distance) = get_vector(self.x, self.y, self.kick_x, self.kick_y)
                twist.linear.x = range_PID.get_output(distance)
                twist.angular.z = theta_PID.get_output(self.theta - bearing)

                if abs(self.x - self.kick_x) < 0.2 and abs(self.y - self.kick_y) < 0.2:
                    self.state = 'LINE_UP'

            elif self.state == 'LINE_UP':
		print 'LINE_UP'
                twist.linear.x = 0
                twist.angular.z = ball_bearing_PID.get_output(self.ball_bearing)

                if abs(self.ball_bearing - 320) < 10:
                    self.event_time = rospy.get_time()
                    self.state = 'KICK'

            elif self.state == 'KICK':
		print 'KICK'
                twist.linear.x = 1.5
                twist.angular.z = 0

                if rospy.get_time() - self.event_time >= 1.5: # Maybe change this value a bit
                    self.state = 'SEARCH'

            elif self.state == 'BACK_UP':
		print 'BACK_UP'
                twist.linear.x = -0.5
                twist.angular.z = 0
                
                if rospy.get_time() - self.event_time >= 2:
                    self.state = 'SEARCH'


            self.twist_pub.publish(twist)
            rate.sleep()

rospy.init_node('soccer_player')
robot = Robot()
robot.run()

#!/usr/bin/env python
import rospy
from geometry_msgs.msg import Twist
from kobuki_msgs.msg import BumperEvent

class Robot:
    def __init__(self):
        # Define an instance variable (self.pub, say) to hold the rospy.Publisher.
        # It should publish Twist messages on the '/mobile_base/commands/velocity' topic.
        # Subscribe to the BumperEvent messages that the robot driver publishes
        # on the '/mobile_base/events/bumper' topic.
        # It should identify the method to be called when a message arrives: self.bumped
        # Set up a variable to hold the robot's state (self.state, perhaps?)
        # and a variable to keep track of the time of the last state change
        self.pub = rospy.Publisher('/mobile_base/commands/velocity', Twist, queue_size=10)
        rospy.Subscriber("/mobile_base/events/bumper", BumperEvent, self.bumped)
        self.state = 1 # 0="go backward", 1="go forward" 2="turn"

    def bumped(self, msg):
        # If the BumperEvent message says that we just hit something,
        # and we are currently in the "go forward" state, change
        # to the "go backward" state. Make a note of the time.
        if self.state == 1 and msg.BumperEvent == BumperEvent.PRESSED:
            self.state = 0
            self.event_time = rospy.get_time()
    def run(self):
        rate = rospy.Rate(10)
        twist = Twist()
        while not rospy.is_shutdown():
            if self.state == 1: # go forward
                # Set twist.linear.x to a positive value <= 0.2
                # Publish twist
		twist.angular.z = 0
                twist.linear.x = 0.2
            elif self.state == 0: # go backward 
                # Set twist.linear.x to a negative value >= -0.2
                # Publish twist
                # If enough time has elapsed, change to the "turn" state
		twist.angular.z = 0
                twist.linear.x = -0.2
                if rospy.get_time()-self.event_time >= 2:
                    self.state = 2
                    self.event_time = rospy.get_time()
            elif self.state == 2: # turn state
                # Set twist.angular.z to a nonzero value
                # Publish twist
                # If enough time has elapsed, change to the "go forward" state
		twist.linear.x = 0
                twist.angular.z = 1
                if rospy.get_time()-self.event_time >= 2:
                    self.state = 1

	    self.pub.publish(twist)	

            rate.sleep()

rospy.init_node('prison_break')
robot = Robot()
robot.run()

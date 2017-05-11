#!/usr/bin/env python

import math
import roslib
roslib.load_manifest('assn3')
import rospy
import cv2
from sensor_msgs.msg import Image, LaserScan
from assn3.msg import BallLocation
from cv_bridge import CvBridge, CvBridgeError

class Detector:
    def __init__(self):
        # The image publisher is for debugging and figuring out
        # good color values to use for ball detection
        self.impub = rospy.Publisher('/ball_detector/image', Image, queue_size=1)
        self.locpub = rospy.Publisher('/ball_detector/ball_location', BallLocation, queue_size=1)
        self.bridge = CvBridge()
        self.bearing = -1
        self.distance = -1.0
        rospy.Subscriber('/camera/rgb/image_raw', Image, self.handle_image)
        rospy.Subscriber('/scan', LaserScan, self.handle_scan)

    def handle_image(self, msg):
        try:
            image = self.bridge.imgmsg_to_cv2(msg, "bgr8")
        except CvBridgeError, e:
            print e

        # Find the average column of the bright yellow pixels
        # and store as self.bearing. Store -1 if there are no
        # bright yellow pixels in the image.
        # Feel free to change the values in the image variable
        # in order to see what is going on
        (rows, columns, channels) = image.shape
        column_nums = []
        for row in range(0, rows, 10):
            for column in range(0, columns, 10):
		blue = image[row, column, 0]
		green = image[row, column, 1]
		red = image[row, column, 2]
                if blue <= 50 and green >= 110 and red >= 70:
                    column_nums.append(column)
		    image[row:row+10, column:column+10, 0:3] = 0
        if len(column_nums) > 3:
            self.bearing = sum(column_nums)/len(column_nums)
        else:
            self.bearing = -1
	
        # Here we publish the modified image; it can be
        # examined by running image_view
        self.impub.publish(self.bridge.cv2_to_imgmsg(image, "bgr8"))
    
    def handle_scan(self, msg):
        # If the bearing is valid, store the corresponding range
        # in self.distance. Decide what to do if range is NaN.
        if self.bearing != -1:
	    if (math.isnan(msg.ranges[-self.bearing])):
		self.distance = -1.0
	    else:
            	self.distance = msg.ranges[-self.bearing]
    
    def start(self):
        rate = rospy.Rate(10)
        while not rospy.is_shutdown():
            location = BallLocation()
            location.bearing = self.bearing
            location.distance = self.distance
            self.locpub.publish(location)
            rate.sleep()

rospy.init_node('ball_detector')
detector = Detector()
detector.start()

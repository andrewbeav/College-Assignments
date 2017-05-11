# Assignment 3 - Ball Finder

Assignment 3 was to use the Kinect camera on the turtlebot to find a yellow ball, and publish it's location (bearing + distance). The code pertaining to this is located at ./scripts/ball_detector.py

# Assignment 4 - Robot Puppy

Assignment 4 was to subscribe to the location published by the ball detector (see assn3^) and approach to a distance of 1.5m from the ball and with the ball centered in the turtlebot's field of view (bearing = 320 because 640 x 480 res.). This involved having the robot spin in place until it found the ball, then use PID controllers to move to the necessary distance and bearing, at which point it would move forward and _kick_ the ball. The code is located at ./scripts/robot_puppy.py

# Assignment 5 - Dodgeball

Assignment 5 was to not ONLY kick the ball, but to kick it in the opposite direction. This involved using the Odometry frame and some trigonometry to calculate the coordinates of a position directly behind the ball, so that the turtlebot could use PIDs to go to that coordinate, turn around, and kick the ball. But, if it went straight there it would run into the ball. Therefore, the program also had to calculate the coordinates of a position directly to the right of left of the ball so that it could navigate to that positon, then the final position, and finally turn around and kick the ball. The code for this assignment is in ./scripts/dodgeball.py

# Notes

* It would have probably been best if I had put assn4 and assn5 in their own package.

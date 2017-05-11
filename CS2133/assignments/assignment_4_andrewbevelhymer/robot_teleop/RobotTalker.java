import java.net.*;
import java.io.*;

public class RobotTalker {
	public static final String IP_ADDRESS = "lear.cs.okstate.edu";
	public static final int PORT = 9095;

	// Land/Takeoff constants
	public static final int LANDED = 111;
	public static final int IN_AIR = 112;

	private PrintWriter commandWriter;

	private int state = LANDED;

	private double speed = 0.10;

	public RobotTalker() {
		try {
			Socket socket = new Socket(IP_ADDRESS, PORT);
			System.out.println("Connection to " + IP_ADDRESS + " successful");
			commandWriter = new PrintWriter(socket.getOutputStream());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public int getState() {
		return this.state;
	}

	public double getSpeed() {
		return this.speed;
	}

	public void increaseSpeed() {
		if (this.speed <= 1.5) this.speed += 0.1;
	}

	public void decreaseSpeed() {
		if (this.speed > 0) this.speed -= 0.1;
	}

	public void takeoff() {
		String takeoff_msg = "{\"op\":\"publish\",\"topic\":\"/ardrone/takeoff\",\"msg\":{}}";
		commandWriter.print(takeoff_msg);
		commandWriter.flush();
		this.state = IN_AIR;
	}

	public void land() {
		String land_msg = "{\"op\":\"publish\",\"topic\":\"/ardrone/land\",\"msg\":{}}";
		commandWriter.print(land_msg);
		commandWriter.flush();
		this.state = LANDED;
	}

	public void moveUp() {
		String up_msg = "{\"op\":\"publish\", \"topic\":\"/cmd_vel\", \"msg\":{\"linear\":{\"x\":0, \"y\":0, \"z\":" + speed + "}, \"angular\":{\"x\":0, \"y\":0, \"z\":0}}}";

		commandWriter.print(up_msg);
		commandWriter.flush();
	}

	public void moveDown() {
		String down_msg = "{\"op\":\"publish\", \"topic\":\"/cmd_vel\", \"msg\":{\"linear\":{\"x\":0, \"y\":0, \"z\":" + -speed + "}, \"angular\":{\"x\":0, \"y\":0, \"z\":0}}}";
		commandWriter.print(down_msg);
		commandWriter.flush();
	}

	public void goForward() {
		String forward_msg = "{\"op\":\"publish\", \"topic\":\"/cmd_vel\", \"msg\":{\"linear\":{\"x\":" + speed + ", \"y\":0, \"z\":0}, \"angular\":{\"x\":0, \"y\":0, \"z\":0}}}";
		commandWriter.print(forward_msg);
		commandWriter.flush();
	}

	public void goBack() {
		String reverse_msg = "{\"op\":\"publish\", \"topic\":\"/cmd_vel\", \"msg\":{\"linear\":{\"x\":" + -speed + ", \"y\":0, \"z\":0}, \"angular\":{\"x\":0, \"y\":0, \"z\":0}}}";
		commandWriter.print(reverse_msg);
		commandWriter.flush();
	}

	public void goLeft() {
		String left_msg = "{\"op\":\"publish\", \"topic\":\"/cmd_vel\", \"msg\":{\"linear\":{\"x\":0, \"y\":" + speed +  ", \"z\":0}, \"angular\":{\"x\":0, \"y\":0, \"z\":0}}}";
		commandWriter.print(left_msg);
		commandWriter.flush();
	}

	public void goRight() {
		String right_msg = "{\"op\":\"publish\", \"topic\":\"/cmd_vel\", \"msg\":{\"linear\":{\"x\":0, \"y\":" + -speed + ", \"z\":0}, \"angular\":{\"x\":0, \"y\":0, \"z\":0}}}";
		commandWriter.print(right_msg);
		commandWriter.flush();
	}

	public void turnLeft() {
		String turn_left_msg = "{\"op\":\"publish\", \"topic\":\"/cmd_vel\", \"msg\":{\"linear\":{\"x\":0, \"y\":0, \"z\":0}, \"angular\":{\"x\":0, \"y\":0, \"z\":" + speed + "}}}";
		commandWriter.print(turn_left_msg);
		commandWriter.flush();
	}

	public void turnRight() {
		String turn_right_msg = "{\"op\":\"publish\", \"topic\":\"/cmd_vel\", \"msg\":{\"linear\":{\"x\":0, \"y\":0, \"z\":0}, \"angular\":{\"x\":0, \"y\":0, \"z\":" + -speed + "}}}";
		commandWriter.print(turn_right_msg);
		commandWriter.flush();
	}

	public void stop() {
		String stop_msg = "{\"op\":\"publish\", \"topic\":\"/cmd_vel\", \"msg\":{\"linear\":{\"x\":0, \"y\":0, \"z\":0}, \"angular\":{\"x\":0, \"y\":0, \"z\":0}}}";
		commandWriter.print(stop_msg);
		commandWriter.flush();
	}
}

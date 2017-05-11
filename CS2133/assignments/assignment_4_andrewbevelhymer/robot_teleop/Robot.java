import java.util.*;

public class Robot {
	public static void main(String[] args) {
		RobotTalker robotTalker = new RobotTalker();	

		System.out.println("Robot Teleoperation\nType <h> for a list of commands");

		Scanner scan = new Scanner(System.in);
		while(true) {
			System.out.print("> ");
			String line = scan.nextLine();
			String[] tokens = line.split(" ");
			if (tokens[0].equals("h")) usage();
			else if (tokens[0].equals("s")) robotTalker.stop();
			else if (tokens[0].equals("t")) robotTalker.takeoff(); 
			else if (tokens[0].equals("l")) robotTalker.land();
			else if (tokens[0].equals("tl")) robotTalker.turnLeft();
			else if (tokens[0].equals("ml")) robotTalker.goLeft();
			else if (tokens[0].equals("mu")) robotTalker.moveUp();
			else if (tokens[0].equals("md")) robotTalker.moveDown();
			else if (tokens[0].equals("mr")) robotTalker.goRight();
			else if (tokens[0].equals("tr")) robotTalker.turnRight();
			else if (tokens[0].equals("mf")) robotTalker.goForward();
			else if (tokens[0].equals("mb")) robotTalker.goBack();
			else if (tokens[0].equals("+")) {
				robotTalker.increaseSpeed();
				System.out.println("Speed: " + robotTalker.getSpeed());
			}
			else if (tokens[0].equals("-")) {
				robotTalker.decreaseSpeed();
				System.out.println("Speed: " + robotTalker.getSpeed());
			}
			else if (tokens[0].equals("speed")) System.out.println(robotTalker.getSpeed());
			else if (tokens[0].equals("q")) break;
			else System.out.println("Improper command");
		}
	}

	public static void usage() {
		System.out.println("t: takeoff\nl: land\ns: stop\ntl: turn left\ntr: turn right\nml: move left\nmr: move right\nmf: move forward\nmb: move backward\n+: speed up\n-: slow down\nspeed: print current speed\nq: quit");
	}
}


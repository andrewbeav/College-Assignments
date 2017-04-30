import java.io.*;
import java.util.*;

public class Tmp {
	public static void main(String[] args) {
		HuffmanNode zeroNode = new HuffmanNode((byte)1);
		HuffmanNode oneNode = new HuffmanNode((byte)3);
		HuffmanNode otherZeroNode = new HuffmanNode((byte)1);
		HuffmanNode otherOneNode = new HuffmanNode((byte)3);
		HuffmanNode parentNode = new HuffmanNode(zeroNode, oneNode);
		HuffmanNode otherParentNode = new HuffmanNode(otherZeroNode, otherOneNode);
		HuffmanNode daddyNode = new HuffmanNode(parentNode, otherParentNode);
		oneNode.incrementFrequency();
		zeroNode.incrementFrequency();
		zeroNode.incrementFrequency();

		System.out.println(oneNode.compareTo(zeroNode));
		/*String test = "hello henry";
		for (int i = 0; i < test.length(); i++) {
			System.out.println((byte)test.charAt(i));
		}*/
	}
}

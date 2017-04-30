import java.io.*;
import java.util.*;

public class HuffmanEncoder {
	public void encode(String fileName) {
		HashMap<Byte, HuffmanNode> leaves = new HashMap<>(256);
		for (int i = -128; i <= 128; i++) {
			leaves.put((byte)i, new HuffmanNode((byte)i));	
		}
		File file = new File(fileName);

		try {
			FileInputStream in = new FileInputStream(file);
			byte[] byteArray = new byte[in.available()];
			in.read(byteArray);
			for (int i = 0; i < byteArray.length; i++) {
				System.out.print(byteArray[i] + ":");
				HuffmanNode node = leaves.get(byteArray[i]);
				node.incrementFrequency();
			}
			System.out.println();

			PriorityQueue<HuffmanNode> queue = new PriorityQueue<>();

			for (HuffmanNode leaf : leaves.values()) {
				if (leaf.getFrequency() > 0) {
					queue.add(leaf);
				}
			}

			while(queue.size() > 1) {
				HuffmanNode zeroNode = queue.poll();
				HuffmanNode oneNode = queue.poll();
				HuffmanNode parent = new HuffmanNode(zeroNode, oneNode);
				System.out.println(zeroNode + ":" + zeroNode.getOneChild() + ":" + oneNode + ":" + oneNode.getOneChild() +  ":" + parent);
				queue.offer(parent);
			}

			ArrayList<Integer> bitList = new ArrayList<>();
			for (int i = 0; i < byteArray.length; i++) {
				HuffmanNode leaf = leaves.get(byteArray[i]);
				String bitString = leaf.getBitString();

				String[] bitStringArray = bitString.split("");
				for (int j = bitString.length()-1; j >= 0; j--) {
					bitList.add(Integer.parseInt(bitStringArray[j]));
				}
			}

			int numOfPads = 0;
			if (bitList.size() % 8 != 0) {
				for (int i = 0; i < bitList.size() % 8; i++) {
					bitList.add(0);
					numOfPads++;
				}
			}

			byte[] compressedBytes = Twiddle.bitsToBytes(bitList);
			System.out.println(compressedBytes.length);
			writeToDisk(fileName, queue.peek(), compressedBytes, numOfPads);
		}
		catch (FileNotFoundException e) {
			System.out.println("File Not Found");
			System.exit(0);
		}
		catch (IOException e) {
			System.out.println("IO Exception");
		}

	}
	public void writeToDisk(String fileName, HuffmanNode root, byte[] compressedData, int numOfPads) {
		fileName += ".huff";

		HuffmanCompressedData toWrite = new HuffmanCompressedData(compressedData, root, numOfPads);
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
			out.writeObject(toWrite);
			out.flush();
			out.close();
		}
		catch(IOException e) {
			System.out.println("Error when writing file");
		}
	}

}

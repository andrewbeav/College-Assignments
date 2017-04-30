import java.io.*;
import java.util.*;

public class HuffmanDecoder {
	public void decode(String fileName) {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
			HuffmanCompressedData fileData = (HuffmanCompressedData)in.readObject();

			HuffmanNode rootNode = fileData.getTree();
			byte[] compressedData = fileData.getData();

			List<Integer> bitList = Twiddle.bytesToBits(compressedData);

			int numOfPads = fileData.getNumOfPads();
			int firstIndex = bitList.size()-numOfPads;
			while(bitList.size() < firstIndex) {
				bitList.remove(bitList.size());
			}

			for (Integer i : bitList) {
				System.out.print(i);
			}
			System.out.println();

			ArrayList<Byte> byteList = new ArrayList<>();
			while(bitList.size() > 0) {
				byteList.add(getBytes(bitList, rootNode));
			}

			byte[] byteArray = new byte[byteList.size()];
			for (int i = 0; i < byteArray.length; i++) {
				byteArray[i] = byteList.get(i);
				System.out.println(byteArray[i]);
			}

			fileName = fileName.replace(".huff", "");
			fileName += ".decoded";
			FileOutputStream out = new FileOutputStream(fileName);
			out.write(byteArray);
			out.close();
		}
		catch(IOException e) {
			System.out.println("File Not Found");
		}
		catch(ClassNotFoundException e) {
			System.out.println("File Not Correct");
		}
	}

	private byte getBytes(List<Integer> bitList, HuffmanNode rootNode) {
		System.out.println(bitList.size());
		if (rootNode.getZeroChild() == null && rootNode.getOneChild() == null) {
			return rootNode.getByte();
		}
		int value = bitList.get(0);
		bitList.remove(0);
		if (value == 0) return getBytes(bitList, rootNode.getZeroChild());
		else if (value == 1) return getBytes(bitList, rootNode.getOneChild());

		return 0; // This should never be called
	}
}

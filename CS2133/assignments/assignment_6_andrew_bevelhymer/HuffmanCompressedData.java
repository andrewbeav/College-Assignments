import java.io.*;

public class HuffmanCompressedData implements Serializable {
	private byte[] compressedData;
	private HuffmanNode root;
	private int numOfPads;

	public HuffmanCompressedData(byte[] compressedData, HuffmanNode root, int numOfPads) {
		this.compressedData = compressedData;
		this.root = root;
		this.numOfPads = numOfPads;
	}

	public HuffmanNode getTree() {
		return this.root;
	}

	public byte[] getData() {
		return this.compressedData;
	}

	public int getNumOfPads() {
		return this.numOfPads;
	}
}

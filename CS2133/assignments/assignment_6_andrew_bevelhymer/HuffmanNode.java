import java.io.Serializable;

public class HuffmanNode implements Comparable<HuffmanNode>, Serializable {
	private HuffmanNode parent;
	private HuffmanNode zeroChild;
	private HuffmanNode oneChild;

	private byte byteObject;
	private int frequency = 0;

	public HuffmanNode(HuffmanNode zeroChild, HuffmanNode oneChild) {
		zeroChild.setParent(this);
		oneChild.setParent(this);
		this.zeroChild = zeroChild;
		this.oneChild = oneChild;
		this.frequency = zeroChild.getFrequency() + oneChild.getFrequency();
	}

	public void setParent(HuffmanNode parent) {
		this.parent = parent;
	}

	public HuffmanNode getParent() {
		return this.parent;
	}

	public HuffmanNode(byte byteObject) {
		this.byteObject = byteObject;
	}

	public int compareTo(HuffmanNode node) {
		return this.frequency - node.getFrequency();
	}

	public void incrementFrequency() {
		this.frequency++;
	}

	public String getBitString() {
		// This means we are root
		if (this.parent == null) return "";

		else if (this.parent.getZeroChild().equals(this)) return "0" + this.parent.getBitString();
		else if (this.parent.getOneChild().equals(this)) return "1" + this.parent.getBitString();

		return "";
	}

	public HuffmanNode getZeroChild() {
		return this.zeroChild;
	}

	public HuffmanNode getOneChild() {
		return this.oneChild;
	}

	public byte getByte() {
		return this.byteObject;
	}

	public String toString() {
		return "{ " + this.byteObject + ":" + this.frequency + " }";
	}

	public int getFrequency() {
		return this.frequency;
	}
}

import java.util.*;
public class Twiddle {
	public static byte[] bitsToBytes(List<Integer> l) {
		byte[] toReturn = new byte[l.size() / 8];
		int index = 0;
		for (int i = 0; i < l.size(); i += 8) {
			for (int j = 0; j < 8; j++) {
				toReturn[index] = (byte)(toReturn[index] | (l.get(i+j) << (7-j)));
			}
			index ++;
		}
		return toReturn;
	}

	public static List<Integer> bytesToBits(byte[] b) {
		ArrayList<Integer> toReturn = new ArrayList<Integer>();
		for (int i = 0; i < b.length; i++) {
			for (int j = 7; j >= 0; j--) {
				toReturn.add((b[i] & (1 << j)) >> j);
			}
		}
		return toReturn;
	}
}

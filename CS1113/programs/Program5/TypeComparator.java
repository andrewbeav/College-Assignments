import java.util.*;

class TypeComparator implements Comparator<BloodDonor>
{
  public int compare(BloodDonor a, BloodDonor b)
  {
    String aType = a.type;
    String bType = b.type;
    int comp = aType.compareTo(bType);
    return comp;
  }
}

import java.util.*;

class IdComparator implements Comparator<BloodDonor>
{
  public int compare(BloodDonor a, BloodDonor b)
  {
    int aId = a.idNum;
    int bId = b.idNum;
    if (aId > bId) return 1;
    else if (aId < bId) return -1;
    else return 0;
  }
}

import java.util.*;

class FirstNameComparator implements Comparator<BloodDonor>
{
  public int compare(BloodDonor a, BloodDonor b)
  {
    String aFirstName = a.firstName;
    String bFirstName = b.firstName;
    int comp = aFirstName.compareTo(bFirstName);
    return comp;
  }
}

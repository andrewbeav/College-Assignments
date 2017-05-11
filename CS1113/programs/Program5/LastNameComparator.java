import java.util.*;

class LastNameComparator implements Comparator<BloodDonor>
{
  public int compare(BloodDonor a, BloodDonor b)
  {
    String aLastName = a.lastName;
    String bLastName = b.lastName;
    int comp = aLastName.compareTo(bLastName);
    return comp;
  }
}

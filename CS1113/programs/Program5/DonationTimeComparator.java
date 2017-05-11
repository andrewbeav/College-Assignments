import java.util.*;

class DonationTimeComparator implements Comparator<BloodDonor>
{
  public int compare(BloodDonor a, BloodDonor b)
  {
    double aDonationTime = a.donationTime;
    double bDonationTime = b.donationTime;
    if (aDonationTime > bDonationTime) return 1;
    else if (aDonationTime < bDonationTime) return -1;
    else return 0;
  }
}

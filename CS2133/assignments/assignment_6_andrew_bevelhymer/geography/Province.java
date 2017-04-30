package geography;

import java.util.*;

/**
 * Abstract class used to represent a Province or State
 */
public abstract class Province extends GeographicContainerObject {
	/**
	 * Cities this Province contains
	 */
	private ArrayList<City> cities;

	/**
	 * @return ArrayList of Citys that are contained in this Province
	 */
	public abstract ArrayList<City> getCities();

	/**
	 * @return Country that this Province is in
	 */
	public abstract Country getCountry();
}

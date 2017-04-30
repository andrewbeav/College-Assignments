package geography;

/**
 * Abstract class used to represent a City
 */
public abstract class City extends NamedGeographicalObject {
	/**
	 * @return Country that this city lies in
	 */
	public abstract Country GetCountry();

	/**
	 * @return distance between this city and targetCity
	 * @param targetCity city of which to find the distance from this city to
	 */
	public abstract double distance(City targetCity);
}

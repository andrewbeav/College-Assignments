package geography;

/**
 * Class used to represent a boundary between two NamedGeographicalObjects
 */
public abstract class BoundarySegment {
	/**
	 * @return Array showing what this object is a border of
	 */
	public abstract NamedGeographicalObject[] borderOf();

	/**
	 * @return the length of this boundary
	 */
	public abstract double boundaryLength();
}

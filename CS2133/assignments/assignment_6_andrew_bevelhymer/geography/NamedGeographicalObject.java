package geography;

import java.util.*;

/**
 * Abstract class used to define a geographical object that has a name.
 */
public abstract class NamedGeographicalObject {
	public static final String DEFAULT_RIVER_NAME = "River";

	/**
	 * Name of this geographical object
	 */
	private String name;

	/**
	 * ArrayList containing all the neighboring NamedGeographicalObjects of this Object
	 */
	private ArrayList<NamedGeographicalObject> neighbors;

	public NamedGeographicalObject() {
		this(DEFAULT_RIVER_NAME);
	}

	/**
	 * @param name name of this object
	 */
	public NamedGeographicalObject(String name) {
		this.name = name;
	}

	/**
	 * Changes the name of this object
	 * @param name The name to change this object to
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the name of this object
	 */
	public String name() {
		return this.name;
	}
	
	/**
	 * @return the area taken up by this object
	 */
	public abstract double area();

	/**
	 * @return An ArrayList containing the objects this object neighbors
	 */
	public abstract ArrayList<NamedGeographicalObject> neighbors();
}

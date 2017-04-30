package geography;

/**
 * Class used to represent an object that contains other geographical objects, such as Cities or States
*/
public abstract class GeographicContainerObject extends NamedGeographicalObject {
	/**
	 * @Return the City that is the captial of this Object
	*/
	public abstract City capital();
}

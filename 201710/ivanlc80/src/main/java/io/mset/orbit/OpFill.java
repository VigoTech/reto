package io.mset.orbit;

/**
 * Interface for the Filling operation of a bucket.
 */
public interface OpFill {
	/**
	 * The name of the operation.
	 */
	public static final String name = "Fill [{0}]";
	/**
	 * Method to fill the bucket specified by the position.
	 * @param pos the position of the bucket to fill.
	 * @return Element resulting from the operation.
	 * @throws Exception when the operation fails.
	 */
	public Element fill(int pos) throws Exception;
}

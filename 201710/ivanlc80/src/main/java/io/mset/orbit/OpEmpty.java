package io.mset.orbit;

/**
 * Interface for the emptying operation of a bucket.
 */
public interface OpEmpty {
	/**
	 * The name of the operation.
	 */
	public static final String name = "Empty [{0}]";
	/**
	 * Method to empty the bucket specified by the position.
	 * @param pos the position of the bucket to empty.
	 * @return Element resulting from the operation.
	 * @throws Exception when the operation fails.
	 */
	public Element empty(int pos) throws Exception;
}

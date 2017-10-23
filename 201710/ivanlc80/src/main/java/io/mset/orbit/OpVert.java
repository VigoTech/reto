package io.mset.orbit;

/**
 * Interface for the verting operation of one bucket into another.
 */
public interface OpVert {
	/**
	 * The name of the operation.
	 */
	public static final String name = "Vert [{0}] to [{1}]";
	/**
	 * Method to vert the content of one bucket into another.
	 * @param posInt the position of the initial bucket.
	 * @param posFinal the position of the final bucket.
	 * @return Element resulting from the operation.
	 * @throws Exception when the operation fails.
	 */
	public Element vert(int posInt, int posFinal) throws Exception;
}

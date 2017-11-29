package io.mset.orbit;

import java.util.Arrays;
import java.util.logging.Logger;

public class Element implements OpEmpty, OpFill, OpVert{
	private static Logger LOGGER =  Logger.getLogger(Element.class.getName());
	/**
	 * Maximum for each coordinate.
	 */
	private int [] maxCoordinates;
	/**
	 * Coordinates representing the quantities within each bucket.
	 */
	private int [] coordinates;
	/**
	 * Indicator that all operations have been executed from this element.
	 */
	private boolean indAllOperationsExecuted = Boolean.FALSE;
	/**
	 * Parent element.
	 */
	private Element parent;
	/**
	 * Operation used to generate this element.
	 */
	private String fromOperation;

	/**
	 * Constructor
	 * @param coordinatesParam the coordinates array.
	 */
	public Element(int [] maxCoordinatesParam, int [] coordinatesParam) throws Exception{
		if (maxCoordinatesParam == null) throw new NullPointerException("maxCoordinatesParam is null");
		if (coordinatesParam == null) throw new NullPointerException("coordinatesParam is null");
		if (maxCoordinatesParam.length != coordinatesParam.length) throw new Exception("array length mismatch");
		maxCoordinates = maxCoordinatesParam;
		coordinates = new int[coordinatesParam.length];
		for(int i=0; i < coordinatesParam.length; i++) {
			coordinates[i] = coordinatesParam[i];
		}
	}
	
	/**
	 * Getter for parent element.
	 * @return Element parent.
	 */
	public Element getParent() {
		return parent;
	}
	/**
	 * Setter for parent element.
	 * @param parent
	 */
	public void setParent(Element parent) {
		this.parent = parent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(coordinates);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Element other = (Element) obj;
		if (!Arrays.equals(coordinates, other.coordinates))
			return false;
		return true;
	}
	
	/**
	 * Check if one of the coordinates equals the value.
	 * @param value the passed value to check.
	 * @return boolean indicating if the value is amongst the coordinates.
	 */
	public boolean containsValue(int value) {
		boolean b = false;
		for(int i=0; i<coordinates.length; i++) {
			if (coordinates[i] == value) {
				b = true;break;
			}
		}
		return b;
	}
	
	/**
	 * Getter for the indicator of all operations executed.
	 * @return boolean.
	 */
	public boolean getIndAllOperationsExecuted() {
		return indAllOperationsExecuted;
	}
	/**
	 * Setter for the indicator of all operations executed.
	 */
	public void setIndAllOperationsExecuted(boolean indAllOperationsExecuted) {
		this.indAllOperationsExecuted = indAllOperationsExecuted;
	}
	
	/**
	 * Getter for the operation that created this element.
	 * @return String
	 */
	public String getFromOperation() {
		return fromOperation;
	}
	/**
	 * Setter for the operation that created this element.
	 * @param fromOperation
	 */
	public void setFromOperation(String fromOperation) {
		this.fromOperation = fromOperation;
	}
	/**
	 * 
	 * @return 
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		String op = null;
		if (fromOperation != null)
			op = String.format("%1$-20s", fromOperation);
		else
			op = String.format("%1$-20s", "Initial");
		sb.append(op).append(" : ");
		
		sb.append(coordinates[0]);
		for (int i = 1; i < coordinates.length; i++) {
			sb.append(",").append(coordinates[i]);
		}
		return sb.toString();
	}

	/**
	 * Method to empty the bucket specified by the position.
	 * @param pos the position of the bucket to empty.
	 * @return Element resulting from the operation.
	 * @throws Exception when the operation fails.
	 */
	@Override
	public Element empty(int pos) throws Exception{
		if (this.coordinates[pos] == 0) {
			return this;
		} else {
			int[] coordinatesOp = Arrays.copyOf(coordinates, coordinates.length);
			coordinatesOp[pos] = 0;
			Element elementOp = new Element(this.maxCoordinates, coordinatesOp);
			elementOp.setFromOperation(OpEmpty.name.replace("{0}", String.valueOf(pos)));
			elementOp.setParent(this);
			return elementOp;
		}
	}

	/**
	 * Method to fill the bucket specified by the position.
	 * @param pos the position of the bucket to fill.
	 * @return Element resulting from the operation.
	 * @throws Exception when the operation fails.
	 */
	@Override
	public Element fill(int pos) throws Exception{
		if (coordinates[pos] == maxCoordinates[pos]) {
			return this;
		} else {
			int[] coordinatesOp = Arrays.copyOf(coordinates, coordinates.length);
			coordinatesOp[pos] = maxCoordinates[pos];
			Element elementOp = new Element(maxCoordinates, coordinatesOp);
			elementOp.setFromOperation(OpFill.name.replace("{0}", String.valueOf(pos)));
			elementOp.setParent(this);
			return elementOp;
		}
	}

	/**
	 * Method to vert the content of one bucket into another.
	 * @param posInt the position of the initial bucket.
	 * @param posFinal the position of the final bucket.
	 * @return Element resulting from the operation.
	 * @throws Exception when the operation fails.
	 */
	@Override
	public Element vert(int posInt, int posFinal) throws Exception{
		LOGGER.finer("Entering vert: posInt=" + posInt + " posFinal=" + posFinal);
		if (posInt == posFinal || coordinates[posInt] == 0) {
			return this;
		} else {
			int[] coordinatesOp = Arrays.copyOf(coordinates, coordinates.length);
			
			int sumFinal = coordinates[posInt] + coordinates[posFinal];
			
			if (sumFinal <= maxCoordinates[posFinal]) {
				coordinatesOp[posInt] = 0;
				coordinatesOp[posFinal] = sumFinal;
			} else {
				coordinatesOp[posInt] = sumFinal - maxCoordinates[posFinal];
				coordinatesOp[posFinal] = maxCoordinates[posFinal];
			}
			Element elementOp = new Element(maxCoordinates, coordinatesOp);
			elementOp.setFromOperation(OpVert.name
				.replace("{0}", String.valueOf(posInt))
				.replace("{1}", String.valueOf(posFinal))
			);
			
			elementOp.setParent(this);
			return elementOp;
		}
	}
	
}

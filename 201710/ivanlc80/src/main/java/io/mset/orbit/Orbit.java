package io.mset.orbit;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Orbit {
	private static Logger LOGGER =  Logger.getLogger(Orbit.class.getName());
	/**
	 * List of elements within the orbit.
	 */
	private List<Element> elements;
	/**
	 * Constructor.
	 */
	public Orbit() {
		elements = new ArrayList<Element>();
	}
	/**
	 * Adds an element to the orbit if not con
	 * @param element
	 * @return
	 */
	public synchronized boolean addElement(Element element) {
		boolean bAdd = !elements.contains(element);
		if (bAdd) {
			LOGGER.finer("Adding " + element.toString());
			return elements.add(element);
		} else
			return false;
	}
	
	public List<Element> getElementsForOperation() {
		List<Element> list = elements.stream()
				.filter(x -> !x.getIndAllOperationsExecuted())
				.collect(Collectors.toList());
		return list;
	}
	
	public boolean containsElement(Element element) {
		return elements.contains(element);
	}
}

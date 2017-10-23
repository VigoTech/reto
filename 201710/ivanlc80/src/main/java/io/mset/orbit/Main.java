package io.mset.orbit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import io.mset.orbit.util.Constants;
/**
 * Main class
 * @author Dr. Ivan Le Creurer
 * @email ivan.j.le.creurer@gmail.com
 */
public class Main {
	private static Logger LOGGER =  Logger.getLogger(Main.class.getName());
	
	public static void main(String[] args) {
		LOGGER.info(Constants.WELCOME_MSG);
		
		if (args.length != 2) {
			LOGGER.warning(Constants.CHECK_ARGS_MSG);
			System.exit(-1);
		}
		
		String [] arSizeAsString = args[0].split(",");
		if (arSizeAsString.length < 2) {
			LOGGER.warning(Constants.CHECK_ARGS_MSG);
			System.exit(-1);
		}
		
		LOGGER.info("Parameters: \n" + 
				"\tbucket sizes   : " + args[0] + "\n" +
				"\trequested value: " + args[1] + "\n"
				);
		
		int requestedValue = 0;
		int [] arSize = new int[arSizeAsString.length];
		try {
			requestedValue = Integer.parseInt(args[1]);
			if (requestedValue <= 0)
				doExit(Constants.CHECK_ARGS_MSG);
			
			boolean requestedValueTooLarge = true;
			for(int i=0; i < arSize.length; i++) {
				arSize[i] = Integer.parseInt(arSizeAsString[i]);
				
				if (arSize[i] <= 0)
					doExit(Constants.CHECK_ARGS_MSG);
				
				if (requestedValueTooLarge && arSize[i] >= requestedValue) {
					requestedValueTooLarge = false;
				}
			}
			
			if (requestedValueTooLarge) {
				doExit(Constants.REQUESTED_VALUE_TOO_LARGE_MSG);
			}
		} catch (Exception e) {
			doExit(Constants.CHECK_ARGS_MSG);
		}
		
		try {
			Main prog = new Main(arSize, requestedValue);
			Element matchElement = prog.search();
			
			if (matchElement != null) {
				LOGGER.info("Found match: " + matchElement.toString());
				
				StringBuffer sbPath = new StringBuffer();
				sbPath.append("\nOperations path:\n");
				List<Element> listPathElements = prog.getPath(matchElement);
				for (Element element:listPathElements) {
					sbPath.append(element.toString()).append("\n");
				}
				
				LOGGER.info(sbPath.toString());
			} else {
				LOGGER.info("No match found in orbit.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.severe("Failed to obtain the requested value");
		}
	}
	
	public static void doExit(String msg) {
		LOGGER.warning(msg);
		System.exit(-1);
	}
	
	/**
	 * Array of bucket sizes.
	 */
	private int[] arBucketSize;
	/**
	 * The requested value.
	 */
	private int requestedValue;
	/**
	 * The orbit of the initial element
	 */
	private Orbit orbit;
	/**
	 * Constructor
	 * @param arBucketSizeParam Array of bucket sizes.
	 * @param requestedValueParam the requested value.
	 */
	public Main(int[] arBucketSizeParam, int requestedValueParam) throws Exception {
		this.arBucketSize = arBucketSizeParam;
		this.requestedValue = requestedValueParam;
		
		orbit = new Orbit();
		int [] origin = new int[arBucketSize.length]; 
		Element initialElement = new Element(arBucketSize,origin);
		
		orbit.addElement(initialElement);
	}
	
	/**
	 * Method to search for match.
	 * @return Element matching criteria.
	 * @throws Exception
	 */
	public Element search() throws Exception{
		LOGGER.fine("Entering launch...");
		List<Element> tmpList =  orbit.getElementsForOperation();
		
		LOGGER.finer("Starting with " + tmpList.size() + " elements.");
		do {
			for (Element element:tmpList) {
				LOGGER.finer("Processing " + element.toString());
				
				//Exec operation empty for each position
				for(int pos = 0; pos < arBucketSize.length; pos++) {
					Element elementE = element.empty(pos);
					LOGGER.finer("elementE " + elementE.toString());
					if (elementE.containsValue(this.requestedValue)) {
						return elementE;
					}
					if (!elementE.equals(element)) {
						orbit.addElement(elementE);
					}
				}
				
				//Exec operation fill for each position
				for(int pos = 0; pos < arBucketSize.length; pos++) {
					Element elementF = element.fill(pos);
					LOGGER.finer("elementF " + elementF.toString());
					if (elementF.containsValue(this.requestedValue)) {
						return elementF;
					}
					if (!elementF.equals(element)) {
						orbit.addElement(elementF);
					}
				}
				
				//Exec operation vert for each par of positions
				for(int posInt = 0; posInt < arBucketSize.length; posInt++) {
					for(int posFinal = 0; posFinal < arBucketSize.length; posFinal++) {
						//Verting content to itself is no operation.
						if (posInt == posFinal) continue;
						
						Element elementNM = element.vert(posInt, posFinal);
						LOGGER.finer("elementNM " + elementNM.toString());
						if (elementNM.containsValue(this.requestedValue)) {
							return elementNM;
						}
						if (!elementNM.equals(element)) {
							orbit.addElement(elementNM);
						}
					}
				}
				
				//Mark element for ops executed
				element.setIndAllOperationsExecuted(Boolean.TRUE);
				
			}
			
			tmpList = orbit.getElementsForOperation();
			LOGGER.finer("tmpList has " + tmpList.size() + " elements.");
		}
		while(tmpList.size() > 0);
		
		LOGGER.fine("Done");
		return null;
	}
	/**
	 * Get the path of the search to the element
	 * @param element the matched element.
	 * @return List<Element> list representing the path of the initial element in the orbit.
	 */
	public List<Element> getPath(Element element) {
		LOGGER.fine("Entering getPath...");
		List<Element> list = new ArrayList<Element>();
		Element currentElement = element;
		Element parentElement = null;
		
		list.add(currentElement);
		while ((parentElement=currentElement.getParent()) != null) {
			currentElement = parentElement;
			list.add(currentElement);
		}
		Collections.reverse(list);
		return list;
	}
}

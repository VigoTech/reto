/*
 *
 * Copyright 2017 @ Santiago Rodríguez .
 *
 * License: MIT
 *
 * Project: vigoreto-201710
 *
 */
package es.rocasan.vigojug.reto201710;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javafx.stage.StageStyle;

class MaxTicksReachException extends Exception {
	
}

/**
 * Represents the number of attempts to explore a new possible state. It takes 
 * care of throw the MaxTicksReachException when number of ticks reach the limit.
 * @author Santiago Rodríguez 
 */
class Ticks {
	
	/**
	 * Limit of ticks before throw the exception.
	 */
	private Integer limit;
	
	/**
	 * Store the number of ticks made
	 */
	private Integer count;
	
	public Ticks(Integer limit) {
		this.limit = limit;
		this.count = 0;
	}
	
	/**
	 * Write down a tick and return the number of remaining ticks. If requested
	 * tick is over the limit, thow a MaxTicksReachException.
	 * @return
	 * @throws MaxTicksReachException 
	 */
	public Integer tick() throws MaxTicksReachException {
		count ++;
		if(count > limit) {
			throw new MaxTicksReachException();
		}
		return limit - count;
	}
}

/**
 * Simple class to hold a state information to find out a solution.
 * @author Santiago Rodríguez 
 */
class State {
	public String name;
	public String operation;
	public String fromState;
	public Integer recursionLevel;
	public Boolean visited;
	public State(String name, String operation, String fromState, Integer recursionLevel) {
		this.name = name;
		this.operation = operation;
		this.fromState = fromState;
		this.recursionLevel = recursionLevel;
		this.visited = false;
	}
}


/**
 * Holds the jugs and target, and can init a research for a solution
 * @author Santiago Rodríguez 
 */
public class Stage {
	
	private static final int FILL = 0;
	private static final int EMPTY = 1;
	private static final int DUMP = 2;
	
	/**
	 * Quantity of units to be defined as target. Almost one jug must have that 
	 * or greater capacity
	 */
	private Integer target;
	
	/**
	 * List of jugs.
	 */
	private Jug[] jugs;
	
	/**
	 * State while solve. The key String is the comma separated amount of jugs, 
	 * that represents the state. The value is a array with the previous state,
	 * the instruction to reach the key state and the iteration.
	 */
	private HashMap<String,State> states = new HashMap();
	
	/**
	 * Max number of instructions after declare the solve as unresolved.
	 */
	private Ticks maxTicks;
	
	/**
	 * Indicates the recursion level while solving.
	 */
	private Integer recursionLevel;
	
	/**
	 * Store the state key in states table tha represent the best solution found.
	 */
	private String bestStateSolution = null;
	
	/**
	 * Indicates if the debug info is printed to the CLI.
	 */
	public Boolean debug = false;
	
	private ArrayList<String> notVisitedStates;
	
	/**
	 * Constructor that receibes the target to reach, and the list of jugs. The 
	 * default limit of ticks are 1000.
	 * @param target The amount that a jug should achieve to declare the stage solved
	 * @param jugs A array of Jugs
	 */
	public Stage(Integer target, Jug[] jugs) {
		this.notVisitedStates = new ArrayList<>();
		this.target = target;
		this.jugs = jugs;
		ensureTargetAndJugCapacities();
		this.maxTicks = new Ticks(1000);
	}
	
	/**
	 * Constructor that receibes the target to reach, the list of jugs and a 
	 * limit of ticks.
	 * @param target The amount that a jug should achieve to declare the stage solved
	 * @param jugs  A array of Jugs
	 * @param maxTicks The limit of attempts of find out all posible states.
	 */
	public Stage(Integer target, Jug[] jugs, Integer maxTicks) {
		this.notVisitedStates = new ArrayList<>();
		this.target = target;
		this.jugs = jugs;
		ensureTargetAndJugCapacities();
		this.maxTicks = new Ticks(maxTicks);
	}
	
	/**
	 * Check that there ara one or more jug in jugs, that has enough capcity
	 * for the target
	 */
	private void ensureTargetAndJugCapacities() {
		if(!(target > 0)) {
			throw new IllegalArgumentException("Target ("+target+") must be greater than 0");
		}
		for(Jug jug : jugs) {
			if(jug.getCapacity() >= target) {
				// return back when find one jug
				return;
			}
		}
		// if no jug with enough capacity if found, throw a exception
		throw new IllegalArgumentException("Target ("+target+") is too much bigger for the capacity of the jugs");
	}
	
	/**
	 * Set the state with the amounts
	 * @param amounts 
	 */
	private void setState(Integer[] amounts) {
		for(int a=0; a<amounts.length; a++) {
			jugs[a] = new Jug(jugs[a].getCapacity(), amounts[a]);
		}
	}
	
	/**
	 * Set the state with the amouns in comma-separted string format
	 * @param amounts 
	 */
	private void setState(String amounts) {
		String[] separated = amounts.split(",");
		Integer[] numbers = new Integer[ separated.length ];
		for(int n=0; n<separated.length; n++) {
			numbers[n] = new Integer(separated[n]);
		}
		setState(numbers);
	}
	
	/**
	 * Transform the actual state (the amount of each jug in order of declaration)
	 * into the comma-separated format of the amounts 
	 * @return 
	 */
	private String actualState() {
		String state = "";
			for(int j=0; j<jugs.length; j++) {
			state += jugs[j].getAmount();
				if((j+1) < jugs.length) {
				state += ",";
				}
			}
		return state;
	}
	
	/**
	 * Add the new state to the states used to find a solution. The state has to
	 * be a new state or finded with a better recursion level
	 * @param newState
	 * @param transition
	 * @param from
	 * @return 
	 */
	private Boolean addNewState(String newState, String transition, String from) {
		if(!states.containsKey(newState)) {
			states.put(newState, new State(newState, transition, from, recursionLevel ));
			notVisitedStates.add(newState);
			// at this point new state was stablished. Check if it is a valid solution
			if(checkIsASolution()) {
				print("SOLUTION!");
				// if the new state is a solution mark it as a solution, as it is the first found
				if(bestStateSolution==null) {
					bestStateSolution = newState;
				}
			}
		}
		return false;
	}
	
	/**
	 * Check if the actual state is a valid solution (one of the jugs has 
	 * the targeted amount) and store it as the best solution. It's the best, because
	 * it is granted during addNewState method, becouse only replace states if 
	 * the newer one has less recursion than the older.
	 * @return Boolean
	 */
	private Boolean checkIsASolution() {
		for(Jug jug : jugs) {
			if(jug.getAmount() == target) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Run the operation and after that, save the new state if necessary. In this
	 * case, explore a new bunch of instructions, calling recursively to findSolution()
	 * 
	 * @param type The operation's type: FILL, EMPTY or DUMP
	 * @param j1 The main jug that runs the operation
	 * @param j2 For the DUMP operation, the other jug, where to dump into.
	 * @throws MaxTicksReachException 
	 */
	private void operate(int type, Integer j1, Integer j2) throws MaxTicksReachException {
		maxTicks.tick();
		String initialState = actualState();
		String operation = "";
		
		switch(type) {
			case FILL:
				jugs[j1].fill();
				operation = "Fill "+j1;
				break;
			case EMPTY:
				jugs[j1].empty();
				operation = "Empty "+j1;
				break;
			case DUMP:
				jugs[j1].dumpInto(jugs[j2]);
				operation = "Dump "+j1+" into "+j2;
				break;
		}
		
		addNewState(actualState(), operation, initialState);
		
		setState(initialState);
	}
	
	/**
	 * Show the states table for debugging.
	 * @return 
	 */
	private void showStates() {
		if(debug) {
			String out = "";
			for(String stateName : states.keySet()) {
				State state = states.get(stateName);
				out += "\t"+state.name+" [ "+state.fromState+" > "+state.operation+" / "+state.recursionLevel+" "+(state.visited?"\u2714":"")+"]\n";
			}
			print("Full states table:\n"+out);
		}
	}
	
	/**
	 * Controls the recursion process. Run all operations for the first n-remaining 
	 * unvisited states (new recursion level).
	 * @param n The number of states to operate
	 * @throws MaxTicksReachException 
	 */
	private void findSolution(int n) throws MaxTicksReachException {
		recursionLevel++;
		
		// if not unvisited states remain, return
		if(notVisitedStates.size() == 0) {
			return;
		}
		
		// loop the n-first not visited states
		for( ; n > 0 ; n--) {
			setState(notVisitedStates.remove(0));

			String actualState = actualState();

			print("Exploring "+actualState);

			states.get(actualState).visited = true;

			// explore new posible states, operate over the actual state
			for(int j1=0; j1<jugs.length; j1++) {
				for(int j2=0; j2<jugs.length; j2++) {
					if(j1 != j2) {
						operate(DUMP,j1,j2);
					}
				}
				operate(FILL,j1,null);
				operate(EMPTY,j1,null);
				
				// if solution is detected, end
				if(bestStateSolution != null) {
					return;
				}
			}
		}
		
		findSolution(notVisitedStates.size());
		
		recursionLevel--;
	}
	
	/**
	 * Init properly the recursive findSolution process.
	 * @throws MaxTicksReachException 
	 */
	private void initFindSolution() throws MaxTicksReachException {
		findSolution(notVisitedStates.size());
	}
	
	/**
	 * Build the rest of the solution found to be showed in CLI. The process 
	 * is recursive, building the operations needed from the indicated state 
	 * until initial.
	 * @param fromState 
	 * @return 
	 */
	private String buildSolution(String fromState) {
		String solution = "";
		State state = states.get(fromState);
		if(state.fromState != "") {	// if not initial state (from != "")
			solution = buildSolution(state.fromState);
		}
		return solution + "\n" + state.operation+" ("+fromState+")";
	}
	
	/**
	 * Build the solution found to be showed in CLI.
	 * @param fromState 
	 * @return 
	 */
	private String buildSolution() {
		return buildSolution(bestStateSolution);
	}
	
	/**
	 * Util method to print debug formated information to output.
	 * @param msg 
	 */
	private void print(String msg) {
		if(debug) {
			String pre = String.join("", Collections.nCopies(recursionLevel, "  "));
			System.out.println(pre+msg);
		}
	}
	
	/**
	 * Init the process to create a list of instructions (fill x, empty x, 
	 * fill x into y) to achieve at least one jug with exactly target quantity 
	 * as its amount.
	 * @return String with the solution statements, if the solution could be 
	 * found within the number of attempts.
	 */
	public String solve() {
		String solution = "";
		states.clear();
		recursionLevel = 0;
		try {
			// init states with the actual state
			addNewState(actualState(), "Start", "");
			initFindSolution();
		}
		catch(MaxTicksReachException e) {
			solution = "Not all posibilities explored, too much intents.\n\n";
		}
		catch(StackOverflowError e) {
			solution = "Not all posibilities explored, recursion exceeded.\n\n";
		}
		if(bestStateSolution!=null) {
			solution += "Solution found:" + buildSolution();
		}
		else {
			solution += "There is no solution.";
		}
		showStates();
		return solution;
	}
}

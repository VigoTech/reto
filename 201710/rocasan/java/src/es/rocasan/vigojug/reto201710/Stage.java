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

import java.util.Collections;
import java.util.HashMap;

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
	public State(String name, String operation, String fromState, Integer recursionLevel) {
		this.name = name;
		this.operation = operation;
		this.fromState = fromState;
		this.recursionLevel = recursionLevel;
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
	
	/**
	 * Constructor that receibes the target to reach, and the list of jugs. The 
	 * default limit of ticks are 1000.
	 * @param target The amount that a jug should achieve to declare the stage solved
	 * @param jugs A array of Jugs
	 */
	public Stage(Integer target, Jug[] jugs) {
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
		if(states.containsKey(newState)) {
			// if newState exists but was included in other recursion branch more deeper, overwrite and continue
			// In other words, the actual recursion level is lower than the state saved previously
			State state = states.get(newState);
			if(recursionLevel < state.recursionLevel ) {
				states.put(newState, new State(newState, transition, from, recursionLevel));
			}
			else {
				return false;
			}
		}
		else {
			states.put(newState, new State(newState, transition, from, recursionLevel ));
		}
		// at this point new state was stablished. Check if it is a valid solution
		if(checkIsASolution()) {
			print("SOLUTION!");
			// if the new state is a solution mark it as a solution if it is the first
			// found or if it has a lower recursion level
			if(bestStateSolution==null || recursionLevel < states.get(bestStateSolution).recursionLevel) {
				bestStateSolution = actualState();
			}
		}
		return true;
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
		
		print("Exploring "+initialState+" / "+operation+" / "+actualState());
		
		if(addNewState(actualState(), operation, initialState)) {
			findSolution();	
		}
		
		setState(initialState);
	}
	
	/**
	 * Show the states table for debugging.
	 * @return 
	 */
	private String showStates() {
		String out = "";
		for(String stateName : states.keySet()) {
			State state = states.get(stateName);
			out += "\t"+state.name+" [ "+state.fromState+" "+state.operation+" "+state.recursionLevel+" ]\n";
		}
		return out;
	}
	
	/**
	 * Controls the recursion process. Run all operations with the current state.
	 * @throws MaxTicksReachException 
	 */
	private void findSolution() throws MaxTicksReachException {
		recursionLevel++;
		for(int j1=0; j1<jugs.length; j1++) {
			for(int j2=0; j2<jugs.length; j2++) {
				if(j1 != j2) {
					operate(DUMP,j1,j2);
				}
			}
			operate(FILL,j1,null);
			operate(EMPTY,j1,null);
		}
		recursionLevel--;
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
			findSolution();
		}
		catch(MaxTicksReachException e) {
			solution = "Not all posibilities explored, too much intents.\n\n";
		}
		if(bestStateSolution!=null) {
			solution += "Solution found:" + buildSolution();
		}
		else {
			solution += "Solution not found";
		}
		print("Full states table:\n"+showStates());
		return solution;
	}
}

/*
 *
 * Copyright 2017 @ Santiago Rodríguez <rocasan@gmail.com>.
 *
 * License: MIT
 *
 * Project: vigoreto-201710
 *
 */
package es.rocasan.vigojug.reto201710;

class MaxTicksReachException extends Exception {
	
}

class Ticks {
	private Integer amount;
	public Ticks(Integer amount) {
		this.amount = amount;
	}
	public Integer tick() throws MaxTicksReachException {
		amount --;
		if(!(amount > 0)) {
			throw new MaxTicksReachException();
		}
		return amount;
	}
}

/**
 * Holds the mugs and target, and can init a research for a solution
 * @author Santiago Rodríguez <rocasan@gmail.com>
 */
public class Stage {
	private Integer target;
	private Mug[] mugs;
	private Ticks maxTicks;
	public Stage(Integer target, Mug[] mugs) {
		this.target = target;
		this.mugs = mugs;
		this.maxTicks = new Ticks(100);
	}
	public Stage(Integer target, Mug[] mugs, Integer maxTicks) {
		this.target = target;
		this.mugs = mugs;
		this.maxTicks = new Ticks(maxTicks);
	}
	public String solve() {
		String solution = "";
		Boolean solved = false;
		try {
			do {
				maxTicks.tick();
			} while(!solved);
		}
		catch(MaxTicksReachException e) {
			solution = "Too much intents. Solution not found.";
		}
		return solution;
	}
}

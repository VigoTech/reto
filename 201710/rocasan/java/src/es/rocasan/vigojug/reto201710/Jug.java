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

import java.lang.Math;

/**
 * Represents the recipient and its capacity
 * @author Santiago Rodríguez 
 */
public class Jug {
	
	/**
	 * total capacity of the jug. Should be mpositive.
	 */
	private Integer capacity;
	
	/**
	 * number of units that jug contains in each moment. Shuld be positive and 
	 * minor or equal than capacity
	 */
	private Integer amount;
	
	/**
	 * Create a empty jug with certain capacity
	 * @param capacity The capacity of the new jug
	 */
	public Jug(Integer capacity) {
		ensureValidCapacity(capacity);
		this.capacity = capacity;
		setAmount(0);
	}
	
	/**
	 * Create a jug with certain capacity, with a initial filled amount
	 * @param capacity The capacity of the new jug
	 * @param amount A initial value for the amount
	 */
	public Jug(Integer capacity, Integer amount) {
		ensureValidCapacity(capacity);
		this.capacity = capacity;
		setAmount(amount);
	}
	
	private void ensureValidCapacity(Integer capacity) {
		if(!(capacity > 0)) {
			throw new IllegalArgumentException("The capacity of a jug ("+capacity+") must be greater than 0");
		}
	}
	
	/**
	 * Set the amount
	 * @param amount The amount to set. Should be between 0 and jug's capacity (both included)
	 */
	private void setAmount(Integer amount){
		if(amount < 0) {
			throw new IllegalArgumentException("The amount of a jug ("+amount+") must be greater than 0");
		}
		if(amount > capacity) {
			throw new IllegalArgumentException("The amount of a jug ("+amount+") must be less or equal than its capacity ("+capacity+")");
		}
		this.amount = amount;
	}
	
	/**
	 * Return the amount
	 * @return The amount
	 */
	public Integer getAmount() {
		return amount;
	}
	
	/**
	 * Return the capacity of the jug
	 * @return The capacity
	 */
	public Integer getCapacity() {
		return capacity;
	}
	
	/**
	 * Fill the amount up to the capacity
	 */
	public void fill() {
		setAmount(capacity);
	}
	
	/**
	 * Set amout to 0
	 */
	public void empty() {
		setAmount(0);
	}
	
	/**
	 * Return the number of units from amount up to capacity
	 * @return 
	 */
	private Integer rest() {
		return capacity - amount;
	}
	
	/**
	 * Empty partially or totally the actual amount, depends on the other jug, 
	 * filling that quantity in the other jug
	 * @param jug Other jug where to dump into.
	 */
	public void dumpInto(Jug jug) {
		/**
		 * The quantity to fill. It will be the rest of the other jug or the 
		 * actual amount, if it is smaller.
		 */
		Integer added = Math.min(jug.rest(), getAmount());
		
		// set the amount of the other jug to indicate the filling
		jug.setAmount(jug.getAmount() + added);
		
		// set the actual amount of this jug to indicate the dump
		setAmount(amount - added);
	}
}

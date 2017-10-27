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

/**
 * Represents the recipient and its capacity
 * @author Santiago Rodríguez <rocasan@gmail.com>
 */
public class Mug {
	private Integer capacity;
	private Integer amount;
	public Mug(Integer capacity) {
		assert(capacity > 0);
		this.capacity = capacity;
		setAmount(0);
	}
	private void setAmount(Integer amount){
		assert (0 <= amount);
		assert (amount <= capacity);
		this.amount = amount;
	}
	public Integer getAmount() {
		return amount;
	}
}

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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit Test for Jug Class
 * @author Santiago Rodríguez 
 */
public class JugTest {
	
    @Test
    public void createJugWithPositiveCapacityTest() {
		Jug jug = new Jug(2);
		assertEquals(jug.getCapacity(), new Integer(2));
    }
	
	@Test (expected = IllegalArgumentException.class)
    public void createJugWithNoCapacityShouldThrowExceptionTest() {
		Jug jug = new Jug(0);
    }
	
	@Test (expected = IllegalArgumentException.class)
    public void createJugWithNegativeCapacityShouldThrowExceptionTest() {
		Jug jug = new Jug(-2);
    }
	
	@Test (expected = IllegalArgumentException.class)
    public void createJugWithInitalNegativeAmountShouldThrowExceptionTest() {
		Jug jug = new Jug(5,-1);
    }
	
	@Test (expected = IllegalArgumentException.class)
    public void createJugWithInitalAmountGreaterThanCapacityShouldThrowExceptionTest() {
		Jug jug = new Jug(5,10);
    }
	
	@Test
    public void createJugWithInitalAmountBetweenZeroAndCapacityTest() {
		Jug jug1 = new Jug(5,0);
		Jug jug2 = new Jug(5,5);
		Jug jug3 = new Jug(5,3);
		assertEquals(jug1.getAmount(), new Integer(0));
		assertEquals(jug2.getAmount(), new Integer(5));
		assertEquals(jug2.getAmount(), jug2.getCapacity());
		assertEquals(jug3.getAmount(), new Integer(3));
    }
	
}

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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit test for Stage class
 * @author Santiago Rodríguez
 */
public class StageTest {
	
    @Test
    public void createStageWithTargetBelowJugsCapacitiesShouldNotThrowExceptionTest() {
		new Stage(6, new Jug[] { new Jug(4), new Jug(7) });
		new Stage(7, new Jug[] { new Jug(4), new Jug(7) });
    }
	
	@Test (expected = IllegalArgumentException.class)
    public void createStageWithNegativeTargetShouldThrowExceptionTest() {
		new Stage(-1, new Jug[] { new Jug(4), new Jug(7) });
    }
	
	@Test (expected = IllegalArgumentException.class)
    public void createStageWithZeroTargetShouldThrowExceptionTest() {
		new Stage(0, new Jug[] { new Jug(4), new Jug(7) });
    }

	@Test (expected = IllegalArgumentException.class)
    public void createStageWithTargetGreaterThanJugsCapacitiesShouldThrowExceptionTest() {
		new Stage(8, new Jug[] { new Jug(4), new Jug(7) });
    }

	
}

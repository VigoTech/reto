package es.rocasan.vigojug.reto201710;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Integration test class
 * @author Santiago Rodríguez
 */
public class MainTest {
	
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private App app;

	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
		app = new App();
	}

	@After
	public void cleanUpStreams() {
		System.setOut(null);
	}
	
    @Test
    public void TooMuchaIntentsUnsolvedBasicTest() {
		app.main(new String[]{"6","4","7","-50"});
		assertEquals("Not all posibilities explored, too much intents.\n" +
"\n" +
"There is no solution.\n", outContent.toString());
    }
	
    @Test
    public void SolvedBasicTest() {
		app.main(new String[]{"6","4","7","-1000","-v"});
		assertTrue(outContent.toString().contains("Exploring 3,7\n" +
"            SOLUTION!\n"));
		assertTrue(outContent.toString().endsWith("Solution found:\n" +
"Start (0,0)\n" +
"Fill 1 (0,7)\n" +
"Dump 1 into 0 (4,3)\n" +
"Empty 0 (0,3)\n" +
"Dump 1 into 0 (3,0)\n" +
"Fill 1 (3,7)\n" +
"Dump 1 into 0 (4,6)\n"));
    }
	
    @Test
    public void SolvedThreeJugsTest() {
		app.main(new String[]{"6","4","7","9"});
		assertTrue(outContent.toString().endsWith("Solution found:\n" +
"Start (0,0,0)\n" +
"Fill 0 (4,0,0)\n" +
"Dump 0 into 1 (0,4,0)\n" +
"Fill 2 (0,4,9)\n" +
"Dump 2 into 1 (0,7,6)\n"));
    }
	
    @Test
    public void NotSolvedSimpleTest() {
		app.main(new String[]{"6","5","10"});
		assertEquals("There is no solution.\n", outContent.toString());
    }
	
    @Test
    public void NotSolvedSameCapacityTest() {
		app.main(new String[]{"6","7","7"});
		assertEquals("There is no solution.\n", outContent.toString());
    }
	
    @Test
    public void NotEnaughParamsTest() {
		app.main(new String[]{});
		assertTrue(outContent.toString().startsWith("Not enaugh params"));
    }
	
    @Test
    public void RecursionExceededTest() {
		app.main(new String[]{"10067", "2", "3", "100067", "-10000000"});
		assertEquals("Not all posibilities explored, recursion exceeded.\n" +
"\n" +
"There is no solution.\n", outContent.toString());
    }
	
    @Test
    public void BadParamsTest() {
		app.main(new String[]{"6", "aText", "3"});
		assertTrue(outContent.toString().startsWith("Parameter 2 'aText' is not valid."));
    }
	
	
}

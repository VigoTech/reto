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
    public void PartialSolvedBasicTest() {
		app.main(new String[]{"6","4","7","-100"});
		assertEquals("Not all posibilities explored, too much intents.\n" +
"\n" +
"Solution found:\n" +
"Start (0,0)\n" +
"Fill 0 (4,0)\n" +
"Dump 0 into 1 (0,4)\n" +
"Fill 0 (4,4)\n" +
"Dump 0 into 1 (1,7)\n" +
"Fill 0 (4,7)\n" +
"Empty 0 (0,7)\n" +
"Dump 1 into 0 (4,3)\n" +
"Empty 0 (0,3)\n" +
"Dump 1 into 0 (3,0)\n" +
"Fill 1 (3,7)\n" +
"Dump 1 into 0 (4,6)\n", outContent.toString());
    }
	
    @Test
    public void SolvedBasicTest() {
		app.main(new String[]{"6","4","7","-1000","-v"});
		assertTrue(outContent.toString().contains("Exploring 3,7 / Dump 1 into 0 / 4,6\n" +
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
    public void NotSolvedBasicTest() {
		app.main(new String[]{"6","7","7"});
		assertEquals("Solution not found\n", outContent.toString());
    }
	
    @Test
    public void NotEnaughParamsTest() {
		app.main(new String[]{});
		assertTrue(outContent.toString().startsWith("Not enaugh params"));
    }
	
}

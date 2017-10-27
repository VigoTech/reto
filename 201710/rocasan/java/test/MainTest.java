import static org.junit.Assert.assertEquals;

import org.junit.Test;

import es.rocasan.vigojug.reto201710.*;

public class MainTest {
	
    @Test
    public void BasicTest() {
		Mug[] mugs = { new Mug(4), new Mug(7) };
        Stage stage = new Stage(6, mugs);
		String instructions = stage.solve();
		assertEquals(instructions, "Too much intents. Solution not found.");
    }

}

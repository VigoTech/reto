import es.kix2902._201706.java.Main;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    @Test
    void testProcessFile() {
        assertEquals("50 10 5", Main.processFile("input.txt"));

        // Bonus 1
        assertEquals("50 50 50 20 10 5 2", Main.processFile("bonus1-1.txt"));
        assertEquals("50 50 50 25 10 1 1", Main.processFile("bonus1-2.txt"));
        assertEquals("100 50 25 10 1 1", Main.processFile("bonus1-3.txt"));

        // Bonus 2
        assertEquals("50 50 20 20 20 20 5 2", Main.processFile("bonus2-1.txt"));
        assertEquals("50 50 50 25 5 5 1 1", Main.processFile("bonus2-2.txt"));
        assertEquals("3 3", Main.processFile("bonus2-3.txt"));
    }

}

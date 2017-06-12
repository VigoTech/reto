import org.junit.Assert.assertEquals
import org.junit.Test

class MainTest {

    @Test
    fun processFile() {
        assertEquals("50 10 5", processFile("input.txt"))

        // Bonus 1
        assertEquals("50 50 50 20 10 5 2", processFile("bonus1-1.txt"))
        assertEquals("50 50 50 25 10 1 1", processFile("bonus1-2.txt"))
        assertEquals("100 50 25 10 1 1", processFile("bonus1-3.txt"))

        // Bonus 2
        assertEquals("50 50 20 20 20 20 5 2", processFile("bonus2-1.txt"))
        assertEquals("50 50 50 25 5 5 1 1", processFile("bonus2-2.txt"))
        assertEquals("3 3", processFile("bonus2-3.txt")) // Not passing
    }

}
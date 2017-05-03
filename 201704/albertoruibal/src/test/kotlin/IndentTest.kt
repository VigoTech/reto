
import org.junit.Test
import java.io.StringWriter
import java.lang.Exception
import kotlin.test.assertEquals

internal class IndentTest {
    val indent = Indent()

    @Test
    fun test1() {
        val sw = StringWriter()
        val bsw = sw.buffered()
        indent.indent(this.javaClass.getResourceAsStream("/test1.bas").bufferedReader(), bsw)
        val expectedResult = this.javaClass.getResourceAsStream("/test1.bas.out").bufferedReader().readText()
        assertEquals(expectedResult, sw.toString())
    }

    @Test(expected=Exception::class)
    fun bonus1() {
        indent.indent(this.javaClass.getResourceAsStream("/bonus1.bas").bufferedReader(), StringWriter().buffered())
    }

    @Test(expected=Exception::class)
    fun bonus2() {
        indent.indent(this.javaClass.getResourceAsStream("/bonus2.bas").bufferedReader(), StringWriter().buffered())
    }

    @Test(expected=Exception::class)
    fun bonus3() {
        indent.indent(this.javaClass.getResourceAsStream("/bonus3.bas").bufferedReader(), StringWriter().buffered())
    }

    @Test(expected=Exception::class)
    fun bonus4() {
        indent.indent(this.javaClass.getResourceAsStream("/bonus4.bas").bufferedReader(), StringWriter().buffered())
    }


}

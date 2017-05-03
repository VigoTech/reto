import java.io.BufferedReader
import java.io.BufferedWriter
import java.util.*

fun String.removeLineIndents() = this.replace("^[»·]*".toRegex(), "") // Single expression extension function

fun String.firstWord() = if (this.indexOf(" ") >= 0) this.substring(0, this.indexOf(" ")) else this

class Indent {
    val FOR = "FOR"
    val NEXT = "NEXT"
    val IF = "IF"
    val ENDIF = "ENDIF"

    fun indent(reader: BufferedReader, writer: BufferedWriter) {
        val lines = reader.readLine()?.toInt() ?: throw Exception("Missing the line number in the first line")
        val indentString = reader.readLine()!! // If missing, it will throw a NullPointerException
        val indents = LinkedList<String>() // Stores the indent type at each level

        for (i in 1..lines) { // Ranges usage sample
            val line = (reader.readLine() ?: throw Exception("Missing line from the input")).removeLineIndents()
            var indentSize = indents.size

            val firstWord = line.firstWord()
            when (firstWord) {
                FOR, IF -> indents.add(firstWord)
                NEXT, ENDIF -> {
                    if (indentSize-- == 0) throw Exception("Line $i: Closing unopened $firstWord") // "--" reduce indent
                    if ((if (NEXT == firstWord) FOR else IF) != indents.removeLast()) { // Notice != and == operators
                        throw Exception("Line $i: Unmatched $firstWord") // Notice the String templates
                    }
                }
            }
            writer.write((if (i != 1) "\n" else "") + indentString.repeat(indentSize) + line) // a "ternary" sample
        }
        if (indents.size != 0) throw Exception("Unclosed block at the end of the file")
        writer.flush()
    }
}

fun main(args: Array<String>) { // Main function outside the class, added as an static main to IndentKt
    Indent().indent(System.`in`.bufferedReader(), System.out.bufferedWriter())
}
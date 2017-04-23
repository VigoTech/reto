import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode

/**
 * Basic formatter by @antonmry
 *
 * One possible Groovy solution to the April'17 VigoJUG Challenge.
 * More info: https://github.com/vigojug/reto
 *
 */

@CompileStatic
class BasicFormatter {

    String indentationStr = ''
    List blocks = []
    int numberLines = 0
    int indentFor = 0
    int indentIf = 0
    final String lineSeparator = System.getProperty('line.separator')

    // Used to make easier unit testing
    def console

    BasicFormatter() {
        this.console = System.console()
    }

    BasicFormatter(def console) {
        if (console != null) {
            this.console = console
        } else {
            this.console = System.console()
        }
    }

    static main(String[] args) {
        def formatter = new BasicFormatter()
        if (formatter.setNumberLines(System.err)) {
            formatter.setIdentationStr()
            formatter.autoIndent(System.out)
            formatter.validateSourceCode(System.err)
        }
    }

    // Read from the console the number of lines.
    boolean setNumberLines(PrintStream output) {
        String input = getInput('', '0')
        numberLines = input.isInteger() ? input as int : null
        if (numberLines < 1) {
            output << 'You have to insert a number bigger than 0' << lineSeparator
            return false
        }
        return true
    }

    // Read from the console the char/string used to indent.
    void setIdentationStr() {
        indentationStr = getInput('', '')
    }

    // Read from the console and indent the source code.
    void autoIndent(PrintStream output) {

        String input

        (1..numberLines).forEach {
            input = getInput('', '').replaceAll(/·|»/, '')

            if (input.startsWith('ENDIF')) {
                indentIf--
                blocks << ['ENDIF', indentIf + indentFor]
            }

            if (input.startsWith('NEXT')) {
                indentFor--
                blocks << ['NEXT', indentIf + indentFor]
            }

            if (indentIf + indentFor > 0) {
                output <<= indentationStr * (indentIf + indentFor) + input + lineSeparator
            } else {
                output <<= input + lineSeparator
            }

            if (input.startsWith('FOR')) {
                blocks << ['FOR', indentIf + indentFor]
                indentFor++
            }

            if (input.startsWith('IF')) {
                blocks << ['IF', indentIf + indentFor]
                indentIf++
            }
        }
    }

    // Validate BASIC source code previously auto-indented with autoIndent
    // and print errors in the output parameter.
    @CompileStatic(value = TypeCheckingMode.SKIP)
    boolean validateSourceCode(PrintStream output) {

        if (blocks.empty) {
            autoIndent(null)
        }

        Map b = blocks.countBy {
            [it[0], it[1]]
        }

        b.each() { key, value ->
            switch (key[0]) {
                case 'IF':
                    if (!b.containsKey(['ENDIF', key[1]]) && indentIf > 0) {
                        output << "Error: ENDIF missed, identation $value" << lineSeparator
                        break
                    }

                    if (b[['ENDIF', key[1]]] != value && indentFor == 0 && indentIf == 0) {
                        output << "Error: block IF not properly closed" << lineSeparator
                    }
                    break

                case 'FOR':
                    if ((!b.containsKey(['NEXT', key[1]])) && indentFor > 0) {
                        output << "Error: NEXT missed, identation $value" << lineSeparator
                        break
                    }

                    if (b[['NEXT', key[1]]] != value && indentFor == 0 && indentIf == 0) {
                        output << "Error: block FOR not properly closed" << lineSeparator
                    }
                    break

                case 'ENDIF':
                    if (!b.containsKey(['IF', key[1]]) && indentIf < 0) {
                        output << "Error: IF missed, identation $value" << lineSeparator
                    }
                    break

                case 'NEXT':
                    if (!b.containsKey(['FOR', key[1]]) && indentFor < 0) {
                        output << "Error: FOR missed, identation $value" << lineSeparator
                    }
                    break

                default:
                    assert false: "This block shouldn't be executed, there is a bug in the code"
                    break
            }
        }

    }

    // Utility method to make easier to mock the console in Unit Tests
    // Sources: http://stackoverflow.com/questions/20789662/test-groovy-class-that-uses-system-console
    @CompileStatic(value = TypeCheckingMode.SKIP)
    private String getInput(String prompt, String defValue) {
        String input = (console.readLine(prompt, null).trim() ?: defValue)?.toString()
        input
    }
}


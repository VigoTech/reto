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
            }

            if (input.startsWith('NEXT')) {
                indentFor--
            }

            if (indentIf + indentFor > 0) {
                output <<= indentationStr * (indentIf + indentFor) + input + lineSeparator
            } else {
                output <<= input + lineSeparator
            }

            if (input.startsWith('FOR')) {
                indentFor++
            }

            if (input.startsWith('IF')) {
                indentIf++
            }
        }

    }

    // Validate BASIC source code previously auto-indented with autoIndent
    // and print errors in the output parameter.
    boolean validateSourceCode(PrintStream output) {

        if (indentIf > 0) {
            output << "Error: $indentIf ENDIF missed" << lineSeparator
        } else if (indentIf < 0) {
            output << "Error: ${indentIf.abs()} IF missed" << lineSeparator
        }

        if (indentFor > 0) {
            output << "Error: $indentFor NEXT missed" << lineSeparator
        } else if (indentFor < 0) {
            output << "Error: ${indentFor.abs()} FOR missed" << lineSeparator
        }

    }

    // Utility method to make easier to mock the console in Unit Tests
    // Sources: http://stackoverflow.com/questions/20789662/test-groovy-class-that-uses-system-console
    @CompileStatic(value=TypeCheckingMode.SKIP)
    private String getInput(String prompt, String defValue) {
        String input = (console.readLine(prompt, null).trim() ?: defValue)?.toString()
        input
    }
}


/**
 * Basic formatter by @antonmry
 *
 * One possible Groovy solution to the April'17 VigoJUG Challenge.
 * More info: https://github.com/vigojug/reto
 *
 * Sources:
 *  http://stackoverflow.com/questions/20789662/test-groovy-class-that-uses-system-console
 */

class BasicFormatter {

    def console
    final String lineSeparator = System.getProperty('line.separator')

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

    def getInput = { prompt, defValue ->
        def input = (this.console.readLine(prompt, null).trim() ?: defValue)?.toString()
        input
    }

    void publishln(def output) {
        System.out << output << lineSeparator
    }

    void publish(def output) {
        System.out << output
    }

    void publishError(output) {
        System.out << 'Error: ' << output << lineSeparator
    }

    void run() {
        // Retrieve the number of lines
        def input = getInput('', 0)
        int numberLines = input.isInteger() ? input as int : null
        if (numberLines < 1) {
            publishError 'You have to insert a number bigger than 0'
            return
        }

        // Retrieve indent char
        String indStr = getInput('', '')

        // Retrieve lines
        int indentFor = 0
        int indentIf = 0
        def output = '' << ''

        (1..numberLines).forEach {
            input = getInput('', '').replaceAll(/·|»/, '')

            if (input.startsWith('ENDIF')) {
                indentIf--
            }

            if (input.startsWith('NEXT')) {
                indentFor--
            }

            if (indentIf + indentFor > 0) {
                output <<= indStr * (indentIf + indentFor) + input + lineSeparator
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

        if (indentIf > 0) {
            publishError "$indentIf ENDIF missed"
        }

        if (indentIf < 0) {
            publishError "${indentIf.abs()} IF missed"
        }

        if (indentFor > 0) {
            publishError "$indentFor NEXT missed"
        }

        if (indentFor < 0) {
            publishError "${indentFor.abs()} FOR missed"
        }

        if ((indentFor == 0) && (indentIf == 0)) {
            publish output
        }
    }
}


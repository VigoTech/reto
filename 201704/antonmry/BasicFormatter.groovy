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

    void run() {
        // Retrieve the number of lines
        def input = getInput("", 0)
        int numberLines = input.isInteger() ? input as int : null
        if (numberLines < 1) {
            print 'You have to insert a number bigger than 0'
            return
        }

        // Retrieve indent char
        String indStr = getInput("", "")

        // Retrieve lines
        int indentFor = 0
        int indentIf = 0
        def output = '' << ''

        (1..numberLines).forEach() {
            input = getInput("", "") as String

            input = input.replaceAll(/·|»/, "")

            if (input.startsWith('ENDIF')) {
                indentIf--
            }

            if (input.startsWith('NEXT')) {
                indentFor--
            }

            if (indentIf + indentFor > 0) {
                output <<= indStr * (indentIf + indentFor) + input + System.getProperty("line.separator")
            } else {
                output <<= input + System.getProperty("line.separator")
            }

            if (input.startsWith('FOR')) {
                indentFor++
            }

            if (input.startsWith('IF')) {
                indentIf++
            }
        }

        if (indentIf > 0) {
            println "Error: $indentIf ENDIF missed"
        }

        if (indentIf < 0) {
            println "Error: ${-1*indentIf} IF missed"
        }

        if (indentFor > 0) {
            println "Error: $indentFor NEXT missed"
        }

        if (indentFor < 0) {
            println "Error: ${-1*indentFor} FOR missed"
        }

        if ((indentFor == 0) && (indentIf == 0) ) {
            print output
        }
    }
}



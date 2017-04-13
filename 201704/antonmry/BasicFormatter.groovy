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

    void finishWithError(String error) {
        println "You have to insert at least 1 line"
        System.exit(-1)
    }

    void run() {
        // Retrieve the number of lines
        def input = getInput("", 0)
        int numberLines = input.isInteger() ? input.toInteger() : null
        if (numberLines < 1) finishWithError("You have to insert a number bigger than 0")

        // Retrieve indent char
        def indStr = getInput("", "")

        (1..numberLines).forEach() {
            input = getInput("", "")
        }
    }
}


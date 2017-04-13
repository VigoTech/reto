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
        def input = getInput("", 0)
        println input
    }
}


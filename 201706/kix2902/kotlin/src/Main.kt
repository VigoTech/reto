import java.io.File
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        print("You must include input filename as parameter")
        exitProcess(1)
    }

    print(processFile(args[0]))
}

fun processFile(filename: String): String {

    val lines = File(filename).readLines()

    var pos = 0
    while (lines[pos].startsWith("#")) {
        pos++
    }

    val coins_values = lines[pos + 1].trim().split(Regex("\\s+")).map { it.trim().toInt() }.reversed()
    val coins_quantity = lines[pos + 2].trim().split(Regex("\\s+")).map { it.trim().toInt() }.reversed().toMutableList()
    var change = lines[pos + 3].trim().toInt()

    val coins_change = ArrayList<Int>()
    while (change > 0) {
        var index: Int = 0
        var value: Int = 0
        var quantity: Int = 0

        for (i in coins_values.indices) {
            index = i
            value = coins_values[index]
            quantity = coins_quantity[index]
            if ((quantity != 0) && (value <= change)) {
                break
            }
        }

        coins_change.add(value)
        change -= value

        if (quantity != -1) {
            coins_quantity[index] = quantity - 1
        }
    }

    return coins_change.joinToString<Int>(separator = " ")
}

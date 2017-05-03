import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import scala.io.StdIn._


val INDENT_CODE = "····"
def cleanLine(s: String) = s.replaceAll("[»·]", "")

var inputList = ListBuffer[String]()
var num = readLong()
readLine()
while (num > 0) {
  num = num - 1
  inputList += cleanLine(readLine())
}


@tailrec def clean(inputList: List[String], outputList: List[String], state: List[String], indentLevel: Int): List[String] = {
  (inputList, state.head) match {
    case (s :: tail, _) if s.startsWith("IF")  => clean(tail, (INDENT_CODE * indentLevel + s) :: outputList, "IF" :: state, indentLevel + 1)
    case (s :: tail, "IF") if s.startsWith("ENDIF")  => clean(tail, (INDENT_CODE * (indentLevel - 1) + s) :: outputList, state.tail, indentLevel - 1)
    case (s :: tail, "FOR" | "") if s.startsWith("ENDIF")  =>  throw new IllegalArgumentException("ENDIF without IF block")
    case (s :: tail, _) if s.startsWith("FOR")  => clean(tail, (INDENT_CODE * indentLevel + s) :: outputList, "FOR" :: state, indentLevel + 1)
    case (s :: tail, "FOR") if s.startsWith("NEXT")  => clean(tail, (INDENT_CODE * (indentLevel - 1 )+ s) :: outputList, state.tail, indentLevel - 1)
    case (s :: tail, "IF" | "") if s.startsWith("NEXT")  => throw new IllegalArgumentException("NEXT without FOR block")
    case (s :: tail, _) => clean(tail, (INDENT_CODE * indentLevel + s) :: outputList, state, indentLevel)
    case (Nil, "") => outputList.reverse
    case (Nil, next) => throw new IllegalArgumentException("EOF without " + next + " block")
  }
}

val outputList = clean(inputList.toList, Nil, "" :: Nil, 0)
println(outputList.mkString("\n"))

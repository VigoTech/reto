package example


object Reto extends RetoTrait {
  def main(args: Array[String]): Unit = {

    var i = 1

    while (i < 50) {

      val r = 1 to i
      val start = System.currentTimeMillis();

      hasSubsetSumZero(r.toList)

      val elapsed = System.currentTimeMillis() - start;
      println("Array with " + i + " records: elapsed time = " + elapsed + "ms");

      if (elapsed > 300000) {
        return;
      }

      i = i + 1
    }
  }
}


trait RetoTrait {
  def twoSumZero(numbers: List[Int]): Boolean = {
    if (numbers.contains(0)) true

    numbers.find(x => numbers.contains(-1 * x)) match {
      case None => false
      case _ => true
    }
  }

  def hasSubsetSumZero(numbers: List[Int]): Boolean = {
    numbers.toSet[Int].subsets.filter(_.size != 0).map(_.sum).find(_ == 0) match {
      case None => false
      case _ => true
    }
  }

}

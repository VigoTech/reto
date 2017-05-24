package example


object Reto extends RetoTrait {
  def main(args: Array[String]): Unit = {

    var i = 1

    val random = scala.util.Random
    while (i < 100) {

      val r = (1 to i).map(_ => random.nextInt())

      val start = System.currentTimeMillis();

      hasSubsetSumZero(r.toArray)

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

  def hasSubsetSumZero(numbers: Array[Int]): Boolean = {
    if (numbers.contains(0)) return true

    val sortedNumbers = numbers.sorted
    (2 to numbers.size).par.find(i => generateAllSubArrays(sortedNumbers, i)) match {
      case None => false
      case _ => true
    }
  }


  def generateAllSubArrays(@specialized elements: Array[Int] , K: Int): Boolean = {
    val N = elements.length; //size nums

    var idx = new Array[Int](K);
    var i = 0
    while (i < K ) {
      idx(i) = i
      i += 1
    }

    var sums = new Array[Int](K+1);
    i = 0
    sums(0) = 0
    while (i < K ) {
      sums(i+1) = 0;
      var ii = i
      while(ii >= 0 ) {
        sums(i+1) += elements(ii);
        ii -= 1
      }
      i += 1
    }


    val last = K - 1;
    var rr = 0;

    while (true) {
      if (sums(last) < 0) {
        while(idx(last) < N) {
          sums(K) = sums(last) + elements(idx(last));

          if (sums(K) == 0) {
            return true;
          }

          idx(last) += 1;
        }
      }

      do {
        rr += 1;
        if(rr == K) {
          return false;
        }
        idx(last-rr) += 1;

      } while (idx(last-rr) >= N-rr || sums(K-rr) > 0);

      while(rr > 0) {
        idx(K-rr) = idx(last-rr) + 1;

        sums(K-rr) = sums(last-rr) + elements(idx(last-rr));
        rr -= 1;
      }
    }


    return false;
  }
}

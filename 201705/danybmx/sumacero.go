package sumacero

import (
	"math"
	"strconv"
	"time"
)

func doOperations(x int, nNumbers int, numbers []int) <-chan int {
	out := make(chan int)
	go func(x int, nNumbers int, numbers []int) {
		option := strconv.FormatInt(int64(x+1), 2)
		nOption := len(option)
		result := 0
		for i := nOption - 1; i >= 0; i-- {
			if string(option[i]) == "1" {
				result += numbers[nNumbers-nOption+i]
			}
		}
		out <- result
		close(out)
	}(x, nNumbers, numbers)
	return out
}

func hasZeroSetConcurrent(numbers []int) (bool, float64) {
	start := time.Now()
	nNumbers := len(numbers)
	nOptions := int(math.Pow(2, float64(nNumbers))) - 1
	if nOptions > 0 {
		for x := 0; x < nOptions; x++ {
			result := doOperations(x, nNumbers, numbers)
			for res := range result {
				if res == 0 {
					return true, time.Since(start).Seconds()
				}
			}
		}
	}
	return false, time.Since(start).Seconds()
}

package sumacero

import (
	"math"
	"strconv"
	"time"
)

func hasZeroSet(numbers []int) (result bool, elapsed float64) {
	start := time.Now()
	nNumbers := len(numbers)
	nOptions := int(math.Pow(2, float64(nNumbers))) - 1
	result = false
	if nOptions > 0 && !result {
		for x := 0; x < nOptions; x++ {
			sum := 0
			option := strconv.FormatInt(int64(x+1), 2)
			offset := nNumbers - len(option)
			for z := 0; z < nNumbers; z++ {
				if offset-1 < z && option[z-offset] == 49 {
					sum += numbers[z]
				}
			}
			if sum == 0 {
				result = true
			}
		}
	}
	elapsed = time.Since(start).Seconds()
	return
}

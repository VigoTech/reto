package sumacero

import (
	"math"
	"strconv"
	"strings"
	"time"
)

func doOperations(pad string, x int, nNumbers int, numbers []int) <-chan int {
	out := make(chan int)
	go func(pad string, x int, nNumbers int, numbers []int) {
		option := pad + string(strconv.FormatInt(int64(x+1), 2))
		var sum []int
		for i, c := range option[len(option)-nNumbers:] {
			if string(c) == "1" {
				sum = append(sum, numbers[i])
			}
		}
		result := 0
		for _, v := range sum {
			result += v
		}
		out <- result
		close(out)
	}(pad, x, nNumbers, numbers)
	return out
}

func hasZeroSetConcurrent(numbers []int) (bool, float64) {
	start := time.Now()
	nNumbers := len(numbers)
	nOptions := int(math.Pow(2, float64(nNumbers))) - 1
	pad := strings.Repeat("0", int(nNumbers))

	if nOptions == 0 {
		return false, time.Since(start).Seconds()
	}

	for x := 0; x < nOptions; x++ {
		result := doOperations(pad, x, nNumbers, numbers)

		for res := range result {
			if res == 0 {
				return true, time.Since(start).Seconds()
			}
		}
	}

	return false, time.Since(start).Seconds()
}

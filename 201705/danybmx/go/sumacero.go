package sumacero

import (
	"math"
	"runtime"
	"strconv"
	"sync"
	"time"
)

func calculate(result *bool, wg *sync.WaitGroup, nOptions int, nTotalOptions int, offset int, nNumbers int, numbers []int) {
	for x := 0; x < nOptions; x++ {
		if *result || nTotalOptions <= x+offset {
			break
		}
		sum := 0
		option := strconv.FormatInt(int64(x+offset+1), 2)
		offset := nNumbers - len(option)
		for z := 0; z < nNumbers; z++ {
			if offset-1 < z && option[z-offset] == 49 {
				sum += numbers[z]
			}
		}
		if sum == 0 {
			*result = true
		}
	}
	wg.Done()
}

func hasZeroSet(numbers []int) (bool, float64) {
	start := time.Now()
	nNumbers := len(numbers)
	nOptions := int(math.Pow(2, float64(nNumbers))) - 1
	availableWorkers := runtime.GOMAXPROCS(-1)
	result := false
	var wg sync.WaitGroup

	if nOptions > 0 {
		if nOptions < availableWorkers {
			wg.Add(1)
			go calculate(&result, &wg, nOptions, nOptions, 0, nNumbers, numbers)
		} else {
			nOperationsPerWorker := int(math.Ceil(float64(nOptions) / float64(availableWorkers)))
			for x := 0; x < availableWorkers; x++ {
				wg.Add(1)
				go calculate(&result, &wg, nOperationsPerWorker, nOptions, x*nOperationsPerWorker, nNumbers, numbers)
			}
		}
	}
	wg.Wait()

	return result, time.Since(start).Seconds()
}

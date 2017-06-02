#include <stdio.h>
#include <stdlib.h>
#include <limits.h>

#define kBUFFERSIZE 4096    // How many bytes to read at a time

#define SOLUTION_NOT_FOUND 0
#define SOLUTION_FOUND 1
#define SOLUTION_IMPOSSIBLE 2

//copied from here http://www.comp.dit.ie/rlawlor/Alg_DS/sorting/quickSort.c
int partition( int a[], int l, int r) {
        int pivot, i, j, t;
        pivot = a[l];
        i = l; j = r+1;
        while( 1)
        {
                do ++i; while( a[i] <= pivot && i <= r );
                do --j; while( a[j] > pivot );
                if( i >= j ) break;
                t = a[i]; a[i] = a[j]; a[j] = t;
        }
        t = a[l]; a[l] = a[j]; a[j] = t;
        return j;
}

//copied from here http://www.comp.dit.ie/rlawlor/Alg_DS/sorting/quickSort.c
void quickSort( int a[], int l, int r)
{
        int j;
        if( l < r )
        {
                // divide and conquer
                j = partition( a, l, r);
                quickSort( a, l, j-1);
                quickSort( a, j+1, r);
        }

}

//convert input and get some special values
int atoiAndFindElements( int inputSize,
                         char** input,
                         int* closeToZeroPositive,
                         int* closetoZeroNegative,
                         long* sumPositiveNumbers,
                         long* sumNegativeNumbers,
                         int* sortedInputList[]) {
        for(int i = 0; i < inputSize; i++) {
                int number = atoi(input[i]);
                if ( number == 0) {
                        return 0;
                }
                if (number > 0 ) {
                        *closeToZeroPositive = (number < *closeToZeroPositive ? number : *closeToZeroPositive);
                        *sumPositiveNumbers += number;
                } else {
                        *closetoZeroNegative = (number > *closetoZeroNegative ? number : *closetoZeroNegative);
                        *sumNegativeNumbers += number;
                }
                (*sortedInputList)[i] = number;
        }
        return 1;
}

int main( int argc, char** argv ) {
        int currentState = SOLUTION_NOT_FOUND;

        int inputSize = argc-1;
        printf("Wow let's have fun with %d elements\n", inputSize);

        int* sortedInputList = (int*) malloc((inputSize)*sizeof(int));

        int closeToZeroPositive = INT_MAX;
        int closetoZeroNegative = INT_MIN;
        long sumPositiveNumbers = 0;
        long sumNegativeNumbers = 0;

        int foundZero = atoiAndFindElements(inputSize,
                                            &(argv[1]),
                                            &closeToZeroPositive,
                                            &closetoZeroNegative,
                                            &sumPositiveNumbers,
                                            &sumNegativeNumbers,
                                            &sortedInputList);

        printf("%10ld %8d %8d %10ld\n", sumNegativeNumbers, closetoZeroNegative, closeToZeroPositive, sumPositiveNumbers);

        // if has zero return eureka!
        if(foundZero == 0) {
                currentState = SOLUTION_FOUND;
        }

        // OPTIMIZATION: dummy cases the small element of each side is already bigger than the sum of the other side
        if (currentState == SOLUTION_NOT_FOUND &&
            (sumPositiveNumbers + closetoZeroNegative < 0 ||
             sumNegativeNumbers + closeToZeroPositive > 0)) {
                //impossible solution
                currentState = SOLUTION_IMPOSSIBLE;
        }

        // now we can have some fun :)

        //sort the monster to apply some tricks
        quickSort(sortedInputList, 0, inputSize-1);

        int listSize = (-1*sumNegativeNumbers) + 1;
        //store partial sums (max is the sumNegativeNumbers until zero)
        long* preliminarSumsArray = (long*) malloc(listSize*sizeof(long));
        //store only negative sums (max is the sumNegativeNumbers until zero)
        char* listOfExistentNegativeSums = (char*) calloc(listSize,sizeof(char));

        //store only positive sums
        int positiveListSize = sumPositiveNumbers > -sumNegativeNumbers ? sumPositiveNumbers : -sumNegativeNumbers;
        printf("List size is %d \n", positiveListSize);
        char* listOfExistentPositiveSums = (char*) calloc(positiveListSize+1,sizeof(char));

        //find first positive element using binary search
        int first = 0;
        int last = inputSize - 1;
        int firstPositivePosition = (first+last)/2;
        while (first <= last) {
                if (sortedInputList[firstPositivePosition] < closeToZeroPositive) {
                        first = firstPositivePosition + 1;
                } else if (sortedInputList[firstPositivePosition] == closeToZeroPositive) {
                        break;
                } else {
                        last = firstPositivePosition - 1;
                }
                firstPositivePosition = (first + last)/2;
        }
        for(int j=firstPositivePosition; j < inputSize; j++) {
                printf("Set to 1 = %d \n", sortedInputList[j]);
                listOfExistentPositiveSums[sortedInputList[j]] = 1;
        }

        //OPTIMIZATION: add zero to create the case of the element alone
        preliminarSumsArray[0] = 0;
        long firstPositionFree = 1;
        long numberOfPreliminarSums = 1;
        for (int i = 0; i < inputSize && currentState == SOLUTION_NOT_FOUND; i++) {
                int currentNumber = sortedInputList[i];
                printf("Element[%6d] = %8d | Subsets = %12lu \n", i, currentNumber, numberOfPreliminarSums);

                if (currentNumber > 0) {
                        //OPTIMIZATION: if the currentNumber + negativeSUM is greater than zero
                        // we cannot generate a zero sum, so we can stop
                        if (currentNumber + sumNegativeNumbers > 0) {
                                currentState = SOLUTION_IMPOSSIBLE;
                                continue;
                        }

                        //OPTIMIZATION: if currentNumber is positive and its sum was done, so we found a solution
                        if (listOfExistentNegativeSums[currentNumber] == 1) {
                                currentState = SOLUTION_FOUND;
                                continue;
                        }
                }


                for(long currentPosition = 0;
                    currentPosition < numberOfPreliminarSums && currentState == SOLUTION_NOT_FOUND;
                    currentPosition++) {
                        long sum = preliminarSumsArray[currentPosition] + currentNumber;
                        if (sum == 0 ) {
                                currentState = SOLUTION_FOUND;
                        } else if (currentNumber < 0 && listOfExistentPositiveSums[-sum] == 1) {
                                currentState = SOLUTION_FOUND;
                        } else {
                                //printf("%ld %d %d %d %d \n", sum, (sum < 0), (sum + currentNumber <= 0), (sum + sumPositiveNumbers >= 0), (listOfExistentNegativeSums[-sum]));
                                //OPTIMIZATION: positive sums are useless
                                if ((sum < 0) &&
                                    //OPTIMIZATION: the list is sorted, if the next sum is greater than zero, sum is useless
                                    (sum + currentNumber <= 0) &&
                                    //OPTIMIZATION: I need to be able to generate a possible positive sum
                                    (sum + sumPositiveNumbers >= 0) &&
                                    //OPTIMIZATION: the sum does not exist in my list
                                    (listOfExistentNegativeSums[-sum] == 0)) {
                                        //sum is useful, storing it
                                        preliminarSumsArray[firstPositionFree++] = sum;
                                        listOfExistentNegativeSums[-sum] = 1;
                                }
                        }
                }
                numberOfPreliminarSums = firstPositionFree;
                // for (int j = 0 ; j < firstPositionFree; j++){
                //         printf(" %ld ", preliminarSumsArray[j]);
                // }
                // printf("\n");
        }

        //a good man always frees the used memory
        free(listOfExistentNegativeSums);
        free(listOfExistentPositiveSums);
        free(preliminarSumsArray);
        free(sortedInputList);

        switch (currentState) {
                case SOLUTION_NOT_FOUND:
                case SOLUTION_IMPOSSIBLE:
                        printf("\n> > > There is NO possible solutions :-(\n");
                        return 1;
                case SOLUTION_FOUND:
                default:
                        printf("\n> > > There is a subset with sum Zero :-)\n");
                        return 0;
        }
}

#include <stdio.h>
#include <stdlib.h>
#include <limits.h>

#define kBUFFERSIZE 4096    // How many bytes to read at a time

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
                         long long* sumPositiveNumbers,
                         long long* sumNegativeNumbers,
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

        int inputSize = argc-1;
        printf("Wow let's have fun with %d elements\n", inputSize);

        int* sortedInputList = (int*) malloc((inputSize)*sizeof(int));
        int closeToZeroPositive = INT_MAX;
        int closetoZeroNegative = INT_MIN;
        long long sumPositiveNumbers = 0;
        long long sumNegativeNumbers = 0;

        int foundZero = atoiAndFindElements(inputSize, &(argv[1]), &closeToZeroPositive, &closetoZeroNegative, &sumPositiveNumbers, &sumNegativeNumbers, &sortedInputList);


        printf("%8d %8d %10lld %10lld\n", closeToZeroPositive, closetoZeroNegative, sumPositiveNumbers, sumNegativeNumbers);

        // if has zero return eureka!
        if(foundZero == 0) {
                printf("There is a subset with sum 0!!!\n");
                return 0;
        }

        // OPTIMIZATION: dummy cases the small element of each side is already bigger than the sum of the other side
        if (sumPositiveNumbers + closetoZeroNegative < 0 ||
            sumNegativeNumbers + closeToZeroPositive > 0) {
                //impossible solution
                return 0;
        }

        // now we can have some fun :)

        //sort the monster to apply some tricks
        quickSort(sortedInputList, 0, inputSize-1);

        unsigned long firstPositionFree = 1;
        unsigned long lastPositionFromPreviousIt = 1;

        //store partial sums
        int* preliminarSumsArray = (int *) malloc(sizeof(long[150000000]));
        //store only negative sums
        char* listOfExistentSums = (char*) calloc((-1*sumNegativeNumbers),sizeof(char));

        //add zero to create the case of the element alone
        preliminarSumsArray[0] = 0;

        for (int i = 0; i < inputSize; i++) {
                int current = sortedInputList[i];
                printf("Element[%6d] = %8d | Subsets = %12lu \n", i, current, lastPositionFromPreviousIt);

                for(unsigned long currentPosition = 0; currentPosition < lastPositionFromPreviousIt; currentPosition++) {
                        int sum = preliminarSumsArray[currentPosition] + current;
                        if (sum == 0) {
                                printf("There is a subset with sum 0!!! \n");
                                return 0;
                        }

                        //printf("> %d %d %d %d %d\n", sum, (sum < 0),(sum+current <= 0) , (sum+sumPositiveNumbers >= 0),(listOfExistentSums[sum-sumNegativeNumbers] == 0));
                        //OPTIMIZATION: positive sums are useless
                        if ((sum < 0) &&
                            //OPTIMIZATION: the list is sorted, if the next sum is greater than zero, sum is useless
                            (sum+current <= 0) &&
                            //OPTIMIZATION: I need to be able to generate a possible positive sum
                            (sum+sumPositiveNumbers >= 0) &&
                            //OPTIMIZATION: the sum does not exist in my list
                            (listOfExistentSums[sum-sumNegativeNumbers] == 0)) {
                                //sum is useful, storing it
                                preliminarSumsArray[firstPositionFree++] = sum;
                                listOfExistentSums[sum-sumNegativeNumbers] = 1;
                        }
                }
                lastPositionFromPreviousIt = firstPositionFree;
        }

        free(preliminarSumsArray);
        free(sortedInputList);
        free(listOfExistentSums);
        return 0;
}

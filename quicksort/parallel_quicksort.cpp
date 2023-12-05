#include <cmath>
#include <omp.h>
#include "parallel_quicksort.h"
#include "quicksort.h"

void parallel_quicksort(std::vector<int> &arr, int low, int high) {
    if (low < high) {
        std::pair<int, int> p = partition(arr, low, high);
        int pl = p.first;
        int pr = p.second;

        if (high - low < 10000) {
            quicksort(arr, low, pl);
            quicksort(arr, pr, high);
            return;
        }
        #pragma omp task default(none) shared(arr, low, pl)
            parallel_quicksort(arr, low, pl);
        #pragma omp task default(none) shared(arr, pr, high)
            parallel_quicksort(arr, pr, high);
    }

}
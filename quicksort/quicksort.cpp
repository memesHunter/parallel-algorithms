#include "quicksort.h"
#include <algorithm>

std::pair<int, int> partition(std::vector<int> &arr, int low, int high) {
    int pivot = arr[low + rand() % (high - low + 1)]; // arr[(low + high) / 2]

    int i = low;
    for (int j = low; j < high; j++) {
        if (arr[j] < pivot) {
            std::swap(arr[j], arr[i++]);
        }
    }

    int j = i;
    for (int k = i; k < high; k++) {
        if (arr[k] == pivot) {
            std::swap(arr[k], arr[j++]);
        }
    }
    return {i, j};
}

void quicksort(std::vector<int> &arr, int low, int high) {
    if (low < high) {
        std::pair<int, int> pivots = partition(arr, low, high);
        int pl = pivots.first;
        int pr = pivots.second;
        quicksort(arr, low, pl);
        quicksort(arr, pr, high);
    }
}

void quicksort(std::vector<int> &arr) {
    quicksort(arr, 0, arr.size());
}
#ifndef PARALGO_QUICKSORT_H
#define PARALGO_QUICKSORT_H
#include <vector>

std::pair<int, int> partition(std::vector<int> &arr, int low, int high);
void quicksort(std::vector<int> &arr);
void quicksort(std::vector<int> &arr, int low, int high);

#endif //PARALGO_QUICKSORT_H

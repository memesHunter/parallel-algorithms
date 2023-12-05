#include <iostream>
#include <chrono>
#include <algorithm>
#include <cassert>
#include <omp.h>
#include "quicksort.h"
#include "parallel_quicksort.h"

int main() {
    const int size = 1e8;
    const int iter_num = 5;

    double seq_time = 0;
    double par_time = 0;
    for (int iter = 0; iter < iter_num; iter++) {
        std::vector<int> seq_arr(size);
        std::vector<int> par_arr(size);
        for (int i = 0; i < size; i++) {
            int num = rand();
            seq_arr[i] = num;
            par_arr[i] = num;
        }

        // sequential
        auto start_time_seq = std::chrono::high_resolution_clock::now();
        quicksort(seq_arr);
        auto end_time_seq = std::chrono::high_resolution_clock::now();
        assert(std::is_sorted(seq_arr.begin(), seq_arr.end()));
        auto elapsed_time_seq = std::chrono::duration_cast<std::chrono::milliseconds>(end_time_seq - start_time_seq).count();
        std::cout << "Quicksort took " << elapsed_time_seq << " ms." << std::endl;
        seq_time += elapsed_time_seq;

        // parallel
        auto start_time_par = std::chrono::high_resolution_clock::now();

        omp_set_num_threads(4);
        omp_set_nested(true);

        #pragma omp parallel default(none) shared(par_arr)
        {
            #pragma omp single
                parallel_quicksort(par_arr, 0, size);
        }

        auto end_time_par = std::chrono::high_resolution_clock::now();
        assert(std::is_sorted(par_arr.begin(), par_arr.end()));
        auto elapsed_time_par = std::chrono::duration_cast<std::chrono::milliseconds>(end_time_par - start_time_par).count();
        std::cout << "Parallel Quicksort took " << elapsed_time_par << " ms.\n" << std::endl;
        par_time += elapsed_time_par;
    }

    std::cout << "Quicksort average time: " << seq_time / iter_num << " in " << iter_num << " iterations." << std::endl;
    std::cout << "Parallel quicksort average time: " << par_time / iter_num << " in " << iter_num << " iterations." << std::endl;
    std::cout << "Parallel version advantage: " << seq_time / par_time << " times." << std::endl;

    return 0;
}

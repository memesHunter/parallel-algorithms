cmake_minimum_required(VERSION 3.20)
project(ParAlgo)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_FLAGS -o2)

set(SOURCES
        quicksort/main.cpp
        quicksort/quicksort.cpp
        quicksort/quicksort.h
        quicksort/parallel_quicksort.cpp
        quicksort/parallel_quicksort.h
        )
add_executable(ParAlgo ${SOURCES})

# Enable OpenMP support
find_package(OpenMP REQUIRED)
target_compile_options(ParAlgo PRIVATE ${OpenMP_CXX_FLAGS})
target_link_libraries(ParAlgo PRIVATE ${OpenMP_CXX_LIBRARIES})

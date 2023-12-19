import kotlin.system.measureTimeMillis

fun runPar(sideLength: Int): Float {
    var res: Long = 0
    val graph = CubicGraph(sideLength)
    repeat(5) {
        val distances: IntArray
        val timeCubic = measureTimeMillis {
            distances = bfsParallel(graph)
        }
        validateCubicDistances(distances, sideLength)
        res += timeCubic
        println("Parallel run [${it + 1}/5] -> $timeCubic ms.")
    }

    return res.toFloat()
}

fun runSeq(sideLength: Int): Float {
    var res: Long = 0
    val graph = CubicGraph(sideLength)
    repeat(5) {
        val distances: IntArray
        val timeCubic = measureTimeMillis {
            distances = bfsSequential(graph)
        }
        validateCubicDistances(distances, sideLength)
        res += timeCubic
        println("Sequential run [${it + 1}/5] -> $timeCubic ms.")
    }

    return res.toFloat()
}

fun validateCubicDistances(distances: IntArray, sideLength: Int) {
    for (i in distances.indices) {
        val x = i / (sideLength * sideLength)
        val y = (i % (sideLength * sideLength)) / sideLength
        val z = i % sideLength
        assert(distances[i] == (x + y + z))
    }
}

fun main() {
    /*
        val adjacencyListGraph = AdjacencyListGraph(
            mapOf(
                0 to listOf(1, 2),
                1 to listOf(0, 3, 4),
                2 to listOf(0, 5),
                3 to listOf(1),
                4 to listOf(1),
                5 to listOf(2)
            )
        )
    */
    val sideLength = 500
    println("Score: ${(1 / runPar(sideLength)) * runSeq(sideLength)}")
}
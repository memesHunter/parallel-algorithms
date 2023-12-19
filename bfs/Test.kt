import kotlin.system.measureTimeMillis

fun runPar(): Float {
    var res: Long = 0
    val graph = CubicGraph(500)
    repeat(5) {
        val timeCubic = measureTimeMillis {
            bfsParallel(graph)
        }
        res += timeCubic
        println("Parallel run [${it + 1}/5] -> $timeCubic ms.")
    }

    return res.toFloat()
}

fun runSeq(): Float {
    var res: Long = 0
    val graph = CubicGraph(500)
    repeat(5) {
        val timeCubic = measureTimeMillis {
            bfsSequential(graph)
        }
        res += timeCubic
        println("Sequential run [${it + 1}/5] -> $timeCubic ms.")
    }

    return res.toFloat()
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

    println("Score: ${(1 / runPar()) * runSeq()}")
}
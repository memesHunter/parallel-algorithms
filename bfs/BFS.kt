import java.util.*
import java.util.concurrent.atomic.AtomicIntegerArray

fun bfsParallel(graph: Graph, source: Int = 0): IntArray {
    val distances = IntArray(graph.size)
    val visited = AtomicIntegerArray(graph.size)
    visited.set(0, 1)

    var frontier = List(1) { source }
    var d = 1
    while (frontier.isNotEmpty()) {
        val degree = IntArray(frontier.size)

        ParallelFor(0, frontier.size) { i: Int ->
            degree[i] = graph.getNodeDegree(frontier[i])
        }.fork().join()

        val degreeScan = ParallelScan(degree).runScan()
        val nextFrontier = IntArray(degreeScan.last()) { -1 }
        ParallelFor(0, frontier.size) { i: Int ->
            val nextNodes = graph.getNeighbors(frontier[i])
            for (j in nextNodes.indices) {
                val nextNode = nextNodes[j]
                if (visited.compareAndSet(nextNode, 0, 1)) {
                    distances[nextNode] = d
                    nextFrontier[(degreeScan[i] + j)] = nextNode
                }
            }
        }.fork().join()
        frontier = nextFrontier.filter { it != -1 }
        d++
    }
    return distances
}

fun bfsSequential(graph: Graph, source: Int = 0): IntArray {
    val visited = BooleanArray(graph.size) { false }
    val queue: Queue<Int> = LinkedList()
    val distances = IntArray(graph.size)

    queue.add(source)
    visited[source] = true
    distances[source] = 0

    while (queue.isNotEmpty()) {
        val node = queue.poll()
        for (neighbor in graph.getNeighbors(node)) {
            if (visited[neighbor]) continue
            queue.add(neighbor)
            visited[neighbor] = true
            distances[neighbor] = 1 + distances[node]
        }
    }
    return distances
}
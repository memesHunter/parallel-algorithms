interface Graph {
    val size: Int
    fun getNeighbors(node: Int): List<Int>
    fun getNodeDegree(node: Int): Int
}

class AdjacencyListGraph(private val adjacencyList: Map<Int, List<Int>>) : Graph {
    override val size = adjacencyList.size
    override fun getNeighbors(node: Int): List<Int> {
        return adjacencyList[node] ?: emptyList()
    }

    override fun getNodeDegree(node: Int): Int {
        return getNeighbors(node).size
    }
}

class CubicGraph(private val sideLength: Int) : Graph {
    override val size = sideLength * sideLength * sideLength

    override fun getNeighbors(node: Int): List<Int> {
        val x = node / (sideLength * sideLength)
        val y = (node % (sideLength * sideLength)) / sideLength
        val z = node % sideLength

        return buildList {
            if (x > 0) add((x - 1) * sideLength * sideLength + y * sideLength + z)
            if (y > 0) add(x * sideLength * sideLength + (y - 1) * sideLength + z)
            if (z > 0) add(x * sideLength * sideLength + y * sideLength + (z - 1))
            if (x < sideLength - 1) add((x + 1) * sideLength * sideLength + y * sideLength + z)
            if (y < sideLength - 1) add(x * sideLength * sideLength + (y + 1) * sideLength + z)
            if (z < sideLength - 1) add(x * sideLength * sideLength + y * sideLength + (z + 1))
        }
    }


    override fun getNodeDegree(node: Int): Int {
        val x = node / (sideLength * sideLength)
        val y = (node % (sideLength * sideLength)) / sideLength
        val z = node % sideLength

        return listOf(
            x to x - 1, x to x + 1,
            y to y - 1, y to y + 1,
            z to z - 1, z to z + 1
        ).count { it.first in 0 until sideLength && it.second in 0 until sideLength }
    }
}
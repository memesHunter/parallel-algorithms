import java.util.concurrent.RecursiveAction

private class Tree(
    var value: Int, var leftChild: Tree? = null, var rightChild: Tree? = null
) {
    fun updateValue() {
        this.value = this.leftChild!!.value + this.rightChild!!.value
    }
}

class ParallelScan(private val array: IntArray) {

    private fun buildTree(startIndex: Int, endIndex: Int, node: Tree): RecursiveAction = object : RecursiveAction() {
        override fun compute() {
            if (endIndex - startIndex < 10000) {
                for (i in startIndex..endIndex) {
                    node.value += array[i]
                }
            } else {
                val middle = (startIndex + endIndex) / 2
                node.leftChild = Tree(0, null, null)
                node.rightChild = Tree(0, null, null)
                val leftTask = buildTree(startIndex, middle, node.leftChild!!)
                val rightTask = buildTree(middle + 1, endIndex, node.rightChild!!)

                leftTask.fork()
                rightTask.fork()

                leftTask.join()
                rightTask.join()

                node.updateValue()
            }
        }
    }

    private fun computePrefSum(
        startIndex: Int,
        endIndex: Int,
        accumulator: Int,
        node: Tree,
        result: IntArray
    ): RecursiveAction = object : RecursiveAction() {
        override fun compute() {
            if (endIndex - startIndex < 10000) {
                result[startIndex + 1] = accumulator + array[startIndex]
                for (i in startIndex + 1..endIndex) {
                    result[i + 1] = array[i] + result[i]
                }
            } else {
                val middle = (startIndex + endIndex) / 2
                val leftTask = computePrefSum(startIndex, middle, accumulator, node.leftChild!!, result)
                val rightTask = computePrefSum(
                    middle + 1, endIndex, accumulator + node.leftChild!!.value, node.rightChild!!, result
                )

                leftTask.fork()
                rightTask.fork()

                leftTask.join()
                rightTask.join()
            }
        }
    }

    fun runScan(): IntArray {
        val tree = Tree(0)
        val result = IntArray(array.size + 1)

        buildTree(0, array.size - 1, tree).fork().join()
        computePrefSum(0, array.size - 1, 0, tree, result).fork().join()

        return result
    }
}

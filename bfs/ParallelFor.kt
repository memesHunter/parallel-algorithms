import java.util.concurrent.RecursiveAction

class ParallelFor(
    private val startIndex: Int,
    private val endIndex: Int,
    private val task: (Int) -> Unit
) : RecursiveAction() {

    override fun compute() {
        if (endIndex - startIndex < 5000) {
            for (i in startIndex until endIndex) {
                task(i)
            }
        } else {
            val middle = (startIndex + endIndex) / 2
            val leftTask = ParallelFor(startIndex, middle, task)
            val rightTask = ParallelFor(middle, endIndex, task)

            leftTask.fork()
            rightTask.fork()

            leftTask.join()
            rightTask.join()
        }
    }
}

import kotlin.system.measureTimeMillis

class CspCli {

    fun serveUser() {
        try {
            tryToServeUser()
        } catch (e: Exception) {
            println(e)
            serveUser()
        }
    }

    private fun tryToServeUser() {
        println("Usage: queens/square n first/all")
        val line = readLine()!!.split(' ')
        val (problemName, nString, mode) = line
        val problem = problem(problemName, nString)
        val ifFirst = isFirstMode(mode)
        printResults(problem, ifFirst)
        serveUser()
    }

    private fun isFirstMode(mode: String) = mode[0] in arrayOf('f', 'F')

    private fun problem(problemName: String, nString: String): Problem {
        val firstLetter = problemName[0]
        val n = nString.toInt()
        return when (firstLetter) {
            'q', 'Q' -> QueensProblem(n)
            else -> LatinSquareProblem(n)
        }
    }

    private fun printResults(problem: Problem, first: Boolean) {
        val backtrackingExecutor = BacktrackingExecutor(problem)
        printResult(backtrackingExecutor, first) // todo: problem immutable?
        val forwardCheckingExecutor = ForwardCheckingExecutor(problem)
        printResult(forwardCheckingExecutor, first)
    }

    private fun printResult(executor: CspExecutor, first: Boolean) {
        executor.printName()
        val method = if (first) executor::findFirst else executor::countAll
        val time = measureTimeMillis {
            println("result: ${method()}")
        }
        println("time: $time ms")
    }
}

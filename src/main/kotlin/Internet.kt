var count = 0
var c = IntArray(0)
var first = ""
 
fun nQueens(row: Int, n: Int) {
    outer@ for (candidate in 1..n) {
        for (previousRow in 1 until row) {
            if (c[previousRow] == candidate) continue@outer
            if (row - previousRow == Math.abs(candidate - c[previousRow])) continue@outer
        }
        c[row] = candidate
        if (row < n) nQueens(row + 1, n)
        else if (++count == 1) first = c.drop(1).map { it - 1 }.toString()
    }
}

fun main(args: Array<String>) {
   for (n in 4..4) {
       count = 0
       c = IntArray(n + 1)
       first = ""
       nQueens(1, n)
       println("For a $n x $n board:")
       println("  Solutions = $count")
       if (count > 0) println("  First is $first")
       println()
   }
}
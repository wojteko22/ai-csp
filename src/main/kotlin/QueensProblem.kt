data class QueensProblem(
        override val numberOfVariables: Int,
        private val board: List<Variable> = List(numberOfVariables) {
            Variable(0, List(numberOfVariables) { it + 1 })
        }
) : Problem {

    override val currentResult: String
        get() = board.map { it.value }.toString()

    override val someDomainEmpty: Boolean
        get() = board.any { it.domain.isEmpty() }

    override fun setVariable(variableIndex: Int, value: Int): Problem {
        val actualVariableIndex = actualVariableIndex(variableIndex)
        val updatedBoard = board.copy { this[actualVariableIndex] = this[actualVariableIndex].copy(value = value) }
        return copy(board = updatedBoard)
    }

    override fun domainOfVariable(variableIndex: Int): Domain {
        val actualVariableIndex = actualVariableIndex(variableIndex)
        return board[actualVariableIndex].domain
    }

    private fun actualVariableIndex(variableIndex: Int) = numberOfVariables / 2 + (if (variableIndex % 2 == 0) 1 else -1) * (variableIndex + 1) / 2

    override fun areConstrainsSatisfied(variableIndex: Int, value: Int): Boolean {
        val actualVariableIndex = actualVariableIndex(variableIndex)
        for (otherColumn in (0 until numberOfVariables) - actualVariableIndex) {
            if (board[otherColumn].value != 0) {
                if (board[otherColumn].value == value) return false
                if (Math.abs(actualVariableIndex - otherColumn) == Math.abs(value - board[otherColumn].value)) return false
            }
        }
        return true
    }

    override fun updateDomains(variableIndex: Int, value: Int): Problem {
        val actualVariableIndex = actualVariableIndex(variableIndex)
        val newBoard = board.copy {
            var increment = 1
            for (columnIndex in (actualVariableIndex + 1) until numberOfVariables) {
                val oldDomain = this[columnIndex].domain
                val newDomain = oldDomain - value - (value + increment) - (value - increment)
                this[columnIndex] = this[columnIndex].copy(domain = newDomain)
                ++increment
            }
            increment = 1
            for (columnIndex in (actualVariableIndex - 1) downTo 0) {
                val oldDomain = this[columnIndex].domain
                val newDomain = oldDomain - value - (value + increment) - (value - increment)
                this[columnIndex] = this[columnIndex].copy(domain = newDomain)
                ++increment
            }
        }
        return QueensProblem(numberOfVariables, newBoard)
    }
}

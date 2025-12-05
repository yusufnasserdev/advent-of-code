package dev.yunas.aoc.day4

import dev.yunas.Solution
import java.io.File
import kotlin.math.max


/**
 * Day 3: Printing Department
 *
 */

class PrintingDepartment : Solution {
    val grid = mutableListOf<String>()

    override fun solve(): String {
        grid.addAll(processInput())
        val accessibleRollsInitial = calculateInitialAccessibleRolls()
        val accessibleRollsFinal = calculateFinalAccessibleRolls()

        // Reset the grid to its initial state to try a different solution
        grid.addAll(processInput())
        val accessibleRollsFinalQueue = calculateFinalAccessibleRollsQueue()
        return "Accessible Paper Rolls Initial: $accessibleRollsInitial\n" +
                "Accessible Paper Rolls Final: $accessibleRollsFinal\n" +
                "Accessible Paper Rolls Final (Queue): $accessibleRollsFinalQueue\n"
    }

    private val adjacent = listOf(
        1 to 1,
        1 to 0,
        1 to -1,
        0 to 1,
        0 to -1,
        -1 to 0,
        -1 to 1,
        -1 to -1
    )

    private fun getSurroundingRollsCount(x: Int, y: Int): Int {
        var adjacentCount = 0
        adjacent.forEach { (adjX, adjY) ->
            if (y + adjY < 0 || y + adjY >= grid.size) return@forEach
            if (x + adjX < 0 || x + adjX >= grid[0].length) return@forEach

            if (grid[y + adjY][x + adjX] == '@') adjacentCount++
        }

        return adjacentCount
    }

    private fun calculateInitialAccessibleRolls(): Int {
        var rollsCount = 0
        grid.forEachIndexed { row, line ->
            line.forEachIndexed { col, cell ->
                if (cell == '@' && getSurroundingRollsCount(col, row) < 4) rollsCount++
            }
        }

        return rollsCount
    }

    private fun calculateFinalAccessibleRolls(): Int {
        var rollsCount = 0
        var row = 0
        while (row < grid.size) {
            var col = 0
            while (col < grid[0].length) {

                if (grid[row][col] == '@' && getSurroundingRollsCount(col, row) < 4) {
                    grid.setGridChar(row, col, '.')

                    rollsCount++
                    row = max(-1, row - 2)
                    break
                }

                col++
            }

            row++
        }

        return rollsCount
    }

    private fun calculateFinalAccessibleRollsQueue(): Int {
        var rollsCount = 0
        val queue = ArrayDeque<Pair<Int, Int>>()

        grid.forEachIndexed { row, line ->
            line.forEachIndexed { col, _ ->
                queue.add(row to col)
            }
        }

        while (queue.isNotEmpty()) {
            val (row, col) = queue.removeFirst()

            if (grid[row][col] == '@' && getSurroundingRollsCount(col, row) < 4) {
                grid.setGridChar(row, col, '.')
                rollsCount++

                adjacent.forEach { (adjX, adjY) ->
                    val newRow = row + adjY
                    val newCol = col + adjX
                    if (newRow in 0 until grid.size && newCol in 0 until grid[0].length && grid[newRow][newCol] == '@') {
                        queue.add(newRow to newCol)
                    }
                }
            }
        }

        return rollsCount
    }

    private fun processInput(): List<String> {
        return File("assets/day4/tc2.txt").readLines().map { line -> line.trim() }
    }

    private fun MutableList<String>.setGridChar(row: Int, col: Int, newChar: Char) {
        val rowString = this[row].toCharArray()
        rowString[col] = newChar
        this[row] = String(rowString)
    }

}

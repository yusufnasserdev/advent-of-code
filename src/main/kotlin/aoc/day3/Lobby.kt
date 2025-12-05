package dev.yunas.aoc.day3

import dev.yunas.Solution
import java.io.File

/**
 * Day 3: Lobby
 *
 */

class Lobby : Solution {
    override fun solve(): String {
        val banks = processInput()
        val totalOutputVoltage2 = calculateBanksTotalOutputVoltage(banks, 2)
        val totalOutputVoltage12 = calculateBanksTotalOutputVoltage(banks, 12)
        return "Total Output Voltage (2-digit): $totalOutputVoltage2\n" +
               "Total Output Voltage (12-digit): $totalOutputVoltage12\n"
    }

    fun extractLargestKDigits(digits: String, k: Int): String {
        val n = digits.length
        val toRemove = n - k
        val stack = mutableListOf<Char>()
        var removed = 0

        for (digit in digits) {
            while (stack.isNotEmpty() && stack.last() < digit && removed < toRemove) {
                stack.removeLast()
                removed++
            }
            stack.add(digit)
        }

        while (removed < toRemove) {
            stack.removeLast()
            removed++
        }

        return stack.joinToString("")
    }

    private fun calculateBanksTotalOutputVoltage(banks: List<String>, k: Int): Long {
        var totalOutputVoltage = 0L
        banks.forEach { bank ->
            val largestKDigits = extractLargestKDigits(bank, k)
            val bankTotal = largestKDigits.toLong()
            totalOutputVoltage += bankTotal
        }
        return totalOutputVoltage
    }

    private fun processInput(): List<String> {
        return File("assets/day3/tc2.txt").readLines().map { line -> line.trim() }
    }
}

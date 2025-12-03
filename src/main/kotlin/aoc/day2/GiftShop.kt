package dev.yunas.aoc.day2

import dev.yunas.Solution
import java.io.File
import kotlin.math.pow

/**
 * Day 2: Gift Shop
 * Contains both a Brute-Force and an efficient Pre-Generation solution
 * for counting patterned numbers within ranges.
 */
class GiftShop : Solution {

    override fun solve(): String {
        val numberRanges = parseNumberRanges()

        val (bfPalindromeSum, bfRepeatingSum) = solveBruteForce(numberRanges)
        val (fastPalindromeSum, fastRepeatingSum) = solveFast(numberRanges)

        return """
            |--- Brute-Force Results (Slow for large ranges) ---
            |Even Length Palindrome Sum: $bfPalindromeSum
            |All Repeating Pattern Sum: $bfRepeatingSum
            |
            |--- Fast Pre-Generation Results (Recommended) ---
            |Even Length Palindrome Sum: $fastPalindromeSum
            |All Repeating Pattern Sum: $fastRepeatingSum
        """.trimMargin()
    }

    private data class NumberRange(val start: Long, val end: Long)

    private fun solveBruteForce(ranges: List<NumberRange>): Pair<Long, Long> {
        var totalPalindromeSum = 0L
        var totalRepeatingSum = 0L

        for (range in ranges) {
            var current = range.start
            while (current <= range.end) {
                if (current.isEvenLengthPalindrome()) totalPalindromeSum += current
                if (current.isRepeatedPattern()) totalRepeatingSum += current
                current++
            }
        }
        return totalPalindromeSum to totalRepeatingSum
    }

    private fun solveFast(ranges: List<NumberRange>): Pair<Long, Long> {
        var totalPalindromeSum = 0L
        var totalRepeatingSum = 0L

        for (range in ranges) {
            totalPalindromeSum += ALL_EVEN_PALINDROMES
                .filter { it in range.start..range.end }
                .sum()

            totalRepeatingSum += ALL_REPEATED_PATTERNS
                .filter { it in range.start..range.end }
                .sum()
        }
        return totalPalindromeSum to totalRepeatingSum
    }

    private fun parseNumberRanges(): List<NumberRange> {
        return File("assets/day2/tc2.txt").readLines()
            .flatMap { line -> line.trim().split(',') }
            .map { it.toNumberRange() }
    }

    private fun String.toNumberRange(): NumberRange {
        val bounds = this.split('-')
        return NumberRange(start = bounds.first().toLong(), end = bounds.last().toLong())
    }

    private fun Long.isEvenLengthPalindrome(): Boolean {
        val s = this.toString()
        if (s.length % 2 != 0) return false
        val halfLength = s.length / 2
        return s.take(halfLength) == s.substring(halfLength)
    }

    private companion object {
        fun Long.isRepeatedPattern(): Boolean {
            val s = this.toString()
            val length = s.length

            if (length < 2) return false

            for (partLength in 1 until length) {
                if (length % partLength == 0) {
                    val numberOfParts = length / partLength
                    val pattern = s.take(partLength)

                    if (pattern.repeat(numberOfParts) == s) {
                        return true
                    }
                }
            }
            return false
        }

        fun Long.pow(exponent: Int): Long {
            return this.toDouble().pow(exponent.toDouble()).toLong()
        }

        fun generateAllRepeatedPatterns(): Set<Long> {
            val maxDigits = 18
            val repeatedPatterns = mutableSetOf<Long>()

            for (pLen in 1 until maxDigits) {
                var k = 2
                while (pLen * k <= maxDigits) {
                    val minX = 1L.pow(pLen - 1)
                    val maxX = 1L.pow(pLen) - 1

                    for (x in minX..maxX) {
                        val repeatedNumberString = x.toString().repeat(k)
                        if (repeatedNumberString.length > maxDigits) break
                        val repeatedNumber = repeatedNumberString.toLong()

                        if (repeatedNumber.isRepeatedPattern()) {
                            repeatedPatterns.add(repeatedNumber)
                        }
                    }
                    k++
                }
            }
            return repeatedPatterns
        }

        fun generateAllEvenLengthPalindromes(): Set<Long> {
            val maxHalfDigits = 9
            val palindromes = mutableSetOf<Long>()

            for (l in 1..maxHalfDigits) {
                val minX = 1L.pow(l - 1)
                val maxX = 1L.pow(l) - 1

                for (x in minX..maxX) {
                    val s = x.toString()
                    palindromes.add((s + s.reversed()).toLong())
                }
            }
            return palindromes
        }

        val ALL_REPEATED_PATTERNS: Set<Long> by lazy { generateAllRepeatedPatterns() }
        val ALL_EVEN_PALINDROMES: Set<Long> by lazy { generateAllEvenLengthPalindromes() }
    }

}
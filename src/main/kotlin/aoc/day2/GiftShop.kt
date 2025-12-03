package dev.yunas.aoc.day2

import dev.yunas.Solution
import java.io.File

/**
 * Day 2: Gift Shop
 *
 * Counts the sum of specific pattern-matching numbers within defined ranges.
 */

class GiftShop : Solution {

    override fun solve(): String {
        val numberRanges = parseNumberRanges()

        val palindromeSum = sumEvenLengthPalindromes(numberRanges)
        val repeatingSum = sumAllRepeatingPatterns(numberRanges)

        return "Even Length Palindrome Sum: $palindromeSum, All Repeating Pattern Sum: $repeatingSum"
    }

    data class NumberRange(val start: Long, val end: Long)

    private fun parseNumberRanges(): List<NumberRange> {
        return File("assets/day2/tc2.txt").readLines()
            .flatMap { line -> line.trim().split(',') }
            .map { it.toNumberRange() }
    }

    private fun String.toNumberRange(): NumberRange {
        val bounds = this.split('-')

        return NumberRange(
            start = bounds.first().toLong(),
            end = bounds.last().toLong()
        )
    }

    private fun sumEvenLengthPalindromes(ranges: List<NumberRange>): Long {
        var totalSum = 0L
        ranges.forEach { range ->
            totalSum += getPalindromeSumForRange(range)
        }
        return totalSum
    }

    private fun sumAllRepeatingPatterns(ranges: List<NumberRange>): Long {
        var totalSum = 0L
        ranges.forEach { range ->
            totalSum += getRepeatingPatternSumForRange(range)
        }
        return totalSum
    }

    private fun getPalindromeSumForRange(range: NumberRange): Long {
        var total = 0L
        var current = range.start

        while (current <= range.end) {
            if (current.isEvenLengthPalindrome()) total += current
            current++
        }
        return total
    }

    private fun getRepeatingPatternSumForRange(range: NumberRange): Long {
        var total = 0L
        var current = range.start

        while (current <= range.end) {
            if (current.isRepeatedPattern()) total += current
            current++
        }
        return total
    }

    private fun Long.isEvenLengthPalindrome(): Boolean {
        val s = this.toString()

        if (s.length % 2 != 0) return false
        val halfLength = s.length / 2
        return s.take(halfLength) == s.substring(halfLength)
    }

    private fun Long.isRepeatedPattern(): Boolean {
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

}
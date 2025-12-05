package dev.yunas.aoc.day5

import dev.yunas.Solution
import java.io.File

/**
 * Day 5: Cafeteria
 *
 */

class Cafeteria : Solution {
    override fun solve(): String {
        val (ranges, ingredients) = processInput()

        val freshIngredientsCount = calculateFreshIngredientsCount(ranges, ingredients)
        val freshIngredientsCountInRanges = calculateFreshIngredientsCountInRanges(ranges)

        return "Fresh Ingredients Count: $freshIngredientsCount\n" +
                "Fresh Ingredients Count In Ranges: ${freshIngredientsCountInRanges}\n"
    }

    private fun calculateFreshIngredientsCountInRanges(ranges: List<LongRange>): Long {
        if (ranges.isEmpty()) return 0

        val sortedRanges = ranges.sortedBy { it.first }

        val mergedRanges = mutableListOf<LongRange>()
        var currentRange = sortedRanges.first()

        for (i in 1 until sortedRanges.size) {
            val nextRange = sortedRanges[i]

            if (nextRange.first <= currentRange.last + 1) {
                currentRange = currentRange.first..maxOf(currentRange.last, nextRange.last)
            } else {
                mergedRanges.add(currentRange)
                currentRange = nextRange
            }
        }
        mergedRanges.add(currentRange)

        return mergedRanges.sumOf { it.last - it.first + 1 }
    }


    private fun calculateFreshIngredientsCount(
        ranges: List<LongRange>,
        ingredients: List<Long>
    ): Int {
        var count = 0

        ingredients.forEach { ingredient ->
            run loop@{
                ranges.forEach { range ->
                    if (ingredient in range) {
                        count++
                        return@loop
                    }
                }
            }
        }

        return count
    }

    private fun processInput(): Pair<List<LongRange>, List<Long>> {
        val (rangesText, ingredientsText) = File("assets/day5/tc2.txt").readText().split("\n\n")

        val ranges = rangesText.split('\n').map { rangeText ->
            val bounds = rangeText.split('-')
            bounds.first().toLong()..bounds.last().toLong()
        }

        val ingredients = ingredientsText.split('\n').map { it.toLong() }

        return ranges to ingredients
    }
}

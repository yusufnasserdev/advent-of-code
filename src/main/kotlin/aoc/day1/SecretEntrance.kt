package dev.yunas.aoc.day1

import dev.yunas.Solution
import java.io.File

/**
 * Day 1: Secret Entrance
 *
 * Process a document containing a sequence of rotations to obtain the password
 */

class SecretEntrance : Solution {

    data class Step(
        val direction: Direction,
        val count: Int,
    ) {
        enum class Direction(val value: Char) {
            RIGHT('R'), LEFT('L')
        }
    }

    override fun solve(): String {
        val steps = processInput()
        val passwordNormal = calculatePasswordNormal(steps)
        val passwordSpecial = calculatePasswordSpecial(steps)
        return "Password using normal way is $passwordNormal\nPassword using 0x434C49434B is $passwordSpecial\n"
    }

    private fun processInput(): List<Step> {
        val steps = File("assets/day1/tc2 .txt").readLines().map { line ->
            line.trim().toStep()
        }

        return steps
    }

    private fun calculatePasswordNormal(steps: List<Step>): Int {
        return calculateZeros(steps, countPassZero = false)
    }

    private fun calculatePasswordSpecial(steps: List<Step>): Int {
        return calculateZeros(steps, countPassZero = true)
    }

    private fun calculateZeros(steps: List<Step>, countPassZero: Boolean): Int {
        var dial = STARTING_POINT
        var zeros = 0

        for (step in steps) {
            var count = step.count

            if (count >= MOD) {
                if (countPassZero) {
                    zeros += count / MOD
                }
                count %= MOD
            }

            when (step.direction) {
                Step.Direction.LEFT -> {
                    var nextDial = dial - count
                    if (nextDial < MIN_DIAL) {
                        if (countPassZero && dial > MIN_DIAL) {
                            zeros++
                        }
                        nextDial += MOD
                    }
                    dial = nextDial
                }
                Step.Direction.RIGHT -> {
                    var nextDial = dial + count
                    if (nextDial >= MOD) {
                        if (countPassZero && nextDial != MOD) {
                            zeros++
                        }
                        nextDial %= MOD
                    }
                    dial = nextDial
                }
            }

            if (dial == MIN_DIAL) {
                zeros++
            }
        }

        return zeros
    }

    private fun String.toStep(): Step {
        val direction = if (this.first() == Step.Direction.LEFT.value)
            Step.Direction.LEFT
        else Step.Direction.RIGHT

        val count = this.substring(startIndex = 1).toInt()

        return Step(direction = direction, count = count)
    }

    private companion object {
        const val STARTING_POINT = 50
        const val MOD = 100
        const val MIN_DIAL = 0
    }

}
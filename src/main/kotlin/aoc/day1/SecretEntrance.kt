package dev.yunas.aoc.day1

import dev.yunas.Solution
import java.io.File
import kotlin.collections.forEach

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
        val password = calculatePassword(steps)
        return "Password is $password"
    }

    private fun processInput(): List<Step> {
        val steps = File("assets/day1.txt").readLines().map { line ->
            print("line: $line\n")
            line.trim().toStep()
        }

        return steps
    }

    private fun calculatePassword(steps: List<Step>): Int {
        var password = 0
        var dial = STARTING_POINT

        steps.forEach { step ->
            dial += calculateRotation(step)

            if (dial < MIN_PASSWORD) dial += MOD
            dial %= MOD

            if (dial == 0) password++
        }

        return password
    }

    private fun calculateRotation(step: Step): Int {
        return step.count * if (step.direction == Step.Direction.LEFT) -1 else 1
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
        const val MIN_PASSWORD = 0
    }

}
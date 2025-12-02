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
        val passwordNormal = calculatePasswordNormal(steps)
        val passwordSpecial = calculatePasswordSpecial(steps)
        return "Password using normal way is $passwordNormal\nPassword using 0x434C49434B is $passwordSpecial\n"
    }

    private fun processInput(): List<Step> {
        val steps = File("assets/day1.txt").readLines().map { line ->
            line.trim().toStep()
        }

        return steps
    }

    private fun calculatePasswordNormal(steps: List<Step>): Int {
        var password = 0
        var dial = STARTING_POINT

        steps.forEach { step ->
            dial += calculateRotation(step)

            if (dial < MIN_DIAL) dial += MOD
            dial %= MOD

            if (dial == 0) password++
        }

        return password
    }

    private fun calculatePasswordSpecial(steps: List<Step>): Int {
        var password = 0
        var dial = STARTING_POINT

        steps.forEach { step ->
            var rotationAmount = step.count

            when (step.direction) {
                Step.Direction.RIGHT -> {

                    password += (rotationAmount / MOD)
                    rotationAmount %= MOD

                    if (rotationAmount + dial > MOD) {
                        rotationAmount -= MOD
                        password++
                    }

                    dial = Math.floorMod(dial + rotationAmount, MOD)
                }

                Step.Direction.LEFT -> {
                    val oldDial = dial
                    while (rotationAmount > oldDial) {
                        if (oldDial == 0 && rotationAmount < MOD) {
                            break
                        }
                        rotationAmount -= MOD
                        password++
                    }
                    dial = Math.floorMod(dial - rotationAmount, MOD)
                }
            }

            if (dial == 0) {
                password++
            }
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
        const val MIN_DIAL = 0
    }

}
package dev.yunas.aoc.day3

import dev.yunas.Solution
import dev.yunas.aoc.day1.SecretEntrance.Step
import java.io.File
import kotlin.math.pow

/**
 * Day 3: Lobby
 *
 */
class Lobby : Solution {

    override fun solve(): String {


        return ""
    }


    private fun processInput(): List<Step> {
        val steps = File("assets/day1/tc2.txt").readLines().map { line ->
            line.trim().toStep()
        }

        return steps
    }

}
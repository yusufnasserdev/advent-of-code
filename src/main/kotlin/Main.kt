package dev.yunas

import dev.yunas.aoc.day1.SecretEntrance
import dev.yunas.aoc.day2.GiftShop
import dev.yunas.aoc.day3.Lobby
import dev.yunas.aoc.day4.PrintingDepartment

fun main() {
    val day1Solution = SecretEntrance()
    val day2Solution = GiftShop()
    val day3Solution = Lobby()
    val day4Solution = PrintingDepartment()


    println(day4Solution.solve())
}
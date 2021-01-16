package minesweeper

import java.util.*
import kotlin.random.Random

class MineField {
    var field : Array<CharArray> = arrayOf(
        CharArray(9) { '.' },
        CharArray(9) { '.' },
        CharArray(9) { '.' },
        CharArray(9) { '.' },
        CharArray(9) { '.' },
        CharArray(9) { '.' },
        CharArray(9) { '.' },
        CharArray(9) { '.' },
        CharArray(9) { '.' },
    )

    private val minesCoordinates = arrayListOf<Array<Int>>()

    fun setTotalMines(number: Int) {
        setFieldWithMines(number)
    }

    private fun setFieldWithMines(number: Int) {
        var coordinate: Pair<Int, Int>
        for (i in 0 until number) {
            do {
                coordinate = getRandomCoordinate()
            } while (field[coordinate.first][coordinate.second] == 'X')

            insertMine(coordinate)
        }
    }

    private fun getRandomCoordinate(): Pair<Int,Int> {
        return Pair(Random.nextInt(0, 8), Random.nextInt(0, 8))
    }

    private fun insertMine(coordinate: Pair<Int, Int>) {
        val x = coordinate.first
        val y = coordinate.second

        checkCoordinateForInsertMine((x - 1), (y - 1))
        checkCoordinateForInsertMine((x - 1), (y))
        checkCoordinateForInsertMine((x - 1), (y + 1))
        checkCoordinateForInsertMine((x), (y - 1))
        checkCoordinateForInsertMine((x), (y + 1))
        checkCoordinateForInsertMine((x + 1), (y - 1))
        checkCoordinateForInsertMine((x + 1), (y))
        checkCoordinateForInsertMine((x + 1), (y + 1))

        minesCoordinates.add(arrayOf(x, y))
    }

    private fun checkCoordinateForInsertMine(x: Int, y: Int) {
        try {
            when {
                minesCoordinates.find {
                    it[0] == x && it[1] == y
                }?.isNotEmpty() == true -> return

                field[x][y] == '.' -> field[x][y]  = '1'
                else -> field[x][y] = field[x][y] + 1
            }
        } catch (e: Exception) {
        }
    }

    fun printField() {
        println(" |123456789|")
        println("-|---------|")
        field.forEachIndexed { index, chars ->
            print("$index|")
            chars.forEach {
                print(it)
            }
            print("|")
            println()
        }
        println("-|---------|")
    }

    fun checkUserInput(y: Int, x: Int): Boolean {
        return if(minesCoordinates.find {
                it[1] == y && it[0] == x
            }.isNullOrEmpty()) {
            field[x][y] = '*'
            true
        } else {
            false
        }
    }
}

fun main() {
    val scanner = Scanner(System.`in`)
    println("How many mines do you want on the field?")
    val input = scanner.nextInt()
    val mine = MineField()
    var x: Int
    var y: Int
    var userInput: Int = 0

    mine.setTotalMines(input)
    mine.printField()

    do {
        println("Set/delete mines marks (x and y coordinates): ")
        y = scanner.nextInt()
        x = scanner.nextInt()

        if (!mine.checkUserInput(y, x)) {
            println("There is a number here!")
        } else {
            userInput++
            mine.printField()
        }
    } while (userInput != input)

    println("Congratulations! You found all the mines!")
}
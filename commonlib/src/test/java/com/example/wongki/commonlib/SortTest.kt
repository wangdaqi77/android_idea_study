package com.example.wongki.commonlib

import org.junit.Test
import java.lang.reflect.Proxy
import java.util.*

class SortTest {

    @Test
    fun main(/*args: Array<String>*/) {
        val array = arrayOf(4, -2, -6, 2, 3, 3, 2)
//        selectionTest(array)
//
//        val num = 4
//        val index = findNum(array, num)
//        println("findNum num:$num -> index:$index")

        quickSort(array, 0, array.size - 1)

        array.forEach { a -> println(a) }
//        minesweeperPlayGame()
    }

    private fun quickSort(array: Array<Int>, low: Int, high: Int) {
        if (high <= low) return
        val baseIndex = findBaseIndex(array, low, high)
        quickSort(array, 0, baseIndex - 1)
        quickSort(array, baseIndex + 1, high)
    }

    private fun findBaseIndex(array: Array<Int>, low: Int, high: Int): Int {

        val base = array[low]
        var left = low
        var right = high
        while (left < right) {
            while (left < right && base < array[right]) right--
            if (left < right) array[left++] = array[right]

            while (left < right && base > array[left]) left++
            if (left < right) array[right--] = array[left]

        }
        array[left] = base
        return left
    }

    private fun swapArray(array: Array<Int>, i: Int, j: Int) {
        val temp = array[i]
        array[i] = array[j]
        array[j] = temp
    }

    // 获取周围雷的数量
    class Point {
        //坐标点
        var x = 0
        var y = 0
        var isMine = false // 是否是地雷
        var aroundMineNum = 0 //周围地雷的数量
        var isClicked = false // 是否点击过


        fun show(gameOver: Boolean = false): String {
            return if (isClicked || gameOver) if (isMine) "*" else "$aroundMineNum" else "□"
        }

        /**
         * 点击的位置是否匹配
         */
        fun matcher(point: Array<Int>) = point[0] == x && point[1] == y

        // 如果匹配到坐标点 执行点击动作
        fun click() {
            isClicked = true
        }
    }

    /**
     * 开始扫雷游戏
     */
    private fun minesweeperPlayGame() {
        val scanner = Scanner(System.`in`)
        println("请输入行:")
        // 游戏开始的矩阵 几行几列
        val row = scanner.nextInt()

        println("请输入列:")
        val column = scanner.nextInt()

        // 格子总数量
        val len = row * column

        println("请输入地雷的数量(不能大于总格子数量的一半):")
        var minesNum = 0
        while (true) {
            minesNum = scanner.nextInt()
            if (len <= 0 && minesNum > len / 2) {
                println("您输入的地雷数量过多，请重新输入:")
            } else {
                break
            }
        }

        // 生成所有的地雷索引
        val mines = generateMines(len, minesNum)

        // 地雷数量
        val minesLen = mines.size

        // 生成矩阵
        val matrix = Array<Point>(len) { index ->
            //坐标点
            val x = index.rem(row)
            val y = index / row

            val isMine = mines.contains(index)

            var aroundMineNum = 0 // 周围地雷的数量


            if (!isMine) {
                // 找到周围所有的地雷
                val aroundMines = findAroundMines(row, column, x, y, mines)
                aroundMineNum = aroundMines.size
            }

            Point().apply {
                this.x = x
                this.y = x
                this.isMine = isMine
                this.aroundMineNum = aroundMineNum
            }
        }


        val totalSafeNum = len - minesLen // 安全格子的总数量
        var safeNum = 0 // 记忆当前踩过格子的安全数量
        while (true) {
Proxy.newProxyInstance()
            printMatrix(row, matrix)

            println("请点击（ex:点击第一个格子点输入:0,0） :")

            try {
                val next = scanner.next()
                val point: List<String>
                point = next.trim().split(",")
                val x = point[0].toInt()
                val y = point[1].toInt()
                val clickIndex = findIndexByPoint(row, column, x, y)
                val preClickPoint = matrix[clickIndex]
                if (!preClickPoint.isClicked) {
                    preClickPoint.click()
                    // 失败
                    if (preClickPoint.isMine) {
                        println("踩到地雷了（x:$x,y:$y），游戏失败~~")
                        printMatrix(row, matrix, gameOver = true)
                        break
                    }
                    safeNum++
                } else {
                    println("已经踩过!")
                }

                // 胜利
                if (safeNum == totalSafeNum) {
                    println("恭喜，游戏胜利者~~")
                    printMatrix(row, matrix, gameOver = true)
                    break
                }
            } catch (e: Exception) {
                println("输入异常")
            }

        }

    }

    /**
     * 打印矩阵格子
     */
    private fun printMatrix(row: Int, matrix: Array<Point>, gameOver: Boolean = false) {
        for (index in 0 until matrix.size) {
            val point = matrix[index]
            print(point.show(gameOver) + "  ")

            if (point.y == row - 1) {
                println("")
                println("")
            }
        }
    }

    /**
     * 找到周边的地雷
     */
    private fun findAroundMines(row: Int, column: Int, x: Int, y: Int, mines: Set<Int>): Set<Int> {
        val roundIndex = HashSet<Int>()
        val index1 = findIndexByPoint(row, column, x + 1, y)
        val index2 = findIndexByPoint(row, column, x + 1, y + 1)
        val index3 = findIndexByPoint(row, column, x, y + 1)
        val index4 = findIndexByPoint(row, column, x - 1, y + 1)
        val index5 = findIndexByPoint(row, column, x - 1, y)
        val index6 = findIndexByPoint(row, column, x - 1, y - 1)
        val index7 = findIndexByPoint(row, column, x, y - 1)
        val index8 = findIndexByPoint(row, column, x + 1, y - 1)
        roundIndex.add(index1)
        roundIndex.add(index2)
        roundIndex.add(index3)
        roundIndex.add(index4)
        roundIndex.add(index5)
        roundIndex.add(index6)
        roundIndex.add(index7)
        roundIndex.add(index8)
        val roundMines = HashSet<Int>()
        roundMines.addAll(mines)
        roundMines.retainAll(roundIndex)
        return roundMines
    }

    /**
     * 根据坐标点找到索引位置
     */
    private fun findIndexByPoint(row: Int, column: Int, x: Int, y: Int): Int {
        if (x < 0 || y < 0 || x > row - 1 || y > column - 1) return -1
        return y * row + x
    }

    /**
     * 生成地雷
     */
    private fun generateMines(len: Int, minesNum: Int): Set<Int> {
        val mines = HashSet<Int>()
        var tempMineIndex = 0
        val random = Random()
        while (minesNum != mines.size) {
            tempMineIndex = random.nextInt(len - 1)
            mines.add(tempMineIndex)
        }
        return mines
    }


    private fun findNum(array: Array<Int>, num: Int): Int {
        val len = array.size

        var left = 0
        var right = len - 1
        var midIndex = -1
        while (left <= right) {
            midIndex = left + (right - left) / 2
            if (num > array[midIndex]) {
                left = midIndex + 1
            } else if (num < array[midIndex]) {
                right = midIndex - 1
            }
        }

        return midIndex

    }

    fun selectionTest(array: Array<Int>) {
        val len = array.size
        for (i in 0 until len - 1) {
            var minNumIndex = i
            var minNum = array[minNumIndex]
            for (j in i + 1 until len) {
                val num = array[j]
                minNumIndex = if (minNum > num) j else minNumIndex
                minNum = array[minNumIndex]
            }
            array.swap(i, minNumIndex)


        }

        for (i in 0 until len) {
            println("index:$i -> ${array[i]}")
        }

    }


    fun <T> Array<T>.swap(i: Int, j: Int) {
        val temp = this[i]
        this[i] = this[j]
        this[j] = temp
    }
}
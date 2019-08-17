package com.example.wongki.modularization.other

import java.util.*

/**
 * @author  wangqi
 * date:    2019-08-02
 * email:   wangqi7676@163.com
 * desc:    .
 */
object Study {
    class Data {
        companion object {
            var head: Data? = null
        }

        var data: String? = null
        var next: Data? = null

    }

    class TreeNode<T>(var data: T?) {

        var LChild: TreeNode<T>? = null
        var RChild: TreeNode<T>? = null

    }

    fun testQuickSort() {
        var a =1
        if (true) {
            a++
        }

        val array = arrayOf(2, 1, 9, 10, 3)
        quickSort(array)
        val sb = StringBuilder()
        array.forEach {sb.append("$it").append(",") }
        println(sb.toString())
    }

    private fun quickSort(array: Array<Int>, low: Int = 0, high: Int = array.size-1) {
        if (low < high) {
            val baseIndex = findBaseIndex(array, low, high)
            quickSort(array, low, baseIndex - 1)
            quickSort(array, baseIndex + 1, high)
        }
    }

    private fun findBaseIndex(array: Array<Int>, low: Int, high: Int): Int {
        val baseValue = array[low]  // 基准值
        var low = low
        var high = high
        while (low < high) {
            while (low < high && array[high] >= baseValue) {
                high--
            }
            array[low] = array[high]
            while (low < high && array[low] <= baseValue) {
                low++
            }
            array[high] = array[low]
        }

        array[low] = baseValue

        return low
    }

    fun test1() {
        val treeNode1 = TreeNode(1)
        val treeNode2 = TreeNode(2)
        val treeNode3 = TreeNode(3)
        val treeNode4 = TreeNode(4)
        val treeNode5 = TreeNode(5)
        val treeNode6 = TreeNode(6)
        val treeNode7 = TreeNode(7)
        treeNode1.LChild = treeNode2
        treeNode1.RChild = treeNode3

        treeNode2.LChild = treeNode4
        treeNode2.RChild = treeNode5

        treeNode3.LChild = treeNode6
        treeNode3.RChild = treeNode7

        //preTree(treeNode1)
        preTreeLoop(treeNode1)
        midTreeLoop(treeNode1)
        aftTreeLoop(treeNode1)


    }

    private fun aftTreeLoop(treeNode: TreeNode<Int>?) {
        if (treeNode == null) return
        aftTreeLoop(treeNode.LChild)
        aftTreeLoop(treeNode.RChild)
        print("${treeNode.data},")
        println()
    }

    private fun midTreeLoop(treeNode: TreeNode<Int>?) {
        if (treeNode == null) return
        midTreeLoop(treeNode.LChild)
        print("${treeNode.data},")
        midTreeLoop(treeNode.RChild)
        println()
    }

    private fun preTreeLoop(treeNode: TreeNode<Int>?) {
        if (treeNode == null) return
        print("${treeNode.data},")
        preTreeLoop(treeNode.LChild)
        preTreeLoop(treeNode.RChild)
        println()
    }

    private fun preTree(head: TreeNode<Int>) {
        val stack = Stack<TreeNode<Int>>()
        stack.push(head)
        while (stack.size != 0) {
            val cur = stack.pop()
            print("${cur.data},")
            if (cur.RChild != null) {
                stack.push(cur.RChild)
            }
            if (cur.LChild != null) {
                stack.push(cur.LChild)
            }
        }
        println()
    }

    fun test() {
        val a = Data()
        val b = Data()
        val c = Data()
        val d = Data()
        Data.head = a
        a.data = "a"
        b.data = "b"
        c.data = "c"
        d.data = "d"

        a.next = b
        b.next = c
        c.next = d

        Data.head = convert(Data.head)

        print(Data.head)
    }

    private fun convert(head: Data?): Data? {
        if (head?.next == null) {
            return head
        }

        var pre: Data? = null
        var cur = head
        var next: Data? = null
        while (cur != null) {
            next = cur.next
            cur.next = pre
            pre = cur
            cur = next
        }
        return pre

    }

    private fun print(head: Data?) {
        var cur = head
        while (cur != null) {
            println(cur.data)
            cur = cur.next
        }

    }


}
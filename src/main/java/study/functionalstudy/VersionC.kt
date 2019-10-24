package com.functionalstudy

import java.util.*

/**
 * 字符串列表变换，使用Kotlin API实现
 */
class VersionC {
    fun cleanName(nameList: List<String>): String {
        return nameList.filter {
            it.length > 1
        }.map {
            it.substring(0, 1).toUpperCase() + it.substring(1, it.length)
        }.reduce { acc, s ->
            "$acc, $s"
        }
    }
}

fun main() {
    val orgList = Arrays.asList("neal", "s", "stu", "j", "rich",
            "bob", "aiden", "j", "ethan", "liam",
            "mason", "noah", "lucas", "jacob", "jayden", "jack")

    val test = VersionC()
    print(test.cleanName(orgList))
}
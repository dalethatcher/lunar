package com.dalethatcher.lunar

import java.lang.reflect.Method

interface Expression {
    fun evaluate(data: Any): ResultRow
}

data class Column(val identifier: String) : Expression {
    override fun evaluate(data: Any): ResultRow {
        val method = getMethod(data)

        return ResultRow(listOf(method.invoke(data).toString()))
    }

    private fun getMethod(data: Any): Method {
        val functionName = "get${identifier.substring(0, 1).toUpperCase()}${identifier.substring(1)}"

        return data.javaClass.getMethod(functionName)
    }
}

data class IntegerConstant(val value: Int) : Expression {
    override fun evaluate(data: Any): ResultRow {
        return ResultRow(listOf(value.toString()))
    }
}

data class Query(val expression: Expression, val table: String?)

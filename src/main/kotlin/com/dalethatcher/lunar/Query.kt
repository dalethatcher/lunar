package com.dalethatcher.lunar

interface Expression

data class Column(val identifier: String) : Expression

data class IntegerConstant(val value: Int) : Expression

data class Query(val expression: Expression, val table: String?)

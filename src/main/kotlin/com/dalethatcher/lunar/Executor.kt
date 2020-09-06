package com.dalethatcher.lunar

interface DataTable {
    fun all(): Sequence<Any>
    fun byId(id: String): Any
}

interface DataProvider {
    fun table(name: String): DataTable?
}

data class ResultRow(val row: List<String?>)

class Executor(val dataProvider: DataProvider) {
    val UNDEFINED_ROW = Object()
    val NULL_DATA_TABLE = object : DataTable {
        override fun all(): Sequence<Any> = sequenceOf(UNDEFINED_ROW)

        override fun byId(id: String): Any {
            throw Exception("Must specify table to load by id ($id).")
        }

    }

    fun execute(query: String): Sequence<ResultRow> = execute(parse(query))

    private fun execute(query: Query): Sequence<ResultRow> {
        val dataTable = if (query.table == null) NULL_DATA_TABLE else dataProvider.table(query.table)

        if (dataTable == null) {
            throw Exception("Table ${query.table} is unknown!")
        }

        return dataTable.all().map { data -> execute(data, query.expression) }
    }

    private fun execute(data: Any, expression: Expression): ResultRow = expression.evaluate(data)
}
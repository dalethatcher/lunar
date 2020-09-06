package com.dalethatcher.lunar

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.fail

data class TestRow(val name: String)

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ExecutorTest {
    @Test
    fun canFetchSimplePropertyOfAllObjects() {
        val executor = Executor(object : DataProvider {
            override fun table(name: String) =
                if (name == "test_table")
                    object : DataTable {
                        override fun all(): Sequence<Any> = sequenceOf(TestRow("alpha"))
                        override fun byId(id: String) = fail("should not have been called")
                    }
                else
                    null
        })

        val result = executor.execute("select name from test_table")

        assertEquals(listOf(ResultRow(listOf("alpha"))), result.toList())
    }
}
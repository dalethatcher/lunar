package com.dalethatcher.lunar

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ParserTest {
    @Test
    fun canParseSimpleConstantExpression() {
        val result = parse("select 1")

        assertEquals(Query(IntegerConstant(1), null), result)
    }

    @Test
    fun canParseSimpleTableExpression() {
        val result = parse("select id from data")

        assertEquals(Query(Column("id"), "data"), result)
    }
}
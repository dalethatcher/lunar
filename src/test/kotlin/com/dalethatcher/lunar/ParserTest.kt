package com.dalethatcher.lunar

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ParserTest {
    @Test
    fun canParseSimpleConstantExpression() {
        val result = parse("select 1")

        Assertions.assertEquals(Query(IntegerConstant(1), null), result)
    }
}
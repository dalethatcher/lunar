package com.dalethatcher.lunar

import com.dalethatcher.lunar.parser.LunarLexer
import com.dalethatcher.lunar.parser.LunarParser
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream

fun parse(input: String): Query {
    val lexer = LunarLexer(CharStreams.fromString(input))
    val tokens = CommonTokenStream(lexer)
    val parser = LunarParser(tokens)
    val visitor = QueryVisitor()

    return visitor.visit(parser.parse())
}
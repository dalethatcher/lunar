package com.dalethatcher.lunar

import com.dalethatcher.lunar.parser.LunarBaseVisitor
import com.dalethatcher.lunar.parser.LunarLexer
import com.dalethatcher.lunar.parser.LunarParser
import org.antlr.v4.runtime.tree.TerminalNode

class ExpressionVisitor : LunarBaseVisitor<Expression>() {
    override fun visitSelect_core(ctx: LunarParser.Select_coreContext?): Expression {
        return visit(ctx!!.result_column(0))
    }

    override fun visitLiteral_value(ctx: LunarParser.Literal_valueContext?): Expression {
        val node: TerminalNode = ctx!!.getChild(0) as TerminalNode

        return when (node.symbol.type) {
            LunarLexer.NUMERIC_LITERAL -> IntegerConstant(Integer.parseInt(node.text))
            else -> TODO()
        }
    }
}

class QueryVisitor : LunarBaseVisitor<Query>() {
    override fun visitParse(ctx: LunarParser.ParseContext?): Query {
        val firstSqlStatement = ctx!!.sql_stmt_list(0)
        val expression = ExpressionVisitor().visit(firstSqlStatement)

        return Query(expression, null)
    }
}
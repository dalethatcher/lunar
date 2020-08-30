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

    override fun visitColumn_name(ctx: LunarParser.Column_nameContext?): Expression {
        return Column(StringVisitor().visit(ctx!!.any_name()))
    }
}

class QueryVisitor : LunarBaseVisitor<Query>() {
    override fun visitParse(ctx: LunarParser.ParseContext?): Query {
        val firstSqlStatement = ctx!!.sql_stmt_list(0)
        val expression = ExpressionVisitor().visit(firstSqlStatement)
        val table = TableVisitor().visit(firstSqlStatement)

        return Query(expression, table)
    }
}

class TableVisitor : LunarBaseVisitor<String?>() {
    override fun visitSelect_core(ctx: LunarParser.Select_coreContext?): String? {
        if (ctx!!.table_or_subquery().isEmpty()) {
            return null
        }
        else {
            return visit(ctx.table_or_subquery(0))
        }
    }

    override fun visitTable_or_subquery(ctx: LunarParser.Table_or_subqueryContext?): String {
        return StringVisitor().visit(ctx!!.table_name())
    }
}

class StringVisitor : LunarBaseVisitor<String>() {
    override fun visitAny_name(ctx: LunarParser.Any_nameContext?): String {
        return ctx!!.IDENTIFIER().text
    }
}
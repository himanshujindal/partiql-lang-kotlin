package org.partiql.testscript.parser

import com.amazon.ion.system.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.partiql.testscript.parser.ast.AstNode
import org.partiql.testscript.parser.ast.ModuleNode
import java.nio.charset.*

abstract class BaseParseTests {
    companion object {
        @JvmStatic
        protected val ion = IonSystemBuilder.standard().build()!!

        @JvmStatic
        protected val parser = Parser(ion)
    }

    protected fun assertParseError(input: String, expectedErrorMessage: String) {
        val exception = assertThrows<ParserException> { parser.parse(createInput(input)) }
        assertEquals(expectedErrorMessage, exception.message)
    }

    protected fun singleModulesList(vararg node: AstNode) = listOf(ModuleNode(node.asList(),
            ScriptLocation("input[0]", 0)))

    protected fun assertParse(vararg ionDocuments: String, expected: List<ModuleNode>) {
        val inputs = createInput(*ionDocuments)

        assertEquals(expected, parser.parse(inputs))
    }
}
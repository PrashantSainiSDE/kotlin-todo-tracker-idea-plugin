package com.plugin.kotlintodotracker.kotlintodotracker

import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange

data class TodoItem(val text: String, val lineNumber: Int)

object TodoScanner {
    fun scan(document: Document): List<TodoItem> {
        val todos = mutableListOf<TodoItem>()
        for (i in 0 until document.lineCount) {
            val lineText = document.getText(TextRange.create(
                document.getLineStartOffset(i),
                document.getLineEndOffset(i)
            ))
            if (lineText.contains("TODO")) {
                todos.add(TodoItem(lineText.trim(), i))
            }
        }
        return todos
    }
}

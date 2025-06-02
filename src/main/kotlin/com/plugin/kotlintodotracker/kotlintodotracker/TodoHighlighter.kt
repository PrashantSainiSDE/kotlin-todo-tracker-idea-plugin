package com.plugin.kotlintodotracker.kotlintodotracker

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import com.intellij.openapi.editor.markup.*
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import com.intellij.ui.JBColor

class TodoHighlighter : ProjectActivity {

    private fun highlightTodos(editor: Editor) {
        val document = editor.document
        val markup = editor.markupModel
        markup.removeAllHighlighters()

        val todos = TodoScanner.scan(document)
        for (todo in todos) {
            val start = document.getLineStartOffset(todo.lineNumber)
            val end = document.getLineEndOffset(todo.lineNumber)
            markup.addRangeHighlighter(
                start, end,
                HighlighterLayer.ADDITIONAL_SYNTAX,
                TextAttributes().apply {backgroundColor = JBColor.PINK },
                HighlighterTargetArea.EXACT_RANGE
            )
        }
    }

    override suspend fun execute(project: Project) {
        val editorFactory = EditorFactory.getInstance()

        editorFactory.eventMulticaster.addDocumentListener(object : DocumentListener {
            override fun documentChanged(event: DocumentEvent) {
                val editor = editorFactory.allEditors.find { it.document == event.document }
                if (editor != null) {
                    highlightTodos(editor)
                }
            }
        }, project)
    }
}
package com.plugin.kotlintodotracker.kotlintodotracker

import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.editor.ScrollType
import com.intellij.openapi.editor.CaretModel
import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import com.intellij.openapi.fileEditor.FileEditorManagerEvent
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.ui.components.JBList
import com.intellij.util.messages.MessageBusConnection
import java.awt.BorderLayout
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*

class TodoPanel(private val project: Project) : JPanel(BorderLayout()) {
    private val listModel = DefaultListModel<TodoItem>()
    private val todoList = JBList(listModel)
    private var currentDocumentListener: DocumentListener? = null

    init {
        add(JScrollPane(todoList), BorderLayout.CENTER)
        todoList.selectionMode = ListSelectionModel.SINGLE_SELECTION

        todoList.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                if (e.clickCount == 1) {
                    val selected = todoList.selectedValue ?: return
                    jumpToTodo(selected)
                }
            }
        })

        val connection: MessageBusConnection = project.messageBus.connect()
        connection.subscribe(
            FileEditorManagerListener.FILE_EDITOR_MANAGER,
            object : FileEditorManagerListener {
                override fun selectionChanged(event: FileEditorManagerEvent) {
                    refreshTodos()
                    attachLiveUpdateListener()
                }
            }
        )

        refreshTodos()
        attachLiveUpdateListener()
    }

    private fun attachLiveUpdateListener() {
        val editor = FileEditorManager.getInstance(project).selectedTextEditor ?: return
        editor.document

        currentDocumentListener?.let {
            EditorFactory.getInstance().eventMulticaster.removeDocumentListener(it)
        }

        val listener = object : DocumentListener {
            override fun documentChanged(event: DocumentEvent) {
                refreshTodos()
            }
        }
        EditorFactory.getInstance().eventMulticaster.addDocumentListener(listener, project)
        currentDocumentListener = listener
    }

    private fun refreshTodos() {
        val editor = FileEditorManager.getInstance(project).selectedTextEditor ?: return
        val document = editor.document
        val todos = TodoScanner.scan(document)

        listModel.clear()
        todos.forEach { listModel.addElement(it) }
    }

    private fun jumpToTodo(item: TodoItem) {
        val editor = FileEditorManager.getInstance(project).selectedTextEditor ?: return
        val document = editor.document
        val caretModel: CaretModel = editor.caretModel
        val offset = document.getLineStartOffset(item.lineNumber)
        caretModel.moveToOffset(offset)
        editor.scrollingModel.scrollToCaret(ScrollType.CENTER)
    }
}

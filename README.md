# ✅ Kotlin TODO Tracker – IntelliJ Plugin

A lightweight IntelliJ IDEA plugin that **scans Kotlin files** for `TODO` comments, **highlights them inline**, and displays them in a dedicated **side panel with clickable navigation**.

---

## 📌 Features

- ✏️ **Inline Highlighting:** Detects and highlights all `TODO` comments in Kotlin files using IntelliJ's `MarkupModel`.
- 🗂️ **Tool Window Panel:** Displays a live-updated list of TODOs in the sidebar.
- 🔁 **Live Syncing:** Automatically updates the list and highlights when:
  - A new TODO is added or removed.
  - You switch between open Kotlin files.
- 🎯 **Click to Jump:** Double-click any TODO in the panel to jump directly to its location in the editor.

---

## 🧠 How It Works

| Component                  | Responsibility                                                      |
|---------------------------|----------------------------------------------------------------------|
| `TodoScanner.kt`          | Parses a Kotlin file line-by-line to find all `TODO` comments.       |
| `TodoHighlighter.kt`      | Highlights all detected TODO lines in the current editor.            |
| `TodoPanel.kt`            | Custom tool window that shows the TODO list with line info + clicks. |
| `plugin.xml`              | Registers the startup listener and tool window factory.              |

---

## 🚀 Getting Started

### 1. Prerequisites
- IntelliJ IDEA with **DevKit plugin installed**
- Gradle or IntelliJ Plugin SDK Project

### 2. Build & Run
- Open the project in IntelliJ IDEA.
- Run the Plugin via **Run > Run 'Plugin'**.
- A new IntelliJ window will open with the plugin installed.

### 3. Try It Out
- Open a `.kt` file.
- Add lines like: `// TODO: Refactor this method`
- Watch them show up **highlighted in pink** and listed in the **"TODOs" Tool Window**.

---

## 🔍 TODO (Next Milestones)

- [ ] **Filter TODOs** by keyword (e.g., `FIXME`, `URGENT`, etc.)
- [ ] **Persist TODOs** between IDE restarts
- [ ] Add **"Refresh" button** or auto debounce when typing fast
- [ ] Highlight only the comment text, not full line

---

## 🧑‍💻 Author

**Prince**  
Intern – IntelliJ Plugin Development  
> Kotlin-powered productivity!

---

## 🛠️ Built With

- IntelliJ Platform SDK
- Kotlin
- DevKit Plugin

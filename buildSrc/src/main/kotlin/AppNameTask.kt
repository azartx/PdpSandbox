import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

abstract class AppNameTask : DefaultTask() {

    @get:Input
    abstract var appName: String

    init {
        group = "AndroidAppPluginTask"
        description = "Task to applying an application name from the build gradle"
    }

    @TaskAction
    fun applyAppName() {
        if (appName.isBlank()) throw IllegalArgumentException(
            "AppName must not be empty."
        )

        val projectDir = project.projectDir.invariantSeparatorsPath

        val strResFile = project.file("$projectDir/src/main/res/values/strings.xml")

        if (strResFile.exists()) {
            val contentLines = strResFile.readText().lines()
            val appNameLineIndex = contentLines.indexOfFirst {
                it.contains("<string name=\"app_name\">")
            }.takeIf { it != -1 }

            val newContent = if (appNameLineIndex == null) {
                buildList {
                    addAll(contentLines)
                    add(1, "    <string name=\"app_name\">$appName</string>")
                }
            } else {
                contentLines.mapIndexed { index, item ->
                    if (index == appNameLineIndex)
                        "    <string name=\"app_name\">$appName</string>" else item
                }
            }.joinToString("\n")

            strResFile.writeText(newContent)
        } else {
            strResFile.createNewFile()

            val content = buildString {
                appendLine("<resources>")
                appendLine("    <string name=\"app_name\">$appName</string>")
                append("</resources>")
            }

            strResFile.writeText(content)
        }
    }
}
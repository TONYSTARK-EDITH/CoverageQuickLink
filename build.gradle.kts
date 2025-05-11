
plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.25"
    id("org.jetbrains.intellij.platform") version "2.3.0"
}

group = "org.stark"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
        marketplace()
    }
}



// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin.html
dependencies {
    intellijPlatform {
        val type = providers.gradleProperty("platformType")
        val version = providers.gradleProperty("platformVersion")

        create(type, version)
        bundledPlugin("com.intellij.java")

    }
    testImplementation("junit:junit:4.13.2")
}

intellijPlatform {
    pluginConfiguration {
        ideaVersion {
            sinceBuild = "241"
            untilBuild = "252.*"
        }

        changeNotes = """
      <ul>
        <li>Initial release</li>
        <li>Added <b>Open in Coverage Report</b> action for instant viewing of coverage reports directly from supported files (.ic, .exec, .xml)</li>
        <li>Optimized coverage file type detection and coverage runner selection</li>
        <li>Improved notifications and error handling for unsupported formats and tool window issues</li>
        <li>Updated action icon to use Alias icon for better visual representation</li>
        <li>Added comprehensive guidelines for building, testing, and contributing in <code>guidelines.md</code> and <code>README.md</code></li>
      </ul>
    """.trimIndent()
    }
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }
}

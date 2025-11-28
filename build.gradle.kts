plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.1.0"
    id("org.jetbrains.intellij.platform") version "2.5.0"
}

group = "com.wonders"
version = "1.0-SNAPSHOT"

repositories {
    maven { url = uri("https://maven.aliyun.com/repository/jetbrains") }
    maven { url = uri("https://maven.aliyun.com/repository/public") }
    // 保留官方仓库作为 fallback（可选）
    maven { url = uri("https://cache-redirector.jetbrains.com/intellij-repository/releases") }
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

// Configure IntelliJ Platform Gradle Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin.html
dependencies {
    intellijPlatform {
        create("IC", "2025.1")
        testFramework(org.jetbrains.intellij.platform.gradle.TestFrameworkType.Platform)

        // Add Java plugin dependency
       // bundledPlugin("com.intellij.java")
    }

    // Add Gson dependency for JSON formatting
    implementation("com.google.code.gson:gson:2.10.1")

    // Add JUnit for testing
    testImplementation("junit:junit:4.13.2")
}

intellijPlatform {
    pluginConfiguration {
        ideaVersion {
            sinceBuild = "251"
        }

        changeNotes = """
            Initial version with JSON formatting, URL encoding/decoding, 
            Base64 encoding/decoding, encryption/decryption, 
            and random string generation capabilities.
        """.trimIndent()
    }
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "21"
        targetCompatibility = "21"
    }

    test {
        useJUnit()
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
    }
}

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.1.0"
    id("org.jetbrains.intellij.platform") version "2.5.0"
}

group = "com.wonders.cToolKit"
version = "1.0.3"

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

    // Add ZXing dependency for QR code generation
    implementation("com.google.zxing:core:3.5.3")
    implementation("com.google.zxing:javase:3.5.3")

    // Add JUnit for testing
    testImplementation("junit:junit:4.13.2")
}

intellijPlatform {
    pluginConfiguration {
        ideaVersion {
            sinceBuild = "251"
        }

        changeNotes = """
            1.0.3:
            - 新增时间戳转换工具：支持秒/毫秒自动识别，实时转换，时区选择（默认中国时区）
            - 新增二维码生成器：支持输入链接或文字生成二维码，可自定义尺寸，支持保存为PNG
            - 输出区域支持右键菜单复制
            - 修复主窗口 GridLayout 初始渲染不完整的问题
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

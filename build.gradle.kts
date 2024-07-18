plugins {
    kotlin("jvm") version "2.0.0"
}

group = "art.mikezak.register_map"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.register("runAnalyzer") {
    dependsOn("classes") // Ensure that the project classes are compiled before running the analyzer.
    doLast {
        javaexec {
            mainClass = "analyzer.MainKt" // Specify the main class to run.
            classpath = sourceSets.main.get().runtimeClasspath // Use the runtime classpath of the main source set.
            args = listOf() // Add any arguments if needed.
        }
    }
}

tasks.test {
    useJUnitPlatform()
}
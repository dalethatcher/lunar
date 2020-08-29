plugins {
    kotlin("jvm") version "1.3.72"
    antlr
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    antlr("org.antlr:antlr4:4.7.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.0")
}

tasks.compileKotlin {
    dependsOn(tasks.generateGrammarSource)
}

tasks.generateGrammarSource {
    arguments.add("-visitor")
}

tasks.test {
    useJUnitPlatform()
}
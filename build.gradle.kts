plugins {
    kotlin("jvm") version "1.9.22"
    id("org.openjfx.javafxplugin") version "0.0.13"
    application
}

group = "org.ltaddey"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val javaFXModules = listOf(
    "javafx.controls",
    "javafx.fxml",
    "javafx.graphics"
)

val javafxVersion = "17.0.2"

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    for (module in javaFXModules) {
        implementation("org.openjfx:${module}:${javafxVersion}")
    }
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}

javafx {
    version = javafxVersion
    modules = javaFXModules
}

application {
    mainClass.set("org.ltaddey.TetrisGameKt")
}

tasks.withType<JavaExec> {
    jvmArgs = listOf(
        "--module-path", classpath.asPath,
        "--add-modules", javaFXModules.joinToString(",")
    )
}
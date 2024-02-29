import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.22"
}

group = "moe.muska.ami.spcraft.crtcore"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://lss233.littleservice.cn/repositories/minecraft")
    maven("https://maven.aliyun.com/nexus/content/groups/public/")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    implementation("io.papermc.paper:paper-api:1.19-R0.1-SNAPSHOT")
    implementation("me.clip:placeholderapi:2.11.5")
    implementation("net.kyori:adventure-text-minimessage:4.1.0-SNAPSHOT")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.compileJava {
    options.encoding = "UTF-8"
    sourceCompatibility = JavaVersion.VERSION_21.toString()
    targetCompatibility = JavaVersion.VERSION_21.toString()
}
tasks.compileTestJava {
    options.encoding = "UTF-8"
    sourceCompatibility = JavaVersion.VERSION_21.toString()
    targetCompatibility = JavaVersion.VERSION_21.toString()
}
tasks.compileKotlin {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_21.toString()
}
tasks.compileTestKotlin {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_21.toString()
}

tasks.processResources {
    inputs.properties(mapOf("version" to version))
    outputs.upToDateWhen { false }

    duplicatesStrategy = DuplicatesStrategy.INCLUDE

    from(sourceSets["main"].resources.srcDirs) {
        include("**/paper-plugin.yml")
        expand(project.properties)
    }
}

tasks.create<Jar>("fatJar") {
    duplicatesStrategy = DuplicatesStrategy.FAIL
    val sourceMain = java.sourceSets["main"]
    from(sourceMain.output)

    configurations.runtimeClasspath.get().filter {
        it.name.startsWith("kotlin-stdlib")
    }.forEach { jar ->
        from(zipTree(jar))
    }
}

tasks.build {
    dependsOn(
        tasks.processResources,
        "fatJar"
    )
}
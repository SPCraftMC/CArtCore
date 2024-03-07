plugins {
    kotlin("jvm") version "1.9.22"
}

group = "moe.muska.ami.spcraft.crtcore"
version = "1.0.1-SNAPSHOT"

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
    implementation("com.zaxxer:HikariCP:5.1.0")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.compileJava {
    options.encoding = "UTF-8"
    sourceCompatibility = JavaVersion.VERSION_17.toString()
    targetCompatibility = JavaVersion.VERSION_17.toString()
}
tasks.compileTestJava {
    options.encoding = "UTF-8"
    sourceCompatibility = JavaVersion.VERSION_17.toString()
    targetCompatibility = JavaVersion.VERSION_17.toString()
}
tasks.compileKotlin {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
}
tasks.compileTestKotlin {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
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
        val fatList = arrayOf(
            "kotlin-stdlib",
            "HikariCP"
        )
        var res = false
        for (dep in fatList) if (it.name.startsWith(dep)) res = true
        return@filter res
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
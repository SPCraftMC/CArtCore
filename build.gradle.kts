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
    implementation("io.papermc.paper:paper-api:1.18-R0.1-SNAPSHOT")
    implementation("me.clip:placeholderapi:2.11.5")
    implementation("net.kyori:adventure-text-minimessage:4.1.0-SNAPSHOT")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

tasks.processResources {
    doLast {
        val props = mapOf("version" to version)
        inputs.properties(props)
        outputs.upToDateWhen { false }
        from(sourceSets["main"].resources.srcDirs) {
            include("**/paper-plugin.yml")
            expand(props)
        }
    }
}

import io.izzel.taboolib.gradle.*

plugins {
    `java-library`
    id("io.izzel.taboolib") version "2.0.27"
    id("org.jetbrains.kotlin.jvm") version "1.9.22"
}

taboolib {

    description {
        contributors {
            name("Glom_")
        }
        dependencies {
            name("Pouvoir")
            name("GermPlugin").optional(true)
            name("MythicMobs").optional(true)
        }
    }

    env {
        install(Bukkit, BukkitHook, XSeries)
        // NMS
        install(BukkitNMS, BukkitNMSDataSerializer, BukkitNMSItemTag, BukkitNMSUtil)
        // util
        install(CommandHelper, BukkitFakeOp, Metrics, BukkitUtil, BukkitNavigation)
    }

    version {
        taboolib = "6.2.3-2eb93b5"
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs = listOf("-Xjvm-default=all")
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://mvn.lumine.io/repository/maven-public/") }
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    compileOnly("com.github.LoneDev6:API-ItemsAdder:4.0.10")
    compileOnly("io.lumine:Mythic-Dist:5.9.5")
    compileOnly("ink.ptms.core:v12104:12104-minimize:mapped")
    compileOnly(fileTree("libs"))
    compileOnly(kotlin("stdlib-jdk8"))
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

configure<JavaPluginConvention> {
    targetCompatibility = JavaVersion.VERSION_21
    sourceCompatibility = JavaVersion.VERSION_21
}
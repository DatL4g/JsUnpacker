import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform") version "1.9.20"
    `maven-publish`
    signing
    id("com.vanniktech.maven.publish") version "0.25.3"
}

val libName = "jsunpacker"
val libVersion = "1.0.2"
group = "dev.datlag.jsunpacker"
version = libVersion

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(IR) {
        browser()
        nodejs()
        binaries.executable()
    }
    macosX64()
    macosArm64()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    linuxX64()
    linuxArm64()
    mingwX64()
    watchosArm64()
    watchosArm32()
    watchosSimulatorArm64()
    watchosDeviceArm64()
    tvosX64()
    tvosArm64()
    tvosSimulatorArm64()

    applyDefaultHierarchyTemplate()
    
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
}

mavenPublishing {
    publishToMavenCentral(host = SonatypeHost.S01, automaticRelease = true)
    signAllPublications()

    coordinates(
        groupId = "dev.datlag.jsunpacker",
        artifactId = libName,
        version = libVersion
    )

    pom {
        name.set(libName)
        description.set("A javascript unpacker written in Kotlin, available for all platforms")
        url.set("https://github.com/DatL4g/JsUnpacker")

        licenses {
            license {
                name.set("Apache License 2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }

        scm {
            url.set("https://github.com/DatL4g/JsUnpacker")
            connection.set("scm:git:git://github.com/DatL4g/JsUnpacker.git")
        }

        developers {
            developer {
                id.set("DatL4g")
                name.set("Jeff Retz (DatLag)")
                url.set("https://github.com/DatL4g")
            }
        }
    }
}

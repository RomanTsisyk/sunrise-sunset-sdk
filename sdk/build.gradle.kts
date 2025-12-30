plugins {
    kotlin("multiplatform") version "2.1.0"
    kotlin("plugin.serialization") version "2.1.0"
    id("com.android.library") version "8.7.2"
    id("maven-publish")
}

group = "io.github.romantsisyk"
version = "1.1.0"

kotlin {
    // Android target
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
                }
            }
        }
        publishLibraryVariants("release", "debug")
    }

    // JVM target for desktop/server
    jvm {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
                }
            }
        }
    }

    // iOS targets
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "SunriseSunsetSDK"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-core:2.3.7")
                implementation("io.ktor:ktor-client-content-negotiation:2.3.7")
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.7")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
                implementation("io.ktor:ktor-client-mock:2.3.7")
            }
        }

        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-okhttp:2.3.7")
            }
        }

        val androidUnitTest by getting {
            dependencies {
                implementation("io.mockk:mockk:1.13.4")
                implementation("org.junit.jupiter:junit-jupiter:5.10.0")
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-okhttp:2.3.7")
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation("io.mockk:mockk:1.13.4")
                implementation("org.junit.jupiter:junit-jupiter:5.10.0")
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting

        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation("io.ktor:ktor-client-darwin:2.3.7")
            }
        }

        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting

        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "io.github.romantsisyk.sunrisesunsetsdk"
    compileSdk = 35

    defaultConfig {
        minSdk = 21
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

publishing {
    publications {
        withType<MavenPublication> {
            groupId = "com.github.romantsisyk"
            artifactId = "sunrise-sunset-sdk"
            version = "1.1.0"

            pom {
                name.set("Sunrise Sunset SDK")
                description.set("A Kotlin Multiplatform SDK for Sunrise and Sunset calculations.")
                url.set("https://github.com/RomanTsisyk/sunrise-sunset-sdk")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        id.set("romantsisyk")
                        name.set("Roman Tsisyk")
                        email.set("roman.tsisyk.1@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/RomanTsisyk/sunrise-sunset-sdk.git")
                    developerConnection.set("scm:git:ssh://github.com/RomanTsisyk/sunrise-sunset-sdk.git")
                    url.set("https://github.com/RomanTsisyk/sunrise-sunset-sdk")
                }
            }
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

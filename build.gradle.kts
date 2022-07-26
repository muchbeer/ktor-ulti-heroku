val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val koin_version: String by project

plugins {
    application
    kotlin("jvm") version "1.7.10"
}

group = "com.muchbeer"
version = "0.0.1"
application {
    mainClass.set("com.muchbeer.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
    maven { setUrl("https://jitpack.io") }
}

tasks {
    create("stage").dependsOn("installDist")
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-jackson-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")

    implementation ("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation ("io.ktor:ktor-serialization-jackson:$ktor_version")

   // implementation ("io.insert-koin:koin-logger-slf4j:$koin_version")
  //  implementation("io.insert-koin:koin-ktor:$koin_version")

    implementation ("mysql:mysql-connector-java:8.0.29")
    implementation ("org.ktorm:ktorm-core:3.5.0")
    implementation ("org.ktorm:ktorm-support-mysql:3.5.0")

    implementation("io.ktor:ktor-server-call-logging:$ktor_version")

    implementation("io.ktor:ktor-server-default-headers:$ktor_version")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    //africastalking api
    implementation("com.github.AfricasTalkingLtd.africastalking-java:core:3.4.8")
}
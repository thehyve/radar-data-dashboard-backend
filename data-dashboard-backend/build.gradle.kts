plugins {
    application
    kotlin("plugin.serialization")
    kotlin("plugin.noarg")
    kotlin("plugin.jpa")
    kotlin("plugin.allopen")
}

application {
    mainClass.set("org.radarbase.datadashboard.api.Main")
}

dependencies {
    api(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    implementation("org.radarbase:radar-jersey:${Versions.radarJersey}")
    implementation("org.radarbase:radar-jersey-hibernate:${Versions.radarJersey}") {
        runtimeOnly("org.postgresql:postgresql:${Versions.postgresql}")
    }
    implementation("org.radarbase:radar-commons-kotlin:${Versions.radarCommons}")

    testImplementation("org.mockito:mockito-core:${Versions.mockitoKotlin}")
    testImplementation("org.mockito.kotlin:mockito-kotlin:${Versions.mockitoKotlin}")
    testImplementation("org.glassfish.jersey.test-framework:jersey-test-framework-core:${Versions.jersey}")
    testImplementation("org.glassfish.jersey.test-framework.providers:jersey-test-framework-provider-grizzly2:${Versions.jersey}")
    testImplementation("com.h2database:h2:2.2.224")
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

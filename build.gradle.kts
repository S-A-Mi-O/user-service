plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.6"
	kotlin("plugin.jpa") version "1.9.25"
}

group = "com.samio"
version = "1.0.0"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

extra["springCloudVersion"] = "2023.0.3"

repositories {
	mavenCentral()

	maven {
		url = uri("https://maven.pkg.github.com/S-A-Mi-O/core")
		credentials {
			username = findProperty("githubUsername") as String? ?: System.getenv("GITHUB_USERNAME")
			password = findProperty("githubToken") as String? ?: System.getenv("GITHUB_TOKEN")
		}
	}

	maven {
		url = uri("https://repo.spring.io/milestone")
	}
	maven {
		url = uri("https://repo.spring.io/snapshot")
	}

	google()
}


val isLocalProfile: Boolean = project.hasProperty("spring.profiles.active") && project.property("spring.profiles.active") == "local"

dependencies {
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.18.1")
	implementation("com.fasterxml.jackson.module:jackson-module-jakarta-xmlbind-annotations:2.18.1")
	implementation("com.github.S-A-Mi-O:core:7.5.0")
	implementation("com.github.javafaker:javafaker:1.0.2"){
		exclude(group = "org.yaml", module = "snakeyaml")
	}
	implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
	testImplementation ("com.h2database:h2")
	implementation("io.github.cdimascio:dotenv-kotlin:6.2.2")
	implementation("io.hypersistence:hypersistence-utils-hibernate-63:3.9.0")
	implementation("org.hibernate:hibernate-core:6.6.3.Final")
	implementation("org.glassfish.jaxb:jaxb-runtime:2.3.1")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation ("org.mockito:mockito-core:4.6.1")
	testImplementation("org.mockito:mockito-inline:4.6.1")
	testImplementation ("org.mockito.kotlin:mockito-kotlin:4.1.0")
	implementation("org.postgresql:postgresql:42.7.2")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
	implementation("org.webjars:webjars-locator-core")
	implementation("org.webjars:swagger-ui")
	implementation("org.springframework.boot:spring-boot-docker-compose")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	if (!isLocalProfile) {
		implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
	}
	implementation("org.springframework.kafka:spring-kafka")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	implementation("org.springframework.boot:spring-boot-configuration-processor")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
	if (!isLocalProfile) {
		testImplementation("org.springframework.security:spring-security-test")
	}

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

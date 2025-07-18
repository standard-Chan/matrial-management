plugins {
    id("org.springframework.boot") version "3.2.5" // 원하는 버전
    id("io.spring.dependency-management") version "1.1.4"
    id("java")
}

group = "com.springframework.mm"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // lombok
    compileOnly ("org.projectlombok:lombok:1.18.36")
    annotationProcessor ("org.projectlombok:lombok:1.18.36")

    // spring boot
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-logging")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    // spring  boot 유효성 검사
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // --- Mockito ---
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito:mockito-junit-jupiter")

    //MYSQL
    implementation("mysql:mysql-connector-java:8.0.33")

    // Thymeleaf
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

    // test
    testImplementation ("org.springframework.boot:spring-boot-starter-test")
    testImplementation ("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly ("org.junit.jupiter:junit-jupiter-engine")
    testImplementation ("org.assertj:assertj-core")
    // h2
    testImplementation ("com.h2database:h2")
}

tasks.test {
    useJUnitPlatform()
}
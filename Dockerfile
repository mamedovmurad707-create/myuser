# Jar əvvəlcədən kompüterdə yığılır:  ./gradlew bootJar -x test
# Səbəb: şirkət şəbəkəsində konteynerin içindən Gradle/dependency yükləmək SSL sertifikatına görə alınmır.
# Harbor-da 25-jre yoxdur, ona görə JDK image-i istifadə olunur.
FROM harbor.azerconnect.az/infra/eclipse-temurin/eclipse-temurin:25-jdk
WORKDIR /app

# Təhlükəsizlik üçün root olmayan istifadəçi ilə işlədirik.
RUN useradd --system --create-home appuser
USER appuser

# Hazır jar-ı image-ə kopyalayırıq ("-plain.jar" yox, içində bütün dependency-lər olan fat jar).
COPY build/libs/*-SNAPSHOT.jar app.jar

# Spring Boot default olaraq 8080 portunda işləyir.
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

FROM eclipse-temurin:25-jdk AS build
WORKDIR /workspace

# Copy Gradle wrapper first — layer is cached as long as wrapper doesn't change
COPY gradlew .
COPY gradle/ gradle/
RUN chmod +x gradlew

# Copy build scripts — cached until any build file changes
COPY settings.gradle .
COPY build.gradle .
COPY party-core/build.gradle party-core/
COPY party-jpa/build.gradle party-jpa/
COPY party-sample/build.gradle party-sample/

# Resolve dependencies — cached until build files change
RUN ./gradlew dependencies --no-daemon -q

# Copy source and build the fat JAR
COPY party-core/src party-core/src
COPY party-jpa/src party-jpa/src
COPY party-sample/src party-sample/src
RUN ./gradlew :party-sample:bootJar --no-daemon -q

FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /workspace/party-sample/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

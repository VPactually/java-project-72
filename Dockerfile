FROM gradle:8.3.0-jdk20

WORKDIR /app

COPY /app .

RUN ./gradlew installDist

CMD ./build/install/app/bin/app
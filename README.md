# Lab1 - Dynamic loading with Java Reflection (Maven, JDK 17)

Run (from the project root):

    mvn compile exec:java                                   # Presentation2 (dynamic)
    mvn compile exec:java -Dexec.mainClass=presentation.Presentation   # static version

Or build an executable JAR:

    mvn clean package
    java -jar target/Lab1-1.0-SNAPSHOT.jar

Edit `src/main/resources/config.txt` to swap implementations (no code change).

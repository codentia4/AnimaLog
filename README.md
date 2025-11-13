**AnimaLog full project (Spring Boot backend + static frontend)

Structure:
- backend/: Maven Spring Boot project
- frontend/: static HTML/CSS/JS (place in a web server or open files directly)

To run backend:
1. Create MySQL database 'animalog_db' (use MySQL Workbench)
2. Update backend/src/main/resources/application.properties with your DB password and jwt.secret
3. mvn -f backend/pom.xml spring-boot:run

Frontend:
Open frontend/index.html (or host via a static server). Frontend expects backend at http://localhost:8081

**

# 🛒 E-Commerce Backend – Spring Boot

Welcome to the backend of my fully functional **e-commerce platform**, built with **Spring Boot** over the past 5 months. This wasn’t just another CRUD project — it was a real-world lab for learning and applying core backend principles in Java.

From **JWT-based authentication**, **role management**, and **DTO mapping** to **exception handling**, **pagination**, **Dockerization**, and **cloud deployment**, this backend showcases modern Spring Boot development.

---

## 🔥 Features

* ✅ **Role-Based Authentication** with Spring Security and JWT
* ✅ **User Registration & Login APIs**
* ✅ **Admin Access Controls**
* ✅ **DTO Mapping** using ModelMapper
* ✅ **Custom Exception Handling** (global & field-specific)
* ✅ **Pagination and Filtering** for scalable data access
* ✅ **Hibernate & JPA** for ORM and data persistence
* ✅ **Dockerized Application**, deployed on **Render**
* ✅ Ready to be integrated with any frontend (React frontend available [here](#frontend-link))

---

## 📁 Project Structure

```
src/
 ┣ main/
 ┃ ┣ java/
 ┃ ┃ ┣ com.example.ecommerce/
 ┃ ┃ ┃ ┣ controller/
 ┃ ┃ ┃ ┣ dto/
 ┃ ┃ ┃ ┣ entity/
 ┃ ┃ ┃ ┣ exception/
 ┃ ┃ ┃ ┣ repository/
 ┃ ┃ ┃ ┣ security/
 ┃ ┃ ┃ ┣ service/
 ┃ ┃ ┃ ┗ EcommerceApplication.java
 ┃ ┣ resources/
 ┃ ┃ ┣ application.properties
 ┃ ┃ ┗ data.sql
```

---

## 🚀 Getting Started Locally

### 🧰 Prerequisites

* Java 17+
* Maven 3+
* PostgreSQL or MySQL (set up your DB and credentials)
* Docker (optional, for containerized deployment)

---

### 🛠️ Clone & Setup

```bash
git clone https://github.com/your-username/ecommerce-backend.git
cd ecommerce-backend
```

---

### ⚙️ Configure Database

Update `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce_db
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password

spring.jpa.hibernate.ddl-auto=update
jwt.secret=your_jwt_secret
```

---

### ▶️ Run the Project

```bash
# Run with Maven
./mvnw spring-boot:run
```

Or build and run:

```bash
./mvnw clean install
java -jar target/ecommerce-backend-0.0.1-SNAPSHOT.jar
```

---

### 🐳 Run with Docker

```bash
docker build -t ecommerce-backend .
docker run -p 8080:8080 ecommerce-backend
```

---

## 🔐 Authentication Flow

* **JWT Token** is generated on login.
* Protected APIs require `Authorization: Bearer <token>` header.
* Role-based access is handled in the `SecurityConfig`.

---

## 📬 API Overview (Sample)

| Endpoint             | Method   | Auth Required | Description                   |
| -------------------- | -------- | ------------- | ----------------------------- |
| `/api/auth/register` | POST     | ❌             | Register new user             |
| `/api/auth/login`    | POST     | ❌             | Login and get JWT             |
| `/api/products`      | GET      | ❌             | List all products (paginated) |
| `/api/cart`          | GET/POST | ✅             | Cart operations (user only)   |
| `/api/orders`        | POST     | ✅             | Place new order               |

*(Use Postman or Swagger for detailed testing)*

---

## 📡 Deployment

* **Deployed using [Render](https://render.com)**
* Dockerized container is live and integrated with Netlify frontend

---

## 💡 Micro Anecdote

> “It took me 3 hours to fix a broken JWT refresh bug — turned out it was a typo in the filter chain.”

These kinds of bugs test your patience, but fixing them sharpens your skills.

---

## 🤝 Contributions

PRs and feedback are welcome! Just fork the repo and submit your changes.

---

## 🔗 Links

* 🔴 **Live Backend API**: [https://your-render-link.com](https://your-render-link.com)
* 💻 **Frontend Repo**: [GitHub Frontend](https://github.com/your-username/ecommerce-frontend)
* 🔧 **Backend Repo**: [GitHub Backend](https://github.com/your-username/ecommerce-backend)

---

## 🧵 Tech Stack

* **Java 21**
* **Spring Boot**
* **Spring Security + JWT**
* **JPA / Hibernate**
* **PostgreSQL / MySQL**
* **Docker**
* **Render**



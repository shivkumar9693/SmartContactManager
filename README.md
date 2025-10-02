 # 📇 SmartContactManager

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6.2-green)
![Java](https://img.shields.io/badge/Java-17-blue)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.1-orange)

SmartContactManager is a **full-featured contact management web application** built with **Spring Boot**, **Thymeleaf**, and **Bootstrap**.  
It allows users to **add, view, update, and delete contacts** securely with profile images and personal information management.

---

## ✨ Features

### 👤 User Management
- 🔑 **Register/Login** with Spring Security.
- 📝 **Profile Management**: Update username, email, password, profile image, and about section.
- 🛡️ **Role-Based Access**: Only authorized users can update their contacts.
- 🟢/🔴 **Active Status** shown with color.

### 📇 Contact Management
- ➕ **Add Contacts** with full details including profile image and about.
- 📃 **View Contacts** with pagination (5 per page).
- ✏️ **Update Contacts** securely, only by the owner.
- ❌ **Delete Contacts** with confirmation prompt.
- 🖼️ **Profile Image Upload** with default fallback (`default.jpeg`).

### 🛡️ Security & Validation
- Users can only access their own contacts.
- Form validation: email format, phone number pattern, required fields, input lengths.
- Unauthorized access shows a friendly/funny message.

### 💾 File Handling
- Profile images uploaded to `static/img/`.
- Default image used if none provided.

---

## 🛠️ Tech Stack

| Layer            | Technology/Framework          |
|-----------------|-------------------------------|
| Backend         | Spring Boot, Spring MVC       |
| ORM/Database    | Spring Data JPA, Hibernate, MySQL |
| Frontend        | Thymeleaf, HTML5, Bootstrap 5, CSS |
| Security        | Spring Security              |
| File Upload     | MultipartFile, Java NIO      |
| Build Tool      | Maven                        |

---

## 🚀 Setup Instructions

### Prerequisites
- Java 17+
- Maven 3+
- MySQL or H2 database
- IDE (IntelliJ IDEA, Eclipse, VS Code)

### Clone Repository
```bash
git clone https://github.com/yourusername/SmartContactManager.git
cd SmartContactManager
````

### Database Setup

```sql
CREATE DATABASE contact_manager;
```

Update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/contact_manager
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

### Run Application

```bash
mvn spring-boot:run
```

Open browser: [http://localhost:8282](http://localhost:8282)

---

## 🗂️ Project Structure

```
SmartContactManager/
│
├── src/main/java/com/Main/
│   ├── Controller/       # Spring MVC Controllers
│   ├── Entity/           # JPA Entities (User, Contact)
│   ├── Repository/       # Spring Data Repositories
│   ├── Service/          # Business logic
│   └── Config/           # Spring Security config
│
├── src/main/resources/
│   ├── templates/        # Thymeleaf templates
│   ├── static/           # CSS, JS, images
│   └── application.properties
│
├── pom.xml               # Maven dependencies
└── README.md
```

---

## 📸 Screenshots

### 👤 Profile Page

 <img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/d72cc358-ce42-45f2-be98-7d2e32f215e7" />


### 📇 Contact List

<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/a3efe9ab-16c4-4b22-b6d3-6b6e82d1737c" />


### ✏️ Update Contact

 <img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/5fdd1052-a330-4e68-ba2e-6a69bf5df5d9" />


---

## ⚠️ Notes

* Ensure `static/img/default.jpeg` exists for default images.
* DevTools enabled for hot reload during development.
* Only authorized users can access/update their own contacts.
* Pagination: 5 contacts per page.

---

## 🤝 Contribution

Feel free to fork the project, create a branch, and submit a pull request.
Issues and suggestions are welcome!

--- 

## 👨‍💻 Created By

**Shiv Kumar Thakur**

**Happy Contact Managing!** 🚀📇

```

 

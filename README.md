# 📚 Pahana Edu Bookshop System

The **Pahana Edu Bookshop System** is a **web-based Java application** designed to streamline the daily operations of a bookshop. It provides tools for staff to manage books and stationery, handle billing, track stock levels, and generate reports. Administrators can oversee the entire process and manage system-wide operations.

---

## 🏗️ Architecture
The system follows a **layered architecture**:
- **Presentation Layer (UI)** – JSP/HTML pages with CSS for styling.
- **Controller Layer** – Java Servlets handling requests/responses.
- **Business Logic Layer** – Java classes managing application rules.
- **Data Access Layer** – JDBC for database connectivity.
- **Database Layer** – MySQL database for persistent storage.

---

## ⚙️ Dependencies
Make sure the following are installed before running the project:

- **Java JDK 11+**
- **Jakarta Servlet API**
- **Gson (Google JSON library)**
- **JUnit / JUnit Jupiter** (for testing)
- **MySQL (via XAMPP or standalone installation)**

---

## 🗄️ Database Setup
1. Install **XAMPP** and start **Apache** and **MySQL** modules.
2. Open **phpMyAdmin** at: [http://localhost/phpmyadmin](http://localhost/phpmyadmin).
3. Create a new database called `pahana_db`.
4. Import the SQL file located at: `/db/structure.sql`

5. Default login role is staff (credentials defined in DB).

---
🚀 Running the Project

1. Clone the repository:
         git clone https://github.com/PesandiM/pahana_system

2. Open the project in an IDE (IntelliJ IDEA, Eclipse, or NetBeans).

3. Ensure dependencies are added to the classpath (Servlet, Gson, JUnit).

4. Configure your Tomcat or any servlet container.

5. Run the project and open in browser:

         http://localhost:8080/pahana_system
---

🌱 Version Control Workflow

1. The repository follows a branching strategy:

2. dev → Development branch (feature testing)

3. stg → Staging branch (pre-release testing)

4. reg → Regression branch (final checks)

5. prod → Production branch (live version)

    Developers work on dev and changes move through testing branches before being merged into prod.
---

📖 User Manual (Quick Guide)
1. Login
2. Manage customers - add/edit/delete/view customers
3. Manage Items - add/edit/delete/view items
4. Manage Bills - add/delete/view bills
5. Reports - add/edit/delete/view reports
6. Manage users - add/edit/delete/view cusomers
6. Help - guidance to staff
7. Logout

---
### 🛠️ Configuring Smart Tomcat (IntelliJ IDEA Community)
Since IntelliJ IDEA Community does not include Tomcat support by default, we use the **Smart Tomcat** plugin.

1. In IntelliJ, go to **File → Settings → Plugins → Marketplace** and search for **Smart Tomcat**. Install it.
2. Restart IntelliJ IDEA.
3. Configure Smart Tomcat:
    - Go to **Run → Edit Configurations → Add New Configuration → Smart Tomcat**.
    - Set **Tomcat Server** → Browse and select your Tomcat installation folder (e.g., `C:\apache-tomcat-10.x`).
    - Set **Deployment Directory** → `src/main/webapp`.
    - Set **Context Path** → `/pahana_system`.
4. Apply and run the configuration.
5. Open the project in your browser:

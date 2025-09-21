# ShimmerShine   
*A Subscription-Based Car Wash Service*  

## Overview  
ShimmerShine is a modern car wash management system designed to make car care effortless. Customers can subscribe to monthly packages (Basic, Premium, and Ultimate) that allow them to wash their cars anytime within the subscription period. The platform provides both customer and admin features, ensuring smooth operations, subscription tracking, and service management.  

This project is built with **Spring Boot (backend)** and **Angular (frontend)**, using **PostgreSQL** for reliable and scalable data storage.  

---

## Features  

### Customer Features  
- Create an account and manage profile  
- Subscribe to monthly wash packages (Basic, Premium, Ultimate)  
- View subscription details & history  
- Assign cars to a subscription  
- Track remaining washes in the package  

### 🛠️ Admin Features  
- Manage customer accounts  
- Manage subscription packages and pricing  
- Assign washes and track service usage  
- View analytics & customer activity  

---

## Tech Stack  
**Backend**  
- Java 17  
- Spring Boot (REST API)  
- Spring Security (JWT Authentication)  
- JPA/Hibernate (PostgreSQL)  

**Frontend**  
- Angular 16  
- TailwindCSS (styling)  
- ShadCN/UI Components  
- Responsive & Mobile-first design  

**Other Tools**  
- Docker (optional)  
- Postman (API testing)  
- Git/GitHub (version control)  

---

## Installation  

### Prerequisites  
- Java 17+  
- Node.js & npm  
- PostgreSQL  
- Docker  

### Backend Setup  
```bash
# Clone repository
git clone https://github.com/Juniorbarry26/ShimmerShine.git
cd ShimmerShine/backend

# Configure application.properties with PostgreSQL credentials
spring.datasource.url=jdbc:postgresql://localhost:5432/shimmershine
spring.datasource.username=yourusername
spring.datasource.password=yourpassword

# Run Spring Boot
./mvnw spring-boot:run
```

### Frontend Setup  
```bash
cd ../frontend

# Install dependencies
npm install

# Run Angular app
ng serve --open
```

Frontend will run on **http://localhost:4200/** and backend on **http://localhost:8080/**.  

---

## API Endpoints (Sample)  

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST   | `api/v1/auth/register` | Register a new user |
| POST   | `api/v1/auth/login` | Authenticate user & return JWT |
| POST   | `api/v1/subscriptions/user` | Create subscription for user |
| GET    | `api/v1/cars/user` | Get all cars for a user |
| POST   | `api/v1/cars/user` | Add a new car for a user |

---

## Future Enhancements  
- Payment gateway integration (Stripe/PayPal)  
- SMS/Email reminders for wash usage  
- Loyalty rewards system  
- Mobile app version (Flutter/React Native)  

---

## Contributing  
Contributions are welcome! Please fork this repo, create a new branch, and submit a pull request.  

---

## License  
This project is licensed under the MIT License.  

---

## Author  
 **Alsainey Barry**  
- Backend Engineer (Java, Spring Boot)  
- Building scalable business-driven solutions  

---

## GitHub Repository  
[ShimmerShine on GitHub](https://github.com/Juniorbarry26/ShimmerShine)  

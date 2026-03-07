# Database Seeding Guide

This document explains how to seed the ShimmerShine database with sample data.

## Overview

The database seeder will populate your database with:
- **Roles**: ADMIN, MANAGER, EMPLOYEE, USER
- **Subscription Plans**: BASIC (5 washes), PREMIUM (15 washes), ULTIMATE (unlimited)
- **Users**: 5 sample users including 1 admin
- **Employees**: 5 sample employees with different statuses
- **Cars**: 6 sample cars assigned to users
- **Subscriptions**: 4 sample subscriptions with different statuses

## Running the Seeder

### Option 1: Automatic Seeding (Recommended)

The seeder runs automatically when you start the application with the `seed` profile:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=seed
```

### Option 2: Manual Seeding

You can also run the seeder manually by setting the active profile to `seed` in your `.env` file:

```properties
SPRING_PROFILES_ACTIVE=seed
```

Then run the application normally:

```bash
./mvnw spring-boot:run
```

## Sample Data Created

### Users
- **Admin**: admin@shimmershine.com / admin123
- **John Doe**: john.doe@example.com / password123
- **Jane Smith**: jane.smith@example.com / password123
- **Mike Johnson**: mike.johnson@example.com / password123
- **Sarah Williams**: sarah.williams@example.com / password123

### Employees
- Robert Chen (Senior Car Wash Specialist - ACTIVE)
- Maria Rodriguez (Car Wash Technician - ACTIVE)
- James Wilson (Trainee - TRAINING)
- Lisa Thompson (Customer Service Manager - ON_LEAVE)
- David Kim (Detailing Specialist - ACTIVE)

### Cars
- John Doe: Toyota Camry (Silver), Honda CR-V (Blue)
- Jane Smith: Tesla Model 3 (Red)
- Mike Johnson: Ford F-150 (Black), BMW X5 (White)
- Sarah Williams: Mercedes-Benz C-Class (Gray)

### Subscription Plans
- **BASIC**: $29.99/month - 5 car washes
- **PREMIUM**: $79.99/month - 15 car washes
- **ULTIMATE**: $149.99/month - Unlimited car washes

## Important Notes

1. **Database Reset**: The `seed` profile uses `create-drop` DDL mode, which will reset your database each time you run it.
2. **Password Encryption**: All user passwords are encrypted using BCrypt.
3. **Dependencies**: The seeder respects entity dependencies and runs in the correct order.
4. **Idempotent**: The seeder checks if data already exists and skips seeding if tables are not empty.

## Troubleshooting

If you encounter issues:

1. Check the application logs for detailed error messages
2. Ensure your database connection is properly configured
3. Verify that all required dependencies are available
4. Make sure you have sufficient database permissions

## Customizing the Seeder

To modify the sample data:

1. Edit the appropriate seeder class in `src/main/java/com/Alsainey/ShimmerShine/seed/`
2. Update the data in the `seed()` method
3. Re-run the application with the seed profile

The seeder classes are:
- `RoleSeeder`: User roles
- `SubscriptionPlanSeeder`: Subscription plans
- `UserSeeder`: User accounts
- `EmployeeSeeder`: Employee records
- `CarSeeder`: Vehicle records
- `SubscriptionSeeder`: User subscriptions

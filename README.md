# E-commerce Demo

A modern e-commerce demonstration project built with a monorepo architecture, showcasing a complete full-stack solution.

## Technology Stack

### Backend
- **Framework**: Spring Boot 3
- **Language**: Java 21
- **Build Tool**: Maven

### Frontend
- **Framework**: Taro 4
- **UI Library**: React 18
- **Language**: TypeScript
- **Build Tool**: Vite
- **Code Quality**: ESLint, Stylelint, Husky, Commitlint

## Project Structure

```
├── backend/                 # Spring Boot backend application
│   ├── src/main/java/com/zxr/backend/  # Backend source code
│   ├── src/main/resources/   # Backend configuration files
│   ├── pom.xml               # Maven project configuration
│   └── mvnw                  # Maven wrapper
├── frontend/                # Taro React frontend application
│   ├── src/                  # Frontend source code
│   ├── config/               # Taro configuration files
│   ├── package.json          # Node.js dependencies
│   └── project.config.json   # Mini program configuration
└── README.md                # This documentation file
```

## Prerequisites

### System Requirements
- **Java**: JDK 21 or higher
- **Node.js**: 18.x or higher
- **Package Manager**: npm 9.x or yarn 1.22.x
- **Build Tool**: Maven 3.9.x or higher (or use the provided Maven wrapper)

## Getting Started

### Backend Setup

1. **Navigate to backend directory**
   ```bash
   cd backend
   ```

2. **Install dependencies and build**
   ```bash
   # Using Maven wrapper (Windows)
   mvnw.cmd clean install
   
   # Using Maven wrapper (Linux/macOS)
   ./mvnw clean install
   ```

3. **Run the application**
   ```bash
   # Using Maven wrapper (Windows)
   mvnw.cmd spring-boot:run
   
   # Using Maven wrapper (Linux/macOS)
   ./mvnw spring-boot:run
   ```

4. **Verify backend is running**
   The backend service will be available at `http://localhost:8080`

### Frontend Setup

1. **Navigate to frontend directory**
   ```bash
   cd frontend
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Start development server**

    - **For H5 (Web)**:
      ```bash
      npm run dev:h5
      ```
      Access at `http://localhost:10086`

    - **For WeChat Mini Program**:
      ```bash
      npm run dev:weapp
      ```
      Open with WeChat Developer Tools and select the `frontend/dist` directory

## Configuration

### Backend Configuration

The backend configuration is located in `backend/src/main/resources/application.yaml`

```yaml
spring:
  application:
    name: backend
  
# CORS configuration for frontend integration
  web:
    cors:
      allowed-origins: "*"
      allowed-methods: GET, POST, PUT, DELETE, OPTIONS
      allowed-headers: "*"
      allow-credentials: true

server:
  port: 8080
  servlet:
     context-path: /api
```

### Frontend Configuration

The frontend configuration is located in the `frontend/config/` directory:

- **baseConfig** (`index.ts`): Core Taro configuration including design width, device ratio, and build settings
- **devConfig** (`dev.ts`): Development environment specific configuration
- **prodConfig** (`prod.ts`): Production environment specific configuration

## Key Features

### Backend
- RESTful API architecture
- Spring Boot 3 with modern Java 21 features
- CORS support for frontend integration
- Built-in Tomcat server

### Frontend
- Multi-platform support (H5, WeChat Mini Program, etc.)
- React 18 with hooks and functional components
- TypeScript for type safety
- Vite for fast development experience
- Code quality enforcement with ESLint and Stylelint
- Commit message validation with Husky and Commitlint

## API Documentation

### Endpoints

#### 1. Get All Products
**URL**: `/api/products`
**Method**: `GET`
**Description**: Retrieve a list of all available products

**Example Curl Request**:
```bash
curl -X GET http://localhost:8080/api/products
```

**Example Response**:
```json
[
  {
    "id": 1,
    "name": "Red Fuji Apple",
    "price": 1.99,
    "stock": 5
  },
  {
    "id": 2,
    "name": "Imported Banana",
    "price": 0.99,
    "stock": 10
  }
]
```

#### 2. Create Order
**URL**: `/api/orders`
**Method**: `POST`
**Description**: Create a new order with one or more products

**Request Body**:
```json
{
  "items": [
    {
      "productId": 1,
      "quantity": 2
    },
    {
      "productId": 2,
      "quantity": 3
    }
  ]
}
```

**Example Curl Request**:
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{"items": [{"productId": 1, "quantity": 2}, {"productId": 2, "quantity": 3}]}'
```

**Example Response**:
```json
{
   "createdAt": "2026-02-04T01:46:16.947+00:00",
   "orderId": "ORD-20260204094616945",
   "totalPrice": 6.95,
   "status": "pending"
}
```

**Error Response Examples**:
```json
{
  "message": "Insufficient stock"
}
```

```json
{
  "message": "Product not found"
}
```

## Development Guidelines

### Code Style
- **Java**: Follow Spring Boot and Java Coding Conventions
- **TypeScript/React**: Follow ESLint and React best practices
- **CSS**: Follow Stylelint standard configuration

### Commit Messages

Use conventional commit messages:

```
<type>: <description>

[optional body]

[optional footer]
```

Where `<type>` can be:
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `style`: Code style changes (formatting, etc.)
- `refactor`: Code refactoring (no functional changes)
- `test`: Adding or updating tests
- `chore`: Build process or auxiliary tool changes

## Deployment

### Backend Deployment
```bash
java -jar backend/target/backend-0.0.1-SNAPSHOT.jar
```

### Frontend Deployment

#### H5 Deployment
1. Build the H5 version: `npm run build:h5`
2. Deploy the contents of `frontend/dist` to any static web server (Nginx, Apache, etc.)

#### Mini Program Deployment
1. Build the specific platform version: `npm run build:weapp`
2. Upload the `frontend/dist` directory using the respective platform's developer tools

## License

[MIT](LICENSE)

## Acknowledgments

- [Spring Boot](https://spring.io/projects/spring-boot) - Backend framework
- [Taro](https://taro.jd.com/) - Multi-platform frontend framework
- [React](https://react.dev/) - UI library
- [TypeScript](https://www.typescriptlang.org/) - Type safety

## Contact

For any questions or suggestions, please open an issue or submit a pull request.
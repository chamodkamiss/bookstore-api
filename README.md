# Bookstore API

A RESTful web service for managing an online bookstore system using JAX-RS/Jersey.

## 📚 Features

- **Book Management**: CRUD operations for books
- **Author Management**: Track authors and their publications
- **Customer Management**: Handle customer accounts
- **Shopping Cart**: Add, update, remove items
- **Order Processing**: Create and manage orders
- **Stock Management**: Track book inventory
- **Error Handling**: Comprehensive error responses

## 🛠️ Technologies

- **Java**: Version 8+
- **JAX-RS**: Jersey 2.35
- **Server**: Apache Tomcat 9
- **Build Tool**: Maven
- **Data Format**: JSON

## 🔌 API Endpoints

### Books
```http
GET    /api/books           # Get all books
GET    /api/books/{id}      # Get specific book
POST   /api/books           # Create new book
PUT    /api/books/{id}      # Update book
DELETE /api/books/{id}      # Delete book
```

### Authors
```http
GET    /api/authors             # Get all authors
GET    /api/authors/{id}        # Get specific author
GET    /api/authors/{id}/books  # Get author's books
POST   /api/authors             # Create author
PUT    /api/authors/{id}        # Update author
DELETE /api/authors/{id}        # Delete author
```

### Shopping Cart
```http
GET    /api/customers/{id}/cart                # Get cart
POST   /api/customers/{id}/cart/items         # Add item
PUT    /api/customers/{id}/cart/items/{bookId} # Update item
DELETE /api/customers/{id}/cart/items/{bookId} # Remove item
```

## 🚀 Getting Started

### Prerequisites
- Java JDK 8+
- Maven 3.6+
- Apache Tomcat 9

### Installation

1. Clone the repository:
```bash
git clone https://github.com/chamodkamiss/bookstore-api.git
cd bookstore-api
```

2. Build the project:
```bash
mvn clean package
```

3. Deploy to Tomcat:
```bash
copy target\bookstore-api.war %CATALINA_HOME%\webapps
```

4. Start Tomcat:
```bash
%CATALINA_HOME%\bin\startup.bat
```

### Usage Examples

#### Create a Book
```http
POST http://localhost:8080/api/books
Content-Type: application/json

{
    "title": "The Great Book",
    "authorId": 1,
    "price": 29.99,
    "stock": 100
}
```

#### Add to Cart
```http
POST http://localhost:8080/api/customers/1/cart/items
Content-Type: application/json

{
    "bookId": 1,
    "quantity": 2
}
```

## 🔍 Error Handling

Errors follow this format:
```json
{
    "error": "Error Type",
    "message": "Detailed error message"
}
```

Common error types:
- `InvalidInputException`: 400 Bad Request
- `NotFoundException`: 404 Not Found
- `OutOfStockException`: 400 Bad Request

## 🧪 Testing

Use Postman or cURL to test endpoints:

```bash
# Get all books
curl http://localhost:8080/api/books

# Create author
curl -X POST http://localhost:8080/api/authors \
     -H "Content-Type: application/json" \
     -d '{"name":"John Doe","biography":"Famous author"}'
```


## ✨ Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 👥 Authors

- Chamod Kamiss - *Initial work*

## 📧 Contact

Your Name - [@chamodkamiss](https://github.com/chamodkamiss)

Project Link: [https://github.com/chamodkamiss/bookstore-api](https://github.com/chamodkamiss/bookstore-api.git)

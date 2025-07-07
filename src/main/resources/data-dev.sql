DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS order_details;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS shippers;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS suppliers;


CREATE TABLE users (
    id BIGINT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE categories (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE suppliers (
    supplier_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    company_name VARCHAR(255),
    contact_name VARCHAR(255),
    contact_title VARCHAR(255),
    address VARCHAR(255),
    city VARCHAR(255),
    region VARCHAR(255),
    postal_code VARCHAR(255),
    country VARCHAR(255),
    phone VARCHAR(255),
    fax VARCHAR(255),
    homepage VARCHAR(1000)
);

CREATE TABLE products (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL,
    category_id BIGINT,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- Inserción de categorías base
INSERT INTO categories (category_id, category_name, description, picture) VALUES
(1, 'Electronics', 'Electronic devices and gadgets', NULL),
(2, 'Books', 'Books from all genres', NULL),
(3, 'Clothing', 'Men and Women apparel', NULL);

INSERT INTO suppliers (
    company_name,
    contact_name,
    contact_title,
    address,
    city,
    region,
    postal_code,
    country,
    phone,
    fax,
    homepage
) VALUES
('TechWorld Inc.', 'Alice Johnson', 'Sales Manager', '123 Tech Street', 'New York', 'NY', '10001', 'USA', '+1-555-1234', '+1-555-4321', 'https://www.techworld.com'),
('Global Supplies Ltd.', 'Carlos Perez', 'Purchasing Agent', '456 Global Ave', 'Miami', 'FL', '33101', 'USA', '+1-555-5678', '+1-555-8765', 'https://www.globalsupplies.com'),
('Book Lovers Co.', 'Emma Lee', 'Store Manager', '789 Book Blvd', 'Chicago', 'IL', '60601', 'USA', '+1-555-1111', '+1-555-2222', 'https://www.booklovers.com'),
('FashionTrend S.A.', 'Lucía Gómez', 'CEO', '321 Fashion Way', 'Los Angeles', 'CA', '90001', 'USA', '+1-555-3333', '+1-555-4444', 'https://www.fashiontrend.com');



-- Inserción de productos de ejemplo (asume que supplier_id y category_id ya existen)
INSERT INTO products (
    product_name,
    supplier_id,
    category_id,
    quantity_per_unit,
    unit_price,
    units_in_stock,
    units_on_order,
    reorder_level,
    discontinued
) VALUES
('Gaming Laptop', 1, 1, '1 unit', 2500.00, 10, 0, 2, FALSE),
('Wireless Mouse', 2, 1, '1 unit', 25.99, 100, 50, 10, FALSE),
('Sci-fi Novel', 3, 2, '1 book', 15.00, 200, 30, 5, FALSE),
('Cotton T-Shirt', 4, 3, '1 piece', 12.50, 300, 20, 10, FALSE),
('Noise Cancelling Headphones', 1, 1, '1 unit', 120.00, 25, 10, 5, TRUE);



INSERT INTO users (id, username, password) VALUES
(1, 'admin', '$2a$10$dcEUs86lWnajTDzdZPha0.Fj13nYgG852GYTPSbGa6P64sfHV82OS');

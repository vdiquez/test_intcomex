DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS suppliers;

CREATE TABLE categories (
    category_id SERIAL PRIMARY KEY,
    category_name VARCHAR(255) UNIQUE NOT NULL,
    description TEXT,
    picture BYTEA
);

CREATE TABLE suppliers (
    supplier_id SERIAL PRIMARY KEY,
    company_name VARCHAR(255) NOT NULL,
    contact_name VARCHAR(255)
);

CREATE TABLE products (
    product_id SERIAL PRIMARY KEY,
    product_name VARCHAR(255),
    supplier_id INT REFERENCES suppliers(supplier_id),
    category_id INT REFERENCES categories(category_id),
    quantity_per_unit VARCHAR(255),
    unit_price NUMERIC,
    units_in_stock INT,
    units_on_order INT,
    reorder_level INT,
    discontinued BOOLEAN
);

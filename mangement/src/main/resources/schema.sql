-- Products Table
CREATE TABLE IF NOT EXISTS products (
    id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    base_price DECIMAL(10, 2) NOT NULL,
    category VARCHAR(50),
    available_quantity INT NOT NULL
);

-- Promo Codes Table
CREATE TABLE IF NOT EXISTS promo_codes (
    code VARCHAR(20) PRIMARY KEY,
    discount_percentage DECIMAL(5, 2) NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    valid_until DATE
);

-- User Types Table
CREATE TABLE IF NOT EXISTS user_types (
    type VARCHAR(20) PRIMARY KEY,
    discount_percentage DECIMAL(5, 2) NOT NULL
);

-- Quantity Discount Rules Table
CREATE TABLE IF NOT EXISTS quantity_discounts (
    id INT AUTO_INCREMENT PRIMARY KEY ,
    min_quantity INT NOT NULL,
    discount_percentage DECIMAL(5, 2) NOT NULL
);

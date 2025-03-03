-- Sample Products
INSERT INTO products (id, name, description, base_price, category, available_quantity) VALUES
('ABC123', 'Premium Headphones', 'Noise-cancelling wireless headphones', 99.99, 'ELECTRONICS', 50),
('DEF456', 'Fitness Tracker', 'Smart fitness and health tracker', 59.95, 'WEARABLES', 100),
('GHI789', 'Coffee Maker', 'Programmable drip coffee maker', 45.50, 'HOME', 30),
('JKL012', 'Backpack', 'Water-resistant laptop backpack', 35.99, 'ACCESSORIES', 200),
('MNO345', 'Wireless Mouse', 'Ergonomic wireless mouse', 24.99, 'ELECTRONICS', 150),
('PQR678', 'Yoga Mat', 'Non-slip exercise yoga mat', 29.95, 'FITNESS', 80),
('STU901', 'Water Bottle', 'Insulated stainless steel water bottle', 18.50, 'ACCESSORIES', 250),
('VWX234', 'Desk Lamp', 'LED desk lamp with adjustable brightness', 32.99, 'HOME', 45),
('YZA567', 'Bluetooth Speaker', 'Portable wireless bluetooth speaker', 49.99, 'ELECTRONICS', 60),
('BCD890', 'Protein Powder', 'Whey protein supplement', 39.95, 'FITNESS', 75);

-- Sample Promo Codes
INSERT INTO promo_codes (code, discount_percentage, active, valid_until) VALUES
('SPRING25', 25.00, TRUE, '2025-06-30'),
('WELCOME10', 10.00, TRUE, '2025-12-31'),
('FLASH15', 15.00, TRUE, '2025-04-15'),
('SUMMER20', 20.00, FALSE, '2025-05-01');

-- Sample User Types
INSERT INTO user_types (type, discount_percentage) VALUES
('REGULAR', 0.00),
('PREMIUM', 10.00),
('VIP', 15.00);

-- Sample Quantity Discount Rules
INSERT INTO quantity_discounts (id, min_quantity, discount_percentage) VALUES
(1, 3, 5.00),
(2, 5, 7.50),
(3, 10, 12.00);

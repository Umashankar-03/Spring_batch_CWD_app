CREATE TABLE IF NOT EXISTS products
(
    product_id int primary key,
    title varchar(200),
    description varchar(200),
    price varchar(100),
    discount varchar(10),
    discounted_price varchar(10)
);
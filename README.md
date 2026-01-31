-- ADMIN
CREATE TABLE admin (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(50)
);

INSERT INTO admin VALUES ('admin', 'admin123');

-- CUSTOMER
CREATE TABLE customer (
    cus_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE,
    password VARCHAR(50),
    name VARCHAR(100),
    email VARCHAR(100),
    phone_no VARCHAR(15)
);

-- COMPLAINT
CREATE TABLE complaint (
    com_id INT PRIMARY KEY AUTO_INCREMENT,
    cus_id INT,
    description VARCHAR(255),
    status VARCHAR(30),
    c_date DATE,
    FOREIGN KEY (cus_id) REFERENCES customer(cus_id)
);

-- FEEDBACK
CREATE TABLE feedback (
    f_id INT PRIMARY KEY AUTO_INCREMENT,
    cus_id INT,
    rating INT,
    comments VARCHAR(255),
    f_date DATE,
    FOREIGN KEY (cus_id) REFERENCES customer(cus_id)
);

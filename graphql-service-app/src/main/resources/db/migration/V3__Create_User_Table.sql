CREATE TABLE users
(
    id       INT PRIMARY KEY AUTO_INCREMENT,
    name     VARCHAR(250),
    email    VARCHAR(250),
    password VARCHAR(250),
    role     ENUM('USER','ADMIN')
);
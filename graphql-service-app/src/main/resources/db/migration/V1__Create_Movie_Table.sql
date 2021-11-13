CREATE TABLE movies
(
    id            INT PRIMARY KEY AUTO_INCREMENT,
    name          VARCHAR(250),
    description   VARCHAR(250),
    genre         VARCHAR(250),
    certification ENUM('U','A','UA'),
    cast          VARCHAR(250),
    duration      VARCHAR(250)
);
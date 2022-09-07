CREATE TABLE users
(
    id LONG PRIMARY KEY,
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    friends LONG,
    balance DOUBLE,
    bank_id LONG
);
CREATE TABLE banks
(
    id LONG PRIMARY KEY,
    bank_name VARCHAR(255),
    iban VARCHAR(300),
    bic VARCHAR(300),
    user_id LONG
);
CREATE TABLE relation
(
    id LONG PRIMARY KEY,
    buddy VARCHAR(255),
    owner VARCHAR(255)
);
CREATE TABLE transaction
(
    id LONG PRIMARY KEY,
    amount double,
    reason VARCHAR(300),
    date date,
    commission double,
    creditor varchar(200),
    debtor varchar(200)
);
CREATE TABLE friends
(
    id       LONG PRIMARY KEY,
    friendId VARCHAR(255)
);
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS banks;
DROP TABLE IF EXISTS relation;
DROP TABLE IF EXISTS transaction;

CREATE TABLE users
(
    id        LONG PRIMARY KEY,
    firstname VARCHAR(255),
    lastname  VARCHAR(255),
    email     VARCHAR(255),
    password  VARCHAR(255),
    friends   LONG,
    balance   DOUBLE,
    bank_id   LONG,
);

CREATE TABLE banks
(
    id        LONG PRIMARY KEY,
    bank_name VARCHAR(255),
    iban      VARCHAR(300),
    bic       VARCHAR(300),
    user_id   LONG
);
CREATE TABLE relation
(
    id    LONG PRIMARY KEY,
    buddy LONG,
    owner LONG
);
CREATE TABLE transaction
(
    id         LONG PRIMARY KEY,
    amount     double,
    reason     VARCHAR(300),
    date       date,
    commission double,
    creditor   varchar(200),
    debtor     varchar(200)
);

INSERT INTO users (id, firstname, lastname, email, password, friends, balance, bank_id)
VALUES (1, 'Jacob', 'Boyd', 'jboy@email.com', 'mdp1', null, 1000.0, 1),
       (2, 'Tenley', 'Boyd', 'tenley@email.com', 'mdp2', null, 800.0, 2),
       (3, 'John', 'Wick', 'lovemydog@email.com', 'woof', null, 15000.0, 3),
       (4, 'Frodon', 'delacomté', 'frodon@email.com', 'precieux', null, 5000.0, 4);

COMMIT;

INSERT INTO banks (id, bank_name, iban, bic, user_id)
VALUES (1, 'evasionFiscale', 'IBANEVASIONFISCALE', 'BICSTYLO', 1),
       (2, 'donnelessous', 'IBANDONNELESSOUS', 'BICETCOLEGRAM', 2),
       (3, 'jaimemabanque', 'IBANJAIMEMAMBANQUE', 'BICBICETGROMINET', 3),
       (3, 'jaimemabanque', 'IBANJAIMEMAMBANQUE', 'BICBICETGROMINET', 4);
COMMIT;

INSERT INTO relation (id, buddy, owner)
VALUES (1,1,2 ),
       (2,3,2 ),
       (3, 1,3),
       (4, 4,3);
COMMIT;




INSERT INTO users (id, firstname, lastname, email, password, balance, bank_id)
VALUES (1, 'Jacob', 'Boyd', 'jboy@email.com', 'mdp1',  1000.0, 1),
       (2, 'Tenley', 'Boyd', 'tenley@email.com', 'mdp2',  800.0, 2),
       (3, 'John', 'Wick', 'lovemydog@email.com', 'woof',  15000.0, 3),
       (4, 'Frodon', 'delacomté', 'frodon@email.com', 'precieux',  5000.0, 4);

COMMIT;

INSERT INTO banks (id, bank_name, iban, bic, user_id)
VALUES (1, 'evasionFiscale', 'IBANEVASIONFISCALE', 'BICSTYLO', 1),
       (2, 'donnelessous', 'IBANDONNELESSOUS', 'BICETCOLEGRAM', 2),
       (3, 'jaimemabanque', 'IBANJAIMEMAMBANQUE', 'BICBICETGROMINET', 3),
       (4, 'jaimemabanque', 'IBANJAIMEMAMBANQUE', 'BICBICETGROMINET', 4);
COMMIT;

INSERT INTO relation (id, buddy, owner)
VALUES (1,1,2 ),
       (2,3,2 ),
       (3, 1,3),
       (4, 4,3);
COMMIT;


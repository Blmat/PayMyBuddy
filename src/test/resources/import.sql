INSERT INTO 'bank_account' ('id', 'iban' , 'account_name', 'bic' ) VALUES('1', 'aaaaaaaaa', 'Bank', '0123456789');
INSERT INTO 'bank_account' ('id', 'iban' , 'account_name', 'bic' ) VALUES('2', 'bbbbbbbbb', 'OtherBank', '9876543210');

INSERT INTO 'users' ('user_id', 'email', 'first_name', 'last_name', 'password', 'bank', 'balance') VALUES ('1','user@email.com', 'user' , 'superUser', '', '1' , '10');
INSERT INTO 'users' ('user_id', 'email', 'first_name', 'last_name', 'password', 'bank', 'balance') VALUES ('2','friend@email.com', 'buddy' , 'superBuddy', '', '2' , '10');
 DROP TABLE IF EXISTS users;
 DROP TABLE IF EXISTS bank_account;
 DROP TABLE IF EXISTS relation;
 DROP TABLE IF EXISTS transaction;



 CREATE TABLE users
 (
     `id`        INT NOT NULL AUTO_INCREMENT,
     `bank_id`   INT NOT NULL,
     `firstname` VARCHAR(255) NOT NULL,
     `lastname` VARCHAR(255) NOT NULL,
     `email`    VARCHAR(255) NOT NULL,
     `password`  VARCHAR(255) NOT NULL,
     `balance`  DECIMAL(9, 2) NOT NULL DEFAULT 0,
      PRIMARY KEY (`id`)
 );

 CREATE TABLE bank_account
 (
     `bank_name` VARCHAR(255) NOT NULL,
     `iban`      VARCHAR(300) NOT NULL,
     `bic`       VARCHAR(300) NOT NULL,
     `user_id`   INT NOT NULL,
     PRIMARY KEY (`iban`),
     INDEX `fk_bank_account_user1_idx` (`user_id` ASC),
     CONSTRAINT `fk_bank_account_user1`
         FOREIGN KEY (`user_id`)
             REFERENCES `pmbdb`.`users` (`id`)
             ON DELETE NO ACTION
             ON UPDATE NO ACTION
 );
 CREATE TABLE relation
 (
     `id`    INT NOT NULL AUTO_INCREMENT,
     `buddy` INT NOT NULL,
     `owner`INT NOT NULL,
     PRIMARY KEY (`id`),
     INDEX `fk_relation_user2_idx` (`buddy` ASC),
     CONSTRAINT `fk_relation_user1`
         FOREIGN KEY (`owner`)
             REFERENCES `pmbdb`.`users` (`id`)
             ON DELETE NO ACTION
             ON UPDATE NO ACTION,
     CONSTRAINT `fk_relation_user2`
         FOREIGN KEY (`buddy`)
             REFERENCES `pmbdb`.`users` (`id`)
             ON DELETE NO ACTION
             ON UPDATE NO ACTION
 );
 CREATE TABLE transaction
 (
     `id`         INT NOT NULL AUTO_INCREMENT,
     `amount`     DECIMAL(9, 2) NOT NULL,
     `reason`     VARCHAR(300),
     `date`      datetime NOT NULL DEFAULT now(),
     `commission`  DECIMAL(9, 2) NOT NULL DEFAULT 0,
     `creditor`   INT NOT NULL,
     `debtor`     INT NOT NULL,
      PRIMARY KEY (`id`),
     INDEX `fk_internal_transfert_user1_idx` (`creditor` ASC),
     INDEX `fk_internal_transfert_user2_idx` (`debtor` ASC)
 );
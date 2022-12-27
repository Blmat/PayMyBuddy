drop table if exists BankAccount;

drop table if exists hibernate_sequence;

drop table if exists relation;

drop table if exists transaction;

drop table if exists UserAccount;

create table BankAccount
(
    id          integer not null auto_increment,
    accountName varchar(255),
    bic         varchar(255),
    iban        varchar(255),
    primary key (id)
);

create table hibernate_sequence
(
    next_val bigint
);

insert into hibernate_sequence
values (1);

create table relation
(
    owner integer not null,
    buddy integer not null,
    primary key (owner, buddy)
);


create table transaction
(
    id         integer not null,
    amount     decimal(19, 2),
    commission decimal(19, 2),
    date       date,
    reason     varchar(255),
    creditor   integer,
    debtor     integer,
    primary key (id)
);


create table UserAccount
(
    userId    integer        not null auto_increment,
    balance   decimal(19, 2) not null,
    email     varchar(255)   not null,
    firstName varchar(255)   not null,
    lastName  varchar(255)   not null,
    password  varchar(255)   not null,
    bank_id   integer,
    primary key (userId)
);

alter table UserAccount
    add constraint UK_hqo0emx3b1cgbtl1aany7lugc unique (email);

alter table relation
    add constraint FK83lyfheuwr3kltpenl9vd2sca foreign key (buddy) references UserAccount (userId);

alter table relation
    add constraint FKe7gred6yqfarjeehm4e520ftn foreign key (owner) references UserAccount (userId);

alter table transaction
    add constraint FKdfq2b635r033ud9mqx368wwn4 foreign key (creditor) references UserAccount (userId);

alter table transaction
    add constraint FKa4xpyw98jxxwl9ww7u9vcc4xg foreign key (debtor) references UserAccount (userId);

alter table UserAccount
    add constraint FK2mwtxcjirqpi2f28r5ye5ljj1 foreign key (bank_id) references BankAccount (id);


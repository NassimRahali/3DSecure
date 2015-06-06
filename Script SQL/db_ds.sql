create database DB_DS;

use DB_DS;

create table banques(
	bank_id integer not null auto_increment,
	name varchar(100) not null,
	ip char(15) not null,
	port int not null,
	primary key (bank_id)
);

create table cartes(
  card_id bigint not null,
  bank_fk integer not null,
  primary key (card_id),
  constraint FK_CARTES_BANQUES foreign key (bank_fk) references banques(bank_id)
);

-- Seulement en localhost pour l'exam
insert into banques values (1, 'BANK1', 'localhost', 90001);
insert into banques values (2, 'BANK2', 'localhost', 90002);
insert into banques values (3, 'BANK3', 'localhost', 90003);

-- Faudra utiliser ces ids aussi.
insert into cartes values ('123456789', 1);
insert into cartes values ('554817512', 2);
insert into cartes values ('485123541', 3);
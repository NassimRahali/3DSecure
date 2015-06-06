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
-- J'avais mis des ports hors du range autorisé
insert into banques values (1, 'BANK1', '127.0.0.1', 9999);
insert into banques values (2, 'BANK2', '127.0.0.1', 9998);
insert into banques values (3, 'BANK3', '127.0.0.1', 9997);

-- Faudra utiliser ces ids aussi.
insert into cartes values ('123456789', 1);
insert into cartes values ('554817512', 2);
insert into cartes values ('485123541', 3);

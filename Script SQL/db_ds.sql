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
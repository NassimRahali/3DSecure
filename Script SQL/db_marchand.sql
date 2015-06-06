create database DB_MARCHAND;

use DB_MARCHAND;

create table produits(
	idp integer not null auto_increment,
	produit varchar(200) not null,
	prix float not null,
	primary key (idp)
);

insert into produits values (1, 'Rossignol Experience 84 CA OPEN', 399);
insert into produits values (2, 'SALOMON X PRO 120 2015', 459);
insert into produits values (3, 'SCOTT COSMOS II 2015', 549);
insert into produits values (4, 'DUPRAZ N SERIES', 599);
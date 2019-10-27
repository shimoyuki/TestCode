create database chuck;

use chuck;
drop table if exists gene;
create table gene(
`dataId` varchar(100) not null,
`data` varchar(200),
`quality` varchar(200),
primary key(`dataId`)
);
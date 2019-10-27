create database PMS;
use PMS;

drop table if exists `department`;
create table `department` (
    `id` int(11) not null,
    `name` varchar(50) default '',
    primary key (`id`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists `education`;
create table `education` (
    `id` int(11) not null,
    `degree` varchar(50) default '',
    primary key (`id`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists `person`;
create table `person` (
    `id` int(11) not null,
    `name` varchar(50) not null,
    `dep_id` int(11),
    `edu_id` int(11),
    `post` varchar(50) default 'nopost',
    `salary` decimal(8 , 2 ) default 0.00,
    primary key (`dep_id` , `id`),
    constraint `fk_dep_id` foreign key (`dep_id`)
        references `department` (`id`)
        on delete cascade on update cascade,
    constraint `fk_edu_id` foreign key (`edu_id`)
        references `education` (`id`)
        on update cascade
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;

delimiter ||
drop trigger if exists person_deal_insert||
create trigger person_deal_insert before insert on person for each row
begin  
declare di int(10);
declare ei int(10);
set di=new.dep_id;
set ei=new.edu_id;
if not exists (select * from `department` where id=di)then
insert into `department`(`id`) values(di);
end if;
if ei is not null and not exists (select * from `education` where id=ei) then
insert into `education`(`id`) values(ei);
end if;
end||  
delimiter ;


delimiter ||
drop trigger if exists person_deal_update||
create trigger person_deal_update before update on person for each row
begin  
declare ei int(10);
set ei=new.edu_id;
if ei is not null and not exists(select * from education where id=ei) then
insert into education(id) values(ei);
end if;
end||  
drop trigger if exists person_after_update||
create trigger person_after_update after update on person for each row
begin  
declare ei int(10);
set ei=old.edu_id;
if ei is not null and not exists(select * from person where edu_id=ei) then
delete from education where id=ei;
end if;
end||  
delimiter ;

insert into person(id,name,dep_id,edu_id,post,salary)
values(01,'n1',01,01,'p1',11.11);
insert into person(id,name,dep_id,edu_id,post,salary)
values(02,'n2',01,01,'p2',22.22);
insert into person(id,name,dep_id,edu_id,post,salary)
values(03,'n3',02,02,'p3',33.33);
insert into person(id,name,dep_id,post,salary)
values(04,'n4',02,'p4',44.44);

delete from department 
where
    id = 1;
select 
    *
from
    Person
where
    name = 'n1';
update person 
set 
    edu_id = 3,
    post = 'p5'
where
    id = 1 and dep_id = 1;
select 
    count(*) as total
from
    person
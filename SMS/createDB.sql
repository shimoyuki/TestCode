create database SMS;
use SMS;

drop table if exists S_user;

/*==============================================================*/
/* Table: S_user                                                */
/*==============================================================*/
create table S_user
(
   `u_Account`            varchar(100) not null comment '邮箱用户名',
   `u_Pwd`                varchar(32) not null comment '密码',
   `u_Domain`             varchar(100) not null comment '邮箱域名',
   `u_Level`              int not null comment '权限',
   `u_MemoSize`           decimal(7) not null comment '邮箱空间',
   `u_Tel`                varchar(15)  comment '用户Tel',
   `u_Gender`             char(2) comment '用户性别',
   `u_headpic`            varchar(255) comment '用户头像',
   primary key (`u_Account`,`u_Domain`)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

insert into S_user(u_Account,u_Pwd,u_Domain,u_Level,u_MemoSize)
values('cardinal','427023ff7e5f28a11e61fce0b4917b57','admin.shizuku.com',1,1024);
insert into S_user(u_Account,u_Pwd,u_Domain,u_Level,u_MemoSize)
values('from','d98a07f84921b24ee30f86fd8cd85c3c','shizuku.com',2,1024),
('to','01b6e20344b68835c5ed1ddedf20d531','shizuku.com',2,1024);


select * from `s_user` where `u_Level` = 2;

delete from `s_user` where `u_Level` = 2;

update S_user set u_Tel = '110' where u_Account = 'test';


drop table if exists S_contact;
/*==============================================================*/
/* Table: S_contact                                                */
/*==============================================================*/
create table S_contact
(
   `u_Account`            varchar(100) not null comment '邮箱用户名' ,
   `c_Account`            varchar(100) not null comment '联系人用户名',
   `u_Domain`             varchar(100) not null comment '邮箱域名',
   `c_Domain`             varchar(100) not null comment '联系人域名',
   `c_Nick`               varchar(100) comment '联系人昵称',
primary key (`u_Account`,`u_Domain`,`c_Account`,`c_Domain`),
   FOREIGN KEY (`u_Account`,`u_Domain`) REFERENCES `S_user` (`u_Account`,`u_Domain`) on update cascade on delete cascade 
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

insert into S_contact(u_Account,u_Domain,c_Account,c_Domain,c_Nick)
values('from','shizuku.com','cardinal','admin.shizuku.com','邮箱助手'),
('to','shizuku.com','cardinal','admin.shizuku.com','邮箱助手');

insert into S_contact(u_Account,u_Domain,c_Account,c_Domain,c_Nick)
values('cardinal','admin.shizuku.com','from','shizuku.com','发件测试'),
('cardinal','admin.shizuku.com','to','shizuku.com','收件测试');
update S_user set u_Account='change' where u_Account='test';
update S_contact set c_Nick='MailboxHelper' where u_Account='from' and u_Domain='shizuku.com' and c_Account='cardinal' and c_Domain='admin.shizuku.com';
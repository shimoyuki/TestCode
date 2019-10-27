delimiter ||      #mysql 默认结束符号是分号，当你在写触发器或者存储过程时有分号出现，会中止转而执行  
drop trigger if exists updatename||    #删除同名的触发器，  
create trigger updatename after update on user for each row   #建立触发器，  
begin  
#old,new都是代表当前操作的记录行，你把它当成表名，也行;  
if new.name!=old.name then   #当表中用户名称发生变化时,执行  
update comment set comment.name=new.name where comment.u_id=old.id;  
end if;  
end||  
delimiter ;


show variables like "part";

create database partition_test;
use partition_test;
create table if not exists `user`(
`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',   
`name` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',   
`sex` int(1) NOT NULL DEFAULT '0' COMMENT '0为男，1为女',   
PRIMARY KEY (`id`)   
)ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1
PARTITION BY RANGE (id) (   
PARTITION p0 VALUES LESS THAN (3),   
PARTITION p1 VALUES LESS THAN (6),   
PARTITION p2 VALUES LESS THAN (9),   
PARTITION p3 VALUES LESS THAN (12),   
PARTITION p4 VALUES LESS THAN MAXVALUE
);  


create table if not exists `intelligence`(
  `iId` int(11) NOT NULL auto_increment,
  `iTitle` varchar(200) NOT NULL,
  `iAbs` varchar(500) NOT NULL,
  `iUrl` varchar(1000) NOT NULL,
  `iDate` datetime NOT NULL,
  `iExpert_name` varchar(50) NOT NULL,
  `iExpert_org` varchar(50) NOT NULL,
  PRIMARY KEY (`iID`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
partition by range(iId)(
partition part1 values less than(100),
partition part2 values less than(200),
partition part3 values less than(300),
partition part4 values less than maxvalue
);



INSERT INTO `user` (`name` ,`sex`)VALUES ('tank', '0')   
,('zhang',1),('ying',1),('张',1),('映',0),('test1',1),('tank2',1)   
,('tank1',1),('test2',1),('test3',1),('test4',1),('test5',1),('tank3',1)   
,('tank4',1),('tank5',1),('tank6',1),('tank7',1),('tank8',1),('tank9',1)   
,('tank10',1),('tank11',1),('tank12',1),('tank13',1),('tank21',1),('tank42',1);  

select count(id) as count from user;

alter table user drop partition p4; 

alter table user partition by RANGE(id)   
(PARTITION p1 VALUES less than (1),   
PARTITION p2 VALUES less than (5),   
PARTITION p3 VALUES less than MAXVALUE); 


CREATE TABLE IF NOT EXISTS `list_part` (  
  `id` int(11) NOT NULL  COMMENT '用户ID',  
  `province_id` int(2) NOT NULL DEFAULT 0 COMMENT '省',  
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',  
  `sex` int(1) NOT NULL DEFAULT '0' COMMENT '0为男，1为女'   
) ENGINE=INNODB  DEFAULT CHARSET=utf8   
PARTITION BY LIST (province_id) (   
    PARTITION p0 VALUES IN (1,2,3,4,5,6,7,8),   
    PARTITION p1 VALUES IN (9,10,11,12,16,21),   
    PARTITION p2 VALUES IN (13,14,15,19),   
    PARTITION p3 VALUES IN (17,18,20,22,23,24)   
);   

CREATE TABLE IF NOT EXISTS `hash_part` (   
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论ID',   
  `comment` varchar(1000) NOT NULL DEFAULT '' COMMENT '评论',   
  `ip` varchar(25) NOT NULL DEFAULT '' COMMENT '来源IP',   
  PRIMARY KEY (`id`)   
) ENGINE=INNODB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1   
PARTITION BY HASH(id)   
PARTITIONS 3;   

CREATE TABLE IF NOT EXISTS `key_part` (   
  `news_id` int(11) NOT NULL  COMMENT '新闻ID',   
  `content` varchar(1000) NOT NULL DEFAULT '' COMMENT '新闻内容',   
  `u_id` varchar(25) NOT NULL DEFAULT '' COMMENT '来源IP',   
  `create_time` DATE NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '时间'  
) ENGINE=INNODB  DEFAULT CHARSET=utf8   
PARTITION BY LINEAR HASH(YEAR(create_time))   
PARTITIONS 3;   

CREATE TABLE IF NOT EXISTS `sub_part` (   
  `news_id` int(11) NOT NULL  COMMENT '新闻ID',   
  `content` varchar(1000) NOT NULL DEFAULT '' COMMENT '新闻内容',   
  `u_id`  int(11) NOT NULL DEFAULT 0 COMMENT '来源IP',   
  `create_time` DATE NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '时间'  
) ENGINE=INNODB  DEFAULT CHARSET=utf8   
PARTITION BY RANGE(YEAR(create_time))   
SUBPARTITION BY HASH(TO_DAYS(create_time))(   
PARTITION p0 VALUES LESS THAN (1990)(SUBPARTITION s0,SUBPARTITION s1,SUBPARTITION s2),   
PARTITION p1 VALUES LESS THAN (2000)(SUBPARTITION s3,SUBPARTITION s4,SUBPARTITION good),   
PARTITION p2 VALUES LESS THAN MAXVALUE(SUBPARTITION tank0,SUBPARTITION tank1,SUBPARTITION tank3)   
);   

alter table list_part add partition(partition p4 values in (25,26,28));  
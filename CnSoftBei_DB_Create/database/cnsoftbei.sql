create database cnsoftbei;
use cnsoftbei;
drop table if exists `patent`;
create table `patent`(
  `pId` int(11) NOT NULL auto_increment,
  `pTitle` varchar(100) NOT NULL,
  `pDate` date NOT NULL,
  `pAbs` varchar(500) NOT NULL,
  `pApplicant` varchar(50) NOT NULL,
  `pInventor` varchar(50) NOT NULL,
  `pExpert_name` varchar(50) NOT NULL,
  `pExpert_org` varchar(50) NOT NULL,
  `pPatent_agency` varchar(50) NOT NULL,
  PRIMARY KEY (`pId`)
)ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;

drop table if exists `literature`;
create table `literature`(
  `lId` int(11) NOT NULL auto_increment,
  `lTitle` varchar(200) NOT NULL,
  `lAbs` varchar(1200) NOT NULL,
  `lAuthor_cn` varchar(100) NOT NULL,
  `lUnit` varchar(500) NOT NULL,
  `lJournal_cn` varchar(100) NOT NULL,
  `lApp_date` varchar(50) NOT NULL,
  `lKey` varchar(100) NOT NULL,
  `lExpert_name` varchar(50) NOT NULL,
  `lEN_abbr` varchar(10) default 'unknown',
  `lExpert_org` varchar(100) NOT NULL,
  PRIMARY KEY (`lId`,`lEN_abbr`)
)ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1
PARTITION BY RANGE COLUMNS(`lEN_abbr`) (   
PARTITION p0 VALUES LESS THAN ('E'),   
PARTITION p1 VALUES LESS THAN ('I'),   
PARTITION p2 VALUES LESS THAN ('M'),   
PARTITION p3 VALUES LESS THAN ('Q'), 
PARTITION p4 VALUES LESS THAN ('T'), 
PARTITION p5 VALUES LESS THAN ('W'), 
PARTITION p6 VALUES LESS THAN ('Y'),   
PARTITION p7 VALUES LESS THAN MAXVALUE
);  

drop table if exists `intelligence`;
create table `intelligence`(
  `iId` int(11) NOT NULL auto_increment,
  `iTitle` varchar(200) NOT NULL,
  `iAbs` varchar(500) NOT NULL,
  `iUrl` varchar(1000) NOT NULL,
  `iDate` datetime NOT NULL,
  `iExpert_name` varchar(50) NOT NULL,
  `iEN_abbr` varchar(10) default 'unknown',
  `iExpert_org` varchar(100) NOT NULL,
  PRIMARY KEY (`iId`,`iEN_abbr`)
)ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1
PARTITION BY RANGE  COLUMNS(`iEN_abbr`) (   
PARTITION p0 VALUES LESS THAN ('E'),   
PARTITION p1 VALUES LESS THAN ('I'),   
PARTITION p2 VALUES LESS THAN ('M'),   
PARTITION p3 VALUES LESS THAN ('Q'), 
PARTITION p4 VALUES LESS THAN ('T'), 
PARTITION p5 VALUES LESS THAN ('W'), 
PARTITION p6 VALUES LESS THAN ('Y'),   
PARTITION p7 VALUES LESS THAN MAXVALUE
);  

create index p_title_index on patent(pTitle);
create index p_epname_index on patent(pExpert_name);
create index i_title_index on intelligence(iTitle);
create index i_epname_index on intelligence(iExpert_name);
create index l_title_index on literature(lTitle);
create index l_epname_index on literature(lExpert_name);

delimiter ||
drop trigger if exists i_deal_insert||
create trigger i_deal_insert before insert on intelligence for each row
begin  
declare ename varchar(50);
declare abbr varchar(10);
set ename=new.iExpert_name;
set abbr=getPY(ename);
if abbr is not null then
set new.iEN_abbr=abbr;
end if;
end||  
drop trigger if exists l_deal_insert||
create trigger l_deal_insert before insert on literature for each row
begin  
declare ename varchar(50);
declare abbr varchar(10);
set ename=new.lExpert_name;
set abbr=getPY(ename);
if abbr is not null then
set new.lEN_abbr=abbr;
end if;
end|| 
delimiter ;



select user,host,password from mysql.user;
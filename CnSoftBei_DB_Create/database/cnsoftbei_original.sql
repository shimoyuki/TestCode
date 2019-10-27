create database cnsoftbei;
use cnsoftbei;
drop table if exists patent;
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
  PRIMARY KEY (`pID`)
)ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;

drop table if exists literature;
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
  `lExpert_org` varchar(50) NOT NULL,
  PRIMARY KEY (`lID`)
)ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;

drop table if exists intelligence;
create table intelligence(
  `iId` int(11) NOT NULL auto_increment,
  `iTitle` varchar(200) NOT NULL,
  `iAbs` varchar(500) NOT NULL,
  `iUrl` varchar(1000) NOT NULL,
  `iDate` datetime NOT NULL,
  `iExpert_name` varchar(50) NOT NULL,
  `iExpert_org` varchar(50) NOT NULL,
  PRIMARY KEY (`iID`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

create index p_title_index on patent(pTitle);
create index p_epname_index on patent(pExpert_name);
create index i_title_index on intelligence(iTitle);
create index i_epname_index on intelligence(iExpert_name);
create index l_title_index on literature(lTitle);
create index l_epname_index on literature(lExpert_name);


insert into `literature`(lTitle,lAbs,lAuthor_cn,lUnit,lJournal_cn,lApp_date,lKey,lExpert_name,lExpert_org)
values('title','abs','author','unit','journal','20120101','key','张教授，李教授','zjs');
insert into `literature`(lTitle,lAbs,lAuthor_cn,lUnit,lJournal_cn,lApp_date,lKey,lExpert_name,lExpert_org)
values('title','abs','author','unit','journal','20120101','key','张教授','zjs');
insert into `intelligence`(iTitle,iAbs,iUrl,iDate,iExpert_name,iExpert_org)
values('title','abs','url','2015-01-01','张教授','zjs');

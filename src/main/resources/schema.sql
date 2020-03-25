CREATE TABLE `USER` 
(  
	`USER_ID`  integer not null auto_increment,
	`EMAIL` varchar(40) NOT NULL,   
	`PASSWORD` varchar(20), 
	PRIMARY KEY (`USER_ID`),
	UNIQUE KEY `EMAIL` (`EMAIL`)
) ;

CREATE TABLE `PET` 
(  
	`PET_ID`  integer not null auto_increment,
	`PET_TYPE` varchar(40) NOT NULL,   
	`BREED` varchar(20), 
	`NAME` varchar(20), 
	`PREFERENCES` TEXT, 
	`USER_ID`  integer NOT NULL,
	PRIMARY KEY (`PET_ID`),
	FOREIGN KEY (`USER_ID`) REFERENCES `USER` (`USER_ID`)
) ;

CREATE TABLE `POST` 
(  
	`POST_ID`  integer not null auto_increment,
	`POST_TEXT` TEXT,   
	`USER_ID`  integer NOT NULL,
	`CREATED_AT` timestamp default current_timestamp,
	PRIMARY KEY (`POST_ID`),
	FOREIGN KEY (`USER_ID`) REFERENCES `USER` (`USER_ID`)
) ;

CREATE TABLE `USER_RELATION`
(
	USER_ID integer not null,
	RELATION_ID integer not null,
	PRIMARY KEY (`USER_ID`,`RELATION_ID`),
	FOREIGN KEY (`USER_ID`) REFERENCES `USER` (`USER_ID`),
	FOREIGN KEY (`RELATION_ID`) REFERENCES `USER` (`USER_ID`)
) ;

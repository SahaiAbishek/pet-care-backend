CREATE TABLE `USER` 
(  
	`USER_ID`  integer not null auto_increment,
	`EMAIL` varchar(40) NOT NULL,   
	`PASSWORD` varchar(20), 
	PRIMARY KEY (`USER_ID`),
	UNIQUE KEY `EMAIL` (`EMAIL`)
) ;
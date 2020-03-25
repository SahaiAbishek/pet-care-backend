insert into USER values (1,'abhi@test.com','test123');
insert into USER values (2,'Abdul@test.com','test123');
insert into USER values (3,'Raghav@test.com','test123');
insert into USER values (4,'Binod@test.com','test123');
insert into USER values (5,'Test@test.com','test123');

insert into PET values (1,'DOG','HUSKY','TEST_HUSKY','any comments about your pet, likes dislikes etc',1);
insert into PET values (2,'CAT','DONO','TEST_CAT','any comments about your pet, likes dislikes etc',1);
insert into PET values (3,'DOG','BULL DOG','TEST_BULL','any comments about your pet, likes dislikes etc',2);
insert into PET values (4,'DOG','GERMAN SHEPHARD','TEST_GERMAN','any comments about your pet, likes dislikes etc',3);

insert into POST values (1,'First post by abhi..',1,parsedatetime('22-03-2020 18:47:52.69', 'dd-MM-yyyy hh:mm:ss.SS'));
insert into POST values (2,'Second post by abhi..',1,parsedatetime('23-03-2020 18:47:52.69', 'dd-MM-yyyy hh:mm:ss.SS'));
insert into POST values (3,'First post by Abdul..',2,parsedatetime('20-03-2020 18:47:52.69', 'dd-MM-yyyy hh:mm:ss.SS'));
insert into POST values (4,'Second post by Abdul..',2,parsedatetime('21-03-2020 18:47:52.69', 'dd-MM-yyyy hh:mm:ss.SS'));
insert into POST values (5,'First post by Test User..',5,parsedatetime('19-03-2020 18:47:52.69', 'dd-MM-yyyy hh:mm:ss.SS'));
insert into POST values (6,'First post by Raghav..',3,parsedatetime('18-03-2020 18:47:52.69', 'dd-MM-yyyy hh:mm:ss.SS'));

insert into USER_RELATION values (1,2);
insert into USER_RELATION values (1,3);
insert into USER_RELATION values (1,5);
insert into USER_RELATION values (3,2);
insert into USER_RELATION values (3,4);
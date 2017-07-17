INSERT INTO admins VALUES ('1111', '', 'Albus', 'Dumbledore', 'alby', 'hogwarts', '05/26/1984');

INSERT INTO students VALUES ('101','Harry','Potter','username','password','email','01/12/1990',1,'in',1200,'ECE','','CE420(A)','phone','addr'),
	 ('102','Hermione','Granger','username','password','','04/16/1989',2,'in',0,'CS','','CS510(B+),CS515(B+)','phone','addr'),
	 ('103','Ron','Weasley','username','password','','12/19/1991',2,'out',0,'CS','','CS515(A),CS520(A-),CS530(A+)','phone','addr'),
	 ('104','Draco','Malfoy','username','password','','03/21/1992',2,'inter',0,'CS','','CS510(A),CS515(B+),CS530(A+),CS525 (3 Credits)(A+)','phone','addr');
	 
INSERT INTO courses VALUES ('CS402','Numerical Methods',3,'CS',1),
	 ('CS510','Database',3,'CS',2),
	 ('CS505','Algorithms',3,'CS',2),
	 ('CS521','Cloud Computing',3,'CS',2),
	 ('CS525','Independent Study',2,'CS',2),
	 ('CS530','Dev-Ops',3,'CS',2),
	 ('CS421','VLSI II',3,'ECE',1),
	 ('CE420','Wizard Computing',3,'ECE',1);	
	 
INSERT INTO SEMESTERS VALUES ('CS402','Fall','2016','',2,10,'',2,'M,W 11:00AM-12:00PM'),
	 ('CS510','Fall','2016','Ogan',5,10,'',2,'M,W 11:00AM-12:00PM'),
	 ('CS505','Fall','2016','Stallman',2,10,'',2,'Tu,Th 1:00PM-2:00PM'),
	 ('CS521','Fall','2016','Instructor1, Instructor2',3,10,'',2,'Tu,Th 1:00PM-2:00PM'),
	 ('CS525','Fall','2016','Jin,Murphy-Hill',2,10,'',0,'M,W 2:00PM-3:00PM'),
	 ('CS530','Fall','2016','Parnin',2,10,'',2,'M,W 11:00AM-12:00PM'),
	 ('CS421','Fall','2016','???',4,10,'',2,'Tu,Th 4:00PM-5:00PM');
	 
INSERT INTO GRADING_SYSTEM VALUES ('A+',4.33),('A',4.00),('A-',3.67),('B+',3.33),('B',3.00),('B-',2.67),
	 ('C+',2.33),('C',2.00),('C-',1.67);
	 
INSERT INTO COSTS VALUES (2,'in-state',500),(2,'out-of-state',800),(2,'international',1000),(1,'in-state',400),
	 (1,'out-of-state',700),(1,'international',900);
	 
INSERT INTO CREDIT_LIMITS VALUES (2,'in-state',0,9),(2,'out-of-state',0,9),(2,'international',9,9),
	 (1,'in-state',0,12),(1,'out-of-state',0,12),(1,'international',9,12);
	 
INSERT INTO PREREQUISITES VALUES ('CS401',0,'CS402','F'),('CS521',3.5,'CS520','F'),('CS525',0,'','T'),
	 ('CS530',0,'CS515','F'),('CS421',0,'CE420','F');
	 

CREATE TABLE OD_USER
(
Account varchar(100) NOT NULL,
Password varchar(100) NOT NULL,
Comp_Code varchar(100),
Branch_Code varchar(100),
Role_Code varchar(50),
Sector varchar(50),
Last_loging date,
Apply_End_Date date,
Apply_Start_Date date,
CONSTRAINT pk_PersonID PRIMARY KEY (Account,Comp_Code)
)
--------------------
ALTER TABLE OD_USER
ADD FOREIGN KEY (Comp_Code)REFERENCES OD_COMPANY(Comp_Code),
FOREIGN KEY (Branch_Code)REFERENCES OD_BRANCH(Branch_Code),
FOREIGN KEY (Role_Code)REFERENCES OD_ROLE(Role_Code)
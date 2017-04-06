CREATE TABLE OD_COMPANY
(
Comp_Code INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
Comp_Name varchar(100) NOT NULL,
Group_Comp_code INT,
Email varchar(100),
Picture varchar(100),
Address varchar(100),
Description varchar(500),
Phone numeric(18, 0),
Apply_End_Date date,
Apply_Start_Date date,
)
------------ add khoa ngoai
ALTER TABLE OD_COMPANY
ADD FOREIGN KEY (Group_Comp_code)REFERENCES OD_COMPANY_GROUP(Group_Comp_code)

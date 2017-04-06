CREATE TABLE OR_FUNCTION
(
Function_Code INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
Function_Name varchar(100) NOT NULL,
Function_Name_EN varchar(100),
Branch_Code int FOREIGN KEY REFERENCES OD_BRANCH(Branch_Code),
Role_Code nchar(10),
Apply_End_Date date,
Apply_Start_Date date,
)
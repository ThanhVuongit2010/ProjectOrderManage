CREATE TABLE OD_COMPANY_GROUP
(
Group_Comp_code INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
GRoup_Comp_Name varchar(100) NOT NULL,
Email varchar(100),
Picture varchar(100),
Address varchar(100),
Description varchar(500),
Phone numeric(18, 0),
Apply_End_Date date,
Apply_Start_Date date,
)

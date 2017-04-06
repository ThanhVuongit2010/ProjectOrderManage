CREATE TABLE OD_BRANCH
(
Branch_Code INT NOT NULL IDENTITY(1,1) PRIMARY KEY,
Branch_Name varchar(100) NOT NULL,
Comp_code INT FOREIGN KEY REFERENCES OD_COMPANY(Comp_code),
Type INT NOT NULL,
Email varchar(100),
Picture varchar(100),
Address varchar(100),
Description varchar(500),
Phone numeric(18, 0),
Apply_End_Date date,
Apply_Start_Date date,
)
ALTER TABLE OD_BRANCH
ADD FOREIGN KEY (Comp_Code)REFERENCES OD_COMPANY(Comp_Code)

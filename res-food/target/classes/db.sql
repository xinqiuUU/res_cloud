create database  mybank;

use  mybank;

#
create table accounts(
                         accountid int primary key auto_increment,
                         balance numeric(10,2) check ( balance>0 ),
                         email varchar(255)
);
insert into accounts(balance,email) values (1000,'2921310632@qq.com');
insert into accounts(balance,email) values (1,'2921310632@qq.com');
insert into accounts(balance,email) values (9999,'2921310632@qq.com');

CREATE TABLE oprecord (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          accountid INT REFERENCES accounts(accountid),
                          opmoney NUMERIC(10,2),
                          optime DATETIME,
                          optype ENUM('deposit', 'withdraw', 'transfer') NOT NULL,
                          transferid INT
);
drop table oprecord;

select * from accounts;  # 账户
select * from oprecord;  # 流水记录
update accounts set email='3108542443@qq.com' ;
update accounts set email='2921310632@qq.com' ;

# 测试存钱
update accounts set balance=balance+100 where accountid=1;
insert into oprecord(accountid,opmoney,optime,optype,transferid)
values (1,100,now(),'deposite',null);

delete from oprecord;
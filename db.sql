# 数据库初始化脚本
# 创建数据库
create database res;
# 选择数据库
use res;
# 用户表
create table resuser
(
    userid   int auto_increment
        primary key,
    username varchar(50)  null,
    pwd      varchar(255) null,
    email    varchar(500) null
);

#  商品表
create table resfood
(
    fid       int auto_increment
        primary key,
    fname     varchar(50)   null,
    normprice decimal(8, 2) null,
    realprice decimal(8, 2) null,
    detail    varchar(2000) null,
    fphoto    varchar(1000) null
);
# 订单表
create table resorder
(
    roid         int auto_increment
        primary key,
    userid       int           null,
    address      varchar(500)  null,
    tel          varchar(100)  null,
    ordertime    date          null,
    deliverytime date          null,
    ps           varchar(2000) null,
    status       int           null,
    constraint fk_resorder
        foreign key (userid) references resuser (userid)
);
# 订单详情表
create table resorderitem
(
    roiid     int auto_increment
        primary key,
    roid      int           null,
    fid       int           null,
    dealprice decimal(8, 2) null,
    num       int           null,
    constraint fk_resorderitem_roid
        foreign key (roid) references resorder (roid),
    constraint fk_tbl_res_fid
        foreign key (fid) references resfood (fid)
);
INSERT INTO `res`.`resfood` (`fid`, `fname`, `normprice`, `realprice`, `detail`, `fphoto`) VALUES (1, '素炒莴笋丝', 22.00, 20.00, '营养丰富', '500008.jpg');
INSERT INTO `res`.`resfood` (`fid`, `fname`, `normprice`, `realprice`, `detail`, `fphoto`) VALUES (2, '蛋炒饭', 22.00, 20.00, '营养丰富', '500022.jpg');
INSERT INTO `res`.`resfood` (`fid`, `fname`, `normprice`, `realprice`, `detail`, `fphoto`) VALUES (3, '酸辣鱼', 42.00, 40.00, '营养丰富', '500023.jpg');
INSERT INTO `res`.`resfood` (`fid`, `fname`, `normprice`, `realprice`, `detail`, `fphoto`) VALUES (4, '鲁粉', 12.00, 10.00, '营养丰富', '500024.jpg');
INSERT INTO `res`.`resfood` (`fid`, `fname`, `normprice`, `realprice`, `detail`, `fphoto`) VALUES (5, '西红柿蛋汤', 12.00, 10.00, '营养丰富', '500025.jpg');
INSERT INTO `res`.`resfood` (`fid`, `fname`, `normprice`, `realprice`, `detail`, `fphoto`) VALUES (6, '炖鸡', 102.00, 100.00, '营养丰富', '500026.jpg');
INSERT INTO `res`.`resfood` (`fid`, `fname`, `normprice`, `realprice`, `detail`, `fphoto`) VALUES (7, '炒鸡', 12.00, 10.00, '营养丰富', '500033.jpg');
INSERT INTO `res`.`resfood` (`fid`, `fname`, `normprice`, `realprice`, `detail`, `fphoto`) VALUES (8, '炒饭', 12.00, 10.00, '营养丰富', '500034.jpg');
INSERT INTO `res`.`resfood` (`fid`, `fname`, `normprice`, `realprice`, `detail`, `fphoto`) VALUES (9, '手撕前女友', 12.00, 10.00, '营养丰富', '500035.jpg');
INSERT INTO `res`.`resfood` (`fid`, `fname`, `normprice`, `realprice`, `detail`, `fphoto`) VALUES (10, '面条', 12.00, 10.00, '营养丰富', '500036.jpg');
INSERT INTO `res`.`resfood` (`fid`, `fname`, `normprice`, `realprice`, `detail`, `fphoto`) VALUES (11, '端菜', 12.00, 10.00, '营养丰富', '500038.jpg');
INSERT INTO `res`.`resfood` (`fid`, `fname`, `normprice`, `realprice`, `detail`, `fphoto`) VALUES (12, '酸豆角', 12.00, 10.00, '营养丰富', '500041.jpg');
INSERT INTO `res`.`resfood` (`fid`, `fname`, `normprice`, `realprice`, `detail`, `fphoto`) VALUES (13, '鱼香肉丝', 25.00, 100.00, '好吃好吃', '1.jpg');
INSERT INTO `res`.`resfood` (`fid`, `fname`, `normprice`, `realprice`, `detail`, `fphoto`) VALUES (14, '鱼香肉丝', 25.00, 100.00, '好吃好吃', '1.jpg');
INSERT INTO `res`.`resfood` (`fid`, `fname`, `normprice`, `realprice`, `detail`, `fphoto`) VALUES (15, '鱼香肉丝', 25.00, 100.00, '好吃好吃', '1.jpg');
INSERT INTO `res`.`resfood` (`fid`, `fname`, `normprice`, `realprice`, `detail`, `fphoto`) VALUES (16, '炒肉', 20.00, 15.00, '好吃',

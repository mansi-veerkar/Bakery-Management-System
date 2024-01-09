create database test2db;
use test2db;

create table product_info (product_id int primary key, product_name varchar(30) not null, product_price double, product_qty int check (product_qty>=0));
create table order_info (order_id int primary key auto_increment, customer_name varchar(30) not null, product_id int, product_qty int check(product_qty>0), total double, foreign key (product_id) references product_info(product_id)); 

insert into product_info values (101, 'Laptop', 45000.99, 5);
insert into product_info values (102, 'Mobile', 145000, 3);
insert into product_info values (103, 'Oven', 23000.5, 2);
insert into Product_info values(104, 'Watch',19000.33,10);
insert into product_info values(105,'Speaker',6500,4);

select * from product_info;
select * from order_info;


delimiter //
create procedure placeOrder(in cName varchar(20), in pId int, in pQty int, out sts boolean)
begin
    declare qty int;
    select product_qty into qty from product_info where product_id=pId;
    set sts = false;
     if pQty <= qty then
        insert into order_info(customer_name, product_id, product_qty) values (cName, pId, pQty);
        update product_info set product_qty = product_qty - pQty where product_id = pId;
        set sts = true;
	end if;
end //
delimiter ;

SELECT O.ORDER_ID, O.CUSTOMER_NAME,P.PRODUCT_NAME, O.PRODUCT_QTY,O.PRODUCT_QTY * P.PRODUCT_PRICE AS TOTAL FROM ORDER_INFO O INNER JOIN PRODUCT_INFO P ON O.PRODUCT_ID=P.PRODUCT_ID ORDER BY O.ORDER_ID ASC



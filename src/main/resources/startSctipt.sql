declare
    users_count number;
begin
    select count(1)
    into users_count
    from dba_tables
    where owner = 'ZYKIN'
      and table_name = 'USER';
    if (0 = users_count)
    then
        execute immediate
            'create table USERS
             (
                 id varchar2(100) not null primary key,
                 role varchar2(100) not null,
                 login varchar(100) unique not null,
                 password varchar(100) not null,
                 surname varchar(100) not null,
                 middlename varchar(100) not null,
                 name varchar(100) not null
             )';
    end if;
end;

declare
    buyer_count number;
begin
    select count(1)
    into buyer_count
    from dba_tables
    where owner = 'ZYKIN'
      and table_name = 'buyer';
    if (0 = buyer_count)
    then
        execute immediate
            'create table buyer
             (
                 id varchar2(100) not null primary key,
                 surname varchar(100) not null,
                 middlename varchar(100) not null,
                 name varchar(100) not null,
                 date_of_birth date not null,
                 phone_number number unique not null,
                 address varchar2(200) not null
             )';
    end if;
end;

declare
    recipe_count number;
begin
    select count(1)
    into recipe_count
    from dba_tables
    where owner = 'ZYKIN'
      and table_name = 'RECIPE';
    if (0 = recipe_count)
    then
        execute immediate
            'create table RECIPE
             (
                 id varchar2(100) not null primary key,
                 id_buyer varchar2(100) not null,
                 id_doctor varchar2(100) not null,
                 diagnosis varchar2(100) not null,
                 date_of_done date,
                 id_medicine varchar2(100) not null,
                 CONSTRAINT fk_recipe_buyer FOREIGN KEY (id_buyer) references BUYER(ID),
                 CONSTRAINT fk_recipe_doctor FOREIGN KEY (id_doctor) references USERS(ID),
                 CONSTRAINT fk_recipe_medicine FOREIGN KEY (id_medicine) references MEDICINE(ID)
             )';
    end if;
end;

create or replace trigger tr_recipe
    before insert or update
    on RECIPE
    for each row
declare
    user_role varchar(100);
begin
    select role
    into user_role
    from USERS where
            USERS.ID = :new.id_doctor;
    if (user_role != 'DOCTOR')
    then
        RAISE_APPLICATION_ERROR( -20001,
                                 'Person should be organizer, competition must exist' );
    end if;
end;

declare
    order_count number;
begin
    select count(1)
    into order_count
    from dba_tables
    where owner = 'ZYKIN'
      and table_name = 'ORDERS';
    if (0 = order_count)
    then
        execute immediate
            'create table ORDERS
             (
                 id varchar2(100) not null primary key,
                 id_seller varchar2(100) not null,
                 id_technologist varchar2(100) not null,
                 id_recipe varchar2(100) not null,
                 date_of_order date not null,
                 date_of_manufacturing date,
                 date_of_receive date,
                 CONSTRAINT fk_order_seller FOREIGN KEY (id_seller) references USERS(ID),
                 CONSTRAINT fk_order_technologist FOREIGN KEY (id_technologist) references USERS(ID),
                 CONSTRAINT fk_order_recipe FOREIGN KEY (id_recipe) references RECIPE(ID)
             )';
    end if;
end;

create or replace trigger tr_order
    before insert or update
    on ORDERS
    for each row
declare
    seller_role varchar(100);
    technologist_role varchar(100);
begin
    select role
    into seller_role
    from USERS where
            USERS.ID = :new.id_seller;
    select role
    into technologist_role
    from USERS where
            USERS.ID = :new.id_technologist;
    if (seller_role != 'PHARMACIST_SELLER' || technologist_role != 'PHARMACIST_TECHNOLOGIST')
    then
        RAISE_APPLICATION_ERROR( -20001,
                                 'Person should be organizer, competition must exist' );
    end if;
end;

declare
    medicine_count number;
begin
    select count(1)
    into medicine_count
    from dba_tables
    where owner = 'ZYKIN'
      and table_name = 'MEDICINE';
    if (0 = medicine_count)
    then
        execute immediate
            'create table MEDICINE
             (
                 id varchar2(100) not null primary key,
                 name varchar2(100) not null,
                 type varchar2(100) not null
             )';
    end if;
end;

declare
    goods_on_warehouse_count number;
begin
    select count(1)
    into goods_on_warehouse_count
    from dba_tables
    where owner = 'ZYKIN'
      and table_name = 'GOODS_ON_WAREHOUSE';
    if (0 = goods_on_warehouse_count)
    then
        execute immediate
            'create table GOODS_ON_WAREHOUSE
             (
                 id varchar2(100) not null primary key,
                 unit varchar2(100) not null,
                 price_for_unit varchar2(100) not null,
                 id_medicine varchar2(100) not null unique,
                 CONSTRAINT fk_goods_on_warehouse_medicine FOREIGN KEY (id_medicine) references MEDICINE(ID)
             )';
    end if;
end;

declare
    goods_on_warehouse_count number;
begin
    select count(1)
    into goods_on_warehouse_count
    from dba_tables
    where owner = 'ZYKIN'
      and table_name = 'GOODS_ON_WAREHOUSE';
    if (0 = goods_on_warehouse_count)
    then
        execute immediate
            'create table GOODS_ON_WAREHOUSE
             (
                 id varchar2(100) not null primary key,
                 unit varchar2(100) not null,
                 price_for_unit varchar2(100) not null,
                 id_medicine varchar2(100) not null unique,
                 medicine_count number not null,
                 CONSTRAINT fk_goods_on_warehouse_medicine FOREIGN KEY (id_medicine) references MEDICINE(ID)
             )';
    end if;
end;


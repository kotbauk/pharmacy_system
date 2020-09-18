declare
    users_count number;
begin
    select count(1)
    into users_count
    from all_tables
    where owner = 'ZYKIN'
      and table_name = 'USER';
    if (0 = users_count)
    then
        execute immediate
            'create table USERS
             (
                 id         varchar2(100)         not null primary key,
                 role       varchar2(100)         not null,
                 login      nvarchar2(100) unique not null,
                 password   nvarchar2(100)        not null,
                 surname    nvarchar2(100)        not null,
                 middlename nvarchar2(100),
                 name       nvarchar2(100)        not null
             )';
    end if;
end;

declare
    buyer_count number;
begin
    select count(1)
    into buyer_count
    from all_tables
    where owner = 'ZYKIN'
      and table_name = 'buyer';
    if (0 = buyer_count)
    then
        execute immediate
            'create table buyer
             (
                 id            varchar2(100)  not null primary key,
                 surname       nvarchar2(100) not null,
                 middlename    nvarchar2(100),
                 name          nvarchar2(100) not null,
                 date_of_birth date           not null,
                 phone_number  number unique  not null,
                 address       nvarchar2(200) not null
             )';
    end if;
end;

declare
    prescriptions_count number;
begin
    select count(1)
    into prescriptions_count
    from all_tables
    where owner = 'ZYKIN'
      and table_name = 'PRESCRIPTIONS';
    if (0 = prescriptions_count)
    then
        execute immediate
            'create table PRESCRIPTIONS
             (
                 id           varchar2(100)  not null primary key,
                 id_buyer     varchar2(100)  not null,
                 id_drug      varchar2(100)  not null,
                 diagnosis    nvarchar2(100) not null,
                 date_of_done date,
                 CONSTRAINT fk_recipe_buyer FOREIGN KEY (id_buyer) references BUYER (ID),
                 CONSTRAINT fk_recipe_medicine FOREIGN KEY (id_drug) references GOODS_ON_WAREHOUSE (ID_GOOD)
             )';
    end if;
end;

declare
    order_count number;
begin
    select count(1)
    into order_count
    from all_tables
    where owner = 'ZYKIN'
      and table_name = 'ORDERS';
    if (0 = order_count)
    then
        execute immediate
            'create table ORDERS
             (
                 id                    varchar2(100) not null primary key,
                 id_seller             varchar2(100) not null,
                 id_technologist       varchar2(100) not null,
                 id_prescription       varchar2(100) not null,
                 date_of_order         date          not null,
                 date_of_manufacturing date,
                 date_of_receive       date,
                 CONSTRAINT fk_order_seller FOREIGN KEY (id_seller) references USERS (ID),
                 CONSTRAINT fk_order_technologist FOREIGN KEY (id_technologist) references USERS (ID),
                 CONSTRAINT fk_order_recipe FOREIGN KEY (id_prescription) references PRESCRIPTIONS (ID_PRESCRIPT)
             )';
    end if;
end;

create or replace trigger tr_order
    before insert or update
    on ORDERS
    for each row
declare
    seller_role       varchar(100);
    technologist_role varchar(100);
begin
    select role
    into seller_role
    from USERS
    where USERS.ID = :new.id_seller;
    select role
    into technologist_role
    from USERS
    where USERS.ID = :new.id_technologist;
    if (seller_role != 'PHARMACIST_SELLER' || technologist_role != 'PHARMACIST_TECHNOLOGIST')
    then
        RAISE_APPLICATION_ERROR(-20001,
                                'Person should be organizer, competition must exist');
    end if;
end;

declare
    goods_on_warehouse_count number;
begin
    select count(1)
    into goods_on_warehouse_count
    from all_tables
    where owner = 'ZYKIN'
      and table_name = 'GOODS_ON_WAREHOUSE';
    if (0 = goods_on_warehouse_count)
    then
        execute immediate
            'create table GOODS_ON_WAREHOUSE
             (
                 id             varchar2(100)  not null primary key,
                 name           nvarchar2(100) not null,
                 type           nvarchar2(32)  not null,
                 unit           nvarchar2(16)  not null,
                 component_type varchar2(100),
                 amount         number(*)      not null,
                 minimal_amount number(*)      not null,
                 price_per_unit float(63)
             )';
    end if;
end;

declare
    composition_count number;
begin
    select count(1)
    into composition_count
    from all_tables
    where owner = 'ZYKIN'
      and table_name = 'COMPOSITION';
    if (0 = composition_count)
    then
        execute immediate
            'create table COMPOSITION
             (
                 AMOUNT NUMBER not null
                     constraint COMPOSIT_NON_NEGATIVE_AMOUNT
                         check ( AMOUNT > 0 ),
                 ID_DRUG VARCHAR2(100) not null
                     constraint COMPOSITION_MAN_DRUG_ID_FK
                         references GOODS_ON_WAREHOUSE,
                 ID_COMPONENT VARCHAR2(100) not null
                     constraint COMPOSIT_CMPNT_ID_ON_WARE_FK
                         references GOODS_ON_WAREHOUSE,
                 constraint COMPOSITION_PK
                     primary key (ID_DRUG, ID_COMPONENT)
            )';
    end if;
end;

declare
    sales_count number;
begin
    select count(1)
    into sales_count
    from all_tables
    where owner = 'ZYKIN'
      and table_name = 'SALES';
    if (0 = sales_count)
    then
        execute immediate
            'create table SALES
             (
                 ID_SALE VARCHAR2(100) not null,
                 ID_GOOD VARCHAR2(100) not null
                     constraint SALES_GOODS_ON_WARE_ID_GOOD_FK
                         references GOODS_ON_WAREHOUSE,
                 SALE_DATA TIMESTAMP(6) not null,
                 SALE_AMOUNT NUMBER not null
             )';
    end if;
end;

declare
    technologies_count number;
begin
    select count(1)
    into technologies_count
    from connect_to_my_db
    where owner = 'ZYKIN'
        and table_name = 'TECHNOLOGIES';
    if (0 = technologies_count)
        then
            execute immediate
                'create table TECHNOLOGIES
                 (
                     ID_TECHNOLOGY VARCHAR2(100) not null
                         constraint TECHNOLOGIES_PK
                             primary key,
                     ID_DRUG VARCHAR2(100) not null
                         constraint TECHN_GOODS_ON_WARE__FK
                             references GOODS_ON_WAREHOUSE,
                     PRODUCTION_TIME NUMBER not null,
                     PRODUCTION_ACTION NVARCHAR2(1024) not null
                 )';
    end if;
end;
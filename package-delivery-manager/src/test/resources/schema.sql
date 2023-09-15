create table if not exists package (
    id varchar(36),
    sender_user_id varchar(36),
    receipter_user_id varchar(36),
    package_size int,
    name varchar(36),
    status varchar(15) default 'PROCESSING',
    date_created varchar(30) default current_date
);


create table if not exists "user" (
    id varchar(36),
    email varchar(100),
    street varchar(100),
    floor varchar(10),
    number varchar(6)
);

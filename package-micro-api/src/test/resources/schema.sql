create table if not exists package (
    id varchar(36),
    sender_user_id varchar(36),
    receipter_user_id varchar(36),
    name varchar(36),
    status varchar(15) default 'PROCESSING',
    date_created varchar(30) default current_date
);
create table login_authorities (
    id int not null, 
    username varchar(255), 
    authority varchar(255) not null, 
   primary key (id));

create table login_details (
    id int not null, 
    username varchar(255), 
    password varchar(255) not null,     
    account_non_expired boolean default true not null, 
    account_non_locked boolean default true not null, 
    credentials_non_expired boolean default true not null, 
    enabled boolean default true not null, 
    primary key (id));

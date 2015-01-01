create table users(
  username varchar(10) not null,
  password varchar(32) not null,
  enabled smallint,
  primary key(username)
)
create table authorities(
  username varchar(10) not null,
  authority varchar(10) not null,
  foreign key (username) references users(username)
)

-- ---------------------------------- --
drop table if exists comment;
drop table if exists post;
-- ---------------------------------- --
create table if not exists post(
                                   id bigserial primary key,
                                   title varchar(256) not null,
    content text,
    count_like int default 0,
    tags text,
    image_type varchar(50),
    image bytea
    );


create table if not exists comment(
                                      id bigserial primary key,
--                                       id bigint primary key,
                                      content text,
                                      post_id bigint,
                                      foreign key (post_id) references post(id) on delete cascade
    );
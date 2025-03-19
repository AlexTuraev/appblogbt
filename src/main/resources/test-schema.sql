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

insert into post(title, content, tags, count_like) values ('title1', 'content1', 'tag1', 10);
insert into post(title, content, tags, count_like) values ('title2', 'content2', 'tag1', 8);
insert into post(title, content, tags, count_like) values ('title3', 'content3', 'tag2', 5);

create table if not exists comment(
                                      id bigserial primary key,
                                      content text,
                                      post_id bigint,
                                      foreign key (post_id) references post(id) on delete cascade
    );
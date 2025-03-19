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
                                      content text,
                                      post_id bigint,
                                      foreign key (post_id) references post(id) on delete cascade
);


insert into post(title, tags, content, count_like) values ('Пост1', '#авто #седан',' Содержание поста №1. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ducimus, enim, iure? Aliquam aperiam consectetur, deserunt dignissimos ducimus ea enim excepturi in laborum libero praesentium qui quisquam quod sunt ullam vitae. Содержание поста №1. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ducimus, enim, iure? Aliquam aperiam consectetur, deserunt dignissimos ducimus ea enim excepturi in laborum libero praesentium qui quisquam quod sunt ullam vitae.Содержание поста №1. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ducimus, enim, iure? Aliquam aperiam consectetur, deserunt dignissimos ducimus ea enim excepturi in laborum libero praesentium qui quisquam quod sunt ullam vitae.Содержание поста №1. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ducimus, enim, iure? Aliquam aperiam consectetur, deserunt dignissimos ducimus ea enim excepturi in laborum libero praesentium qui quisquam quod sunt ullam vitae.Содержание поста №1. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ducimus, enim, iure? Aliquam aperiam consectetur, deserunt dignissimos ducimus ea enim excepturi in laborum libero praesentium qui quisquam quod sunt ullam vitae. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ducimus, enim, iure? Aliquam aperiam consectetur, deserunt dignissimos ducimus ea enim excepturi in laborum libero praesentium qui quisquam quod sunt ullam vitae. Содержание поста №1. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ducimus, enim, iure? Aliquam aperiam consectetur, deserunt dignissimos ducimus ea enim excepturi in laborum libero praesentium qui quisquam quod sunt ullam vitae.Содержание поста №1. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ducimus, enim, iure? Aliquam aperiam consectetur, deserunt dignissimos ducimus ea enim excepturi in laborum libero praesentium qui quisquam quod sunt ullam vitae.Содержание поста №1. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ducimus, enim, iure? Aliquam aperiam consectetur, deserunt dignissimos ducimus ea enim excepturi in laborum libero praesentium qui quisquam quod sunt ullam vitae.Содержание поста №1. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ducimus, enim, iure? Aliquam aperiam consectetur, deserunt dignissimos ducimus ea enim excepturi in laborum libero praesentium qui quisquam quod sunt ullam vitae.', 10);
insert into post(title, tags, content) values ('Пост2', '#авто #седан #аренда','Содержание поста №2. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ducimus, enim, iure? Aliquam aperiam consectetur, deserunt dignissimos ducimus ea enim excepturi in laborum libero praesentium qui quisquam quod sunt ullam vitae.');
insert into post(title, tags, content) values ('Пост3', '#авто #седан #аренда #продажа', 'Содержание поста №3. Содержание поста №3');
insert into post(title, content) values ('Пост4', 'Содержание поста №4. Содержание поста №4.');
insert into post(title, content) values ('Пост5', 'Содержание поста №4. Содержание поста №5.');
insert into post(title, content) values ('Пост6', 'Содержание поста №4. Содержание поста №6.');
insert into post(title, content) values ('Пост7', 'Содержание поста №4. Содержание поста №7.');

insert into comment(content, post_id) values ('Комментарий 1_1', 1);
insert into comment(content, post_id) values ('Комментарий 1_2', 1);
insert into comment(content, post_id) values ('Комментарий 1_3', 1);
insert into comment(content, post_id) values ('Комментарий 2_1', 2);
insert into comment(content, post_id) values ('Комментарий 2_2', 2);
insert into comment(content, post_id) values ('Комментарий 2_3', 2);
insert into comment(content, post_id) values ('Комментарий 2_4', 2);
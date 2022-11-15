create table if not exists super_hero (
       uuid varchar(255) not null,
        created_at timestamp,
        deleted boolean,
        updated_at timestamp,
        name varchar(255),
        primary key (uuid)
    )
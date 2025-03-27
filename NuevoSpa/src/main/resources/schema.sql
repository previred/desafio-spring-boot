create table if not exists usuarios (
    id bigint auto_increment primary key,
    code varchar(50) not null,
    creation_date timestamp not null,
    deleted boolean default false,
    username varchar(150) not null,
    password varchar(150) not null,
    token_access varchar(300) null
);

create table if not exists estados_tarea (
    id bigint auto_increment primary key,
    code varchar(50) not null,
    creation_date timestamp not null,
    deleted boolean default false,
    name varchar(50) not null
);

create table if not exists tareas (
    id bigint auto_increment primary key,
    code varchar(50) not null,
    creation_date datetime not null,
    deleted boolean default false,
    name varchar(50) not null,
    description varchar(150),
    start_task datetime not null,
    end_task datetime,
    id_user bigint not null,
    id_status_task bigint not null,
    foreign key (id_user) references usuarios(id),
    foreign key (id_status_task) references estados_tarea(id)
);
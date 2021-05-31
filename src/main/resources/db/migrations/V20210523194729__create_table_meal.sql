create table if not exists meal
(
    id            serial  not null
        constraint meal_pk
            primary key,
    name          varchar not null,
    meal_plan_id  integer not null
        constraint meal_plan_id_fk
            references meal_plan
            on update cascade on delete cascade
);
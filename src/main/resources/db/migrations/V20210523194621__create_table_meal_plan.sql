create table if not exists meal_plan
(
    id   serial  not null
        constraint meal_plan_pk
            primary key,
    name varchar not null
);
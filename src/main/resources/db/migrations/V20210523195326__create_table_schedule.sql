create table if not exists schedule
(
    id            serial  not null
        constraint schedule_pk
            primary key,
    day_id  integer not null
        constraint day_id_fk
            references days
            on update cascade on delete cascade,
    meal_plan_id  integer not null
        constraint meal_plan_id_fk
            references meal_plan
            on update cascade on delete cascade,
    is_workout boolean not null,
    nutrition_score integer not null
);
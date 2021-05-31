create table if not exists nutritions
(
    id            serial  not null
        constraint nutritions_pk
            primary key,
    day_id integer not null
        constraint days_id_fk
            references days
            on update cascade on delete cascade,
    meal_id integer not null
        constraint meal_id_fk
            references meal
            on update cascade on delete cascade,
    carbohydrates integer not null,
    fats          integer not null,
    proteins      integer not null
    /*meal_id       integer not null
        constraint meal_id_fk
            references meal
            on update cascade on delete cascade*/
);
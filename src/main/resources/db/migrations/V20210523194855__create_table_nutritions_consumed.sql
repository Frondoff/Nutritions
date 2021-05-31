create table if not exists nutritions_consumed
(
    id            serial  not null
        constraint nutritions_consumed_pk
            primary key,
    day_id        integer not null
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
/*    nutritions_id integer not null
        constraint nutritions_id_fk
            references nutritions
            on update cascade on delete cascade*/
);
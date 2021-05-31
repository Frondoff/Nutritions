create table if not exists days
(
    id            serial  not null
        constraint days_pk
            primary key,
    name varchar not null,
    date date not null
);
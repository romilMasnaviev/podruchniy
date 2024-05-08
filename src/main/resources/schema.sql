DROP TABLE IF EXISTS reviews;
DROP TABLE IF EXISTS restaurants_criteria;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS restaurants;
DROP TABLE IF EXISTS criteria;

CREATE TABLE IF NOT EXISTS users
(
    id            BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    mobile_number VARCHAR(20) UNIQUE NOT NULL,
    username      VARCHAR(50) UNIQUE NOT NULL,
    email         VARCHAR(50) UNIQUE NOT NULL,
    password      VARCHAR(50)        NOT NULL
);

CREATE TABLE IF NOT EXISTS restaurants
(
    id      BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    name    VARCHAR(50) NOT NULL,
    rating  REAL,
    cuisine SMALLINT,
    price   SMALLINT
);

CREATE TABLE IF NOT EXISTS criteria
(
    id   BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    name VARCHAR(200) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS restaurants_criteria
(
    restaurant_id BIGINT REFERENCES restaurants (id) ON DELETE CASCADE,
    criteria_id   BIGINT REFERENCES criteria (id) ON DELETE CASCADE,
    PRIMARY KEY (restaurant_id, criteria_id)
);

CREATE TABLE IF NOT EXISTS reviews
(
    id            BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    mark          REAL,
    comment       VARCHAR(200),
    restaurant_id BIGINT REFERENCES restaurants (id) ON DELETE CASCADE,
    owner_id      BIGINT REFERENCES users (id) ON DELETE CASCADE,
    criteria_id   BIGINT REFERENCES criteria (id) ON DELETE CASCADE
);



INSERT INTO criteria (name)
VALUES ('Парковка'),
       ('Доступность общественного транспорта'),
       ('Бронирование столиков'),
       ('Детская игровая зона'),
       ('Веганское/вегетарианское меню'),
       ('Алкогольные/безалкогольные напитки'),
       ('Организация мероприятий/праздников'),
       ('Wi-Fi'),
       ('Просмотр спортивных мероприятий на больших экранах'),
       ('Различные банкетные залы'),
       ('Организация фуршета'),
       ('Организация бизнес-ланча'),
       ('Поддержка различных языков для туристов'),
       ('Варианты развлечений (настольные игры, бильярд и т.д.)'),
       ('Открытая терраса');

-- Заполнение таблицы "restaurants" тестовыми данными
INSERT INTO restaurants (name, rating, cuisine, price)
VALUES ('Ресторан "Под липами"', 4.5, 1, 3),
       ('Кафе "У пруда"', 4.2, 2, 2),
       ('Бар "Пляж"', 4.0, 3, 3);

-- Заполнение таблицы "restaurants_criteria" для каждого ресторана
-- Предположим, что каждый ресторан имеет разные критерии

-- Ресторан "Под липами"
INSERT INTO restaurants_criteria (restaurant_id, criteria_id)
VALUES (1, 1),  -- Парковка
       (1, 3),  -- Бронирование столиков
       (1, 5),  -- Веганское/вегетарианское меню
       (1, 7),  -- Организация мероприятий/праздников
       (1, 8),  -- Wi-Fi
       (1, 10), -- Различные банкетные залы
       (1, 13), -- Поддержка различных языков для туристов
       (1, 15);
-- Открытая терраса

-- Ресторан "У пруда"
INSERT INTO restaurants_criteria (restaurant_id, criteria_id)
VALUES (2, 1), -- Парковка
       (2, 2), -- Доступность общественного транспорта
       (2, 3), -- Бронирование столиков
       (2, 4), -- Детская игровая зона
       (2, 5), -- Веганское/вегетарианское меню
       (2, 6), -- Алкогольные/безалкогольные напитки
       (2, 8), -- Wi-Fi
       (2, 9), -- Просмотр спортивных мероприятий на больших экранах
       (2, 14);
-- Варианты развлечений

-- Ресторан "Пляж"
INSERT INTO restaurants_criteria (restaurant_id, criteria_id)
VALUES (3, 1),  -- Парковка
       (3, 3),  -- Бронирование столиков
       (3, 6),  -- Алкогольные/безалкогольные напитки
       (3, 7),  -- Организация мероприятий/праздников
       (3, 8),  -- Wi-Fi
       (3, 10), -- Различные банкетные залы
       (3, 11), -- Организация фуршета
       (3, 12), -- Организация бизнес-ланча
       (3, 13); -- Поддержка различных языков для туристов

 INSERT INTO users (mobile_number, username, email, password)
 VALUES ('1234567890', 'test', 'test@gmail.com', 'testtesttest');
--        ('0987654321', 'user2', 'user2@example.com', 'password2'),
--        ('5555555555', 'user3', 'user3@example.com', 'password3');

-- -- Заполнение таблицы "reviews" тестовыми данными
-- INSERT INTO reviews (mark, comment, restaurant_id, owner_id, criteria_id)
-- VALUES
--     (4.5, 'Отличное место! Большой выбор блюд и напитков.', 1, 1, 1), -- Ресторан "Под липами", отзыв от пользователя 1
--     (4.0, 'Приятное заведение, большой выбор блюд.', 1, 2, 2), -- Ещё один отзыв для Ресторана "Под липами"
--     (4.2, 'Хорошее кафе, недалеко от пруда.', 2, 2, 2), -- Ресторан "У пруда", отзыв от пользователя 2
--     (4.0, 'Бар на пляже с шикарным видом.', 3, 3, 3); -- Ресторан "Пляж", отзыв от пользователя 3
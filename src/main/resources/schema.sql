DROP TABLE IF EXISTS user_favorites;
DROP TABLE IF EXISTS reviews;
DROP TABLE IF EXISTS restaurants_criteria;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS restaurants;
DROP TABLE IF EXISTS criteria;

CREATE TABLE IF NOT EXISTS users
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    mobile_number VARCHAR(20) UNIQUE NOT NULL,
    username      VARCHAR(50) UNIQUE NOT NULL,
    email         VARCHAR(50) UNIQUE NOT NULL,
    password      VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS restaurants
(
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(50) NOT NULL,
    rating  REAL,
    cuisine TINYINT,
    price   TINYINT
);

CREATE TABLE IF NOT EXISTS criteria
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS restaurants_criteria
(
    restaurant_id BIGINT,
    criteria_id   BIGINT,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(id) ON DELETE CASCADE,
    FOREIGN KEY (criteria_id) REFERENCES criteria(id) ON DELETE CASCADE,
    PRIMARY KEY (restaurant_id, criteria_id)
);

CREATE TABLE IF NOT EXISTS reviews
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    mark          REAL,
    comment       VARCHAR(200),
    restaurant_id BIGINT,
    owner_id      BIGINT,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(id) ON DELETE CASCADE,
    FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS user_favorites
(
    user_id       BIGINT,
    restaurant_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, restaurant_id)
);

INSERT INTO criteria (name)
VALUES ('Парковка'),
       ('Транспортная доступность'),
       ('Онлайн бронирование'),
       ('Детская игровая зона'),
       ('Веганское/вегетарианское меню'),
       ('Алкогольные напитки'),
       ('Проведение мероприятий'),
       ('Wi-Fi'),
       ('Спортивные трансляции'),
       ('Наличие банкетного зала'),
       ('Организация фуршета'),
       ('Бизнес Ланч'),
       ('Мультиязычность'),
       ('Настольные игры'),
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
VALUES ('1234567890', 'test', 'test@gmail.com', 'testtesttest'),
       ('0987654321', 'user2', 'user2@example.com', 'password2'),
       ('5555555555', 'user3', 'user3@example.com', 'password3');

-- Заполнение таблицы "reviews" тестовыми данными
INSERT INTO reviews (mark, comment, restaurant_id, owner_id)
VALUES (4.5, 'Тихое место ! Большой выбор блюд и напитков.', 1, 2),
       (4.0, 'На столиках во дворе очень тихо. Все понравилось. ', 1, 3);
-- (4.2
-- (4.2, 'Хорошее кафе, недалеко от пруда.', 1, 4);
-- (4.0, 'Бар на пляже с шикарным видом.', 3, 3, 3);

INSERT INTO user_favorites (user_id, restaurant_id)
VALUES (1, 1), -- Пользователь с ID 1 добавил ресторан с ID 1 в избранное
       (1, 2);
-- Пользователь с ID 1 добавил ресторан с ID 2 в избранное


-- Дополнительные записи для restaurants
INSERT INTO restaurants (name, rating, cuisine, price)
VALUES ('Ресторан "Мраморный дворец"', 4.8, 1, 3),
       ('Кафе "Золотая Рыбка"', 4.0, 2, 1),
       ('Бар "Красный Луч"', 3.9, 3, 2),
       ('Пиццерия "Итальянский вкус"', 4.5, 4, 2);

-- Заполнение restaurants_criteria для новых ресторанов

-- Ресторан "Мраморный дворец"
INSERT INTO restaurants_criteria (restaurant_id, criteria_id)
VALUES (4, 1),  -- Парковка
       (4, 3),  -- Бронирование столиков
       (4, 5),  -- Веганское/вегетарианское меню
       (4, 8),  -- Wi-Fi
       (4, 10), -- Различные банкетные залы
       (4, 12);
-- Организация бизнес-ланча

-- Кафе "Золотая Рыбка"
INSERT INTO restaurants_criteria (restaurant_id, criteria_id)
VALUES (5, 2), -- Доступность общественного транспорта
       (5, 4), -- Детская игровая зона
       (5, 5), -- Веганское/вегетарианское меню
       (5, 6), -- Алкогольные/безалкогольные напитки
       (5, 8), -- Wi-Fi
       (5, 11);
-- Организация фуршета

-- Бар "Красный Луч"
INSERT INTO restaurants_criteria (restaurant_id, criteria_id)
VALUES (6, 1), -- Парковка
       (6, 2), -- Доступность общественного транспорта
       (6, 3), -- Бронирование столиков
       (6, 5), -- Веганское/вегетарианское меню
       (6, 6), -- Алкогольные/безалкогольные напитки
       (6, 8), -- Wi-Fi
       (6, 9), -- Просмотр спортивных мероприятий на больших экранах
       (6, 14);
-- Варианты развлечений

-- Пиццерия "Итальянский вкус"
INSERT INTO restaurants_criteria (restaurant_id, criteria_id)
VALUES (7, 1),  -- Парковка
       (7, 3),  -- Бронирование столиков
       (7, 5),  -- Веганское/вегетарианское меню
       (7, 6),  -- Алкогольные/безалкогольные напитки
       (7, 8),  -- Wi-Fi
       (7, 12), -- Организация бизнес-ланча
       (7, 13);
-- Поддержка различных языков для туристов
-- Дополнительные записи для restaurants
INSERT INTO restaurants (name, rating, cuisine, price)
VALUES ('Ресторан "Лаваш"', 4.6, 5, 0),
       ('Кафе "Сказочная поляна"', 4.3, 6, 2),
       ('Бар "Пивной дом"', 4.1, 7, 0),
       ('Ресторан "Французская пекарня"', 4.7, 8, 0);

-- Заполнение restaurants_criteria для новых ресторанов

-- Ресторан "Лаваш"
INSERT INTO restaurants_criteria (restaurant_id, criteria_id)
VALUES (8, 1),  -- Парковка
       (8, 3),  -- Бронирование столиков
       (8, 5),  -- Веганское/вегетарианское меню
       (8, 6),  -- Алкогольные/безалкогольные напитки
       (8, 8),  -- Wi-Fi
       (8, 10), -- Различные банкетные залы
       (8, 11);
-- Организация фуршета

-- Кафе "Сказочная поляна"
INSERT INTO restaurants_criteria (restaurant_id, criteria_id)
VALUES (9, 1),  -- Парковка
       (9, 2),  -- Доступность общественного транспорта
       (9, 3),  -- Бронирование столиков
       (9, 5),  -- Веганское/вегетарианское меню
       (9, 8),  -- Wi-Fi
       (9, 10), -- Различные банкетные залы
       (9, 14);
-- Варианты развлечений

-- Бар "Пивной дом"
INSERT INTO restaurants_criteria (restaurant_id, criteria_id)
VALUES (10, 1), -- Парковка
       (10, 2), -- Доступность общественного трансп
-- (10, 2), -- Доступность общественного транспорта
       (10, 3), -- Бронирование столиков
       (10, 6), -- Алкогольные/безалкогольные напитки
       (10, 7), -- Организация мероприятий/праздников
       (10, 8), -- Wi-Fi
       (10, 9), -- Просмотр спортивных мероприятий на больших экранах
       (10, 11);
-- Организация фуршета

-- Ресторан "Французская пекарня"
INSERT INTO restaurants_criteria (restaurant_id, criteria_id)
VALUES (11, 1),  -- Парковка
       (11, 3),  -- Бронирование столиков
       (11, 5),  -- Веганское/вегетарианское меню
       (11, 8),  -- Wi-Fi
       (11, 12), -- Организация бизнес-ланча
       (11, 13); -- Поддержка различных языков для туристов

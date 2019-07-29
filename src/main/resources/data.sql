INSERT INTO role(role) VALUES ('ROLE_USER');
INSERT INTO role(role) VALUES ('ROLE_ADMIN');
INSERT INTO role(role) VALUES ('ROLE_SUPER_ADMIN');

INSERT INTO users(email, enabled, password, username) VALUES('admin@email', 1, '$2a$10$cN3SVg7y1feWzpSWJMc4UOHJRsANibzvIY83jG3M/YmXX85mHNyFW', 'admin');--1
INSERT INTO users(email, enabled, password, username) VALUES('user1@email', 1, '$2a$10$cN3SVg7y1feWzpSWJMc4UOHJRsANibzvIY83jG3M/YmXX85mHNyFW', 'user1');--2
INSERT INTO users(email, enabled, password, username) VALUES('admin1@email', 1, '$2a$10$cN3SVg7y1feWzpSWJMc4UOHJRsANibzvIY83jG3M/YmXX85mHNyFW', 'admin1');--3
INSERT INTO users(email, enabled, password, username) VALUES('admin2@email', 1, '$2a$10$cN3SVg7y1feWzpSWJMc4UOHJRsANibzvIY83jG3M/YmXX85mHNyFW', 'admin2');--4
INSERT INTO users(email, enabled, password, username) VALUES('user2@email', 1, '$2a$10$cN3SVg7y1feWzpSWJMc4UOHJRsANibzvIY83jG3M/YmXX85mHNyFW', 'user2');--5
INSERT INTO users(email, enabled, password, username) VALUES('user3@email', 1, '$2a$10$cN3SVg7y1feWzpSWJMc4UOHJRsANibzvIY83jG3M/YmXX85mHNyFW', 'user3');--6
INSERT INTO user_role(user_id, role_id) VALUES(1,2);
INSERT INTO user_role(user_id, role_id) VALUES(1,3);
INSERT INTO user_role(user_id, role_id) VALUES(2,1);
INSERT INTO user_role(user_id, role_id) VALUES(3,2);
INSERT INTO user_role(user_id, role_id) VALUES(4,2);
INSERT INTO user_role(user_id, role_id) VALUES(5,1);
INSERT INTO user_role(user_id, role_id) VALUES(6,1);

INSERT INTO institutions (name, description) VALUES ('Dbam o zdrowie', 'Pomoc dzieciom z ubogich rodzin');--1
INSERT INTO institutions (name, description) VALUES ('A kogo', 'Pomoc w wybudzaniu dzieci ze śpiączki');--2
INSERT INTO institutions (name, description) VALUES ('Dla dzieci', 'Pomoc osobom w trudnej sytuacji życiowej');--3
INSERT INTO institutions (name, description) VALUES ('Bez domu', 'Pomoc dla osób nie posiadających miejsca zamieszkania');--4
INSERT INTO institutions (name, description) VALUES ('No to pa', 'Pomoc dla osób wybierających się na tamtą stronę');--5
INSERT INTO institutions (name, description) VALUES ('Zwierzątka', 'Pomoc dla opuszczonych zwierząt');--6

INSERT INTO categories (name) VALUES ('ubrania, nadające się do ponownego użycia');--1
INSERT INTO categories (name) VALUES ('ubrania do wyrzucenia');--2
INSERT INTO categories (name) VALUES ('zabawki');--3
INSERT INTO categories (name) VALUES ('książki');--4
INSERT INTO categories (name) VALUES ('sprzęt elektroniczny');--5
INSERT INTO categories (name) VALUES ('inne');--6

INSERT INTO donations (quantity, institution_id, status, street, city, zip_code, created, pick_up_date, pick_up_time, pick_up_comment, user_id)  VALUES (2, 1, 0,'ul. Pocztowa 5', 'Kraków', '31-913', '2019-07-01 08:32', '2019-09-12', '12:00', 'zapukać 4 razy szybko i zadzwonić dzwonkiem 2 razy', 1);--1
INSERT INTO donations (quantity, institution_id, status, street, city, zip_code, created, pick_up_date, pick_up_time, pick_up_comment, user_id)  VALUES (12, 3, 0,'ul. Laczka 14', 'Pszczyna', '21-543', '2019-09-22 08:45', '2019-10-02', '17:30', 'zadzwonić', 1);--2
INSERT INTO donations (quantity, institution_id, status, street, city, zip_code, created, pick_up_date, pick_up_time, pick_up_comment, user_id)  VALUES (5, 3, 0,'ul. Laczka 14', 'Pszczyna', '21-543', '2019-08-14 18:03', '2019-08-17', '18:30', 'zadzwonić', 1);--3
INSERT INTO donations (quantity, institution_id, status, street, city, zip_code, created, pick_up_date, pick_up_time, pick_up_comment, user_id)  VALUES (5, 3, 0,'ul. Mączna 31', 'Warszawa', '99-123', '2019-08-30 15:12', '2019-09-11', '10:30', 'zadzwonić', 2);--4
INSERT INTO donations (quantity, institution_id, status, street, city, zip_code, created, pick_up_date, pick_up_time, pick_up_comment, user_id)  VALUES (5, 3, 0,'ul. Kwiatka 2', 'Pszczyna', '21-543', '2019-03-24 07:15', '2019-04-09', '11:30', 'nie zadzwonić', 2);--5
INSERT INTO donations (quantity, institution_id, status, street, city, zip_code, created, pick_up_date, pick_up_time, pick_up_comment, user_id)  VALUES (5, 3, 0,'ul. Trubadura 64', 'Wrocław', '41-756', '2019-02-14 09:21', '2019-03-07', '19:30', 'Dać znać wcześniej', 3);--6
INSERT INTO donations (quantity, institution_id, status, street, city, zip_code, created, pick_up_date, pick_up_time, user_id)  VALUES (5, 3, 0,'ul. Okienka 4', 'Poznań', '11-543', '2019-08-15 08:45', '2019-08-27', '18:30', 4);--7
INSERT INTO donations (quantity, institution_id, status, street, city, zip_code, created, pick_up_date, pick_up_time, pick_up_comment, user_id)  VALUES (5, 3, 0,'ul. Laczka 14', 'Pszczyna', '21-543', '2019-11-05 11:52', '2019-11-11', '18:30', 'zadzwonić', 4);--8
INSERT INTO donations (quantity, institution_id, status, street, city, zip_code, created, pick_up_date, pick_up_time, user_id)  VALUES (4, 2, 0,'ul. św. Jakuba', 'Kraków', '31-943', '2019-10-06 07:34', '2019-10-13', '07:30', 2);--9
INSERT INTO donations (quantity, institution_id, status, street, city, zip_code, created, pick_up_date, pick_up_time, pick_up_comment, user_id)  VALUES (5, 3, 0,'ul. Tkacza', 'Pszczyna', '21-543', '2019-08-12 18:55', '2019-08-17', '18:30', 'testowy', 1);--10

INSERT INTO donation_category (donation_id, category_id) VALUES (1, 2);
INSERT INTO donation_category (donation_id, category_id) VALUES (1, 3);
INSERT INTO donation_category (donation_id, category_id) VALUES (2, 3);
INSERT INTO donation_category (donation_id, category_id) VALUES (2, 4);
INSERT INTO donation_category (donation_id, category_id) VALUES (3, 1);
INSERT INTO donation_category (donation_id, category_id) VALUES (3, 2);
INSERT INTO donation_category (donation_id, category_id) VALUES (3, 6);
INSERT INTO donation_category (donation_id, category_id) VALUES (4, 6);
INSERT INTO donation_category (donation_id, category_id) VALUES (5, 6);
INSERT INTO donation_category (donation_id, category_id) VALUES (5, 3);
INSERT INTO donation_category (donation_id, category_id) VALUES (6, 1);
INSERT INTO donation_category (donation_id, category_id) VALUES (6, 3);
INSERT INTO donation_category (donation_id, category_id) VALUES (7, 2);
INSERT INTO donation_category (donation_id, category_id) VALUES (8, 6);
INSERT INTO donation_category (donation_id, category_id) VALUES (8, 5);
INSERT INTO donation_category (donation_id, category_id) VALUES (9, 4);
INSERT INTO donation_category (donation_id, category_id) VALUES (10, 1);
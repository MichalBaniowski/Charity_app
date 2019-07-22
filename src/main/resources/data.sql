INSERT INTO role(role) VALUES ('ROLE_USER');

INSERT INTO institutions (name, description) VALUES ('Dbam o zdrowie', 'Pomoc dziecion z ubogich rodzin');
INSERT INTO institutions (name, description) VALUES ('A kogo', 'Pomoc w wybudzaniu dzieci ze śpiączki');
INSERT INTO institutions (name, description) VALUES ('Dla dzieci', 'Pomoc osobom w trudnej sytuacji życiowej');
INSERT INTO institutions (name, description) VALUES ('Bez domu', 'Pomoc dla osób nie posiadających miejsca zamieszkania');

INSERT INTO categories (name) VALUES ('ubrania, nadające się do ponownego użycia');
INSERT INTO categories (name) VALUES ('ubrania do wyrzucenia');
INSERT INTO categories (name) VALUES ('zabawki');
INSERT INTO categories (name) VALUES ('książki');
INSERT INTO categories (name) VALUES ('sprzęt elektroniczny');
INSERT INTO categories (name) VALUES ('inne');

INSERT INTO donations (quantity, institution_id, street, city, zip_code, pick_up_date, pick_up_time, pick_up_comment)  VALUES (2, 1, 'ul. Pocztowa 5', 'Kraków', '31-913', '2019-09-12', '12:00', 'zapukać 4 razy szybko i zadzwonić dzwonkiem 2 razy');
INSERT INTO donations (quantity, institution_id, street, city, zip_code, pick_up_date, pick_up_time, pick_up_comment)  VALUES (12, 3, 'ul. Laczka 14', 'Pszczyna', '21-543', '2019-10-02', '17:30', 'zadzwonić');
INSERT INTO donations (quantity, institution_id, street, city, zip_code, pick_up_date, pick_up_time, pick_up_comment)  VALUES (5, 3, 'ul. Laczka 14', 'Pszczyna', '21-543', '2019-08-17', '18:30', 'zadzwonić');
INSERT INTO donations (quantity, institution_id, street, city, zip_code, pick_up_date, pick_up_time)  VALUES (4, 2, 'ul. św. Jakuba', 'Kraków', '31-943', '2019-10-13', '07:30');

INSERT INTO donation_category (donation_id, category_id) VALUES (1, 2);
INSERT INTO donation_category (donation_id, category_id) VALUES (1, 3);
INSERT INTO donation_category (donation_id, category_id) VALUES (2, 3);
INSERT INTO donation_category (donation_id, category_id) VALUES (2, 4);
INSERT INTO donation_category (donation_id, category_id) VALUES (3, 1);
INSERT INTO donation_category (donation_id, category_id) VALUES (3, 2);
INSERT INTO donation_category (donation_id, category_id) VALUES (3, 6);
INSERT INTO donation_category (donation_id, category_id) VALUES (4, 6);
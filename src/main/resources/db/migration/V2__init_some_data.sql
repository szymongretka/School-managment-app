INSERT INTO SCHOOL(id, name, hour_price)
VALUES (1, 'Szkoła 1', 206.56);
INSERT INTO SCHOOL(id, name, hour_price)
VALUES (2, 'Szkoła 2', 500.00);

INSERT INTO PARENT(id, first_name, last_name)
VALUES (1, 'Imie1', 'Nazwisko1');
INSERT INTO PARENT(id, first_name, last_name)
VALUES (2, 'Imie2', 'Nazwisko2');

INSERT INTO CHILD(id, first_name, last_name, parent_id, school_id)
VALUES (1, 'Dziecko1', 'Nazwisko1', 1, 1);
INSERT INTO CHILD(id, first_name, last_name, parent_id, school_id)
VALUES (2, 'Dziecko2', 'Nazwisko1', 1, 1);
INSERT INTO CHILD(id, first_name, last_name, parent_id, school_id)
VALUES (3, 'Dziecko3', 'Nazwisko2', 2, 1);

INSERT INTO ATTENDANCE(child_id, entry_date, exit_date)
VALUES (1, '2024-02-15 07:01:00.000000', '2024-02-15 11:00:00.000000');
INSERT INTO ATTENDANCE(child_id, entry_date, exit_date)
VALUES (1, '2024-02-18 05:05:00.000000', '2024-02-18 16:13:00.000000');
INSERT INTO ATTENDANCE(child_id, entry_date, exit_date)
VALUES (2, '2024-02-18 05:05:00.000000', '2024-02-18 16:13:00.000000');
INSERT INTO ATTENDANCE(child_id, entry_date, exit_date)
VALUES (3, '2024-02-18 05:05:00.000000', '2024-02-18 16:13:00.000000');
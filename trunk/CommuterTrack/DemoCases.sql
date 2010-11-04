INSERT INTO CT_USERS (username, password, role, active) VALUES ('admin', '21232f297a57a5a743894a0e4a801fc3', 1, 1);
INSERT INTO CT_USERS (username, password, role, active) VALUES ('user1', '5f4dcc3b5aa765d61d8327deb882cf99', 2, 1);
INSERT INTO CT_USERS (username, password, role, active) VALUES ('user2', '5f4dcc3b5aa765d61d8327deb882cf99', 2, 1);
INSERT INTO CT_USERS (username, password, role, active) VALUES ('user3', '5f4dcc3b5aa765d61d8327deb882cf99', 2, 1);
INSERT INTO CT_USERS (username, password, role, active) VALUES ('user4', '5f4dcc3b5aa765d61d8327deb882cf99', 2, 1);

INSERT INTO CT_ROUTES (user_id, description, route_start, route_end) VALUES (2, 'TEST ROUTE', 'rstart', 'rend');
INSERT INTO CT_ROUTES (user_id, description, route_start, route_end) VALUES (3, 'TEST ROUTE', 'rstart', 'rend');
INSERT INTO CT_ROUTES (user_id, description, route_start, route_end) VALUES (4, 'TEST ROUTE', 'rstart', 'rend');
INSERT INTO CT_ROUTES (user_id, description, route_start, route_end) VALUES (5, 'TEST ROUTE', 'rstart', 'rend');

INSERT INTO CT_ROUTES (user_id, description, route_start, route_end) VALUES (1, 'TEST ROUTE 2', 'rstart', 'rend');
INSERT INTO CT_ROUTES (user_id, description, route_start, route_end) VALUES (2, 'TEST ROUTE 2', 'rstart', 'rend');
INSERT INTO CT_ROUTES (user_id, description, route_start, route_end) VALUES (3, 'TEST ROUTE 2', 'rstart', 'rend');
INSERT INTO CT_ROUTES (user_id, description, route_start, route_end) VALUES (4, 'TEST ROUTE 2', 'rstart', 'rend');
INSERT INTO CT_ROUTES (user_id, description, route_start, route_end) VALUES (5, 'TEST ROUTE 2', 'rstart', 'rend');

INSERT INTO CT_ROUTES (user_id, description, route_start, route_end) VALUES (1, 'TEST ROUTE 3', 'rstart', 'rend');
INSERT INTO CT_ROUTES (user_id, description, route_start, route_end) VALUES (2, 'TEST ROUTE 3', 'rstart', 'rend');
INSERT INTO CT_ROUTES (user_id, description, route_start, route_end) VALUES (3, 'TEST ROUTE 3', 'rstart', 'rend');
INSERT INTO CT_ROUTES (user_id, description, route_start, route_end) VALUES (4, 'TEST ROUTE 3', 'rstart', 'rend');
INSERT INTO CT_ROUTES (user_id, description, route_start, route_end) VALUES (5, 'TEST ROUTE 3', 'rstart', 'rend');

INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (2, '2010-11-03 16:32:11.132', '2010-11-03 17:55:13.909',0);
INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (2, '2010-11-02 16:39:45.763', '2010-11-02 17:43:23.004',0);
INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (2, '2010-11-01 16:37:07.525', '2010-11-01 18:01:50.812',0);
INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (2, '2010-10-31 16:38:00.999', '2010-10-31 17:23:10.443',0);
INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (2, '2010-10-30 16:32:11.132', '2010-10-30 17:55:13.709',0);
INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (2, '2010-10-29 16:39:45.763', '2010-10-29 17:43:23.004',0);
INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (2, '2010-10-28 16:37:07.525', '2010-10-28 18:01:50.812',0);
INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (2, '2010-10-27 16:27:10.039', '2010-10-27 17:23:10.443',0);
INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (2, '2010-10-26 16:32:11.132', '2010-10-26 17:50:13.909',0);
INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (2, '2010-10-25 16:19:35.763', '2010-10-25 17:43:23.004',0);
INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (2, '2010-10-24 16:37:07.525', '2010-10-24 18:01:50.200',0);
INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (2, '2010-10-23 16:58:03.099', '2010-10-23 17:23:10.443',0);

INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (3, '2010-11-03 16:32:11.132', '2010-11-03 17:55:13.909',0);
INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (3, '2010-11-02 16:39:45.763', '2010-11-02 17:43:23.004',0);
INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (3, '2010-11-01 16:37:07.525', '2010-11-01 18:01:50.812',0);
INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (3, '2010-10-31 16:38:00.999', '2010-10-31 17:23:10.443',0);

INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (7, '2010-11-03 16:32:11.132', '2010-11-03 17:55:13.909',0);
INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (7, '2010-11-02 16:39:45.763', '2010-11-02 17:43:23.004',0);
INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (7, '2010-11-01 16:37:07.525', '2010-11-01 18:01:50.812',0);
INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (7, '2010-10-31 16:38:00.999', '2010-10-31 17:23:10.443',0);

INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (4, '2010-11-03 16:32:11.132', '2010-11-03 17:55:13.909',0);
INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (4, '2010-11-02 16:39:45.763', '2010-11-02 17:43:23.004',0);
INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (4, '2010-11-01 16:37:07.525', '2010-11-01 18:01:50.812',0);
INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (4, '2010-10-31 16:38:00.999', '2010-10-31 17:23:10.443',0);

INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (5, '2010-11-03 16:32:11.132', '2010-11-03 17:55:13.909',0);
INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (5, '2010-11-02 16:39:45.763', '2010-11-02 17:43:23.004',0);
INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (5, '2010-11-01 16:37:07.525', '2010-11-01 18:01:50.812',0);
INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (5, '2010-10-31 16:38:00.999', '2010-10-31 17:23:10.443',0);



INSERT INTO CT_USERS (username, password, role, active) VALUES ('Mariya', '21232f297a57a5a743894a0e4a801fc3', 1, 1);

INSERT INTO CT_ROUTES (user_id, description, route_start, route_end) VALUES (6, 'drive 191 bus then red train', 'home', 'school');
INSERT INTO CT_ROUTES (user_id, description, route_start, route_end) VALUES (6, 'walk 190 bus then red train', 'home', 'school');
INSERT INTO CT_ROUTES (user_id, description, route_start, route_end) VALUES (6, 'walk 190 bus then A train', 'home', 'school');

INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (17, '2010-10-11 16:15:45.763', '2010-10-11 19:20:23.004',0);
INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (15, '2010-10-12 09:50:45.763', '2010-10-12 10:59:23.004',0);
INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (16, '2010-10-13 12:30:45.763', '2010-10-13 14:55:23.004',0);
INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (17, '2010-10-19 09:15:00.999', '2010-10-19 11:03:10.443',0);
INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (16, '2010-10-20 11:45:07.525', '2010-10-20 13:10:50.812',0);
INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (16, '2010-10-26 09:15:11.132', '2010-10-26 11:15:13.909',0);
INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (16, '2010-10-27 12:15:07.525', '2010-10-27 14:30:50.812',0);
INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (15, '2010-10-28 09:55:45.763', '2010-10-28 10:55:23.004',0);
INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (15, '2010-10-29 10:55:45.763', '2010-10-29 12:10:23.004',0);
INSERT INTO CT_TRIPS (route_id, start_time, end_time, status) VALUES (16, '2010-11-03 08:55:45.763', '2010-11-03 10:25:23.004',0);


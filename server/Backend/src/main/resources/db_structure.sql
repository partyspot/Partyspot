/* structure */

drop database partyspot;

create database partyspot;

use partyspot;

create table userrole (
id binary(16) unique not null,
name varchar(50), 
primary key (id)
);

create table party (
id binary(16) unique not null,
name varchar(100),
code varchar(10),
token varchar(1000),
primary key (id)
);

create table playlist (
id binary(16) unique not null,
name varchar(100),
party_id binary(16),
PRIMARY KEY (id),
FOREIGN KEY (party_id) REFERENCES party(id)
);

create table puser (
id binary(16) unique not null,
name varchar(100),
userrole_id binary(16),
party_id binary(16),
PRIMARY KEY (id),
FOREIGN KEY (userrole_id) REFERENCES userrole(id),
FOREIGN KEY (party_id) REFERENCES party(id)
);

create table song (
id binary(16) unique not null,
name varchar(255),
spotify_uri varchar(255),
genre varchar(100),
PRIMARY KEY(id)
);

create table song__playlist(
playlist_id binary(16),
song_id binary(16),
added_date datetime, 
FOREIGN KEY (playlist_id) REFERENCES playlist (id),
FOREIGN KEY (song_id) REFERENCES song (id)
);

create table user__song(
user_id binary(16),
song_id binary(16),
voting integer,
FOREIGN KEY (user_id) REFERENCES puser (id),
FOREIGN KEY (song_id) REFERENCES song (id),
UNIQUE KEY(user_id, song_id) 
);

alter table playlist add column spotify_uri varchar(255);


/* userrole: Data */

insert into userrole (id, name) values (uuid_to_bin('00000000-0000-0000-0000-000000000000'), 'developer');
insert into userrole (id, name) values (uuid_to_bin('00000000-0000-0000-0000-000000000001'), 'host');
insert into userrole (id, name) values (uuid_to_bin('00000000-0000-0000-0000-000000000002'), 'guest');


/* Pseudo-Daten */

insert into party (id, name, code, token) values (uuid_to_bin('00000000-0000-0000-0000-000000000000'), 'party1', '0000000000', 'token1');
insert into party (id, name, code, token) values (uuid_to_bin('00000000-0000-0000-0000-000000000001'), 'party2', '0000000000', 'token2');

insert into puser (id, name, userrole_id, party_id) values (uuid_to_bin('00000000-0000-0000-0000-000000000001'), 'user1', uuid_to_bin('00000000-0000-0000-0000-000000000002'), uuid_to_bin('00000000-0000-0000-0000-000000000000'));
insert into puser (id, name, userrole_id, party_id) values (uuid_to_bin('00000000-0000-0000-0000-000000000002'), 'user2', uuid_to_bin('00000000-0000-0000-0000-000000000002'), uuid_to_bin('00000000-0000-0000-0000-000000000000'));
insert into puser (id, name, userrole_id, party_id) values (uuid_to_bin('00000000-0000-0000-0000-000000000003'), 'user3', uuid_to_bin('00000000-0000-0000-0000-000000000002'), uuid_to_bin('00000000-0000-0000-0000-000000000000'));
insert into puser (id, name, userrole_id, party_id) values (uuid_to_bin('00000000-0000-0000-0000-000000000004'), 'user4', uuid_to_bin('00000000-0000-0000-0000-000000000002'), uuid_to_bin('00000000-0000-0000-0000-000000000000'));
insert into puser (id, name, userrole_id, party_id) values (uuid_to_bin('00000000-0000-0000-0000-000000000005'), 'user5', uuid_to_bin('00000000-0000-0000-0000-000000000002'), uuid_to_bin('00000000-0000-0000-0000-000000000000'));
insert into puser (id, name, userrole_id, party_id) values (uuid_to_bin('00000000-0000-0000-0000-000000000006'), 'user6', uuid_to_bin('00000000-0000-0000-0000-000000000002'), uuid_to_bin('00000000-0000-0000-0000-000000000000'));
insert into puser (id, name, userrole_id, party_id) values (uuid_to_bin('00000000-0000-0000-0000-000000000007'), 'user7', uuid_to_bin('00000000-0000-0000-0000-000000000002'), uuid_to_bin('00000000-0000-0000-0000-000000000000'));
insert into puser (id, name, userrole_id, party_id) values (uuid_to_bin('00000000-0000-0000-0000-000000000008'), 'user8', uuid_to_bin('00000000-0000-0000-0000-000000000002'), uuid_to_bin('00000000-0000-0000-0000-000000000000'));
insert into puser (id, name, userrole_id, party_id) values (uuid_to_bin('00000000-0000-0000-0000-000000000009'), 'user9', uuid_to_bin('00000000-0000-0000-0000-000000000002'), uuid_to_bin('00000000-0000-0000-0000-000000000000'));
insert into puser (id, name, userrole_id, party_id) values (uuid_to_bin('00000000-0000-0000-0000-000000000010'), 'user10', uuid_to_bin('00000000-0000-0000-0000-000000000002'), uuid_to_bin('00000000-0000-0000-0000-000000000000'));
insert into puser (id, name, userrole_id, party_id) values (uuid_to_bin('00000000-0000-0000-0000-000000000011'), 'user11', uuid_to_bin('00000000-0000-0000-0000-000000000002'), uuid_to_bin('00000000-0000-0000-0000-000000000000'));
insert into puser (id, name, userrole_id, party_id) values (uuid_to_bin('00000000-0000-0000-0000-000000000012'), 'user12', uuid_to_bin('00000000-0000-0000-0000-000000000002'), uuid_to_bin('00000000-0000-0000-0000-000000000001'));
insert into puser (id, name, userrole_id, party_id) values (uuid_to_bin('00000000-0000-0000-0000-000000000013'), 'user13', uuid_to_bin('00000000-0000-0000-0000-000000000002'), uuid_to_bin('00000000-0000-0000-0000-000000000001'));
insert into puser (id, name, userrole_id, party_id) values (uuid_to_bin('00000000-0000-0000-0000-000000000014'), 'user14', uuid_to_bin('00000000-0000-0000-0000-000000000002'), uuid_to_bin('00000000-0000-0000-0000-000000000001'));
insert into puser (id, name, userrole_id, party_id) values (uuid_to_bin('00000000-0000-0000-0000-000000000015'), 'user15', uuid_to_bin('00000000-0000-0000-0000-000000000002'), uuid_to_bin('00000000-0000-0000-0000-000000000001'));
insert into puser (id, name, userrole_id, party_id) values (uuid_to_bin('00000000-0000-0000-0000-000000000016'), 'user16', uuid_to_bin('00000000-0000-0000-0000-000000000002'), uuid_to_bin('00000000-0000-0000-0000-000000000001'));
insert into puser (id, name, userrole_id, party_id) values (uuid_to_bin('00000000-0000-0000-0000-000000000017'), 'user17', uuid_to_bin('00000000-0000-0000-0000-000000000002'), uuid_to_bin('00000000-0000-0000-0000-000000000001'));
insert into puser (id, name, userrole_id, party_id) values (uuid_to_bin('00000000-0000-0000-0000-000000000018'), 'user18', uuid_to_bin('00000000-0000-0000-0000-000000000002'), uuid_to_bin('00000000-0000-0000-0000-000000000001'));
insert into puser (id, name, userrole_id, party_id) values (uuid_to_bin('00000000-0000-0000-0000-000000000019'), 'admin1', uuid_to_bin('00000000-0000-0000-0000-000000000001'), uuid_to_bin('00000000-0000-0000-0000-000000000000'));
insert into puser (id, name, userrole_id, party_id) values (uuid_to_bin('00000000-0000-0000-0000-000000000020'), 'admin2', uuid_to_bin('00000000-0000-0000-0000-000000000001'), uuid_to_bin('00000000-0000-0000-0000-000000000001'));

insert into playlist (id, name, party_id) values (uuid_to_bin('00000000-0000-0000-0000-000000000000'), 'playlist1', uuid_to_bin('00000000-0000-0000-0000-000000000000'));
insert into playlist (id, name, party_id) values (uuid_to_bin('00000000-0000-0000-0000-000000000001'), 'playlist2', uuid_to_bin('00000000-0000-0000-0000-000000000001'));

insert into  song (id, name, spotify_uri, genre) values (uuid_to_bin('00000000-0000-0000-0000-000000000001'), 'song1', null, 'Pop');
insert into  song (id, name, spotify_uri, genre) values (uuid_to_bin('00000000-0000-0000-0000-000000000002'), 'song2', null, 'Pop');
insert into  song (id, name, spotify_uri, genre) values (uuid_to_bin('00000000-0000-0000-0000-000000000003'), 'song3', null, 'Rock');
insert into  song (id, name, spotify_uri, genre) values (uuid_to_bin('00000000-0000-0000-0000-000000000004'), 'song4', null, 'Rock');
insert into  song (id, name, spotify_uri, genre) values (uuid_to_bin('00000000-0000-0000-0000-000000000005'), 'song5', null, 'Jazz');
insert into  song (id, name, spotify_uri, genre) values (uuid_to_bin('00000000-0000-0000-0000-000000000006'), 'song6', null, 'Rock');
insert into  song (id, name, spotify_uri, genre) values (uuid_to_bin('00000000-0000-0000-0000-000000000007'), 'song7', null, 'Pop');
insert into  song (id, name, spotify_uri, genre) values (uuid_to_bin('00000000-0000-0000-0000-000000000008'), 'song8', null, 'Jazz');
insert into  song (id, name, spotify_uri, genre) values (uuid_to_bin('00000000-0000-0000-0000-000000000009'), 'song9', null, 'Metal');
insert into  song (id, name, spotify_uri, genre) values (uuid_to_bin('00000000-0000-0000-0000-000000000010'), 'song10', null, 'Disco');
insert into  song (id, name, spotify_uri, genre) values (uuid_to_bin('00000000-0000-0000-0000-000000000011'), 'song11', null, 'Disco');
insert into  song (id, name, spotify_uri, genre) values (uuid_to_bin('00000000-0000-0000-0000-000000000012'), 'song12', null, 'Western');
insert into  song (id, name, spotify_uri, genre) values (uuid_to_bin('00000000-0000-0000-0000-000000000013'), 'song13', null, 'Pop');
insert into  song (id, name, spotify_uri, genre) values (uuid_to_bin('00000000-0000-0000-0000-000000000014'), 'song14', null, 'Blues');
insert into  song (id, name, spotify_uri, genre) values (uuid_to_bin('00000000-0000-0000-0000-000000000015'), 'song15', null, 'Jazz');
insert into  song (id, name, spotify_uri, genre) values (uuid_to_bin('00000000-0000-0000-0000-000000000016'), 'song16', null, 'Pop');
insert into  song (id, name, spotify_uri, genre) values (uuid_to_bin('00000000-0000-0000-0000-000000000017'), 'song17', null, 'Rock');
insert into  song (id, name, spotify_uri, genre) values (uuid_to_bin('00000000-0000-0000-0000-000000000018'), 'song18', null, 'Metal');
insert into  song (id, name, spotify_uri, genre) values (uuid_to_bin('00000000-0000-0000-0000-000000000019'), 'song19', null, 'Metal');
insert into  song (id, name, spotify_uri, genre) values (uuid_to_bin('00000000-0000-0000-0000-000000000020'), 'song20', null, 'Blues');



insert into song__playlist (song_id, playlist_id, added_date) values (uuid_to_bin('00000000-0000-0000-0000-000000000001'), uuid_to_bin('00000000-0000-0000-0000-000000000000'), '2022-06-14 08:03:000');
insert into song__playlist (song_id, playlist_id, added_date) values (uuid_to_bin('00000000-0000-0000-0000-000000000002'), uuid_to_bin('00000000-0000-0000-0000-000000000000'), '2022-06-14 08:10:000');
insert into song__playlist (song_id, playlist_id, added_date) values (uuid_to_bin('00000000-0000-0000-0000-000000000003'), uuid_to_bin('00000000-0000-0000-0000-000000000000'), '2022-06-14 08:25:000');
insert into song__playlist (song_id, playlist_id, added_date) values (uuid_to_bin('00000000-0000-0000-0000-000000000004'), uuid_to_bin('00000000-0000-0000-0000-000000000000'), '2022-06-14 08:30:000');
insert into song__playlist (song_id, playlist_id, added_date) values (uuid_to_bin('00000000-0000-0000-0000-000000000005'), uuid_to_bin('00000000-0000-0000-0000-000000000000'), '2022-06-14 07:03:000');
insert into song__playlist (song_id, playlist_id, added_date) values (uuid_to_bin('00000000-0000-0000-0000-000000000006'), uuid_to_bin('00000000-0000-0000-0000-000000000000'), '2022-06-14 07:59:000');
insert into song__playlist (song_id, playlist_id, added_date) values (uuid_to_bin('00000000-0000-0000-0000-000000000007'), uuid_to_bin('00000000-0000-0000-0000-000000000000'), '2022-06-14 07:23:000');
insert into song__playlist (song_id, playlist_id, added_date) values (uuid_to_bin('00000000-0000-0000-0000-000000000008'), uuid_to_bin('00000000-0000-0000-0000-000000000001'), '2022-06-14 08:20:000');
insert into song__playlist (song_id, playlist_id, added_date) values (uuid_to_bin('00000000-0000-0000-0000-000000000009'), uuid_to_bin('00000000-0000-0000-0000-000000000001'), '2022-06-14 10:40:000');
insert into song__playlist (song_id, playlist_id, added_date) values (uuid_to_bin('00000000-0000-0000-0000-000000000010'), uuid_to_bin('00000000-0000-0000-0000-000000000001'), '2022-06-14 10:21:000');
insert into song__playlist (song_id, playlist_id, added_date) values (uuid_to_bin('00000000-0000-0000-0000-000000000011'), uuid_to_bin('00000000-0000-0000-0000-000000000001'), '2022-06-14 09:17:000');
insert into song__playlist (song_id, playlist_id, added_date) values (uuid_to_bin('00000000-0000-0000-0000-000000000012'), uuid_to_bin('00000000-0000-0000-0000-000000000001'), '2022-06-14 09:20:000');
insert into song__playlist (song_id, playlist_id, added_date) values (uuid_to_bin('00000000-0000-0000-0000-000000000013'), uuid_to_bin('00000000-0000-0000-0000-000000000001'), '2022-06-14 07:44:000');
insert into song__playlist (song_id, playlist_id, added_date) values (uuid_to_bin('00000000-0000-0000-0000-000000000014'), uuid_to_bin('00000000-0000-0000-0000-000000000001'), '2022-06-14 09:55:000');
insert into song__playlist (song_id, playlist_id, added_date) values (uuid_to_bin('00000000-0000-0000-0000-000000000015'), uuid_to_bin('00000000-0000-0000-0000-000000000001'), '2022-06-14 08:06:000');
insert into song__playlist (song_id, playlist_id, added_date) values (uuid_to_bin('00000000-0000-0000-0000-000000000016'), uuid_to_bin('00000000-0000-0000-0000-000000000001'), '2022-06-14 07:39:000');
insert into song__playlist (song_id, playlist_id, added_date) values (uuid_to_bin('00000000-0000-0000-0000-000000000017'), uuid_to_bin('00000000-0000-0000-0000-000000000001'), '2022-06-14 08:14:000');
insert into song__playlist (song_id, playlist_id, added_date) values (uuid_to_bin('00000000-0000-0000-0000-000000000018'), uuid_to_bin('00000000-0000-0000-0000-000000000001'), '2022-06-14 08:34:000');
insert into song__playlist (song_id, playlist_id, added_date) values (uuid_to_bin('00000000-0000-0000-0000-000000000019'), uuid_to_bin('00000000-0000-0000-0000-000000000001'), '2022-06-14 10:11:000');
insert into song__playlist (song_id, playlist_id, added_date) values (uuid_to_bin('00000000-0000-0000-0000-000000000020'), uuid_to_bin('00000000-0000-0000-0000-000000000001'), '2022-06-14 09:09:000');


insert into user__song (user_id, song_id, voting)
select u.id, sp.song_id, 0 from song__playlist sp, puser u, playlist p
where playlist_id = uuid_to_bin('00000000-0000-0000-0000-000000000000')
and p.party_id = u.party_id
and sp.playlist_id = p.id;


insert into user__song (user_id, song_id, voting)
select u.id, sp.song_id, 0 from song__playlist sp, puser u, playlist p
where playlist_id = uuid_to_bin('00000000-0000-0000-0000-000000000001')
and p.party_id = u.party_id
and sp.playlist_id = p.id;

update user__song us, puser p set us.voting = 1
where us.user_id = p.id
and p.name in ('admin1', 'admin2');


-- View for displaying voting

create view voting_view AS
select s.id as song_id, sum(us.voting) as voting, p.id as party_id 
from song s, user__song us, song__playlist sp, playlist pl, party p
where s.id = us.song_id
and us.song_id = sp.song_id
and sp.playlist_id = pl.id
and pl.party_id = p.id
group by s.id;


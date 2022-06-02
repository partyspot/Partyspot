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
added_date date, 
FOREIGN KEY (playlist_id) REFERENCES playlist (id),
FOREIGN KEY (song_id) REFERENCES song (id)
);

create table user__song(
user_id binary(16),
song_id binary(16),
voting integer,
FOREIGN KEY (user_id) REFERENCES puser (id),
FOREIGN KEY (song_id) REFERENCES song (id)
);


/* userrole: Data */

insert into userrole (id, name) values (uuid_to_bin('00000000-0000-0000-0000-000000000000'), 'developer');
insert into userrole (id, name) values (uuid_to_bin('00000000-0000-0000-0000-000000000001'), 'host');
insert into userrole (id, name) values (uuid_to_bin('00000000-0000-0000-0000-000000000002'), 'guest');

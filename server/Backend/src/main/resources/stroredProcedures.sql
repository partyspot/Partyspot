-- UserroleService

DELIMITER //
CREATE PROCEDURE getAllUserroles()
BEGIN
    select bin_to_uuid(id) as id, name from userrole;
END //
    
DELIMITER ;


DELIMITER //
CREATE PROCEDURE getUserroleByName(IN userrole varchar(50))
BEGIN
    select bin_to_uuid(id) as id, name from userrole where name = userrole;
END //
    
DELIMITER ;

DELIMITER //
CREATE PROCEDURE getUserroleById(IN userroleId VARCHAR(36))
BEGIN
    select bin_to_uuid(id) as id, name from userrole where id = uuid_to_bin(userroleId);
END //
    
DELIMITER ;


-- PartyService

DELIMITER //
CREATE PROCEDURE getAllParties()
BEGIN
    select bin_to_uuid(id) as id, name, code, token from party;
END //
    
DELIMITER ;


DELIMITER //
CREATE PROCEDURE getPartyByName(IN party varchar(100))
BEGIN
    select bin_to_uuid(id) as id, name, code, token from party where name = party;
END //
    
DELIMITER ;

DELIMITER //
CREATE PROCEDURE getPartyById(IN partyId VARCHAR(36))
BEGIN
    select bin_to_uuid(id) as id, name, code, token from party where id = uuid_to_bin(partyId);
END //
    
DELIMITER ;


DELIMITER //
CREATE PROCEDURE createParty(IN partyId VARCHAR(36), IN partyName VARCHAR(100), IN partyCode VARCHAR(10), IN partyToken VARCHAR(1000))
BEGIN
    insert into party (id, name, code, token) values (UUID_TO_BIN(partyId), partyName, partyCode, partyToken);
END //
    
DELIMITER ;


-- Playlist

DELIMITER //
CREATE PROCEDURE getAllPlaylists()
BEGIN
    select bin_to_uuid(id) as id, name, bin_to_uuid(party_id) as party_id from playlist;
END //
    
DELIMITER ;


DELIMITER //
CREATE PROCEDURE getPlaylistByName(IN playlistName varchar(100))
BEGIN
    select bin_to_uuid(id) as id, name, bin_to_uuid(party_id) as party_id from playlist where name = playlistName;
END //
    
DELIMITER ;

DELIMITER //
CREATE PROCEDURE getPlaylistById(IN playlistId VARCHAR(36))
BEGIN
    select bin_to_uuid(id) as id, name, bin_to_uuid(party_id)as party_id from playlist where id = uuid_to_bin(playlistId);
END //
    
DELIMITER ;


-- User

DELIMITER //
CREATE PROCEDURE getAllUsers()
BEGIN
    select bin_to_uuid(id) as id, name, bin_to_uuid(userrole_id) as userrole_id, bin_to_uuid(party_id) as party_id from puser;
END //
    
DELIMITER ;


DELIMITER //
CREATE PROCEDURE getUserByName(IN userName varchar(100))
BEGIN
   select bin_to_uuid(id) as id, name, bin_to_uuid(userrole_id) as userrole_id, bin_to_uuid(party_id) as party_id from puser where name = userName;
END //
    
DELIMITER ;

DELIMITER //
CREATE PROCEDURE getUserById(IN userId VARCHAR(36))
BEGIN
    select bin_to_uuid(id) as id, name, bin_to_uuid(userrole_id) as userrole_id, bin_to_uuid(party_id) as party_id from puser where id = uuid_to_bin(userId);
END //
    
DELIMITER ;

DELIMITER //
CREATE PROCEDURE createUser(IN userId VARCHAR(36), IN userName VARCHAR(100), IN userroleId VARCHAR(36), IN partyId VARCHAR(36))
BEGIN
    insert into puser (id, name, userrole_id, party_id) values(uuid_to_bin(userId), userName, uuid_to_bin(userroleId),  uuid_to_bin(partyId));
END //
    
DELIMITER ;


-- update Party Token
DELIMITER //
CREATE PROCEDURE updatePartyToken(IN newToken VARCHAR(1000), IN partyId VARCHAR(36))
BEGIN
    update party set token = newToken where id = uuid_to_bin(partyId);
END //
    
DELIMITER ;



DELIMITER //
CREATE PROCEDURE getPlaylistByPartyId(IN partyId VARCHAR(36))
BEGIN
    select bin_to_uuid(id) as id, name, bin_to_uuid(party_id) as party_id from playlist where party_id = uuid_to_bin(partyId);
END //
    
DELIMITER ;


DELIMITER //
CREATE PROCEDURE createPlaylist(IN playlistId VARCHAR(36), IN playlistName VARCHAR(100), IN partyId VARCHAR(36))
BEGIN
    insert into Playlist (id, name, party_id) values (UUID_TO_BIN(playlistId), playlistName, UUID_TO_BIN(partyId));
END //
    
DELIMITER ;




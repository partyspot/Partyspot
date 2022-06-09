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
    select bin_to_uuid(id) as id, name, party_id from playlist;
END //
    
DELIMITER ;


DELIMITER //
CREATE PROCEDURE getPlaylistByName(IN playlistName varchar(100))
BEGIN
    select bin_to_uuid(id) as id, name, party_id from playlist where name = playlistName;
END //
    
DELIMITER ;

DELIMITER //
CREATE PROCEDURE getPlaylistById(IN playlistId VARCHAR(36))
BEGIN
    select bin_to_uuid(id) as id, name, party_id from playlist where id = uuid_to_bin(playlistId);
END //
    
DELIMITER ;
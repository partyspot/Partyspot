-- UserroleService

DELIMITER //
CREATE PROCEDURE getAllUserroles()
BEGIN
    select bin_to_uuid(id) as id, name from userrole;
END //
    
DELIMITER ;


DELIMITER //
CREATE PROCEDURE getUserroleByName(userrole varchar(50))
BEGIN
    select bin_to_uuid(id) as id, name from userrole where name = userrole;
END //
    
DELIMITER ;

DELIMITER //
CREATE PROCEDURE getUserroleById(userroleId VARCHAR(36))
BEGIN
    select bin_to_uuid(id) as id, name from userrole where id = uuid_to_bin(userroleId);
END //
    
DELIMITER ;


-- PartyService


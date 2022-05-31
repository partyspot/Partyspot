package at.partyspot.db.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity {

	@Id
	@Column(columnDefinition = "BINARY(16)")
	protected UUID id;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID generateId() {
		this.setId(UUID.randomUUID());
		return this.getId();

	}

}

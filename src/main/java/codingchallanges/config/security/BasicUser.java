package codingchallanges.config.security;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name="basic_user")
@EqualsAndHashCode(of = "username")
public class BasicUser implements Serializable {
	
	private static final long serialVersionUID = 0L;

	@Id
	@Column(nullable = false)
	private String username;

	@Column(nullable = false, length = 400)
	private String bcrypthash;

}

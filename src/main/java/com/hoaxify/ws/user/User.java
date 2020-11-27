package com.hoaxify.ws.user;

import com.fasterxml.jackson.annotation.JsonView;
import com.hoaxify.ws.hoax.Hoax;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long id;

	@NotNull(message = "{hoaxify.constraints.username.NotNull.message}")
	@Size(min=4, max=255)
//	@Column(unique = true)
	@UniqueUsername
	 private String username;

	@NotNull
	@Size(min=4, max=255)
	private String displayName;

	@NotNull
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "{hoaxify.constraint.password.Pattern.message}")
	@Size(min=8, max=255)
	//@JsonIgnore requesti de kapatıyor
	private String password;

	private String image;

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private List<Hoax> hoaxes;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList("Role_user");
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}

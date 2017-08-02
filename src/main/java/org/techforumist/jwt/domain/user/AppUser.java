package org.techforumist.jwt.domain.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import org.techforumist.jwt.domain.Instruction;


@Entity
public class AppUser implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	@Column(unique = true)
	private String username;

	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	@ElementCollection
	private List<String> roles = new ArrayList<>();

	@OneToOne(cascade = CascadeType.ALL)
	private UserProfile userProfile;

	@OneToMany(cascade = CascadeType.ALL)
	@ElementCollection
	private List<Instruction> instruction;

	@OneToMany(cascade = CascadeType.ALL)
	@ElementCollection
	List<Achievement> achievements;

	public AppUser() {
		this.achievements = new ArrayList<>();
	}

	@JsonIgnore
	public boolean findAchievementByname(String name){
		for (Achievement achievement:achievements) {
			if(achievement.getName().equals(name)){
				return true;
			}
		}
		return false;
	}

	public List<Achievement> getAchievements() {
		return achievements;
	}

	public void setAchievements(List<Achievement> achievements) {
		this.achievements = achievements;
	}

	public List<Instruction> getInstruction() {
		return instruction;
	}

	public void setInstruction(List<Instruction> instruction) {
		this.instruction = instruction;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

}

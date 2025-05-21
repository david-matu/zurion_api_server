package com.zuriontech.web.test.david.zurionwebapi.model;

public class User {
	
	private String username;
	private String email;
	private String phone;
	private String password;
	private String role;
	
	public User() {}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Staff [username=" + username + ", email=" + email + ", phone=" + phone + ", password=" + password
				+ ", role=" + role + "]";
	}
}

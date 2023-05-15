/**
 * 
 * I declare that this code was written by me, 21030321. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: Leu Jiu Chuan
 * Student ID: 21030321
 * Class: E63C
 * Date created: 2023-Jan-03 10:16:01 am 
 * 
 */

package e63c.leujiuchuan.GA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author 21030321
 *
 */
@Entity
public class Member {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private int id;
	
	@NotNull
	@NotEmpty(message="Member name cannot be empty!")
	@Size(min=5, max=50, message="Member's name length must be between 5 and 50 charaters!")
	private String name;
	
	@NotNull
	@NotEmpty(message="Member description cannot be empty!")
	@Size(min=5, max=100, message="Member's description length must be between 5 and 100 charaters!")
	private String username;
	
	@Pattern(regexp="(?=^.{8,}$)(?=.*\\d)(?=.*[!@#$%^&*]+)(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$",message="Please contain at least one uppercase ,lowercase,special character and numeric value!")
	private String password;
	
	@NotEmpty(message="Invalid format of email")
	@Email
	private String email;
	
	private String role;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
	
}

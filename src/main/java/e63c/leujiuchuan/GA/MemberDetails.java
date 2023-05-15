/**
 * 
 * I declare that this code was written by me, 21030321. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: Leu Jiu Chuan
 * Student ID: 21030321
 * Class: E63C
 * Date created: 2023-Jan-10 11:04:05 am 
 * 
 */

package e63c.leujiuchuan.GA;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author 21030321
 *
 */
public class MemberDetails implements UserDetails{


	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}

	private Member member;
	
	public MemberDetails(Member member) {
		this.member=member;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(){
		SimpleGrantedAuthority authority=new SimpleGrantedAuthority(member.getRole());
		return Arrays.asList(authority);
	}
	@Override
	public String getPassword() {
		return member.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return member.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
}

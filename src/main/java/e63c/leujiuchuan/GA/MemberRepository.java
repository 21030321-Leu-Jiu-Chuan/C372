/**
 * 
 * I declare that this code was written by me, 21030321. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: Leu Jiu Chuan
 * Student ID: 21030321
 * Class: E63C
 * Date created: 2023-Jan-03 10:16:45 am 
 * 
 */

package e63c.leujiuchuan.GA;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 21030321
 *
 */
public interface MemberRepository extends JpaRepository<Member, Integer>{

	public Member findByUsername(String username);
}

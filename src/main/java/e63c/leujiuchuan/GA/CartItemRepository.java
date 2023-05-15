/**
 * 
 * I declare that this code was written by me, 21030321. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: Leu Jiu Chuan
 * Student ID: 21030321
 * Class: E63C
 * Date created: 2023-Feb-05 1:20:18 pm 
 * 
 */

package e63c.leujiuchuan.GA;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
/**
 * @author 21030321
 *
 */
public interface CartItemRepository  extends JpaRepository<CartItem, Integer>{

	public List<CartItem> findByMemberId(int id);

	public CartItem findByMemberIdAndShirtId(int loggedInMemberId, int ShirtId);

}

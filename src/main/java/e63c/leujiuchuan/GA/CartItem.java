/**
 * 
 * I declare that this code was written by me, 21030321. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: Leu Jiu Chuan
 * Student ID: 21030321
 * Class: E63C
 * Date created: 2023-Feb-05 1:16:53 pm 
 * 
 */

package e63c.leujiuchuan.GA;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Min;

/**
 * @author 21030321
 *
 */
@Entity
public class CartItem {

	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private int id;
	
	@ManyToOne
	@JoinColumn(name="member_id", nullable=false)
	private Member member;
	
	@ManyToOne
	@JoinColumn(name="shirt_id", nullable=false)
	private Shirt shirt;
	
	
	private int quantity;
	
	@Transient 
	private double subTotal;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Shirt getShirt() {
		return shirt;
	}

	public void setShirt(Shirt shirt) {
		this.shirt = shirt;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getSubTotal() {
		subTotal = quantity*shirt.getPrice();

		return subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}


}

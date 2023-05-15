/**
 * 
 * I declare that this code was written by me, 21030321. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: Leu Jiu Chuan
 * Student ID: 21030321
 * Class: E63C
 * Date created: 2023-Feb-05 1:18:15 pm 
 * 
 */

package e63c.leujiuchuan.GA;
import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;





/**
 * @author 21030321
 *
 */
@Controller
public class CartItemController {

	@Autowired
	private CartItemRepository cartItemRepo;

	@Autowired
	private ShirtRepository shirtRepo;

	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private OrderItemRepository orderItemRepo;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	
	@GetMapping("/history")
		public String showOrder(Model model,Principal principal,@Valid OrderItem orderItem,BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "history";
		}
		MemberDetails loggedInMember = (MemberDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		int loggedInMemberId = loggedInMember.getMember().getId();
	
		List<OrderItem> orderItemList = orderItemRepo.findByMemberId(loggedInMemberId);
		model.addAttribute("orderItemList",orderItemList);

		
		

		
		return "history";
	}

	@GetMapping("/cart")
	public String showCart(Model model, Principal principal,@Valid CartItem cartItem,BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "cart";
		}
	
		
		
		// Get currently logged in user
		MemberDetails loggedInMember = (MemberDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		int loggedInMemberId = loggedInMember.getMember().getId();

		// Get shopping cart items added by this user
		List<CartItem> cartItemList = cartItemRepo.findByMemberId(loggedInMemberId);
		
		// *Hint: You will need to use the method we added in the CartItemRepository
		// Add the shopping cart items to the model
		model.addAttribute("cartItemList",cartItemList);
		// Calculate the total cost of all items in the shopping cart
		double cartTotal=0.0;
		
		for (int i=0;i<cartItemList.size();i++) {
			
			CartItem currentCartItem = cartItemList.get(i);
			int shirtQuantity = currentCartItem.getQuantity();
			
			Shirt shirt = currentCartItem.getShirt();
			double shirtPrice=shirt.getPrice();
			
			
			cartTotal += shirtPrice*shirtQuantity;
			
		}
		
		// Add the shopping cart total to the model

		model.addAttribute("cartTotal", cartTotal);
		model.addAttribute("memberId", loggedInMemberId);


		return "cart";
	}

	@PostMapping("/cart/add/{shirtId}")
	public String addToCart(@PathVariable("shirtId") int shirtId, @RequestParam("quantity") int quantity,
			Principal principal) {

		// Get currently logged in user
		MemberDetails loggedInMember = (MemberDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		int loggedInMemberId = loggedInMember.getMember().getId();
		
		
		// Check in the cartItemRepo if item was previously added by user.
		// *Hint: we will need to write a new method in the CartItemRepository
		CartItem cartItem=cartItemRepo.findByMemberIdAndShirtId(loggedInMemberId, shirtId);
		
		
		// if the item was previously added, then we get the quantity that was
		// previously added and increase that
		// Save the CartItem object back to the repository
		if(cartItem != null) {
			int currentQuantity=cartItem.getQuantity();
			cartItem.setQuantity(quantity+currentQuantity);
			cartItemRepo.save(cartItem);
		}else {
		
		
		// if the item was NOT previously added,
		// then prepare the item and member objects

		Shirt shirt =shirtRepo.getById(shirtId);
		Member member =memberRepo.getById(loggedInMemberId);
				
		
		// Create a new CartItem object
		CartItem newCartItem=new CartItem();
		
		
		// Set the item and member as well as the new quantity in the new CartItem
		// object
		newCartItem.setShirt(shirt);
		newCartItem.setMember(member);
		newCartItem.setQuantity(quantity);
		
		
		// Save the new CartItem object to the repository

		cartItemRepo.save(newCartItem);
		}
		return "redirect:/cart";
	}

	@PostMapping("/cart/update/{id}")
	public String updateCart(@PathVariable("id") int cartItemId, @RequestParam("qty") int qty) {

		// Get cartItem object by cartItem's id
		CartItem cartItem=cartItemRepo.getById(cartItemId);

		// Set the quantity of the carItem object retrieved
		cartItem.setQuantity(qty);
		
		// Save the cartItem back to the cartItemRepo
		cartItemRepo.save(cartItem);

		return "redirect:/cart";
	}

	@GetMapping("/cart/remove/{id}")
	public String removeFromCart(@PathVariable("id") int cartItemId) {

		//Remove item from cartItemRepo 
		cartItemRepo.deleteById(cartItemId);
		
		

		return "redirect:/cart";
	}

	@PostMapping("/cart/process_order")
	public String processOrder(Model model, @RequestParam("cartTotal") double cartTotal,
		@RequestParam("memberId") int memberId, @RequestParam("orderId") String orderId,
		@RequestParam("transactionId") String transactionId) {
	     // Retrieve cart items purchased		
		List<CartItem> cartItemList = cartItemRepo.findByMemberId(memberId);
	     // Get member object	
		Member member=memberRepo.getById(memberId);
	     // Loop to iterate through all cart items
		for (int i = 0; i < cartItemList.size(); i++) {
			// Retrieve details about current cart item		
			CartItem currentCartItem=cartItemList.get(i);
			Shirt itemToUpdate =currentCartItem.getShirt();
			int quantityOfItemPurchased=currentCartItem.getQuantity();
			int itemToUpdateId=itemToUpdate.getId();
			
			System.out.println("Item: "+itemToUpdate.getDescription());
			// Update item table		
			Shirt inventoryItem=shirtRepo.getById(itemToUpdateId);
			int currentInventoryQuantity=inventoryItem.getQuantity();
			System.out.println("Current inventory quantity: "+inventoryItem.getQuantity());
			inventoryItem.setQuantity(currentInventoryQuantity-quantityOfItemPurchased);
			shirtRepo.save(inventoryItem);
			
			// Add item to order table	
			OrderItem orderItem= new OrderItem();
			orderItem.setOrderid(orderId);
			orderItem.setTransactionid(transactionId);
			orderItem.setShirt(itemToUpdate);
			orderItem.setMember(member);
			orderItem.setQuantity(quantityOfItemPurchased);
			orderItemRepo.save(orderItem);
			
			// clear cart items belonging to member
			cartItemRepo.deleteById(currentCartItem.getId());
		}
		// Pass info to view to display success page
		model.addAttribute("cartTotal", cartTotal);
		model.addAttribute("cartItemList", cartItemList);
		model.addAttribute("member", member);
		model.addAttribute("orderId", orderId);
		model.addAttribute("transactionId", transactionId);
		// Send email
		// Send email
		String subject = "Booklink order is confirmed!";
		String body = "Thank you for your order!Below is your e-receipt\n" + "Order ID: " + orderId + "\n" + "Transaction ID: " + transactionId;
		String to = member.getEmail();
		sendEmail(to, subject, body);

		
		
		return "success";
	}
	public void sendEmail(String to, String subject, String body) {
		   SimpleMailMessage msg = new SimpleMailMessage();
		   msg.setTo(to);
		   msg.setSubject(subject);
		   msg.setText(body);
		   System.out.println("Sending");
		   javaMailSender.send(msg);
		   System.out.println("Sent");
		}

}

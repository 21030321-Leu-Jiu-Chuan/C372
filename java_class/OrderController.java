/**
 * 
 * I declare that this code was written by me, 21030321. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: Leu Jiu Chuan
 * Student ID: 21030321
 * Class: E63C
 * Date created: 2022-Dec-06 2:36:22 pm 
 * 
 */

package e63c.leujiuchuan.GA;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author 21030321
 *
 */
@Controller
public class OrderController {

	@Autowired
	private OrderRepository orderRepository;
	
	@GetMapping("/order")
	public String viewOrder(Model model) {
		
		List<myOrder> listOrder= orderRepository.findAll();
		
		model.addAttribute("listOrder", listOrder);
		
		return "view_order";
	}
	@GetMapping("/order/add")
	public String addOrder(Model model) {
		model.addAttribute("order", new myOrder());
		
		return "add_order";
	}
	@PostMapping("/order/save")
	public String saveOrder(myOrder order) {
		orderRepository.save(order);		
		return "redirect:/order";
	}
	@PostMapping("/order/update/{id}")
	public String saveEOrder(@PathVariable("id") Integer id, myOrder order) {
		orderRepository.save(order);		
		return "redirect:/order";
	} 
	
	@GetMapping("/order/edit/{id}")
	public String editOrder(@PathVariable("id") Integer id, Model model) {
		myOrder order=orderRepository.getById(id);
		model.addAttribute("order", order);
		
		return "edit_order";
	}

	@GetMapping("/order/delete/{id}")
	public String deleteOrder(@PathVariable("id") Integer id) {
		
		orderRepository.deleteById(id);
		return "redirect:/order";
	}
}

/**
 * 
 * I declare that this code was written by me, 21030321. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: Leu Jiu Chuan
 * Student ID: 21030321
 * Class: E63C
 * Date created: 2022-Nov-15 1:36:10 pm 
 * 
 */

package e63c.leujiuchuan.GA;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 21030321
 *
 */
@Controller
public class HomeController {
	@GetMapping("/")
	public String home() {
		return "index";
	}
	@GetMapping("/403")
		public String error403() {
			return "403";
		}
	
}

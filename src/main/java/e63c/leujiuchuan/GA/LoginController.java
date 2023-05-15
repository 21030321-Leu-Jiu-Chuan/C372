/**
 * 
 * I declare that this code was written by me, 21030321. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: Leu Jiu Chuan
 * Student ID: 21030321
 * Class: E63C
 * Date created: 2023-Jan-10 3:07:02 pm 
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
public class LoginController {

	@GetMapping("/login")
	public String login() {
		return "login";
	}
}

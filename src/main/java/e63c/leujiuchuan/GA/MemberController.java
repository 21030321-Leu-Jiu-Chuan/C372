/**
 * 
 * I declare that this code was written by me, 21030321. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: Leu Jiu Chuan
 * Student ID: 21030321
 * Class: E63C
 * Date created: 2023-Jan-03 10:16:29 am 
 * 
 */

package e63c.leujiuchuan.GA;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author 21030321
 *
 */
@Controller
public class MemberController {

	@Autowired
	private MemberRepository memberRepository;

	@GetMapping("/members")
	public String viewMember(Model model) {

		List<Member> listMembers = memberRepository.findAll();

		model.addAttribute("listMembers", listMembers);

		return "view_member";
	}

	@GetMapping("/members/add")
	public String addMembers(Model model) {
		model.addAttribute("member", new Member());
		
		return "add_member";
	}

	/*
	 * @PostMapping("/items/save") public String saveItems(Item item) {
	 * itemRepository.save(item); return "redirect:/items"; }
	 */
	@PostMapping("/members/save")
	public String saveMembers(@Valid Member member,BindingResult bindingResult, RedirectAttributes redirectAttribute) {
		
		if (bindingResult.hasErrors()) {
			return "add_member";
		}
		
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(member.getPassword());
		
		member.setPassword(encodedPassword);
		member.setRole("ROLE_USER");
		
		memberRepository.save(member);		
		
		redirectAttribute.addFlashAttribute("success", "Member registered!");
		
		return "redirect:/members";
	} 
	
	@PostMapping("/members/update/{id}")
	public String saveEMembers(@Valid Member member, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return "edit_member";
		}
		
		memberRepository.save(member);		
		return "redirect:/members";
	} 
	
	@GetMapping("/members/edit/{id}")
	public String editMember(@PathVariable("id") Integer id, Model model) {
		Member member=memberRepository.getById(id);
		model.addAttribute("member", member);
		
		return "edit_member";
	}

	@GetMapping("/members/delete/{id}")
	public String deleteMember(@PathVariable("id") Integer id) {
		
		memberRepository.deleteById(id);
		return "redirect:/members";
	}
	
	
}

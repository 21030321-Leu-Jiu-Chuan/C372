/**
 * 
 * I declare that this code was written by me, 21030321. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: Leu Jiu Chuan
 * Student ID: 21030321
 * Class: E63C
 * Date created: 2022-Nov-15 10:04:47 am 
 * 
 */

package e63c.leujiuchuan.GA;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;





/**
 * @author 21030321
 *
 */
@Controller
public class CategoryController {
	@Autowired
	private CategoryRepository categoryRepository;
	

	@GetMapping("/categories")
	public String viewCategories(Model model) {
		
		List<Category> listCategories = categoryRepository.findAll();
		
		model.addAttribute("listCategories", listCategories);
		
		return "view_categories";
	}
	@GetMapping("/categories/add")
	public String addCategories(Model model) {
		model.addAttribute("category", new Category());
		
		return "add_category";
	}
	@PostMapping("/categories/save")
	public String saveCategories(@Valid Category category ,BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return "add_category";
		}
		
		
		categoryRepository.save(category);		
		return "redirect:/categories";
	} 
	@PostMapping("/categories/update/{id}")
	public String saveECategories(@Valid Category category, BindingResult bindingResult ) {
		if(bindingResult.hasErrors()) {
			return "edit_category";
		}
		
		categoryRepository.save(category);		
		return "redirect:/categories";
	} 
	
	@GetMapping("/categories/edit/{id}")
	public String editCategory(@PathVariable("id") Integer id, Model model) {
		Category category=categoryRepository.getById(id);
		model.addAttribute("category", category);
		
		return "edit_category";
	}

	@GetMapping("/categories/delete/{id}")
	public String deleteCategory(@PathVariable("id") Integer id) {
		
		categoryRepository.deleteById(id);
		return "redirect:/categories";
	}
}

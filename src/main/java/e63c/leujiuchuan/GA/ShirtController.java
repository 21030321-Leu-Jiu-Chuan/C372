package e63c.leujiuchuan.GA;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ShirtController {

	@Autowired
	private ShirtRepository shirtRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CartItemRepository cartItemRepo;
	
	@GetMapping("/shirt")
	public String viewItems(Model model) {

		List<Shirt> listShirts = shirtRepository.findAll();

		model.addAttribute("listShirt", listShirts);
		
		
		return "view_shirt";
	}

	
	@GetMapping("/shirt/add")
	public String addShirt(Model model) {
		model.addAttribute("shirt", new Shirt());
		List<Category> catList = categoryRepository.findAll();
		model.addAttribute("catList", catList);
		return "add_shirt";
	}

	@GetMapping("/shirt/{id}")
	public String viewSingleShirt(@Valid CartItem cartItem,BindingResult bindingResult, @PathVariable("id") Integer id, Model model) {
		
		if (bindingResult.hasErrors()) {
			return "view_single_shirt";
		}
		
		
		Shirt shirt = shirtRepository.getById(id);
		
		
		model.addAttribute("shirt", shirt);

		

		return "view_single_shirt";
	}

	@PostMapping("/shirt/save")
	public String saveShirt(@Valid Shirt shirt,BindingResult bindingResult,@RequestParam("shirtImage") MultipartFile imgFile,Model model) {// can put multiple
		// MultipartFile based on
		// the file u have 
		if (bindingResult.hasErrors()) {
			//return addShirt(model); at the top when i state Shirt shirt , Model model it already help  me write line 60-61
			//model.addAttribute("shirt", new Shirt());
			List<Category> catList = categoryRepository.findAll();
			model.addAttribute("catList", catList);
			
			return "add_shirt";
		}

		String imageName = imgFile.getOriginalFilename();
		// set the image name in item object
		shirt.setImgName(imageName);
		// save the item obj to the db
		Shirt savedShirt = shirtRepository.save(shirt);// give back the id that is auto generate/assign from the
														// database
		try {
			// preparing the directory path
			String uploadDir = "uploads/shirt/" + savedShirt.getId();
			Path uploadPath = Paths.get(uploadDir);
			System.out.println("Directory path : " + uploadPath);
			// checking if the upload path exists,if not it will be created
			if (!Files.exists(uploadPath)) {// checking whether file exist
				Files.createDirectories(uploadPath);// if not create a directories
			}
			// prepare path for file
			Path fileToCreatePath = uploadPath.resolve(imageName);
			System.out.println("file path : " + fileToCreatePath);

			// copy files to the upload location
			Files.copy(imgFile.getInputStream(), fileToCreatePath, StandardCopyOption.REPLACE_EXISTING);

		} catch (IOException io) {
			io.printStackTrace();
		}
		return "redirect:/shirt";
	}

	@PostMapping("/shirt/update/{id}")
	public String saveEShirt(@PathVariable("id") Integer id,@Valid Shirt shirt, BindingResult bindingResult,
			@RequestParam("shirtImage") MultipartFile imgFile) {

		if (bindingResult.hasErrors()) {
			
			
			return "edit_shirt";
		}

		if (!imgFile.isEmpty()) {
			String imageName = imgFile.getOriginalFilename();
			System.out.println("Shirt name from imgFile: "+imageName);
			// Set the image name in item object
			shirt.setImgName(imageName);

			// Save the item object to the db
			Shirt savedShirt = shirtRepository.save(shirt);

			try {
				// Prepare the directory path
				String uploadDir = "uploads/shirt/" + savedShirt.getId();
				Path uploadPath = Paths.get(uploadDir);
				System.out.println("Directory path: " + uploadPath);

				// Checking if the upload path exists, if not it will be created
				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}

				// Prepare path for file
				Path fileToCreatePath = uploadPath.resolve(imageName);
				System.out.println("File path: " + fileToCreatePath);

				// Copy file to the upload location
				Files.copy(imgFile.getInputStream(), fileToCreatePath, StandardCopyOption.REPLACE_EXISTING);

			} catch (IOException io) {
				io.printStackTrace();
			}
		}
		
		else {
			System.out.println("Image anme from item object: "+shirt.getImgName());
			shirtRepository.save(shirt);
		}
		return "redirect:/shirt";
	}

	@GetMapping("/shirt/edit/{id}")
	public String editShirt(@PathVariable("id") Integer id, Model model) {

		Shirt shirt = shirtRepository.getById(id);
		model.addAttribute("shirt", shirt);
		List<Category> catList = categoryRepository.findAll();
		model.addAttribute("catList", catList);

		return "edit_shirt";

	}

	@GetMapping("/shirt/delete/{id}")
	public String deleteShirt(@PathVariable("id") Integer id) {

		shirtRepository.deleteById(id);

		return "redirect:/shirt";
	}

}

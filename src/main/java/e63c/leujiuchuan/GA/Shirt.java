/**
 * 
 * I declare that this code was written by me, 21030321. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: Leu Jiu Chuan
 * Student ID: 21030321
 * Class: E63C
 * Date created: 2022-Nov-08 3:24:44 pm 
 * 
 */

package e63c.leujiuchuan.GA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



/**
 * @author 21030321
 *
 */
@Entity
public class Shirt {
	
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY) 
		private int id;
		
		@NotNull
		@NotEmpty(message="Shirt name cannot be empty!")
		@Size(min=5, max=50, message="Shirt's name length must be between 5 and 50 charaters!")
		private String name;
		
		@NotNull
		@NotEmpty(message="Shirt description cannot be empty!")
		@Size(min=5, max=300, message="Shirt's description length must be between 5 and 100 charaters!")
		private String description; 
		
	
		//@Pattern( regexp = "^(0*[1-9][0-9]*(\\.[0-9]+)?|0+\\.[0-9]*[1-9][0-9]*)$",message="Please input a positive numeric value")
		@DecimalMin(value="0.1",message="Please input a positive numeric value")
		private double price;
		
	
		//@Pattern(regexp = "^[1-9]\\d*$", message="Please input a positive whole number")
		@Min(value =1, message = "The value must be positive")
		private int quantity;
		
		
		private String imgName;
		
		@ManyToOne
		@JoinColumn(name="category_id", nullable=false)
		@NotNull(message="Category cannot be empty")
		private Category category;
		
		public Category getCategory() {
			return category;
		}
		public void setCategory(Category category) {
			this.category = category;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public double getPrice() {
			return price;
		}
		public void setPrice(double price) {
			this.price = price;
		}
		public int getQuantity() {
			return quantity;
		}
		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
		public String getImgName() {
			return imgName;
		}
		public void setImgName(String imgName) {
			this.imgName = imgName;
		}
		


}
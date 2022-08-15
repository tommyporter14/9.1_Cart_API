package co.grandcircus.cartapi;

import java.util.List;
import org.springframework.http.HttpStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "https://gc-express-tester.surge.sh/")
@RestController
public class ItemController {
	

	@ResponseBody
	@ExceptionHandler(ItemNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String itemNotFoundHandler(ItemNotFoundException ex) {
		return ex.getMessage();
	}

	@Autowired
	private ItemRepository ir;
	
	@GetMapping("/reset")
	public String reset() {
		ir.deleteAll();
		
		Item item = new Item("apple", 1.50, 5);
		ir.insert(item);
		
		item = new Item("banana", 1.00, 10);
		ir.insert(item);
		
		item = new Item("cucumber", 3.25, 8);
		ir.insert(item);
		
		item = new Item("tomato", 2.75, 12);
		ir.insert(item);
		
		item = new Item("avocado", 4.50, 7);
		ir.insert(item);
		
		item = new Item("onion", 2.00, 6);
		ir.insert(item);
		
		return "Data reset.";
	}
	
	@GetMapping("/cart-items")
	public List<Item> readAll(@RequestParam(required=false) String product,
			  @RequestParam(required=false) Double maxPrice,
			  @RequestParam(required=false) String prefix,
			  @RequestParam(required=false) Integer pageSize){
		
		if(product != null) {
			product.equalsIgnoreCase(product);
			return ir.findByProduct(product);
		} else if (maxPrice != null) {
			return ir.findByPriceLessThan(maxPrice);
		} else if (prefix != null) {
			prefix.equalsIgnoreCase(prefix);
			return ir.findByProductStartingWith(prefix);
		} else if ((pageSize != null) && (ir.findAll().size() > pageSize)) {
			return ir.findAll().subList(0, pageSize);
		} else {
			return ir.findAll();
		}
	}
	
	@GetMapping("/cart-items/{id}")
	public Item readOne(@PathVariable("id") String id) {
		return ir.findById(id).orElseThrow(() -> new ItemNotFoundException("ID Not Found"));
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/cart-items")
	public Item addOne(@RequestBody Item item) {
		ir.insert(item);
		return item;
	}
	
	@PutMapping("/cart-items/{id}")
	public Item updateOne(@RequestBody Item item, @PathVariable("id") String id) {
		item.setId(id);
		ir.save(item);
		return item;
	}
		
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/cart-items/{id}")
	public void deleteOne(@PathVariable("id") String id) {
		ir.deleteById(id);
	}
	
	@GetMapping("/cart-items/total-cost")
	public double readTotal() {
		double total = 0;
		List<Item> items = ir.findAll();
		for(Item item : items) {
			total += (item.getPrice() * item.getQuantity());
		}
			
		total *= 1.06;
		return total;
	}
	
	@PatchMapping("/cart-items/{id}/add")
	public Item updateQuantity(@PathVariable("id") String id, @RequestParam int count) { 
		Item current = ir.findById(id).orElseThrow(() -> new ItemNotFoundException("ID Not Found"));
		current.setQuantity(current.getQuantity() + count);
		ir.save(current);
		return current;
	}
}

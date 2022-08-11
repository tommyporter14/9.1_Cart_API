package co.grandcircus.cartapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {

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
}

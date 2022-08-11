package co.grandcircus.cartapi;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository extends MongoRepository<Item, String>{

	List<Item> findAll();
	Optional<Item> findById(String id);
	List<Item> findByProduct(String product);
	List<Item> findByPriceLessThan(Double maxPrice);
	List<Item> findByProductStartingWith(String prefix);
	
}

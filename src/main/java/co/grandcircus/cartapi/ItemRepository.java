package co.grandcircus.cartapi;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository extends MongoRepository<Item, String>{

	List<Item> findByProduct(String product);
}

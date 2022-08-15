package co.grandcircus.cartapi;

public class ItemNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ItemNotFoundException(String id) {
		super("ID Not Found");
	}
}

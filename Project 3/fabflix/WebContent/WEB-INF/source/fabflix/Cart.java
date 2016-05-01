package fabflix;

import java.util.*;


public class Cart
{
	private HashMap<Integer, CartItem> basket = new HashMap<Integer, CartItem>();
	private Double total = 0.0;
	private Double tax = 0.08;
	
	public void add_cart_item(Movie movie) throws Exception
	{
		Double price = getMoviePrice(movie.getId());
		add_to_basket(movie, price);
	}
	
	public Double getMoviePrice(Integer movie_id) throws Exception
	{
		dbFunctions db = new dbFunctions();
		db.make_connection("jdbc:mysql://localhost:3306/moviedb", "root", "root");
		Double price = db.getMoviePrice(movie_id);
		db.close();
		return price;
	}
	public void add_to_basket(Movie movie, Double price)
	{
		Integer movie_id = movie.getId();
		if(basket.containsKey(movie_id))
		{
			basket.get(movie_id).addOne();
		}
		else
		{
			CartItem item = new CartItem(movie, price);
			basket.put(movie_id, item);
		}
		this.total += price;
	}
	
	public void remove_item(Integer movie_id)
	{
		CartItem movie = basket.get(movie_id);
		double price = movie.getPrice();
		double old_qty = movie.getQty();
		total -= (price*old_qty);
		basket.remove(movie_id);
	}
	public void setQty(Integer movie_id, int new_qty)
	{
		if(new_qty == 0)
		{
			remove_item(movie_id);
		}
		else if(new_qty > 0)
		{
			CartItem movie = basket.get(movie_id);
			double price = movie.getPrice();
			double old_qty = movie.getQty();
			total -= (price*old_qty);
			basket.get(movie_id).setQty(new_qty);
			total += (price*new_qty);
		}
	}
	public HashMap<Integer, CartItem> getBasket()
	{
		return this.basket;
	}
	public Double get_total()
	{
		return this.total;
	}
	public double get_tax()
	{
		return this.tax;
	}
	public double get_taxed_total()
	{
		return this.total * this.tax;
	}
	public int get_item_count()
	{
		return this.basket.size();
	}

}
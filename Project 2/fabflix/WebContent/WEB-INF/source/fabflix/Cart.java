package fabflix;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;


@SuppressWarnings("serial")
public class Cart
{
	private HashMap<Integer, CartItem> basket = new HashMap<Integer, CartItem>();
	private Double total = 0.0;
	private Double tax = 0.08;
	
	public void add_cart_item(Movie movie, Double price)
	{
		add_to_basket(movie, price);
		this.total += price;
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

}
package fabflix;

public class CustomerCheckout 
{
	private String first_name;
	private Integer customer_id;
	private String last_name;
	private String cc;
	private String exp_date;
	
	public CustomerCheckout(String first_name, String last_name, Integer customer_id, String cc, String exp_date) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.cc = cc;
		this.customer_id = customer_id;
		this.exp_date = exp_date;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getExp_date() {
		return exp_date;
	}

	public void setExp_date(String exp_date) {
		this.exp_date = exp_date;
	}

	public Integer getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}

	
	
	
}

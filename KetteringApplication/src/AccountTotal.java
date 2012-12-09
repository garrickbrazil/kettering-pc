import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/********************************************************************
 * Class: AccountTotal
 * Purpose: stores the total financial account information
/*******************************************************************/
public class AccountTotal {

	// Properties
	private String charges;
	private String credits;
	private String balance;
	private List<AccountDetail> details;
	
	
	/********************************************************************
	 * Constructor: AccountTotal
	 * Purpose: create default account total object
	/*******************************************************************/
	public AccountTotal(Elements rows){
		
		this.details = new ArrayList<AccountDetail>();
		
		if(rows.size() > 6){
			
			// Account balance
			rows.remove(0); rows.remove(0);
			
			
			// Charges
			if(rows.get(rows.size()-3).getElementsByTag("td").size() > 0) this.charges = rows.get(rows.size()-3).getElementsByTag("td").get(0).text();
			
			
			// Credits
			if(rows.get(rows.size()-2).getElementsByTag("td").size() > 0) this.credits = rows.get(rows.size()-2).getElementsByTag("td").get(0).text();
			
			
			// Account Balance
			if(rows.get(rows.size()-1).getElementsByTag("td").size() > 0) this.balance = rows.get(rows.size()-1).getElementsByTag("td").get(0).text();
			
			
			// Remove last three rows
			rows.remove(rows.size()-1); rows.remove(rows.size()-1); rows.remove(rows.size()-1);
			
			
			// Add details
			for(int i = 0; i < rows.size(); i++) details.add(new AccountDetail(rows.get(i)));
		}
		
	}
	
	
	/********************************************************************
	 * Accessors: getAccountBalance, getCharges, getCredits, getBalance,
	 * 		getDetails
	 * Purpose: get the corresponding data
	/*******************************************************************/
	public String getCharges(){ return this.charges; }
	public String getCredits(){ return this.credits; }
	public String getBalance(){ return this.balance; }
	public List<AccountDetail> getDetails(){ return this.details; }
	
	
	/********************************************************************
	 * Method: toString
	 * Purpose: format object into a string
	/*******************************************************************/
	public String toString(){
		
		String accountStr = "";
		
		// Collect details
		for(int i = 0; i < this.details.size(); i++) accountStr += this.details.get(i).toString() + "\n";
		
		// Other properties
		accountStr += "\nAccount Balance: " + this.balance + "\nCharges: " + this.charges + "\nCredits: " + this.credits;
		
		return accountStr;
	}
	
}



/********************************************************************
 * Class: AccountDetail
 * Purpose: holds one detail of either charge or credit
/*******************************************************************/
class AccountDetail{
	
	// Properties
	private String detailCode;
	private String description;
	private String charge;
	private String payment;
	private String balance;
	
	
	/********************************************************************
	 * Constructor: AccountDetail
	 * Purpose: create a default account detail object
	/*******************************************************************/	
	public AccountDetail(Element row){
		
		Elements properties = row.getElementsByTag("td");
		
		if(properties.size() == 5){
			
			// Set properties
			this.detailCode = properties.get(0).text().replaceAll("[\\u00A0\\s]","");
			this.description = properties.get(1).text().replaceAll("[\\u00A0\\s]","");
			this.charge = properties.get(2).text().replaceAll("[\\u00A0\\s]","");
			this.payment = properties.get(3).text().replaceAll("[\\u00A0\\s]","");
			this.balance = properties.get(4).text().replaceAll("[\\u00A0\\s]","");
		}
		
		else{
			
			// Defaults
			this.detailCode = "";
			this.description = "";
			this.charge = "";
			this.payment = "";
			this.balance = "";
		}
	}
	
	
	/********************************************************************
	 * Accessors: getDetailCode, getDescription, getCharge, getPayment,
	 * 		getBalance
	 * Purpose: get the corresponding data
	/*******************************************************************/
	public String getDetailCode(){ return this.detailCode; }
	public String getDescription(){ return this.description; }
	public String getCharge(){ return this.charge; }
	public String getPayment(){ return this.payment; }
	public String getBalance(){ return this.balance; }
	
	
	
	/********************************************************************
	 * Method: toString
	 * Purpose: format object into a string
	/*******************************************************************/
	public String toString(){
		
		return "Detail code: " + this.detailCode + " Description: " + this.description + " Charge: " + this.charge + " Payment: " + this.payment + " Balance: " + this.balance;
	}
	
}

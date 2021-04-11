import java.util.*;
import java.lang.*;
import java.sql.*;

public class Auction {
	private float startPrice,targetPrice,currentPrice;
	private int duration;
	private String startDate,startTime,status;
	Buyer currentWinner;
	Item i;
	
	public Auction() {
		
	}
	
	public Auction(float targetPrice, int duration, String startDate, String startTime, Buyer currentWinner, Item i) {
		this.startPrice = i.getBasePrice();
		this.targetPrice = targetPrice;
		this.currentPrice = startPrice;
		this.duration = duration;
		this.startDate = startDate;
		this.startTime = startTime;
		this.status = "NotStarted";
		this.currentWinner = currentWinner;
		this.i = i;
	}

	public float getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(float startPrice) {
		this.startPrice = startPrice;
	}

	public float getTargetPrice() {
		return targetPrice;
	}

	public void setTargetPrice(float targetPrice) {
		this.targetPrice = targetPrice;
	}

	public float getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(float currentPrice) {
		this.currentPrice = currentPrice;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Buyer getCurrentWinner() {
		return currentWinner;
	}

	public void setCurrentWinner(Buyer currentWinner) {
		this.currentWinner = currentWinner;
	}

	public Item getI() {
		return i;
	}

	public void setI(Item i) {
		this.i = i;
	}

	void startAuction(Connection con) throws Exception{
		status="Active";
		PreparedStatement st=con.prepareStatement("update auction set status='"+status+"' where itemid="+this.i.getItemid()+";");
		st.executeUpdate();
	}
	
	void endAuction(Connection con) throws Exception{
		status="Ended";
		PreparedStatement st=con.prepareStatement("update auction set status='"+status+"' where itemid="+this.i.getItemid()+";");
		st.executeUpdate();
	}
	
	Buyer declareWinner() {
		if(status=="Ended") {
			return currentWinner;
		}
		else {
			System.out.println("Auction hasn't eneded yet.");
			return null;
		}
	}
	
	void payment(Buyer b, Seller s) {
		
	}
	
	void notifyInterestedBuyer() {
		
	}
	
}

import java.util.*;
import java.lang.*;

public class Item {
	float basePrice;
	String name,category,description,certification,owner;
	int itemid,timesReported;
	boolean hidden;
	ArrayList<Buyer> interestedBuyers;
	
	public Item() {
		
	}
	
	public Item(float basePrice, String name, String owner, String category, String description, String certification) {
		super();
		this.basePrice = basePrice;
		this.name = name;
		this.owner=owner;
		this.category = category;
		this.description = description;
		this.certification = certification;
		this.timesReported = 0;
		this.hidden=false;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getItemid() {
		return itemid;
	}

	public void setItemid(int itemid) {
		this.itemid = itemid;
	}

	public float getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(float basePrice) {
		this.basePrice = basePrice;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCertification() {
		return certification;
	}

	public void setCertification(String certification) {
		this.certification = certification;
	}

	public int getTimesReported() {
		return timesReported;
	}

	public void setTimesReported(int timesReported) {
		this.timesReported = timesReported;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	
	public void addInterestedBuyer(Buyer b) {
		interestedBuyers.add(b);
	}
}

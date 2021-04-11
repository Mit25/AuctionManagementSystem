import java.util.*;
import java.lang.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.*;

public class Buyer extends User {

	String billingAddr,preferedPaymentMethod;
	
	public Buyer() {
		
	}
	
	public Buyer(String username, String password, String name, String contactNo, String email, String billingAddr, String preferedPaymentMethod) {
		super(username,password,name,contactNo,email);
		this.billingAddr = billingAddr;
		this.preferedPaymentMethod = preferedPaymentMethod;
	}

	public String getBillingAddr() {
		return billingAddr;
	}

	public void setBillingAddr(String billingAddr) {
		this.billingAddr = billingAddr;
	}

	public String getPreferedPaymentMethod() {
		return preferedPaymentMethod;
	}

	public void setPreferedPaymentMethod(String preferedPaymentMethod) {
		this.preferedPaymentMethod = preferedPaymentMethod;
	}
	
	public void addToWishlist(Connection con,Item i) throws Exception {
		PreparedStatement st=con.prepareStatement("insert into wishlist values(?,?);");
		st.setString(1, username);
		st.setInt(2, i.getItemid());
		st.executeUpdate();
	}
	
	public int login(MySystem sys,String uname,String pwd, Connection con) throws Exception {
		if(sys.authenticateCredentials(uname, pwd, con)==0) {
			PreparedStatement st=con.prepareStatement("select * from \"User\" natural join buyer where uname='"+uname+"';");
			ResultSet rs=st.executeQuery();
			if(rs.next()) {
				super.username=rs.getString(1);
				super.password=rs.getString(2);
				super.name=rs.getString(3);
				super.contactNo=rs.getString(4);
				super.email=rs.getString(5);
				super.isBlock=rs.getBoolean(6);
				this.billingAddr=rs.getString(7);
				this.preferedPaymentMethod=rs.getString(8);
				if(isBlock==true)
					return 1;
				isLoggedIn=true;
				return 0;
			}
			else
				return 1;
		}
		else
			return 1;
	}
	
	public int register(Connection con) throws Exception{
		PreparedStatement st=con.prepareStatement("insert into \"User\" values(?,?,?,?,?,?);");
		st.setString(1, username);
		st.setString(2, password);
		st.setString(3, name);
		st.setString(4, contactNo);
		st.setString(5, email);
		st.setBoolean(6, false);
		st.executeUpdate();
		PreparedStatement st1=con.prepareStatement("insert into buyer values(?,?,?);");
		st1.setString(1, username);
		st1.setString(2, billingAddr);
		st1.setString(3, preferedPaymentMethod);
		st1.executeUpdate();
		return 0;
	}
	
	public int validateUser(MySystem sys,Connection con,String uname) throws Exception {
		return sys.validateUsername(uname,con,0);
	}
	
	public void deleteFromWishlist(Connection con, Item i)throws Exception {
		PreparedStatement st=con.prepareStatement("delete from wishlist where itemid='"+i.getItemid()+"' and uname='"+this.username+"';");
		st.executeUpdate();
	}
	
	public void bid(Connection con,Auction auc,float amount) throws Exception{
		if(auc.getStatus().compareTo("Active")==0 && auc.getCurrentPrice()<amount) {
			auc.setCurrentPrice(amount);
			auc.setCurrentWinner(this);
			PreparedStatement st=con.prepareStatement("update auction set currentprice="+auc.getCurrentPrice()+", currentwinner='"+this.username+"' where itemid="+auc.getI().getItemid()+";");
			st.executeUpdate();
		}
		else {
			System.out.println("Bid is lesser than current price.");
		}
	}
	
	public void seeAuctions(Connection con) throws Exception{
		PreparedStatement st=con.prepareStatement("select * from auction natural join item where status='Active' or status='NotStarted';");
		ResultSet rs=st.executeQuery();
		System.out.println("ID\tName\tStart date\tStart Time\tStart Price\tStatus");
		while(rs.next())
		{
			System.out.println(rs.getInt(1)+"\t"+rs.getString(16)+"\t"+rs.getString(8)+"\t"+rs.getString(9)+"\t"+rs.getFloat(2)+"\t"+rs.getString(4));
		}
	}
	
	public void viewWishlist(Connection con) throws Exception{
		PreparedStatement st=con.prepareStatement("select * from wishlist join item on wishlist.itemid=item.itemid where wishlist.uname='"+this.username+"';");
		ResultSet rs=st.executeQuery();
		System.out.println("Item ID\tName\tDescription\tCategory\tCertification\tBase Price");
		while(rs.next()) {
			System.out.println(rs.getInt(2)+"\t"+rs.getString(10)+"\t"+rs.getString(5)+"\t"+rs.getString(6)+"\t"+rs.getString(7)+"\t"+rs.getFloat(11));
		}
	}
}

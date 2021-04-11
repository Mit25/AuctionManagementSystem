import java.util.*;
import java.lang.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Seller extends User{
	String licNo,affiliation;
	
	public Seller() {
	}
	
	public Seller(String username, String password, String name, String contactNo, String email, String licNo, String affiliation) {
		super(username,password,name,contactNo,email);
		this.licNo = licNo;
		this.affiliation = affiliation;
	}

	public String getLicNo() {
		return licNo;
	}

	public void setLicNo(String licNo) {
		this.licNo = licNo;
	}

	public String getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}
	
	public void addPaymentMethod(Connection con,String pay) throws Exception {
		PreparedStatement st=con.prepareStatement("insert into sellerpayment values(?,?);");
		st.setString(1, username);
		st.setString(2, pay);
		st.executeUpdate();
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
		PreparedStatement st1=con.prepareStatement("insert into seller values(?,?,?);");
		st1.setString(1, username);
		st1.setString(2, licNo);
		st1.setString(3, affiliation);
		st1.executeUpdate();
		return 0;
	}
	
	public int validateUser(MySystem sys, Connection con, String uname) throws Exception{
		return sys.validateUsername(uname,con,1);
	}
	
	public Item addItem(Connection con,float basePrice, String name, String category, String description, String certification,int cnt) throws Exception {
		Item i=new Item(basePrice, name, this.username,category, description, certification);
		PreparedStatement st1=con.prepareStatement("insert into item values(?,?,?,?,?,?,?,?,?);");
		st1.setInt(1,cnt+1);
		st1.setString(2,i.getOwner());
		st1.setString(3, i.getDescription());
		st1.setString(4, i.getCategory());
		st1.setString(5, i.getCertification());
		st1.setInt(6, i.getTimesReported());
		st1.setBoolean(7, i.isHidden());
		st1.setString(8, i.getName());
		st1.setFloat(9, i.getBasePrice());
		st1.executeUpdate();
		return i;
		
	}
	
	public void displayItem(Connection con,Item i) throws Exception {
		PreparedStatement st=con.prepareStatement("update item set ishidden=true where itemid='"+i.getItemid()+"' and uname='"+this.username+"';");
		st.executeUpdate();
		i.setHidden(true);
	}
	
	public int login(MySystem sys,String uname,String pwd, Connection con) throws Exception {
		if(sys.authenticateCredentials(uname, pwd, con)==0) {
			PreparedStatement st=con.prepareStatement("select * from \"User\" natural join seller where uname='"+uname+"';");
			ResultSet rs=st.executeQuery();
			if(rs.next()) {
				super.username=rs.getString(1);
				super.password=rs.getString(2);
				super.name=rs.getString(3);
				super.contactNo=rs.getString(4);
				super.email=rs.getString(5);
				super.isBlock=rs.getBoolean(6);
				this.licNo=rs.getString(7);
				this.affiliation=rs.getString(8);
			}
			isLoggedIn=true;
			return 0;
		}
		else
			return 1;
	}
	
	public void startAuction(Auction auc,Connection con) throws Exception {
		auc.startAuction(con);
	}
	
	public void terminateAuction(Auction auc,int cond, String winner, Connection con) throws Exception{
		if(cond==1) 
			auc.setCurrentWinner(null);
		else
			System.out.println("Auction winner is "+winner);
		auc.endAuction(con);
	}
	
	public Auction addAuctionDetails(float targetPrice, int duration, String startDate, String startTime,Buyer currentWinner, Item i,Connection con) throws Exception{
		Auction a=new Auction(targetPrice, duration, startDate, startTime, currentWinner, i);
		PreparedStatement st1=con.prepareStatement("insert into auction values(?,?,?,?,?,?,?,?,?);");
		st1.setInt(1,i.getItemid());
		st1.setFloat(2,i.getBasePrice());
		st1.setFloat(3, a.getTargetPrice());
		st1.setString(4, a.getStatus());
		st1.setFloat(5, i.getBasePrice());
		if(a.getCurrentWinner()!=null){
			st1.setString(6, a.getCurrentWinner().getUsername());
		}
		else {
			st1.setNull(6, java.sql.Types.VARCHAR); 
		}
		st1.setInt(7, a.getDuration());
		st1.setString(8, a.getStartDate());
		st1.setString(9, a.getStartTime());
		st1.executeUpdate();
		
		return a;
		
	}
	
	public void seeAuctions(Connection con) throws Exception{
		PreparedStatement st=con.prepareStatement("select * from auction natural join item where uname='"+this.username+"';");
		ResultSet rs=st.executeQuery();
		System.out.println("ID\tName\tStart date\tStart Time\tStart Price\tTarget Price\tStatus");
		while(rs.next())
		{
			System.out.println(rs.getInt(1)+"\t"+rs.getString(16)+"\t"+rs.getString(8)+"\t"+rs.getString(9)+"\t"+rs.getFloat(2)+"\t"+rs.getFloat(3)+"\t"+rs.getString(4));
		}
	}
}

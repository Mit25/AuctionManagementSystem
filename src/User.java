import java.util.*;
import java.lang.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class User {
	String username,password,name,contactNo,email;
	boolean isBlock,isLoggedIn;
	
	public User() {
		
	}
	
	public User(String username, String password, String name, String contactNo, String email) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.contactNo = contactNo;
		this.email = email;
		isBlock=false;
		isLoggedIn=false;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isBlock() {
		return isBlock;
	}

	public void setBlock(boolean isBlock) {
		this.isBlock = isBlock;
	}
	
	public int register(Connection con) throws Exception {
		//nothing to do
		//method overridden in Buyer and Seller
		return 1;
	}
	
	public int validateUser(String uname) {
		//nothing to do
		//method overridden in Buyer and Seller
		return 1;
	}
	
	public int login(MySystem sys,String uname,String pwd, Connection con) throws Exception {
		//nothing to do
		//method overridden in Buyer and Seller
		return 1;
	}
	
	public void listAllItem(Connection con) throws Exception{
		PreparedStatement st=con.prepareStatement("Select * from item where ishidden=true");
		ResultSet rs=st.executeQuery();
		System.out.println("ID\tName\tOwner\tDescription\tCategory\tCertification\tPrice");
		while(rs.next()) {
			System.out.println(rs.getInt(1)+"\t"+rs.getString(8)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5)+"\t"+rs.getFloat(9));
		}
	}
	
	public void Report(Connection con,Item i)throws Exception{
		i.setTimesReported(i.getTimesReported()+1);
		PreparedStatement st=con.prepareStatement("update item set timereported="+i.getTimesReported()+" where itemid='"+i.getItemid()+"';");
		st.executeUpdate();
	}
	
	public void sendQuery(Admin a,String query) {
		String output=a.resolveQuery(query);
		System.out.println(output);
	}
}

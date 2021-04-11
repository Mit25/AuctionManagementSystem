import java.util.*;
import java.lang.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Admin {
	private String username;
	private String password;
	boolean isLoggedIn;
	
	public Admin() {
		username="admin";
		password="12345678";
	}
	
	public Admin(String username, String password) {
		super();
		this.username = username;
		this.password = password;
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
	
	public int login(MySystem sys,String uname,String pwd){
		if(uname.equals(username) && pwd.equals(password)) {
			this.isLoggedIn=true;
			return 0;
		}
		else
			return 1;
	}
	
	public void blockUser(Connection con,User u)throws Exception{
		u.setBlock(true);
		PreparedStatement st=con.prepareStatement("update \"User\" set isblocked=true where uname='"+u.getUsername()+"';");
		st.executeUpdate();
	}
	
	public void unblockUser(Connection con,User u)throws Exception {
		u.setBlock(false);
		PreparedStatement st=con.prepareStatement("update \"User\" set isblocked=false where uname='"+u.getUsername()+"';");
		st.executeUpdate();
	}
	
	public void changeCategory(Connection con,Item i,String newCat) throws Exception{
		PreparedStatement st=con.prepareStatement("update item set category='"+newCat+"' where itemid="+i.getItemid()+";");
		st.executeUpdate();
		i.setCategory(newCat);
	}
	
	public String resolveQuery(String query) {
		return "Query resolved";
	}
}

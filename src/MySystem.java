import java.util.*;
import java.lang.*;
import java.sql.*;

public class MySystem{
	public int authenticateCredentials(String uname,String pwd, Connection con) throws Exception {
		PreparedStatement st=con.prepareStatement("select * from \"User\" where uname='"+uname+"';");
		ResultSet rs=st.executeQuery();
		if(rs.next()) {
			String tmppwd=rs.getString(2);
			if(pwd.equals(tmppwd))
				return 0;
			else
				return 1;
		}
		else {
			return 1;
		}
	}
	
	public int validateUsername(String uname,Connection con,int ch) throws Exception {
		if(ch==0) {
			//Buyer
			PreparedStatement st=con.prepareStatement("select * from buyer where uname='"+uname+"';");
			ResultSet rs=st.executeQuery();
			if(rs.next())
				return 1;
			else
				return 0;
		}
		else {
			//Seller
			PreparedStatement st=con.prepareStatement("select * from seller where uname='"+uname+"';");
			ResultSet rs=st.executeQuery();
			if(rs.next())
				return 1;
			else
				return 0;
		}
	}
	
	public void validateAuction(Auction a) {
		
	}
	
	public void authenticatePayment(Auction a, Buyer b, Seller s) {
		
	}
}

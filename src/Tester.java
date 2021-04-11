import java.sql.*;
import java.util.*;
import java.io.*;

public class Tester {

	public static void main(String[] args) throws Exception {
		
		Class.forName("org.postgresql.Driver");
		Connection con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","admin");
		
		MySystem sys=new MySystem();
		System.out.println("Select");
		System.out.println("1. Login as Buyer");
		System.out.println("2: Login as Seller");
		System.out.println("3. Login as Admin");
		System.out.println("4. Register as Buyer");
		System.out.println("5. Register as Seller");
		Scanner sc=new Scanner(System.in);
		int lchoice=sc.nextInt();
		if(lchoice==1) {
			String uname,pwd;
			System.out.println("Username: ");
			uname=sc.next().toString();
			System.out.println("Password: ");
			pwd=sc.next().toString();
			Buyer b=new Buyer();
			if(b.login(sys, uname, pwd, con)==0) {
				System.out.println("Buyer login scuccessful");
				boolean exit=true;
				while(exit) {
					System.out.println("Select Task:");
					System.out.println("1. View all items");
					System.out.println("2. Add item to Wishlist");
					System.out.println("3. View wishlist");
					System.out.println("4. Delete item from Wishlist");
					System.out.println("5. View current auctions");
					System.out.println("6. Bid in auction");
					System.out.println("7. Report item");
					System.out.println("8. Log out");
					lchoice=sc.nextInt();
					if(lchoice==1) {
						b.listAllItem(con);
					}
					else if(lchoice==2) {
						System.out.println("Enter item id to add in wishlist: ");
						int itemid=sc.nextInt();
						PreparedStatement st=con.prepareStatement("Select * from item where itemid='"+itemid+"';");
						ResultSet rs=st.executeQuery();
						if(rs.next()) {
							Item i=new Item(rs.getFloat(9),rs.getString(8),rs.getString(2),rs.getString(4),rs.getString(3),rs.getString(5));
							i.setTimesReported(rs.getInt(6));
							i.setHidden(rs.getBoolean(7));
							i.setItemid(rs.getInt(1));
							b.addToWishlist(con, i);
						}
						else {
							System.out.println("Invalid item id.");
						}
					}
					else if(lchoice==3) {
						b.viewWishlist(con);
					}
					else if(lchoice==4) {
						System.out.println("Enter item id to delete from wishlist: ");
						int itemid=sc.nextInt();
						PreparedStatement st=con.prepareStatement("Select * from item where itemid='"+itemid+"';");
						ResultSet rs=st.executeQuery();
						if(rs.next()) {
							Item i=new Item(rs.getFloat(9),rs.getString(8),rs.getString(2),rs.getString(4),rs.getString(3),rs.getString(5));
							i.setTimesReported(rs.getInt(6));
							i.setHidden(rs.getBoolean(7));
							i.setItemid(rs.getInt(1));
							b.deleteFromWishlist(con,i);
						}
						else {
							System.out.println("Invalid item id.");
						}
					}
					else if(lchoice==5) {
						b.seeAuctions(con);
					}
					else if(lchoice==6) {
						System.out.println("Enter item id you want to bid: ");
						int itemid=sc.nextInt();
						PreparedStatement st=con.prepareStatement("Select * from item where itemid='"+itemid+"';");
						ResultSet rs=st.executeQuery();
						if(rs.next()) {
							Item i=new Item(rs.getFloat(9),rs.getString(8),rs.getString(2),rs.getString(4),rs.getString(3),rs.getString(5));
							i.setTimesReported(rs.getInt(6));
							i.setHidden(rs.getBoolean(7));
							i.setItemid(rs.getInt(1));
							PreparedStatement st1=con.prepareStatement("Select * from auction where itemid='"+itemid+"';");
							ResultSet rs1=st1.executeQuery();
							if(rs1.next()) {
								int cond;
								Auction a=new Auction(rs1.getFloat(3),rs1.getInt(7),rs1.getString(8),rs1.getString(9),null,i);
								a.setStatus(rs1.getString(4));
								System.out.println("Current price is "+rs1.getFloat(5)+". Do you want to bid? [y/n]");
								if(sc.next().equals("y")) {
									System.out.println("Enter your bid: ");
									int amount=sc.nextInt();
									b.bid(con,a,amount);
								}
							}
							else {
								System.out.println("Invalid item id.");
							}
						}
						else {
							System.out.println("Invalid item id.");
						}
						
					}
					else if(lchoice==7) {
						System.out.println("Enter item id of item to report: ");
						int itemid=sc.nextInt();
						PreparedStatement st=con.prepareStatement("Select * from item where itemid='"+itemid+"';");
						ResultSet rs=st.executeQuery();
						if(rs.next()) {
							Item i=new Item(rs.getFloat(9),rs.getString(8),rs.getString(2),rs.getString(4),rs.getString(3),rs.getString(5));
							i.setTimesReported(rs.getInt(6));
							i.setHidden(rs.getBoolean(7));
							i.setItemid(rs.getInt(1));
							b.Report(con, i);
						}
						else {
							System.out.println("Invalid item id.");
						}
					}
					else if(lchoice==8) {
						System.out.println("Logging out.");
						exit=false;
					}
					else {
						System.out.println("Invalid choice.");
					}
				}
			}
			else{
				System.out.println("Login failed");
			}
		}
		else if(lchoice==2) {
			String uname,pwd;
			System.out.println("Username: ");
			uname=sc.next().toString();
			System.out.println("Password: ");
			pwd=sc.next().toString();
			Seller s=new Seller();
			if(s.login(sys, uname, pwd, con)==0) {
				System.out.println("Seller login scuccessful");
				boolean exit=true;
				while(exit) {
					System.out.println("Select Task:");
					System.out.println("1. Add item");
					System.out.println("2. Display item");
					System.out.println("3. View all items");
					System.out.println("4. Add auction");
					System.out.println("5. View my auctions");
					System.out.println("6. Start auction");
					System.out.println("7. End auction");
					System.out.println("8. Report item");
					System.out.println("9. Log out");
					lchoice=sc.nextInt();
					if(lchoice==1) {
						String name,category,desc,cert;
						float baseprice;
						System.out.println("Add item details");
						System.out.println("Base price:");
						baseprice=sc.nextFloat();
						System.out.println("Name");
						name=sc.next();
						System.out.println("Category");
						category=sc.next();
						System.out.println("Description");
						desc=sc.next();
						System.out.println("Certification");
						cert=sc.next();
						PreparedStatement st=con.prepareStatement("select count(itemid) from item;");
						ResultSet rs=st.executeQuery();
						int cnt=0;
						if(rs.next())
							cnt=rs.getInt(1);
						System.out.println(cnt);
						
						s.addItem(con,baseprice, name, category, desc, cert,cnt);
						
					}
					else if(lchoice==2) {
						System.out.println("Enter item id of item to display: ");
						int itemid=sc.nextInt();
						PreparedStatement st=con.prepareStatement("Select * from item where itemid='"+itemid+"';");
						ResultSet rs=st.executeQuery();
						if(rs.next()) {
							Item i=new Item(rs.getFloat(9),rs.getString(8),rs.getString(2),rs.getString(4),rs.getString(3),rs.getString(5));
							i.setTimesReported(rs.getInt(6));
							i.setHidden(rs.getBoolean(7));
							i.setItemid(rs.getInt(1));
							s.displayItem(con, i);
						}
						else {
							System.out.println("Invalid item id.");
						}
					}
					else if(lchoice==3) {
						s.listAllItem(con);
					}
					else if(lchoice==4) {
						float targetPrice;
						int duration,itemid;
						String startDate,startTime;
						System.out.println("Add auction details");
						System.out.println("Target Price:");
						targetPrice=sc.nextFloat();
						System.out.println("Duration: ");
						duration=sc.nextInt();
						System.out.println("Start Date: ");
						startDate=sc.next();
						System.out.println("Start Time: ");
						startTime=sc.next();
						System.out.println("Item id: ");
						itemid=sc.nextInt();
						PreparedStatement st=con.prepareStatement("Select * from item where itemid='"+itemid+"';");
						ResultSet rs=st.executeQuery();
						if(rs.next()) {
							Item i=new Item(rs.getFloat(9),rs.getString(8),rs.getString(2),rs.getString(4),rs.getString(3),rs.getString(5));
							i.setTimesReported(rs.getInt(6));
							i.setHidden(rs.getBoolean(7));
							i.setItemid(rs.getInt(1));
							s.addAuctionDetails(targetPrice, duration, startDate, startTime, null, i, con);
						}
						else {
							System.out.println("Invalid item id.");
						}
					}
					else if(lchoice==5) {
						s.seeAuctions(con);					
					}
					else if(lchoice==6) {
						int itemid;
						System.out.println("Enter the idem id of the item you want to sell");
						itemid=sc.nextInt();
						PreparedStatement st=con.prepareStatement("Select * from item where itemid='"+itemid+"';");
						ResultSet rs=st.executeQuery();
						if(rs.next()) {
							Item i=new Item(rs.getFloat(9),rs.getString(8),rs.getString(2),rs.getString(4),rs.getString(3),rs.getString(5));
							i.setTimesReported(rs.getInt(6));
							i.setHidden(rs.getBoolean(7));
							i.setItemid(rs.getInt(1));
							PreparedStatement st1=con.prepareStatement("Select * from auction where itemid='"+itemid+"';");
							ResultSet rs1=st1.executeQuery();
							if(rs1.next()) {
								Auction a=new Auction(rs1.getFloat(3),rs1.getInt(7),rs1.getString(8),rs1.getString(9),null,i);
								s.startAuction(a,con);
							}
							else {
								System.out.println("Invalid item id.");
							}
						}
						else {
							System.out.println("Invalid item id.");
						}
						
					}
					else if(lchoice==7) {
						int itemid;
						System.out.println("Enter the idem id of the auction you want to end");
						itemid=sc.nextInt();
						PreparedStatement st=con.prepareStatement("Select * from item where itemid='"+itemid+"';");
						ResultSet rs=st.executeQuery();
						if(rs.next()) {
							Item i=new Item(rs.getFloat(9),rs.getString(8),rs.getString(2),rs.getString(4),rs.getString(3),rs.getString(5));
							i.setTimesReported(rs.getInt(6));
							i.setHidden(rs.getBoolean(7));
							i.setItemid(rs.getInt(1));
							PreparedStatement st1=con.prepareStatement("Select * from auction where itemid='"+itemid+"';");
							ResultSet rs1=st1.executeQuery();
							if(rs1.next()) {
								int cond;
								Auction a=new Auction(rs1.getFloat(3),rs1.getInt(7),rs1.getString(8),rs1.getString(9),null,i);
								System.out.println("Current price is "+rs1.getFloat(5)+". Do you want to sell? [y/n]");
								if(sc.next().equals("y"))
									cond=0;
								else
									cond=1;
								String winner=rs1.getString(6);
								s.terminateAuction(a,cond,winner,con);
							}
							else {
								System.out.println("Invalid item id.");
							}
						}
						else {
							System.out.println("Invalid item id.");
						}
					}
					else if(lchoice==8) {
						System.out.println("Enter item id of item to report: ");
						int itemid=sc.nextInt();
						PreparedStatement st=con.prepareStatement("Select * from item where itemid='"+itemid+"';");
						ResultSet rs=st.executeQuery();
						if(rs.next()) {
							Item i=new Item(rs.getFloat(9),rs.getString(8),rs.getString(2),rs.getString(4),rs.getString(3),rs.getString(5));
							i.setTimesReported(rs.getInt(6));
							i.setHidden(rs.getBoolean(7));
							i.setItemid(rs.getInt(1));
							s.Report(con, i);
						}
						else {
							System.out.println("Invalid item id.");
						}
					}
					else if(lchoice==9) {
						System.out.println("Logging out.");
						exit=false;
					}
					else {
						System.out.println("Invalid choice.");
					}
				}
			}
			else{
				System.out.println("Login failed");
			}
			
		}
		else if(lchoice==3) {
			String uname,pwd;
			System.out.println("Username: ");
			uname=sc.next();
			System.out.println("Password: ");
			pwd=sc.next();
			Admin a=new Admin();
			if(a.login(sys, uname, pwd)==0) {
				boolean exit=true;
				while(exit) {
					System.out.println("Select Task:");
					System.out.println("Login scuccessful");
					System.out.println("1. Block user");
					System.out.println("2. Unblock user");
					System.out.println("3. Change category");
					System.out.println("4. Logout");
					lchoice=sc.nextInt();
					if(lchoice==1) {
						System.out.println("Enter username to block: ");
						String username=sc.next();
						PreparedStatement st=con.prepareStatement("select * from \"User\" where uname='"+username+"';");
						ResultSet rs=st.executeQuery();
						if(rs.next()) {
							User u=new User(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));
							u.setBlock(rs.getBoolean(6));
							a.blockUser(con, u);
						}
						else {
							System.out.println("User does not exist.");
						}
					}
					else if(lchoice==2) {
						System.out.println("Enter username to block: ");
						String username=sc.next();
						PreparedStatement st=con.prepareStatement("select * from \"User\" where uname='"+username+"';");
						ResultSet rs=st.executeQuery();
						if(rs.next()) {
							User u=new User(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));
							u.setBlock(rs.getBoolean(6));
							a.unblockUser(con, u);
						}
						else {
							System.out.println("User does not exist.");
						}				
					}
					else if(lchoice==3) {
						System.out.println("Enter item id of item for changing category: ");
						int itemid=sc.nextInt();
						PreparedStatement st=con.prepareStatement("Select * from item where itemid='"+itemid+"';");
						ResultSet rs=st.executeQuery();
						if(rs.next()) {
							Item i=new Item(rs.getFloat(9),rs.getString(8),rs.getString(2),rs.getString(4),rs.getString(3),rs.getString(5));
							i.setTimesReported(rs.getInt(6));
							i.setHidden(rs.getBoolean(7));
							i.setItemid(rs.getInt(1));
							System.out.println("Enter new category");
							String newcat=sc.next();
							a.changeCategory(con, i, newcat);
						}
						else {
							System.out.println("Invalid item id.");
						}
					}
					else if(lchoice==4) {
						exit=false;
					}
				}
			}
			else{
				System.out.println("Login failed");
			}
		}
		else if(lchoice==4) {
			String uname,pwd,name,contactno,email,billingadr,payment;int pay;
			System.out.println("Username: ");
			uname=sc.next();
			System.out.println("Password: ");
			pwd=sc.next();
			System.out.println("Name: ");
			name=sc.next();
			System.out.println("Contact No: ");
			contactno=sc.next();
			System.out.println("Email ID: ");
			email=sc.next();
			System.out.println("Billing Address: ");
			billingadr=sc.next();
			System.out.println("Prefferd Payment Method: ");
			System.out.println("1. Debit Card");
			System.out.println("2. Credit Card");
			System.out.println("3. Cash on Delivery");
			System.out.println("4. Net Banking");
			System.out.println("5. Bit Coin");
			pay=sc.nextInt();
			switch(pay) {
				case 1: payment="Debit Card";
					break;
				case 2: payment="Credit Card";
					break;
				case 3: payment="Cash on Delivery";
					break;
				case 4: payment="Net Banking";
					break;
				case 5: payment="Bit Coin";
					break;
				default:
					payment="";
			}
			Buyer b=new Buyer(uname,pwd,name,contactno,email,billingadr,payment);
			while(b.validateUser(sys,con,uname)==1) {
				System.out.println("Username already exists. Try different username: ");
				uname=sc.next();
				b.setUsername(uname);
			}
			if(b.register(con)==0)
				System.out.println("Registration successful.");
			else
				System.out.println("Registration failed");
		}
		else if(lchoice==5) {
			String uname,pwd,name,contactno,email,licno,affiliation;
			System.out.println("Username: ");
			uname=sc.next();
			System.out.println("Password: ");
			pwd=sc.next();
			System.out.println("Name: ");
			name=sc.next();
			System.out.println("Contact No: ");
			contactno=sc.next();
			System.out.println("Email ID: ");
			email=sc.next();
			System.out.println("Licence No: ");
			licno=sc.next();
			System.out.println("Affiliation: ");
			affiliation=sc.next();
			Seller s=new Seller(uname,pwd,name,contactno,email,licno,affiliation);
			while(s.validateUser(sys,con,uname)==1) {
				System.out.println("Username already exists. Try different username: ");
				uname=sc.next();
				s.setUsername(uname);
			}
			if(s.register(con)==0)
				System.out.println("Registration successful.");
			else
				System.out.println("Registration failed");
			System.out.println("Add Payment Method provided [y/n]: ");
			String yn;
			System.out.println("1. Debit Card");
			yn=sc.next();
			if(yn.equals("y"))
				s.addPaymentMethod(con, "Debit Card");
			System.out.println("2. Credit Card");
			yn=sc.next();
			if(yn.equals("y"))
				s.addPaymentMethod(con, "Credit Card");
			System.out.println("3. Cash on Delivery");
			yn=sc.next();
			if(yn.equals("y"))
				s.addPaymentMethod(con, "Cash on Delivery");
			System.out.println("4. Net Banking");
			yn=sc.next();
			if(yn.equals("y"))
				s.addPaymentMethod(con, "Net Banking");
			System.out.println("5. Bit Coin");
			yn=sc.next();
			if(yn.equals("y"))
				s.addPaymentMethod(con, "Bit Coin");			
		}
		
	}

}

import java.io.*;
import java.util.*;
public class Passenger {
	String name;
	String gender;
	String address;
	String email;
	String password;
	String passport;
	Scanner myObj = new Scanner(System.in);
	public boolean UserExist(String Pas,String Pass) throws Exception
	{
		File readData=new File("Passenger.txt");
		BufferedReader temp=new BufferedReader(new FileReader(readData));
		String TempString;
		while((TempString=temp.readLine())!=null)
		{
			StringTokenizer S= new StringTokenizer(TempString,",");
			S.nextToken();
			String PS=S.nextToken(); 
			S.nextToken();
			S.nextToken();
			S.nextToken();
			String P=S.nextToken();
			if(P.equals(Pas)&&PS.equals(Pass))
			{
				temp.close();
				return true;
			}
		}
		temp.close();
		return false;
	}
	public void register() throws Exception
	{
		System.out.println("Enter your Name: ");
		name=myObj.nextLine();
		System.out.println("Enter your Gender: ");
		gender=myObj.nextLine();
		System.out.println("Enter your address: ");
		address=myObj.nextLine();
		System.out.println("Enter your Email: ");
		email=myObj.nextLine(); 
		System.out.println("Enter your Passport Number: ");
		passport=myObj.nextLine();
		System.out.println("Enter your Password for next login: ");
		password=myObj.nextLine();
		if(UserExist(passport,password))
		{
			throw new UserAlreadyExist("This User already Exist!");
		}
		StoreData();
		System.out.println("Your are Registered! Log in to System for availing services");
	}
	public boolean login() throws Exception
	{
		String Name;
		String Password;
		System.out.println("Enter your Name: ");
		Name=myObj.nextLine();
		System.out.println("Enter your Password: ");
		Password=myObj.nextLine();
		File readData=new File("Passenger.txt");
		BufferedReader temp=new BufferedReader(new FileReader(readData));
		String TempString;
		while((TempString=temp.readLine())!=null)
		{
			StringTokenizer S= new StringTokenizer(TempString,",");
			String N=S.nextToken();
			String P=S.nextToken();
			if(P.equals(Password)&&N.equals(Name))
			{
				name=N;
				gender=S.nextToken();
				address=S.nextToken();
				email=S.nextToken();
				password=P;
				passport=S.nextToken();
				temp.close();
				System.out.println("Login Successful!");
				return true;
			}
		}
		temp.close();
		throw new InvalidLoginDetails("Member is not authorized to use the system");
	}
	public void StoreData()
	{
		try
		{
			BufferedWriter temp=new BufferedWriter(new FileWriter("Passenger.txt",true));
			temp.write(name +","+password + ","+ gender+"," + address + ","+email+","+passport+"\n");
			temp.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	public void updateRecord(String req)
	{
		try
		{
			Flight FL=new Flight();
			BufferedWriter temp=new BufferedWriter(new FileWriter("PassengerTicket.txt",true));
			String ToBeStored=name+","+gender+","+address+","+passport+","+email+",";
			String TempString=FL.getFlightDetails(req);
			StringTokenizer S= new StringTokenizer(TempString,",");
			ToBeStored=ToBeStored+S.nextToken()+",";
			S.nextToken();
			ToBeStored=ToBeStored+S.nextToken()+",";
			ToBeStored=ToBeStored+S.nextToken()+"\n";
			//System.out.println(ToBeStored);
			temp.write(ToBeStored);
			temp.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public boolean Payment() throws Exception
	{
		String CardNumber;
		String Pin;
		System.out.println("Enter your 16 digit card number:");
		CardNumber=myObj.nextLine();
		if(CardNumber.length()!=16)
		{
			throw new InvalidCardDetails("Invalid Card Number");
		}
		System.out.println("Enter your 4 digit card Pin:");
		Pin=myObj.nextLine();
		if(Pin.length()!=4)
		{
			throw new InvalidCardDetails("Invalid Pin");
		}
		System.out.println("Payment Recieved");
		System.out.println("Card: "+CardNumber);
		return true;
	}
	public void BookTicket() throws Exception
	{
		Flight FL=new Flight();
		String Src,Dest;
		System.out.println("Enter Source: ");
		Src=myObj.nextLine();
		System.out.println("Enter Destination: ");
		Dest=myObj.nextLine();
		if(Src.equals(Dest))
		{
			throw new SamePlace("Source and Destination of a flight can not be Same!");
		}
		FL.searchFlight(Src, Dest);
		String req;
		System.out.println("Enter Flight Number you want to travel on: ");
		req=myObj.nextLine();
		if(FL.FlightExist(req))
		{
			String choice;
			System.out.println("Confirm Booking(Y/N): ");
			choice=myObj.nextLine();
			if(choice.equals("Y")||choice.equals("y"))
			{
				if(Payment())
				{
					FL.UpdateSeats(req,0);
					updateRecord(req); 
					System.out.println("Your Ticket is Booked!");
					PrintTicket(req);
				}
			}
		}
		else
		{
			throw new FlightNotFound("Invalid flight choice! Flight doesn't exist");
		}
		
	}
	public void DeleteRecord(String Pass)
	{
		try 
		{
			File readData=new File("PassengerTicket.txt");
			BufferedReader temp=new BufferedReader(new FileReader(readData));
			String ExistingContent="";
			String TempString;
			while((TempString=temp.readLine())!=null)
			{
				ExistingContent=ExistingContent+TempString+System.lineSeparator();
			}
			temp.close();
			String NewString="";
			Pass+=System.lineSeparator();
			String UpdatedContent=ExistingContent.replaceAll(Pass, NewString);
			FileWriter writeData=new FileWriter("PassengerTicket.txt");
			writeData.write(UpdatedContent);
			writeData.close();
		}
		catch(Exception e)
		{
			
		}
	}
	public void CancelTicket() throws Exception
	{
		Flight FL=new Flight();
		File readData=new File("PassengerTicket.txt");
		BufferedReader temp=new BufferedReader(new FileReader(readData));
		String TempString,req="";
		boolean flag=false;
		while((TempString=temp.readLine())!=null)
		{
			StringTokenizer S= new StringTokenizer(TempString,",");
			S.nextToken();
			S.nextToken();
			S.nextToken();
			if(passport.equals(S.nextToken()))
			{
				S.nextToken();
				req=S.nextToken();
				System.out.println("Your Booking is :\n"+TempString);
				flag=true;
				break;
			}
		}
		temp.close();
		if(flag==false)
		{
			throw new NoReservationFound("You have no Reservation to Cancel");
		}
		String choice;
		System.out.println("Confirm Canellation(Y/N): ");
		choice=myObj.nextLine();
		if(choice.equals("Y")||choice.equals("y"))
		{
			DeleteRecord(TempString);
			FL.UpdateSeats(req,1);
		}
	}
	public void PrintTicket(String R) throws Exception
	{
		File readData=new File("PassengerTicket.txt");
		BufferedReader temp=new BufferedReader(new FileReader(readData));
		String TempString;
		System.out.println("-------Ticket------");
		while((TempString=temp.readLine())!=null)
		{
			StringTokenizer S= new StringTokenizer(TempString,",");
			S.nextToken();
			S.nextToken();
			S.nextToken();
			String PS=S.nextToken();
			S.nextToken();
			if(PS.equals(passport)&&R.equals(S.nextToken()))
			{
				break;
			}
		}
			StringTokenizer S= new StringTokenizer(TempString,",");
			System.out.println("Name: "+S.nextToken());
			System.out.println("Gender: "+S.nextToken());
			System.out.println("Address: "+S.nextToken());
			System.out.println("Passsport: "+S.nextToken());
			S.nextToken();
			System.out.println("Flight: "+S.nextToken());
			System.out.println("From: "+S.nextToken()+"     To: "+S.nextToken());		
		temp.close();
	}
	public static void main(String[] args) throws Exception
	{
		
	}
};

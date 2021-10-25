import java.util.*;
public class Driver {
	
public static void main(String[] args) throws Exception
{
	int choice;
	Scanner myObj = new Scanner(System.in);
	System.out.println("1. Admin Role(Name: admin Password:12345)\n2. User Role");
	choice=myObj.nextInt();
	if(choice==1) 
	{
		Admin Ad=new Admin();
		if(Ad.login())
		{
			System.out.println("Welcome to Admin  Panel");
			while(true)
			{
				int ch1;
				System.out.println("1. Add Another Admin");
				System.out.println("2. Add A Flight");
				System.out.println("3. Exit");
				ch1=myObj.nextInt();
				if(ch1==1)
				{
					System.out.println("Adding a new Admin..");
					Ad.AddAnotherAdmin();
				}
				else if(ch1==2)
				{
					System.out.println("Adding a new flight..");
					Ad.AddAFlight();
				}
				else if(ch1==3)
				{
					System.out.println("exiting the system");
					return;
				}
				else
				{
					throw new InvalidMenuChoice("Invalid Option Entered!");
				}
			}
		}	
	}
	else if(choice==2)
	{
		Passenger P=new Passenger();
		System.out.println("Welcome to User  Panel");
		int ch2;
		System.out.println("1. Register");
		System.out.println("2. Login");
		ch2=myObj.nextInt();
		if(ch2==1)
		{
			P.register();
			ch2=2;
		}
		if(ch2==2)
		{
			if(P.login())
			{
				while(true)
				{
					int ch3;
					System.out.println("1. Book Ticket");
					System.out.println("2. Cancel Ticket");
					System.out.println("3. Exit");
					ch3=myObj.nextInt();
					if(ch3==1)
					{
						System.out.println("Booking a ticket..");
						P.BookTicket();
					}
					else if(ch3==2)
					{
						System.out.println("cancelling a ticket...");
						P.CancelTicket();
					}
					else if(ch3==3)
					{
						System.out.println("exiting the system");
						return;
					}
					else
					{
						throw new InvalidMenuChoice("Invalid Option Entered!");
					}
				}
			}
		}
		else
		{
			throw new InvalidMenuChoice("Invalid Option Entered!");
		}
	}
	else if(choice==3)
	{
		System.out.println("exiting the system...2");
		return;
	}
	else
	{
		throw new InvalidMenuChoice("Invalid Option Entered!");
	}
//	myObj.close();
}
}

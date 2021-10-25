import java.io.*;
import java.util.*;
public class Flight {
String day;
String FlightNumber;
String FlightName;
String source;
String Destination;
String Type;
String capacity;
String fare;
String availableSeats;
Scanner myObj = new Scanner(System.in);
	public void AddFlight() throws Exception
	{
		System.out.println("Enter Flight Number: ");
		FlightNumber=myObj.nextLine();
		if(FlightExist(FlightNumber))
		{
			throw new FlightNotFound("Flight Already Exists with same number");
		}
		System.out.println("Enter Flight Code/Name: ");
		FlightName=myObj.nextLine();
		System.out.println("Enter Source of flight: ");
		source=myObj.nextLine();
		System.out.println("Enter Destination of flight: ");
		Destination=myObj.nextLine(); 
		if(source.equals(Destination))
		{
			throw new SamePlace("Source and Destination can not be Same!");
		}
		System.out.println("Enter flight type: ");
		Type=myObj.nextLine();
		System.out.println("Enter capacity of flight: ");
		capacity=myObj.nextLine();
		System.out.println("Enter fare of flight: ");
		fare=myObj.nextLine();
		availableSeats=capacity;
		InputDate();
		StoreData();
		System.out.println("A new Flight is successfully Added!");
	}
	public void InputDate() throws Exception
	{
		int D,M,Y;
		System.out.println("Enter Date in following format (dd/MM/yyyy): ");
		System.out.println("dd: ");
		D=myObj.nextInt();
		if(D<1||D>31)
		{
			throw new InvalidDate("Invalid Day!");
		}
		System.out.print("MM: ");
		M=myObj.nextInt();
		if(M<1||M>12)
		{
			throw new InvalidDate("Invalid Month!");
		}
		System.out.print("yyyy: ");
		Y=myObj.nextInt();
		if(Y<2021)
		{
			throw new InvalidDate("Invalid Year");
		}
		day=Integer.toString(D)+"/"+Integer.toString(M)+"/"+Integer.toString(Y);
	}
	public void StoreData()
	{
		try
		{
			BufferedWriter temp=new BufferedWriter(new FileWriter("Flights.txt",true));
			temp.write(FlightNumber +","+FlightName + ","+ source+"," + Destination + ","+Type+","+capacity+","+fare+","+day+","+availableSeats+"\n");
			temp.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	public String getFlightDetails(String Num) throws Exception
	{
		File readData=new File("Flights.txt");
		BufferedReader temp=new BufferedReader(new FileReader(readData));
		String TempString;
		while((TempString=temp.readLine())!=null)
		{
			StringTokenizer S= new StringTokenizer(TempString,",");
			String Number=S.nextToken();
			if(Number.equals(Num))
			{
				temp.close();
				return TempString;
			}
			
		}
	temp.close();	
	return null;	
	}
	public void UpdateSeats(String Num,int flag) throws Exception
	{
		try
		{
			File readData=new File("Flights.txt");
			BufferedReader temp=new BufferedReader(new FileReader(readData));
			String ExistingContent="";
			String TempString,OldString="";
			while((TempString=temp.readLine())!=null)
			{
				ExistingContent=ExistingContent+TempString+System.lineSeparator();
				StringTokenizer S= new StringTokenizer(TempString,",");
				String Number=S.nextToken();
				if(Number.equals(Num))
				{
					OldString=TempString;
				}
			}
			String t1=OldString,t2=OldString;
			StringTokenizer S= new StringTokenizer(t1,",");
			int i=0;
			while(i<8)
			{
				S.nextToken();
				i++;
			}
			String Curr=S.nextToken();
			int currentSeats=Integer.valueOf(Curr);
			if(flag==1)
			{
				currentSeats=currentSeats+1;
			}
			else
			{
				currentSeats=currentSeats-1;
			}
			String NewString="";
			StringTokenizer S1= new StringTokenizer(t2,",");
			i=0;
			while(i<8)
			{
				NewString=NewString+S1.nextToken();
				NewString=NewString+",";
				i++;
			}
			NewString=NewString+String.valueOf(currentSeats);
			String UpdatedContent=ExistingContent.replaceAll(OldString, NewString);
			temp.close();
			FileWriter writeData=new FileWriter("Flights.txt");
			writeData.write(UpdatedContent);
			writeData.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public boolean FlightExist(String Num) throws Exception
	{
		File readData=new File("Flights.txt");
		BufferedReader temp=new BufferedReader(new FileReader(readData));
		String ExistingContent="";
		String TempString;
		while((TempString=temp.readLine())!=null)
		{
			ExistingContent=ExistingContent+TempString+System.lineSeparator();
			StringTokenizer S= new StringTokenizer(TempString,",");
			String Number=S.nextToken();
			if(Number.equals(Num))
			{
				temp.close();
				return true;
			}
		}
	temp.close();	
	return false;	
	}
	public void searchFlight(String Source,String Destination) throws Exception
	{
		System.out.println("Available flights currently are following: ");
		File readData=new File("Flights.txt");
		BufferedReader temp=new BufferedReader(new FileReader(readData));
		String TempString;
		while((TempString=temp.readLine())!=null)
		{
			StringTokenizer S= new StringTokenizer(TempString,",");
			S.nextToken();
			S.nextToken();
			String Src=S.nextToken();
			String Dest=S.nextToken();
			if(Src.equals(Source)&&Dest.equals(Destination))
			{
				System.out.println(TempString);
			}
			
		}
		temp.close();
	}
	public static void main(String[] args) throws Exception
	{
		Flight FL=new Flight();
		FL.AddFlight();
	}
}

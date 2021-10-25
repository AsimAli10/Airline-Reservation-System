import java.io.*;
import java.text.ParseException;
import java.util.Scanner;
import java.util.StringTokenizer;
public class Admin {
String Name;
String Password;
Scanner myObj = new Scanner(System.in);
public boolean login() throws Exception
{
	String Name;
	String Password;
	System.out.println("Enter your Name: ");
	Name=myObj.nextLine();
	System.out.println("Enter your Password: ");
	Password=myObj.nextLine();
	File readData=new File("Admin.txt");
	BufferedReader temp=new BufferedReader(new FileReader(readData));
	String TempString;
	while((TempString=temp.readLine())!=null)
	{
		StringTokenizer S= new StringTokenizer(TempString,",");
		String N=S.nextToken();
		String P=S.nextToken();
		if(P.equals(Password)&&N.equals(Name))
		{
			Password=P;
			Name=N;
			temp.close();
			return true;
		}
	}
	temp.close();
	throw new InvalidLoginDetails("Member is not authorized to use the system");
}
public void AddAnotherAdmin() throws Exception
{
	String Name;
	String Password;
	System.out.println("Enter Name of Admin: ");
	Name=myObj.nextLine();
	System.out.println("Set a Password(Minimum 5 character): ");
	Password=myObj.nextLine();
	if(Password.length()<5)
	{
		throw new PasswordConstraint("Password should be of mimimum length 5");
	}
	String ToBeAdded=Name+","+Password+"\n";
	try
	{
		BufferedWriter temp=new BufferedWriter(new FileWriter("Admin.txt",true));
		temp.write(ToBeAdded);
		temp.close();
		System.out.println(Name+" is added as Admin!");
	}
	catch(IOException e)
	{
		e.printStackTrace();
	}
}
public void AddAFlight() throws Exception
{
	try {
		Flight F=new Flight();
		F.AddFlight();
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}

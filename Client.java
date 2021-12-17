
import java.io.*;
import java.net.*;

public class Client {
	public static void main(String[] args) throws IOException {
		//To switch to run from the terminal comment out these and uncomment below and the same in the other file
		
		String hostName = "localHost";
		int portNumber = 8000;
		/*
		String hostName = args[0];
		int portNumber = Integer.parseInt(args[1]);
		*/
		
		//This is the help file that will print out for users I commented it out because you will read the text file I attached
		/*
		System.out.println("Instructions:");
		System.out.println("To Reach Store File Name: Type  What you would like to type then Save and Name");
		System.out.println("For Example-  What I want to type Save RandomFileName");
		System.out.println("To Reach Update File: Type update then the file you would like to update then what you would like to add");
		System.out.println("For Example- Update  FileName What I want to type");
		System.out.println("To Reach Delete File: Type  the file you would like to remove ");
		System.out.println("For Example- Remove RandomFileName");
		 //add print file names when nothing is there
		System.out.println("To Reach All File Names in cashe: Type get or list files ");
		System.out.println("For Example- Get Files or List Files");
		System.out.println("To Reach Read Files: Type read then the file");
		System.out.println("For Example- Read Random FileName");
		System.out.println("For Help- Type (Help Me)\n");
		 */

		try (
				Socket echoSocket = new Socket(hostName, portNumber);
				PrintWriter out =
						new PrintWriter(echoSocket.getOutputStream(), true);
				BufferedReader in =
						new BufferedReader(
								new InputStreamReader(echoSocket.getInputStream()));
				BufferedReader stdIn =
						new BufferedReader(
								new InputStreamReader(System.in))
				) {
			String userInput;
			while ((userInput = stdIn.readLine()) != null) {
				out.println(userInput);
				//I had a problem with it only printing one line so this change makes it so the first line will be the number of lines to print in the while loop
				//The rest is the same as your example
				String lines = in.readLine();
				for(int nlines = Integer.parseInt(lines); nlines > 0; nlines = nlines - 1)
					System.out.println(in.readLine());

			}
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + hostName);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " +
					hostName);
			System.exit(1);
		} 
	}
}
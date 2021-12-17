import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CasheServer {
	public String main () throws IOException {
		//this creates an array list of each word in the instruction list given a delimiter of any white space
		ArrayList<String> input = new ArrayList<>();
		try (Scanner s = new Scanner(new File("instructions.txt")).useDelimiter("\\s+")) {
			while (s.hasNext()) {
				input.add(s.next());
			}
		}
			String x;
			//if the array contains update it will call this method
			if (input.contains("update")) {
				x = (updateFileName(input));
				return x;
			}
			// if the array list at the second to last word contains save it will call this method
			else if (input.get(input.size()-2).contains("save")) {
				x = (storeFileName(input));
				return x;
			}
			//if the first word is read it will call this method
			else if (input.get(0).contains("read")) {
				x = (readFileName(input));
				return x;
			}
			//if the first word is get or list it will call this method
			else if (input.get(0).equals("get")|| input.get(0).equals("list")) {
				x= (getFileNames());
				return x;
			}
			//if the first word is remove or delete it will call this method
			else if (input.get(0).equals("remove")||input.get(0).equals("delete")) {
				x= (removeFileName(input));
				return x;
			}
			//if the first word is gelp it will call this method
			else if (input.get(0).equals("help")) {
				x= (Help());
				return x;
			}
			//else it will call this method and tell you to type help
			else {
				int m =4;
				return (m + "\nSorry I don't recognize it type help me for help \n");
			}
		}
	//this is the help function that helps the user and returns the instructions
	public static String Help() {
		// TODO Auto-generated method stub
		int k =15;
		String i= k +"\nInsructions:";
		i=i+ "\nTo Reach Store File Name: Type  What you would like to type then Save and Name";
		i=i+ "\nFor Example- What I want to type Save RandomFileName";
		i=i+ "\nTo Reach Update File: Type update then the file you would like to update then what you would like to add";
		i=i+ "\nFor Example- Update Random FileName What I want to type";
		i=i+"\nTo Reach Delete File: Type  the file you would like to remove ";
		i=i+"\nFor Example- Remove RandomFileName";
		i=i+"\nTo Reach All File Names in cashe: Type get or list files ";
		i=i+"\nFor Example- Get Files or List Files";
		i=i+"\nTo Reach Read Files: Type read then the file";
		i= i+"\nFor Example- Read Random FileName";
		i=i+ "\nFor Help- Type (help me)\n";
		return i;
	}
	//this is the update file method
	public static String updateFileName(ArrayList<String> input) throws IOException {
		//it gets the second word which should be the file and adds a txt
		String fileName = input.get(1);
		fileName=fileName+".txt";
		//it checks if the file exists and the user entered it correctly
		File file = new File(fileName);
		if (file.exists()){
			//it creates a buffer reader on append mode
			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.append("\n"); 
			//it starts with the third word which is after the save and file name and loops to the end
			for(int i=2;i<input.size();i++) 
			{
				String str =input.get(i) + " ";
				bw.append(str);
			}
			//prints it has been updated
			String k= "File has been updated \n";
			int m=4;
			String l= m+"\n"+k;
			bw.close();
			return l;
		}
		//if the file doesnt exist it returns file not exist and prompts the user to try again
		else {
			String k= "File does not exist: " + "\n" + "Changes Have Not Been Made Please Try again\n";
			int m=5;
			String l=m+"\n"+k;
			return l;
		}
	}

	//save the contents of the given file
	public static String storeFileName(ArrayList<String> input) throws IOException {
		//creates the file name with the last character and adds next
		String fileName = input.get(input.size()-1)+".txt";
		File file = new File(fileName);
		//check if the file exists already or equals my helper file closeFile if it is it returns a fail
		if (file.exists()||fileName.equals("closeFile.txt")) {
			String k= "File already exists under that name and is unable to save it please repeat with a new name\n";
			int m=4;
			String l=m+"\n"+k;
			return l;
		}
		//if the file doesn't exist it creates the cashe in an array
		String[] cashe = createCashe();
		boolean flag = false;
		//it loops through the cashe and checks if there is a open spot if there is it returns flag = true
		for(int i=0;i<cashe.length-1;i++)   
		{
			if (cashe[i].contains("null")) {
				ChangeLineInFile changeFile = new ChangeLineInFile();
				changeFile.changeALineInATextFile("cashe.txt", fileName, i+1);
				flag = true;
				break;
			}
		}  
		//then it creates a new file
		if (flag) {		
			
			file.createNewFile();
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			//writes what the user had submited to save to the file
			for(int i=0;i<input.size()-2;i++) 
			{
				String str =input.get(i) + " ";
				bw.write(str);
			}
			bw.close();
			String k= "File has been saved\n";
			int m=4;
			String l=m+"\n"+k;
			return l;
		}
		//this is if cashe is full
		else {
			String k= "Cashe is Full Unable to Save it\n";
			int m=4;
			String l=m+"\n"+k;
			return l;
		}
	}
	//this reads the file
	public static String readFileName(ArrayList<String> input) throws FileNotFoundException {
		String fileName = input.get(1)+".txt";
		File file = new File(fileName);
		//this checks if the file exists
		if(file.exists()) {
		int k  = 4;
		Scanner scanner = new Scanner(file);
		String i= "File: " + fileName + "\n";
		//then loops through the file if it does
		while(scanner.hasNextLine()){
			i =i +scanner.nextLine() +"\n";
			k++;
		}
		scanner.close();
		String l= k+ "\n"+ i;
		return l;

	}
		else {
			String k= "File does not exist unable to read it\n";
			int m=4;
			String l=m+"\n"+k;
			return l;
		}
	}
	//this gets the file names in the system
	public static String getFileNames() throws FileNotFoundException {
		//this creates the cashe as array
		String[] cashe = createCashe();
		String j="File Names Are: ";
		int k=3;
		//loops through the cashe array checks to see if their is a file in it
		//if there is it is added to the return method
		for(int i=0;i<cashe.length-1;i++)   
		{
			if (!cashe[i].contains("null")) {
				k++;
				j = j+ (cashe[i]) + "\n";
			}
		}
		//if k=3 which is the counter array for amount of lines means the entire cashe is empty it will return no files
		if (k==3) {
			j = j+"\nNo Files in Cashe\n";
			k=k+2;
		}
		String l=k+"\n"+j;
		return l;

	}
	//this is my algorithm to move file from a cashe
	public static String removeFileName(ArrayList<String> input) throws IOException {
		//first it creates a cash array
		String searchString= input.get(1)+ ".txt";
		String[] cashe = createCashe();
		//it loops through the cashe array
		for(int index = 0; index < cashe.length; index++){
			//i probably do not need this but it will speed up my code by a millisecond hahaha
			if(cashe[index] != null){
				//checks if it is equal to any files in the cashe if it isn't null
				if(cashe[index].equals(searchString)){
					//creates it as as file so it can be deleted
					File file= new File(cashe[index]);
					ChangeLineInFile changeFile = new ChangeLineInFile();
					//replaces the line in the cashe file with the thing you want to remove with null
					changeFile.changeALineInATextFile("cashe.txt", "null", index+1);
					//deletes the file
					file.delete();
					//probably do not need this line it is excess
					cashe[index]=null;
					int k =4;							
					String l = (k+  "\nFile Has Been Deleted and Removed from Cashe\n");
					return l;
				}
			}

		}
		//if it doesn't find the file it will return file not found
		int k=4;
		String l=  k+ "\nFile Not Found\n";
		return l;
	}
	//this is the create cashe helper method
	public static String[] createCashe() throws FileNotFoundException {
		File file = new File("cashe.txt");
		//it creates an array list with the cashe to get size of cashe
		ArrayList<String> input = new ArrayList<>();
		try (Scanner s = new Scanner(file).useDelimiter("\n")) {
			while (s.hasNext()) {
				input.add(s.next());
			}
		}
		
		//then creates an array with the size of the array list
		//I probably could have passed a variable with the length but hey this works hahaha
		String[] cashe= new String[input.size()];
		Scanner scanner = new Scanner(file);
		int index=0;
		while(scanner.hasNextLine()){
			cashe[index]=scanner.nextLine();
			index++;
		}
		scanner.close();
		return cashe;
	}


}


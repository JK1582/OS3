import java.io.*;
import java.util.*;
//this is my messaround file or playground I created this to test new code before I would add it
//I am not sure if I am suppose to submit this or not but if you find it interesting here it is
//if you do not just delete this file it does not effect my code
public class MessAround {
	/*
	public static void main(String[] args) throws IOException {
		File file = new File("pizza.txt");
		if (file.exists()){
			file.delete();
		}
		else
			System.out.println("File");
		Scanner sc=new Scanner(System.in);         //object of Scanner class  
		System.out.print("Enter the file name: ");  
		String name=sc.nextLine();              //variable name to store the file name  
		FileOutputStream fos=new FileOutputStream(name+".txt", true);  // true for append mode  
		System.out.print("Enter file content: ");         
		String str=sc.nextLine()+"\n";      //str stores the string which we have entered  
		byte[] b= str.getBytes();       //converts string into bytes  
		fos.write(b);           //writes bytes into file  
		fos.close();            //close the file  
		System.out.println("file saved."); 
		 
	}  
}
*/

	public static void main(String[] args) throws IOException {
		
		//String str = "Hello.txt pizza fdkf kf dskfs kf kfd skf dsklf dskl kl dskl";
		//String[] splitStr = str.trim().split("\\s+");
		//System.out.println(storeFileName(splitStr[1],splitStr));
		//if (splitStr[0].equals("Hello.txt"))
		//	System.out.println("Hi");
		//System.out.println(readFileName("2.txt"));
		//storeFileName("12.txt");
		//updateFileName("2.txt");
		//removeFileName("5.txt");
		//getFileNames();
		//System.out.println(readFileName("2.txt"));
		//words("3");
		int lines =1;
		File file = new File("cashe.txt");
		Scanner scanner = new Scanner(file);
		String line;
		ArrayList<String> input = new ArrayList<>();
		while(scanner.hasNextLine()){
			line= scanner.nextLine();
			if (!line.equals("null"))
				input.add(line);
		
		}
		scanner.close();
		if (input.size()<=lines) {
			file.createNewFile();
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for(int index=0;index<input.size();index++)	
			{
				String str =input.get(index) + "\n";
				bw.write(str); 	
			}
			for(int index=input.size();index<lines;index++)	
			{
				String str ="null" + "\n";
				bw.append(str); 	
			}
			bw.close();
			return true
		}
		else {
			return false;
		}
			
		//String[] cashe= new String[(int) file.length()+1];


	}
		//update the contents of a file
		public static void updateFileName(String fileName) throws IOException {
			Scanner scanner = new Scanner(System.in);
			FileOutputStream fos=new FileOutputStream(fileName, true); 
			System.out.println("Enter file content: ");         
			String str=scanner.nextLine()+"\n";    
			byte[] b= str.getBytes();      
			fos.write(b);         
			fos.close();
			System.out.println("File Has Added New Line With What Has Typed");
			scanner.close();
		}
		//save the contents of the given file
		public static String storeFileName(String fileName,String[] c) throws IOException {
			String[] cashe = createCashe();
			boolean flag = false;
			for(int i=0;i<cashe.length-1;i++)   
			{
				if (cashe[i].contains("null")) {
					ChangeLineInFile changeFile = new ChangeLineInFile();
					changeFile.changeALineInATextFile("cashe.txt", fileName, i+1);
					flag = true;
					break;
				}
			}  
			if (flag) {
				for(int i=0;i<c.length-2;i++) 
				{
					String str =c[i] + " ";
					FileOutputStream fos=new FileOutputStream(fileName, true);
					byte[] b= str.getBytes();      
					fos.write(b);         
					fos.close();
				}
				String k= "File has been updated";
				int m=1;
				String l=m+"\n"+k;
				return l;
			}
			else {
				String k= "Cashe is Full Unable to Save it";
				int m=1;
				String l=m+"\n"+k;
				return l;
			}
		}
		public static String readFileName(String fileName) throws FileNotFoundException {
			File file = new File(fileName);
			Scanner scanner = new Scanner(file);
			String i= "File: " + fileName + "\n";
			while(scanner.hasNextLine()){
				i =i +scanner.nextLine() +"\n";
			}
			scanner.close();
			return i;

		}
		public static String getFileNames() throws FileNotFoundException {
			String[] cashe = createCashe();
			String j="File Names Are: ";
			for(int i=0;i<cashe.length-1;i++)   
			{
				if (cashe[i].contains("null")) {
					System.out.print("");  
				}
				else
					j = j+ (cashe[i]) + "\n";
			}
			return j;

		}
		public static String[] createCashe() throws FileNotFoundException {
			String[] cashe= new String[10];
			File file = new File("cashe.txt");
			Scanner scanner = new Scanner(file);
			int index=0;
			while(scanner.hasNextLine()){
				cashe[index]=scanner.nextLine();
				index++;
			}
			scanner.close();
			return cashe;
		}

		public static void removeFileName(String searchString) throws IOException {
			String[] cashe = createCashe();
			Boolean flag=true;
			for(int index = 0; index < cashe.length; index++){
				if(cashe[index] != null){
					if(cashe[index].equals(searchString)){
						File file= new File(cashe[index]);
						ChangeLineInFile changeFile = new ChangeLineInFile();
						changeFile.changeALineInATextFile("cashe.txt", "null", index+1);
						file.delete();
						cashe[index]=null;
						System.out.println("File Has Been Deleted and Removed from Cashe");
						flag=false;
						break;
					}
				}

			}
			if (flag){
				System.out.println("File Not Found");
			}
		}
		public static void words(String inputfile) throws IOException
		{
			File file = new File(inputfile+ ".txt");
			FileInputStream fileInputStream = new FileInputStream(file);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String line;
			int wordCount = 0;
			int characterCount = 0;
			int numberofLines=0;

			while ((line = bufferedReader.readLine()) != null) {
				numberofLines++;
				characterCount += line.length();
				String words[] = line.split("\\s+");
				wordCount += words.length;
			}
			System.out.println("Total word count = "+ wordCount);
			System.out.println("Total number of characters = "+ characterCount);
			System.out.println("Total number of lines = "+ numberofLines);

		}
	}


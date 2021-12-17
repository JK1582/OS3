/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;


public class WordCountServer {
	public static void main(String[] args) throws IOException, InterruptedException {
		//To switch to run from the terminal comment out these and uncomment below and the same in the other file keep the cashe a minimum of 1 because I have 1 file saved.
		int portNumber = 8000;
		int casheSize = 10;
		/*
		int portNumber = Integer.parseInt(args[0]);
		int casheSize = Integer.parseInt(args[1]);
		*/

		try (
				ServerSocket serverSocket = new ServerSocket(portNumber);
				Socket clientSocket = serverSocket.accept();     
				PrintWriter out =
						new PrintWriter(clientSocket.getOutputStream(), true);                   
				BufferedReader in = new BufferedReader(
						new InputStreamReader(clientSocket.getInputStream()));
				) {
			String inputLine;
			CasheServer changeFile = new CasheServer();
			//this is the variables that will be updated to show persistance
			int wordCount =0;
			int characterCount=0;
			int numberofLines=0;
			//this checks if the cashe can be created with the files saved
			createCashe(casheSize);
			//this checks if a close file has been created to close the system I tired booleans but it was not working it deletes it right after if it is there and exits the program if cashe is too small
			File file = new File("closeFile.txt");
			if (file .exists()) {
				file.delete();
				int d=1;
				out.println(d+ "\nCashe is too small quiting");
				System.exit(0);
			}
			//this is the loop that will check if the client has entered anything
			while ((inputLine = in.readLine()) != null) {
				String m="";
				//this writes the instructions in command line to a file
				writeFile(inputLine);
				//this calls the word count to update server
				int[]wordcount=words("instructions");
				//this is a check to see if user enters more than one word if not it will not send it to the processor because the processor requires a minimum of 2 words.
				if (inputLine.contains(" ")){
				//System.out.println("Server echo: " + inputLine);
				//this calls the processor and the processor does the action and returns an output
				m= changeFile.main();
				}
				//this is an automatic check if not more than 1 word and k is the amount of lines the client should print
				if(!(inputLine.contains(" "))){
					int k=4;
					m=  k+ "\nPlease Insert More than 1 word\n";
				}
				//this updates words from method
				wordCount=wordCount+wordcount[0];
				characterCount= characterCount +wordcount[1];
				numberofLines= numberofLines+ wordcount[2];
				//this adds words to output
				m =m+ "Number of Words Inputed: "+ wordCount;
				m =m+ "\n" + "Number of Characters Inputed: " + characterCount;
				m =m+ "\n" + "Number of Lines Inputed: "+numberofLines;
				//this is the output
				out.println(m);
			}
			} catch (IOException e) {
				System.out.println("Exception caught when trying to listen on port "
						+ portNumber + " or listening for a connection");
				System.out.println(e.getMessage());
			}
		}
		//this is a method similar to one i found on geeks for geeks that I modified to take a file an it returns an array with the three things we must count
		// i threw the exception because I create the input file or instructions so it will never hit it.
		public static int[] words(String inputfile) throws IOException
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
			bufferedReader.close();

			int[] wordcount = {wordCount,characterCount,numberofLines};

			return wordcount;

		}
		//this is the method that copies the instructions to a file called instructions.txt
		public static void  writeFile(String inputLine) throws IOException{
			//I used an array list here because the input length is changing constantly
			ArrayList<String> input = new ArrayList<>();
			try (Scanner s = new Scanner(inputLine).useDelimiter("\\s+")) {
				while (s.hasNext()) {
					input.add(s.next());
				}
			}
			//// I created and deleted because I had an error and I thought it was because the instructions file was not getting deleted but I kept it because why not
			File file = new File("instructions.txt");
			if (file.exists()) 
				file.delete();

			file.createNewFile();

			FileWriter fw = new FileWriter(file.getAbsoluteFile());


			BufferedWriter bw = new BufferedWriter(fw);
			for(int index=0;index<input.size();index++)	
			{
				String str =input.get(index) + " ";
				bw.write(str); 	
			}
			bw.close();
		}
		//this is a method that creates the cashe based out how big the user picks
		public static void createCashe(int lines) throws IOException{
			File file = new File("cashe.txt");
			Scanner scanner = new Scanner(file);
			String line;
			ArrayList<String> input = new ArrayList<>();
			//i used an array list here to check how many files were already saved
			while(scanner.hasNextLine()){
				line= scanner.nextLine();
				if (!line.equals("null"))
					input.add(line);

			}
			scanner.close();
			//then if the size of the array list is greater than the amount size in cashe allowed it creates a bomb text file that will delete when returned
			if (input.size()>lines) {
				File closeFile = new File("closeFile.txt");
				closeFile.createNewFile();
			}
			//if the input size is good enough it fills up the cashe with the files saved
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for(int index=0;index<input.size();index++)	
			{
				String str =input.get(index) + "\n";
				bw.append(str); 	
			}
			//then it fills up the rest of the cashe with null that can be changed to files
			for(int index=input.size();index<lines;index++)	
			{
				String str ="null" + "\n";
				bw.append(str); 	
			}
			bw.close();
		}
	}

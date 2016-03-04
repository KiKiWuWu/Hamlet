package parser.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class PersonParser {

	private DBCInterface controller;
	
	public PersonParser(DBCInterface controller){
		this.controller = controller;
	}
	
	/**
	 * Reads the .csv linked to in path and insets them into the databse via controller
	 * @param path - path to the csv-File containing the people
	 */
	public void parse(final String path){
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			Person person;
			String[] tokens;
			String line, name;
			String[] keys = new String[4];
			//skips header
			br.readLine();
			line = br.readLine();
			while(line != null){
				person = new Person();
				//splits the line of the csv into tokens and then add the line to the database
				tokens = line.split(",");
				if(tokens.length != 9){
					System.err.println(line + " could not be split into 9 tokens with delimitr ','");
				}
				person.setName(tokens[0]);
				person.setFolger_id(tokens[1]);
				for(int i = 2; i < 6; i++){
					keys[i-2] = tokens[i];
				}
				person.setKeys(keys);
				person.setReplacable(Integer.parseInt(tokens[6]));
				person.setTwitter_id(tokens[7]);
				person.setSonstiges(tokens[8]);
				controller.insertPerson(person);
				line = br.readLine();
			}			
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

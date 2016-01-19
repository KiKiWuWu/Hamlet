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
			StringTokenizer tokens;
			String line, name;
			String[] keys = new String[4];
			//skips header
			br.readLine();
			line = br.readLine();
			while(line != null){
				//splits the line of the csv into tokens and then add the line to the database
				tokens = new StringTokenizer(line, ",");
				name = tokens.nextToken().trim();
				for(int i = 0; i < keys.length; i++){
					if(tokens.hasMoreTokens()){
						keys[i] = tokens.nextToken();
					}
				}
				controller.insertPerson(name, keys);
				line = br.readLine();
			}			
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

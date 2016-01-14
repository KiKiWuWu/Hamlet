package parser.database;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.StringTokenizer;

public class PersonParser {

	private DBCInterface controller;
	
	public PersonParser(DBCInterface controller){
		this.controller = controller;
	}
	
	public void parse(final String path){
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			StringTokenizer tokens;
			String line;
			String[] keys = new String[4];
			//skips header
			br.readLine();
			line = br.readLine();
			while(line != null){
				tokens = new StringTokenizer(line, ",");
				String name = tokens.nextToken().trim();
				for(int i = 0; i < keys.length; i++){
					if(tokens.hasMoreTokens()){
						keys[i] = tokens.nextToken();
					}
				}
				controller.insertPerson(name, keys);
				line = br.readLine();
			}
			
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

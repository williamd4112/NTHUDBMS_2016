package sqlParser;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;
enum Error{
	SYNTAX_ERROR,UNKNOWN_KEYWORD,Insertion_num_error;
}

public class SqlParser {
	ArrayList<Query> statement;
	//constant
	public static final String reg_create="^(?i)\\s*[0-9A-Za-z]+\\s*[a-zA-Z0-9]+\\s*(\\(\\s*[0-9]+\\s*\\))?\\s*(primary\\s+key)*\\s*$";
	public static final String reg_insert="^(?i)(\\s*\\w+){3}\\s*(\\(\\s*\\w+\\s*(,\\s*\\w+\\s*)*\\))?\\s*values\\s*\\(\\s*('[\\s\\S]+'|[\\d]+)\\s*(\\s*,\\s*('[\\s\\S]+'|[\\d]+))*\\s*\\)\\s*$";
	public static final int INT=1;
	public static final int VARCHAR=2;
	public static final int CREATE=1;
	public static final int INSERT=2;
//	public static void main(String args[]) throws IOException{
//		  BufferedReader buf = new BufferedReader(new InputStreamReader(System.in)); 
//          String lineTxt =  "" ;  
//          StringBuffer AlartTxt=new StringBuffer() ;  
//          while ((lineTxt = buf.readLine()) !=  null ){  
//              lineTxt+= '\n' ;  
//              AlartTxt.append(lineTxt);  
//          } 
//		 SqlParser sqlparser=new SqlParser();
//		 ArrayList<Query> a=sqlparser.getStatement(AlartTxt.toString());
//		 for(Query q:a){
//			 q.printQuery();
//		 }
//	}
	public SqlParser(){
		statement=new ArrayList<Query>();
	}
	public ArrayList<Query> getStatement(String queries){
		Query q=null;
		String query[];
		String[] ss;
		queries=queries.replace("("," ( ");
		queries=queries.replace(")"," ) ");
		query=queries.trim().split(";");
		
		for (String s:query){
			ss=s.trim().split( "\\s+" );
			if(ss[0].equalsIgnoreCase("create")&&ss[1].equalsIgnoreCase("table")){
				q=create_table(s,ss);
			}
			else if(ss[0].equalsIgnoreCase("insert")&&ss[1].equalsIgnoreCase("into")){
				q=insert(s,ss);
			}
			else{
				printError(Error.UNKNOWN_KEYWORD);
			}
			if(q!=null)
				statement.add(q);
		}	
		return statement;
		
	}
	public Query insert(String s,String[] ss){
		Query q=new Query();
		q.setAction(INSERT);
		try{
			if(s.matches(reg_insert)){
				q.setTableName(ss[2]);
				String[] sValue=s.substring(s.lastIndexOf("(")+1,s.lastIndexOf(")")).split("[,]+(?=([^\']*\'[^\']*\')*[^\']*$)");
				for(String vs:sValue){
					//add value
					vs=vs.trim();
					if(vs.contains("'")){
						vs=vs.substring(1,vs.length()-1);
						q.value.add(vs);
					}
					else q.value.add(Integer.parseInt(vs));
				}
				//add attribute
				if(s.matches("^[^()]+\\([^\\(\\)]+\\)[^()]+\\([\\s\\S]+\\)[^()]+$")){
					String[] sAtt=s.substring(s.indexOf("(")+1, s.indexOf(")")).split(",");
					for(String sa:sAtt)
						q.addAttribute(new Attribute(sa.trim()));
					if(sAtt.length!=sValue.length){
						printError(Error.Insertion_num_error);
						return null;
					}
				}
			}
			else{
				printError(Error.SYNTAX_ERROR);
				return null;
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
		return q;
	}
	public Query create_table(String s,String[] ss){
		Query q=new Query(); 
		q.setAction(CREATE);
		try{
			if(ss[3].equals("(") && ss[ss.length-1].equals(")")){
				//set table name
				if(ss[2].matches("^[\\w]+$"))
					q.setTableName(ss[2]);
				else
					printError(Error.SYNTAX_ERROR);
				// parse attributes
				String[] attrPair;
				attrPair=s.substring(s.indexOf("(")+1, s.lastIndexOf(")")).split(",");
				for(String attr:attrPair){
					if(attr.matches(reg_create)){
						//add attribute
						String[] token=attr.trim().split( "\\s+" );
						//add attribute		
						Attribute a=null;
						//data type
						if(token[1].equalsIgnoreCase("VARCHAR")){
							a=new Attribute(token[0],VARCHAR,Integer.parseInt(token[3]));
						}
						else if(token[1].equalsIgnoreCase("INT")){
							a=new Attribute(token[0],INT);
						}
						else{
							printError(Error.UNKNOWN_KEYWORD);	
							return null;
						}
						if(token[token.length-1].equalsIgnoreCase("key")) a.setPrimaryKey(); 
						q.addAttribute(a);
					}
					else{
					
						printError(Error.SYNTAX_ERROR);
						return null;
						//throw new SQLException();
					}
				}
			}
			else{
				printError(Error.SYNTAX_ERROR);
				return null;
			}
		}
		catch (Exception e){
		}
		return q;
	}
	
	public void printError(Error e){
		switch(e){
			case SYNTAX_ERROR: 
				System.out.println("Syntax Error");
				break;
			case UNKNOWN_KEYWORD:
				System.out.println("Unknown Keyword Error");
				break;
			case Insertion_num_error:
				System.out.println("Insertion Column count doesn't match value count");
				break;
			default: 
				System.out.println("Error");
				break;
		}
		
		
	}

	public void printS(String[] ss){
		for (String s:ss){
			System.out.println(s);
		}
	}

}

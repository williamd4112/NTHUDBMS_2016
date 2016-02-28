package sqlParser;

import java.util.ArrayList;

public class Query {
	private int action=0;
	private String tableName=null;
	ArrayList<Attribute> attribute;
	ArrayList value;
	public Query(){
		attribute=new ArrayList<Attribute>();
		value=new ArrayList();
	}
	public void setTableName(String tableName){
		this.tableName=tableName;
	}
	public String getTableName(){
		return tableName;
	}
	public void setAction(int action){
		this.action=action;
	}
	public int getAction(){
		return action;
	}	
	public void addAttribute(Attribute e) {
		attribute.add(e);
	}
	public void printQuery(){
		System.out.printf("action:%d\n",action);
		System.out.printf("tableName:%s\n",tableName);
		for(Attribute a:attribute){
			System.out.printf("name:%s\n",a.getName());
			System.out.printf("type:%d\n",a.getType());
			System.out.printf("length:%d\n",a.getLength());
			System.out.println(a.IsPrimaryKey());
			System.out.println();
		}
		for(int i=0;i<value.size();i++){
			System.out.println(value.get(i));
		}
		
	}

}

package sqlParser;

public class Attribute{
	private boolean primaryKey=false;
	private int type=0;
	private int length=0;
	private String name=null;
	public Attribute(String name,int type,int length){
		this.name=name;
		this.type=type;
		this.length=length;
	}
	public Attribute(String name,int type){
		this.name=name;
		this.type=type;
	}
	public Attribute(String name){
		this.name=name;
	}
	public boolean IsPrimaryKey(){
		return primaryKey;
	}
	public String getName(){
		return name;
	}
	public int getType(){
		return type;
	}
	public int getLength(){
		return length;
	}
	public void setPrimaryKey(){
		primaryKey=true;
	}
}
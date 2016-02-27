package com.nthu.dbms;

import java.util.HashMap;

public class SQLTable {
	
	private HashMap<Object, SQLRecord> records;
	private String primaryKeyName;
	
	public SQLTable(String[] attributesNames, String primaryKeyName)
	{
		this.records = new HashMap<Object, SQLRecord>();
		this.primaryKeyName = primaryKeyName;
	}
	
	public void insert(SQLRecord record) throws SQLTableException
	{
		Object primaryKey = record.getAttribute(primaryKeyName);
		
		if(primaryKey == null)
		{
			throw new SQLTableException("SQLTable::insert(): primary key must not be null.");
		}
		this.records.put(record.getAttribute(primaryKeyName), record);
	}
	
	public class SQLTableException extends Exception
	{
		final public String errorMessage;
		
		public SQLTableException(String msg)
		{
			this.errorMessage = msg;
		}
	}
}

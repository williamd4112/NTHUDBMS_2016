package com.nthu.dbms;

import java.util.HashMap;

public class SQLRecord {
	
	private HashMap<String, Object> attributes;
	
	public SQLRecord(String[] attributesNames, Object[] attributesValues) throws SQLRecordException
	{
		this.attributes = new HashMap<String, Object>();
		
		if(attributesNames.length != attributesValues.length)
		{
			throw new SQLRecordException("SQLRecord(): inconsistent length of input names and values.");
		}
		
		for(int i = 0; i < attributesNames.length && i < attributesValues.length; i++)
		{
			this.attributes.put(attributesNames[i], attributesValues[i]);
		}
	}
	
	public Object getAttribute(String attributeName)
	{
		return this.attributes.get(attributeName);
	}
	
	public void setAttribute(String attributeName, Object attributeValue)
	{
		this.attributes.replace(attributeName, attributeValue);
	}
	
	public class SQLRecordException extends Exception
	{
		final public String errorMessage;
		
		public SQLRecordException(String msg)
		{
			this.errorMessage = msg;
		}
	}
}

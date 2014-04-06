import java.util.Random;

/**
 * Given a Response Template as input, the Response Builder fills in the canned
 * sentence with its variable substitutions and returns the response as a
 * string. A variable substitution may optionally come from the memTable, which
 * contains previous user input.
 * 
 * @author Bryce
 * @author Yasha
 * 
 * This part of the system also implements the Wikipedia API if certain flags are set.
 * If the flag is set in the response template, the program will call up the appropriate response.
 * 
 * All it has to do, of course, is figure out what that response is going to be.
 * 
 * @author Benjamin
 * 
 * 
 */
public class ResponseBuilder implements ResponseBuilderInterface 
{
	/**
	 * Method that takes a given Response Template, and user inputed Keywords
	 *  and builds a Response to print
	 */
	@Override
	public String buildResponse(ResponseTemplate template, KeyWordList keys) 
	{
		Random rand = new Random();
		
		String response="";
		for(int i=0; i<template.response.length;i++)
		{
				response+=template.response[i];
				if(template.wordBuckets!=null && i < template.wordBuckets.length)
				{
					//if there are wordbucket words to append
					
					//select which word we are going to use.
					int select;
					if(template.wordBuckets[i].length != 1)
						select = rand.nextInt(template.wordBuckets[i].length - 1);
					else
						select = 0;
					
					if(fromMemory(template.wordBuckets[i][select]))
					{
						//if one of the wordbucket words is '#memlocation'
						String mem = template.wordBuckets[i][select].substring(1);
						response+=memTable.get(mem);
					
					}
					else
					{
						//the wordbucket word is not '#' type
						response+=template.wordBuckets[i][select];
					}
				}
						
		}
		return response;
	}
	
	@Override
	public String fromWiki(ResponseTemplate template, KeyWordList keys)
	{
		//This calls the Wikipedia API for a specific topic.
	
		return "This is supposed to be from the wiki.";
	}

	/**
	 * Method that takes a string and returns TRUE if it starts with #
	 * @param str
	 * @return
	 */
	public boolean fromMemory(String str)
	{
		if(str.charAt(0)=='#')
		{
			return true;
		}
		return false;
	}
}

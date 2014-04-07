import java.util.Random;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONObject;

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
	
	/**
	 * This method takes in a response template (and a list of keys, but who cares about those?).
	 * It then calls Wikipedia's web API, which returns more information when it is called for.
	 * 
	 * And I could care less about the keys.
	 */
	@Override
	public String fromWiki(ResponseTemplate template) throws IOException
	{
		//I need the intro for the specific page specified by wikiTerm().
		
		String s = null;
		
		String URLSearch = "https://en.wikipedia.org/w/api.php?action=query&prop=extracts&format=json&exlimit=10&exintro=&explaintext=&titles=";
		
		//important part: getting definitions and information here.
		String totalURL = URLSearch + URLEncoder.encode(template.wikiTerm(), "UTF-8");
		URL myUrl2 = new URL(totalURL);
		
		InputStream is2 = myUrl2.openStream();
		BufferedReader streamReader = new BufferedReader(new InputStreamReader(is2, "UTF-8"));
		//StringBuilder responseStrBuilder = new StringBuilder();
		
		String inputStr;
		String input = "";
		while((inputStr = streamReader.readLine()) != null){
			input += inputStr;
		}
		
		JSONObject json = new JSONObject(input);
		JSONObject query = json.getJSONObject("query");
		JSONObject pages = query.getJSONObject("pages");
	    String[] keys = JSONObject.getNames(pages);
		JSONObject nestedObject = pages.getJSONObject(keys[0]);
		
		
		s = nestedObject.getString("extract");
		if(s == null)
			return "Error.  ";
		return s;
	}

	public static String answerMeThis(String qball) throws IOException{
		String baseUrl = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20answers.search%20where%20query%3D%22";
		String baseUrl2 = "%22%20and%20type%3D%22resolved%22&format=json&diagnostics=true&callback=";
		qball = URLEncoder.encode(qball, "UTF-8");
		String fullUrl = baseUrl + qball + baseUrl2;
		URL myUrl = new URL(fullUrl);

		Exception e = null;
		JSONObject result = new JSONObject();
		// Repeatedly(do-while) establish the InputStream(inside the
		// try-catch-finally) until an exception is not thrown
		do {
			e = null;
			InputStream is = null;
			try {
				is = myUrl.openStream();
				JSONTokener tok = new JSONTokener(is);
				result = new JSONObject(tok);
				is.close();
			} catch (Exception x) {
				System.out.println("x Exception");
				e = x;
			} 
		} while (!(e == null));// do/while

		JSONObject query = result.getJSONObject("query");
		JSONObject results = query.getJSONObject("results");
		JSONArray question = results.getJSONArray("Question");
		JSONObject element = question.optJSONObject(0);
		String yourPrayersHaveBeenAnswered = element.getString("ChosenAnswer");
		
		return yourPrayersHaveBeenAnswered;
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

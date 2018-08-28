package com.jl.mindmesh;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StringUtils {
	
	public static String snipPlural(String word, boolean snippable) {
		return snippable ? word.substring(0, word.length() - 1) : word;
	}
	
	public static String snipFileExtension(String fullName) {
		return fullName;	
	}
	
	public static String clearNumbers(String word) {
		return word.replaceAll("0", "").replaceAll("1", "").replaceAll("2", "").replaceAll("3", "")
				  .replaceAll("4", "").replaceAll("5", "").replaceAll("6", "").replaceAll("7", "").replaceAll("8", "").replaceAll("9", "");
	}
	
	public static String convertStreamToString(InputStream is, boolean appendNewLine) throws Exception {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();
	    String line = null;
	    while ((line = reader.readLine()) != null) {
	      sb.append(line);
	      if (appendNewLine) sb.append("\n");
	    }
	    return sb.toString();
	}

}

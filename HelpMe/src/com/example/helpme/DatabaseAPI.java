package com.example.helpme;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class DatabaseAPI{
	
	public static String[] getLastTen(){
		Socket requestsocket;
		PrintWriter out;
		BufferedReader in;
		String answer=null;
		List<String> ans=null;
		String[] strarray = null;
		try{
			requestsocket = new Socket("128.10.25.10",7755);
			in = new BufferedReader(new InputStreamReader(requestsocket.getInputStream()));
			out = new PrintWriter(requestsocket.getOutputStream(),true);
			
			out.println("GETLASTTEN|");
			answer = in.readLine();
			
			requestsocket.close();
			
		}catch(Exception err){	}
		
		if(answer!=null){
			ans = Arrays.asList(answer.split("\\|"));
			strarray = ans.toArray(new String[0]);
			for(int i=0;i<strarray.length;i++){
				System.out.println(strarray[i]);
			}
		}
		return strarray;
	}
	
	public static String[] findQuestion(String key){
		Socket requestsocket;
		PrintWriter out;
		BufferedReader in;
		String answer=null;
		List<String> ans=null;
		String[] strarray = null;
		try{
			requestsocket = new Socket("128.10.25.10",7755);
			in = new BufferedReader(new InputStreamReader(requestsocket.getInputStream()));
			out = new PrintWriter(requestsocket.getOutputStream(),true);
			
			out.println("FINDQUESTION|"+key);
			answer = in.readLine();
			
			requestsocket.close();
			
		}catch(Exception err){	}
		
		if(answer!=null){
			ans = Arrays.asList(answer.split("\\|"));
			strarray = ans.toArray(new String[0]);
			for(int i=0;i<strarray.length;i++){
				System.out.println(strarray[i]);
			}
		}
		return strarray;
	}
	
	public static String[] randomQuestion(){
		Socket requestsocket;
		PrintWriter out;
		BufferedReader in;
		String answer=null;
		List<String> ans=null;
		String[] strarray = null;
		try{
			requestsocket = new Socket("128.10.25.10",7755);
			in = new BufferedReader(new InputStreamReader(requestsocket.getInputStream()));
			out = new PrintWriter(requestsocket.getOutputStream(),true);
			
			out.println("RANDOMQUESTION|");
			answer = in.readLine();
			
			requestsocket.close();
			
		}catch(Exception err){	}
		
		if(answer!=null){
			ans = Arrays.asList(answer.split("\\|"));
			strarray = ans.toArray(new String[0]);
			for(int i=0;i<strarray.length;i++){
				System.out.println(strarray[i]);
			}
		}
		return strarray;
	}
	
	
	public static void addAnswer(String a){
		Socket requestsocket;
		PrintWriter out;
		try{
			requestsocket = new Socket("128.10.25.10",7755);
			out = new PrintWriter(requestsocket.getOutputStream(),true);
			
			out.println("ADDANSWER|"+a);
			
			requestsocket.close();
			
		}catch(Exception err){	}

	}
	
	public static String[] fetchAnswer(String question){
		Socket requestsocket;
		PrintWriter out;
		BufferedReader in;
		String answer=null;
		List<String> ans=null;
		String[] strarray = null;
		try{
			requestsocket = new Socket("128.10.25.10",7755);
			in = new BufferedReader(new InputStreamReader(requestsocket.getInputStream()));
			out = new PrintWriter(requestsocket.getOutputStream(),true);
			
			out.println("FETCHANSWERS|"+question);
			answer = in.readLine();
			
			requestsocket.close();
			
		}catch(Exception err){	}
		
		if(answer!=null){
			ans = Arrays.asList(answer.split("\\|"));
			strarray = ans.toArray(new String[0]);
			for(int i=0;i<strarray.length;i++){
				System.out.println(strarray[i]);
			}
		}
		return strarray;
	}
	
	public static void addQuestion(String question){
		Socket requestsocket;
		PrintWriter out;
		try{
			requestsocket = new Socket("128.10.25.10",7755);
			out = new PrintWriter(requestsocket.getOutputStream(),true);
			
			out.println("ADDQUESTION|"+question);
			
			requestsocket.close();
			
		}catch(Exception err){	}
	}
}
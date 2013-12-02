package com.example.helpme;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class DatabaseAPI{
	//IP address is for sslab01
	public List<String> getLastTen(){
		Socket requestsocket;
		PrintWriter out;
		BufferedReader in;
		String answer=null;
		List<String> ans=null;
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
		}
		return ans;
	}
	
	public List<String> findQuestion(String key){
		Socket requestsocket;
		PrintWriter out;
		BufferedReader in;
		String answer=null;
		List<String> ans=null;
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
		}
		return ans;
	}
	
	public String randomQuestion(){
		Socket requestsocket;
		PrintWriter out;
		BufferedReader in;
		String answer=null;
		List<String> ans=null;
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
		}
		return ans.get(0);
	}
	
	
	public void addAnswer(String q, String a){
		Socket requestsocket;
		PrintWriter out;
		try{
			requestsocket = new Socket("128.10.25.10",7755);
			out = new PrintWriter(requestsocket.getOutputStream(),true);
			
			out.println("ADDANSWER|"+q+"|"+a);
			
			requestsocket.close();
			
		}catch(Exception err){	}

	}
	
	public List<String> fetchAnswer(String question){
		Socket requestsocket;
		PrintWriter out;
		BufferedReader in;
		String answer=null;
		List<String> ans=null;
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
		}
		return ans;
	}
	
	public void addQuestion(String question){
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
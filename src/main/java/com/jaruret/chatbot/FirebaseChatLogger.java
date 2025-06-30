package com.jaruret.chatbot;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import jakarta.annotation.PostConstruct;

@Service
public class FirebaseChatLogger {
	
	private final FirebaseApp firebaseApp;
	private DatabaseReference dbRef;
	public FirebaseChatLogger(FirebaseApp firebaseApp) {
		this.firebaseApp = firebaseApp;
	}
	
	
	
	
	 @PostConstruct
	 public void init() {
	        try {
	        	FirebaseDatabase database=FirebaseDatabase.getInstance(firebaseApp);
	       	 	dbRef=database.getReference("chatlog");
	            Map<String,Object> initLog=new HashMap<>();
	            initLog.put("Status", "Chatbot Started");
	            initLog.put("timestamp", System.currentTimeMillis());
	            dbRef.push().setValueAsync(initLog);
	        } catch (Exception e) {
	        	e.printStackTrace();
	            throw new IllegalStateException("Failed to connect to Firebase Realtime Database", e);
	        }
	    }
	
	public void logMessage(String from,String message,boolean isBot) {
		Map<String,Object> msgData=new HashMap<>();
		msgData.put("from",from);
		msgData.put("message",message);
		msgData.put("isBot",isBot);
		msgData.put("timestamp",System.currentTimeMillis());
	
		dbRef.push().setValueAsync(msgData);
	}
}

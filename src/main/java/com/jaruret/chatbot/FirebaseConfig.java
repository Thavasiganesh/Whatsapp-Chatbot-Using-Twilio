package com.jaruret.chatbot;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;

@Component
public class FirebaseConfig {
	
	@Bean
	public FirebaseApp init() throws IOException{
		try{
			InputStream inputStream;
			String firebaseConfig=System.getenv("FIREBASE_CONFIG_JSON_BASE64");
			if(firebaseConfig!=null && !firebaseConfig.isBlank()) {
				
				inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(firebaseConfig));
			}
			else {
				inputStream=getClass().getClassLoader().getResourceAsStream("firebase-service-account.json");
				if(inputStream==null) {
					throw new IllegalStateException("Local Firebase Config file not found");
				}
			}
			FirebaseOptions options=new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(inputStream)).
					setDatabaseUrl("https://jarurat-care-chatbot-default-rtdb.asia-southeast1.firebasedatabase.app/").build();
			if(FirebaseApp.getApps().isEmpty())
				return FirebaseApp.initializeApp(options);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Firebase initialization failed.");
		}
		System.out.println("Firebase initialized successfully.");
		return FirebaseApp.getInstance();
		
	}

}

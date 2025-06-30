package com.jaruret.chatbot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

@Service
public class WPService {

	@Value("${twilio.account-sid}")
	private String accSid;
	
	@Value("${twilio.auth-token}")
	private String authToken;
	
	@Value("${twilio.from-number}")
	private String fromNumber;
	
	public void sendMessage(String toNumber,String message) {
		Twilio.init(accSid, authToken);
		Message.creator(new com.twilio.type.PhoneNumber("whatsapp:"+toNumber),
				new com.twilio.type.PhoneNumber(fromNumber),
				message).create();
	}
}

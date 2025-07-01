package com.jaruret.chatbot;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.jaruret.chatbot.*;

@RestController
@RequestMapping("/whatsapp")
public class WhatsappController {
	
	@Autowired
	private WPService WpService;
	
	@Autowired
	private FirebaseChatLogger firebaseLogger;
	
	@PostMapping("/webhook")
	public void receiveMessage(@RequestParam Map<String,String> request) {
		String from=request.get("From").replace("whatsapp:","");
		String message=request.get("Body").trim().toLowerCase();
		
		firebaseLogger.logMessage("from", message, false);
		
		String reply;

        switch (message) {
            case "help":
                reply = "Here are some resources:\n1. Emotional: 1800-123-CARE\n2. Financial Aid: jaruratcare.org/aid\n3. Type 'connect' for a care navigator.";
                break;
            case "connect":
                reply = "We are connecting you to a care navigator now...";
                break;
            case "hi":
            case "hello":
                reply = "Hello! I'm JaruratBot. Type 'help' to get started.";
                break;
            default:
                reply = "Sorry, I didn’t understand that. Type 'help' to see available options.";
        }

        firebaseLogger.logMessage("bot", reply, true);
        
        WpService.sendMessage(from, reply);
		
	}

}

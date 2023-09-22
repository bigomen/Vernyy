package com.luxfacta.vernyy.api.push;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;

@Service
public class FCMService {

	
	public void sendNotification(Notification notification) {
		Message message = Message.builder()
            // Set the configuration for our web notification
           .setWebpushConfig(
               // Create and pass a WebpushConfig object setting the notification
               WebpushConfig.builder()
                   .setNotification(
                       // Create and pass a web notification object with the specified title, body, and icon URL 
                       WebpushNotification.builder()
	                       .setTitle(notification.getTitle())
	                       .setBody(notification.getMessage())
	                       .setIcon(notification.getIcon())
	                       .build()
                   ).build()
           )
           // Specify the user to send it to in the form of their token  
           .setToken(notification.getTarget())
           .build();
		
		FirebaseMessaging.getInstance().sendAsync(message);
       
	}

	public void sendNotificationTopic(Notification notification) {
		Message message = Message.builder()
            // Set the configuration for our web notification
           .setWebpushConfig(
               // Create and pass a WebpushConfig object setting the notification
               WebpushConfig.builder()
                   .setNotification(
                       // Create and pass a web notification object with the specified title, body, and icon URL 
                       WebpushNotification.builder()
                           .setTitle(notification.getTitle())
                           .setBody(notification.getMessage())
                           .setIcon(notification.getIcon())
                           .build()
                   ).build()
           )
            // Specify the user to send it to in the form of their token  
           .setTopic(notification.getTarget())
           .build();
       FirebaseMessaging.getInstance().sendAsync(message);
       
	}
	
	
	public void subscribeToTopic(List<String> token, String topic) throws FirebaseMessagingException{
	   FirebaseMessaging.getInstance().subscribeToTopic(token, topic);
	}
	
	public void subscribeToTopic(String token, String topic) throws FirebaseMessagingException{
		
		List<String> tokens = new ArrayList<>();
		tokens.add(token);
		subscribeToTopic(tokens, topic);

	}
}

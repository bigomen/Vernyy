package com.luxfacta.vernyy.api.push;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;


@Service
public class FirebaseInitializer {
	
	 @Autowired
	 private Environment env;
	
	@PostConstruct
	public void initialize(){
	   try {
		   
		   File f = new File(env.getProperty("app.firebase-config-file"));
		   
		   
		   FirebaseOptions options = FirebaseOptions.builder()
	               .setCredentials(GoogleCredentials.fromStream(new FileInputStream(f))).build();

		   if (FirebaseApp.getApps().isEmpty()) {
	           FirebaseApp.initializeApp(options);
	           
	       }
	   } catch (IOException e) {
		   e.printStackTrace();
	   }

	}
	
}

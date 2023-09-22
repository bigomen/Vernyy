package com.luxfacta.vernyy;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.luxfacta.vernyy.api.shared.CryptoUtils;


@SpringBootApplication
public class Vernyy {

    public static void main(String[] args) throws Exception {
		
		
		if ("RSA".equals(Mapper.CRYPTO_CYPHER)) {
			File privateKeyFile = new File ("private.key");
			if (!privateKeyFile.exists()) {
				KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
				generator.initialize(2048);
				KeyPair pair = generator.generateKeyPair();
				
				try (FileOutputStream outputStream = new FileOutputStream(privateKeyFile)) {
				    outputStream.write(pair.getPrivate().getEncoded());
				} 
				
				File publicKeyFile = new File ("public.key");
				try (FileOutputStream outputStream = new FileOutputStream(publicKeyFile)) {
				    outputStream.write(pair.getPublic().getEncoded());
				}
			}

			
			KeyFactory kf = KeyFactory.getInstance(Mapper.CRYPTO_CYPHER);
			Mapper.PRIVATE_KEY =  kf.generatePrivate(new PKCS8EncodedKeySpec(Files.readAllBytes(new File("private.key").toPath())));
			Mapper.PUBLIC_KEY = kf.generatePublic(new X509EncodedKeySpec(Files.readAllBytes(new File("public.key").toPath())));

		
		} else if ("Blowfish".equals(Mapper.CRYPTO_CYPHER)) {
			File blowfishFile = new File ("blowfish.key");
			if (!blowfishFile.exists()) {
				KeyGenerator keygenerator = KeyGenerator.getInstance("Blowfish");
				SecretKey secretkey = keygenerator.generateKey();

				try (FileOutputStream outputStream = new FileOutputStream(blowfishFile)) {
				    outputStream.write(secretkey.getEncoded());
				}
			}
			
			Mapper.SECRET = new SecretKeySpec(Files.readAllBytes(new File("blowfish.key").toPath()),"Blowfish");
			CryptoUtils.SECRET = Mapper.SECRET;
		}
    	
        SpringApplication.run(Vernyy.class, args);
    }
}

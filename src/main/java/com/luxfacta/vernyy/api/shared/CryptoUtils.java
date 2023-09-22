package com.luxfacta.vernyy.api.shared;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

public class CryptoUtils {
	
	public static SecretKeySpec SECRET = null;

	
	public static String encrypt(String param) throws Exception {
    	if (param == null)
    		return null;
	    	
        Cipher cipher = Cipher.getInstance("Blowfish");
    	cipher.init(Cipher.ENCRYPT_MODE, SECRET);
        
    	
        byte[] paramCrypto = param.getBytes(StandardCharsets.UTF_8);
        byte[] cryptoData = cipher.doFinal(paramCrypto);
        return Hex.encodeHexString(cryptoData);
    }
	
	

    public static String decrypt(String param) throws Exception {
        
        if (param == null)
            return null;
        
        Cipher cipher = Cipher.getInstance("Blowfish");
    	cipher.init(Cipher.DECRYPT_MODE, SECRET);

        byte[] cryptoData = Hex.decodeHex(param);
        return new String(cipher.doFinal(cryptoData));

        
    }
	
	
}

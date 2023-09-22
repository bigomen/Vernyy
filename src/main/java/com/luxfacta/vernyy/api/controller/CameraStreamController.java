package com.luxfacta.vernyy.api.controller;

import java.io.InputStream;
import java.net.URL;
import java.util.Optional;

import org.bouncycastle.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.luxfacta.vernyy.api.exception.BusinessRuleException;
import com.luxfacta.vernyy.api.exception.BusinessSecurityException;
import com.luxfacta.vernyy.api.model.Camera;
import com.luxfacta.vernyy.api.repository.CameraRepository;

@Controller
public class CameraStreamController {

	@Autowired
	private CameraRepository repository;

	@GetMapping(value = {"publico/v1/camera/video-feed/{token}/{id}"})
    public ResponseEntity<StreamingResponseBody> videoFeedStream (@PathVariable String token, @PathVariable String id) throws BusinessSecurityException, BusinessRuleException {

        Optional<Camera> optCam = repository.findById(Mapper.decryptIdIgnoreSecurity(Camera.class, id));
        
        if (optCam.isPresent()) {
			String ipCondominio = optCam.get().getArea().getCondominio().getIpExterno();
            
			StreamingResponseBody stream = out -> {
				InputStream in = null;
			    try {
			    	URL u = new URL(ipCondominio + ":8111/video-feed/" + token + "/" + id);
			    	in = u.openStream();
			    	byte[] b = new byte[1024];
			    	int c = 0;
			    	while((c = in.read(b)) != -1) {
			    		out.write(Arrays.copyOfRange(b, 0, c));
			    	}
			    	
			    } 
			    catch (Exception ex) {
			      ex.printStackTrace();
			    }
			    finally 
			    {
					try {
						if (in != null) 
							in.close();
					} catch (Exception e) {
					}
			    }
			};
			
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_TYPE, "multipart/x-mixed-replace; boundary=frame")
					.body(stream);
        }
        throw new BusinessRuleException("Camera nao disponivel");
        
	    
    }
}

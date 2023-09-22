package com.luxfacta.vernyy.api.shared;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Map;

import com.nimbusds.jose.shaded.gson.Gson;

public class ExternalAPI  {

    public static int sendData(String url, Map<String,Object> data) {
    	HttpRequest request = HttpRequest.newBuilder()
    			  .uri(URI.create(url))
    			  .POST(HttpRequest.BodyPublishers.ofString(getFormDataAsString(data)))
    			  .header("Content-Type", "application/json")
    			  .build();
    	
    	HttpResponse<String> response;
		try {
			
			response = HttpClient.newBuilder().build().send(request, BodyHandlers.ofString());
	    	return response.statusCode();
	    	
		} catch (Exception e) {
			return 0;
		}
    	
    	
    }
    
    public static String sendDataResponse(String url, Map<String,Object> data) {
    	HttpRequest request = HttpRequest.newBuilder()
    			  .uri(URI.create(url))
    			  .POST(HttpRequest.BodyPublishers.ofString(getFormDataAsString(data)))
    			  .header("Content-Type", "application/json")
    			  .build();
    	
    	HttpResponse<String> response;
		try {
			
			response = HttpClient.newBuilder().build().send(request, BodyHandlers.ofString());
	    	return response.body();
	    	
		} catch (Exception e) {
			return null;
		}
    	
    	
    }
    
    public static String getData(String url) {
    	HttpRequest request = HttpRequest.newBuilder()
    			  .uri(URI.create(url))
    			  .GET()
    			  .build();
    	
    	HttpResponse<String> response;
		try {
			
			response = HttpClient.newBuilder().build().send(request, BodyHandlers.ofString());
	    	return response.body();
	    	
		} catch (Exception e) {
			return null;
		}
    	
    	
    }

    private static String getFormDataAsString(Map<String, Object> inputMap) {
    	Gson gsonObj = new Gson();
        return gsonObj.toJson(inputMap);
    }
}
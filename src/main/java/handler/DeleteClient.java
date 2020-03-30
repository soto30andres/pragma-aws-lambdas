package handler;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import domain.ApiGatewayResponse;
import domain.RequestClass;
import domain.ResponseClass;
import model.Client;

public class DeleteClient implements RequestHandler<RequestClass, ApiGatewayResponse>{   
	

    private static final Logger logger = LogManager.getLogger(DeleteClient.class);
    DateFormat dfDateMedium = DateFormat.getDateInstance(DateFormat.MEDIUM);
    public ApiGatewayResponse handleRequest(RequestClass input, Context context){
    	
		
		Client client = new Client();
		Client clientReturn = new Client();
		ResponseClass responseBody = null;
		int statusCode = 200;
		Map<String, Object> data = new HashMap<String, Object>();
		try{
			//JsonNode body = new ObjectMapper().readTree((String) input.get("body"));
			boolean deleted = client.delete(input.getId().toString());
			data.put("deleted", deleted);
			responseBody = new ResponseClass("Se ha realizado la consulta con exito" , data);
		}catch(IOException e){
			logger.error("Ha ocurrido un error: ", e);
			responseBody = new ResponseClass("Error interno! ", null);
			statusCode = 500;
		}
		
		return ApiGatewayResponse.builder()
				.setStatusCode(statusCode)
				.setObjectBody(responseBody)
				.build();
	}
        
 }

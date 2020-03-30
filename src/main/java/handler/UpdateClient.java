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

public class UpdateClient implements RequestHandler<Map<String, Object>, ApiGatewayResponse>{   
	

    private static final Logger logger = LogManager.getLogger(UpdateClient.class);
    DateFormat dfDateMedium = DateFormat.getDateInstance(DateFormat.MEDIUM);
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context){
    	
		
		Client client = new Client();
		Client clientSave = new Client();
		ResponseClass responseBody = null;
		int statusCode = 200;
		Map<String, Object> data = new HashMap<String, Object>();
		try{
			//JsonNode body = new ObjectMapper().readTree((String) input.get("body"));
			
			clientSave.setId(input.get("id").toString());
			clientSave.setNombre(input.get("firstName").toString());
			clientSave.setApellidos(input.get("lastName").toString());
			clientSave.setCiudad(input.get("city").toString());
			clientSave.setNumeroIdentificacion(input.get("identificationNumber").toString());
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			clientSave.setTipoIdentificacion(input.get("identificationType").toString());
			clientSave.setFechaNacimiento(formato.parse(input.get("birthday").toString()));
			client.save(clientSave);
			data.put("data", clientSave);
			responseBody = new ResponseClass("Se ha actualizado el cliente con exito" , data);
		}catch(IOException e){
			logger.error("Ha ocurrido un error: ", e);
			responseBody = new ResponseClass("Error interno! ", null);
			statusCode = 500;
		} catch (ParseException e) {

			logger.error("Ha ocurrido un error: ", e);
			responseBody = new ResponseClass("Error interno! ", null);
			statusCode = 500;
			e.printStackTrace();
		}
		
		return ApiGatewayResponse.builder()
				.setStatusCode(statusCode)
				.setObjectBody(responseBody)
				.build();
	}
        
 }

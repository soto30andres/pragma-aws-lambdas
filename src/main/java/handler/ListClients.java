package handler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.dynamodbv2.datamodeling.marshallers.ObjectSetToStringSetMarshaller;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import domain.ApiGatewayResponse;
import domain.RequestClass;
import domain.ResponseClass;
import model.Client;

public class ListClients implements RequestHandler<RequestClass, ApiGatewayResponse>{   
	

    private static final Logger logger = LogManager.getLogger(ListClients.class);
	
    public ApiGatewayResponse handleRequest(RequestClass request, Context context){
        
		Client client = new Client();
		ResponseClass responseBody = null;
		int statusCode = 200;
		try{
			logger.info("Consultando...");
			Map<String, Object> data = new HashMap<String, Object>();
			List<Client> clientsList = null;
			Map<String, AttributeValue> filters = null;
			if( (!Objects.isNull(request.getIdentificationType()) && !request.getIdentificationType().isEmpty()) 
					&& !Objects.isNull(request.getIdentificationNumber()) && !request.getIdentificationNumber().isEmpty()) {
				
				filters = createFilterByIdentificationTypeAndIdentificationNumber(request.getIdentificationType(), request.getIdentificationNumber());
				
				clientsList = client.listByIdentificationTypeAndIdentificationNumber(filters);
				
			}else if(!Objects.isNull(request.getGreaterOrEqual()) 
					&& (request.getGreaterOrEqual()>0)){
				
				filters = calculateParametersYearsAgo(request.getGreaterOrEqual());
				
				clientsList = client.listByAgeOlderThan(filters);
				
			}else {
				
				clientsList = client.list();
			}
			logger.info("Lista de clientes: {}", clientsList);
			data.put("data", clientsList);
			responseBody = new ResponseClass("El servicio se ha ejecutado satisfactoriamente", data);
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
    
    private Map<String, AttributeValue> createFilterByIdentificationTypeAndIdentificationNumber(String identificationType, String number){

        Map<String, AttributeValue> filters = new HashMap<String, AttributeValue>();
        filters.put(":it", new AttributeValue().withS(identificationType));
        filters.put(":in", new AttributeValue().withS(number));
        
        return filters;
    	
    }
    
    private Map<String, AttributeValue> calculateParametersYearsAgo(int years) {

    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    	
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(Calendar.HOUR_OF_DAY, 0);
    	calendar.set(Calendar.MINUTE, 0);
    	calendar.set(Calendar.SECOND, 0);
    	calendar.add(Calendar.YEAR, -years);
    	String yearsAgo = df.format(calendar.getTime());
    	
    	Map<String, AttributeValue> filters = new HashMap<String, AttributeValue>();
        filters.put(":dt", new AttributeValue().withS(yearsAgo));
    	
    	return filters;
    	
    }
        
 }

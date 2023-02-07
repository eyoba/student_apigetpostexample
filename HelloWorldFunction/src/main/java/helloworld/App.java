package helloworld;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.lambda.runtime.events.CloudFrontEvent;

/**
 * Handler for requests to Lambda function.
 */
public class App {

    public static Object handleRequest(Request request, final Context context) {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
        DynamoDBMapper mapper = new DynamoDBMapper(client);
        Student student = null;
        student = mapper.load(Student.class, request.getId());
        if (student == null)
            throw new ResourceNotFoundException("Reouce with the follwoing id not found=" + request.getId());
        return student;
    }

    public static Object handleRespond(Request request, final Context context) {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
        DynamoDBMapper mapper = new DynamoDBMapper(client);
        Student student = null;
        student = request.getStudent();
        mapper.save(student);
        return student;
    }


}

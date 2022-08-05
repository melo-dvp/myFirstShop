package com.ates.myfirstshop.authentication;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class CognitoHandler implements RequestHandler<Request, Response> {

    @Override
    public Response handleRequest(Request request, Context context){
        return new CognitoService().authentication(request);
    }
}

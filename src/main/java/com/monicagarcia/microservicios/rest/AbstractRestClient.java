package com.monicagarcia.microservicios.rest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
 

public class AbstractRestClient {
    private String url;
    private String contextPath;
 
   
    public AbstractRestClient(String url, String contextPath) {
        this.url = url;
        this.contextPath = contextPath;
    }
 
    protected WebTarget createClient(String path) {
        String assembledPath = assembleEndpoint(path);
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(assembledPath);
        return target;
    }
 
    private String assembleEndpoint(String path) {
        String endpoint = url.concat(contextPath).concat(path);
        return endpoint;
    }
}

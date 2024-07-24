package com.mx.zorktec.backForTolucaLaBellaPage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
//@EnableEurekaClient
@EnableTransactionManagement
public class BackForTolucaLaBellaPageApplication 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(BackForTolucaLaBellaPageApplication.class, args);
    }
}

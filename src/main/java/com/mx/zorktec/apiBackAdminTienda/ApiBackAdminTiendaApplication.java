package com.mx.zorktec.apiBackAdminTienda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableEurekaClient
public class ApiBackAdminTiendaApplication 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(ApiBackAdminTiendaApplication.class, args);
    }
}

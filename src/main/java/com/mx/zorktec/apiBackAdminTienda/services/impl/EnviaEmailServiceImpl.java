package com.mx.zorktec.apiBackAdminTienda.services.impl;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mx.zorktec.apiBackAdminTienda.entities.vo.EmailVo;
import com.mx.zorktec.apiBackAdminTienda.services.EnviaEmailService;

@Service
public class EnviaEmailServiceImpl implements EnviaEmailService{

	private static final Logger LOG = LogManager.getLogger(EnviaEmailServiceImpl.class);
	
	@Value("${spring.correo.host}")
	private String host;
	
	@Value("${spring.correo.usuario}")
	private String usuario;
	
	@Value("${spring.correo.pass}")
	private String pass;
	
	@Value("${spring.correo.link.valida.pass}")
	private String link;
	
	@Override
	public void enviarEmail(String email) {
		
		EmailVo m = new EmailVo();
		m.setRemitente("mail.zorktech.com.mx");
		m.setDestinatario(email);
		m.setSubject("VERIFICACION DE EMAIL");
		m.setBody(this.getBodyEmail(email));
		
		LOG.info("EL mail es: "+m.toString());
		
		Properties props = new Properties(); 
		props.put("mail.smtp.host",host);  
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.trust", m.getRemitente());  
		
		
		 Session session = Session.getInstance(props,  
				 new javax.mail.Authenticator() {  
				 	protected PasswordAuthentication getPasswordAuthentication() {  
				 		return new PasswordAuthentication(usuario,pass);  
				 	}  
				  });
		 
		 try {
			 
			 MimeMessage message = new MimeMessage(session);  
		     message.setFrom(new InternetAddress(usuario));  
		     message.addRecipient(Message.RecipientType.TO,new InternetAddress(m.getDestinatario()));  
		     message.setSubject(m.getSubject());  
		     message.setText(m.getBody());  
		       
		    //send the message  
		     Transport.send(message);  
		  
		     LOG.info("message sent successfully...");  
		 }catch(MessagingException e) {
			 LOG.error("Error enviando correo: "+ e.getLocalizedMessage());
		 }
		 
		 
	}
	
	private String getBodyEmail(String email) {
		StringBuilder str = new StringBuilder();
		str.append("Este correo es para que verifique su cuenta");
		str.append("\n");
		str.append("Favor de ingresar el siguiente código para crear su contraseña");
		str.append("\n");
		str.append(this.generarCodigo(email));
		return str.toString();
	}
	

	private String generarCodigo(String email) {
		int leftLimit = 97; // letter 'a'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 10;
	    Random random = new Random();

	    String generatedString = random.ints(leftLimit, rightLimit + 1)
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();
		 LOG.info("Code: "+generatedString);
		//SETEAR EL CODIGO EN EL USUARIO MEDIANTE EL EMAIL EN EL DAO JUNTO CON LA DATETIME DE CREACION
		return generatedString;
	}

}

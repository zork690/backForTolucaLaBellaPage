package com.mx.zorktec.apiBackAdminTienda;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RequestHttpWrapper extends HttpServletRequestWrapper{
	
	private static final Logger LOG = LogManager.getLogger(RequestHttpWrapper.class);
	private final String body;
	
	public RequestHttpWrapper(HttpServletRequest request) {
		super(request);
		StringBuilder stringBuilder = new StringBuilder();
	    BufferedReader bufferedReader = null;

	    try {
	        InputStream inputStream = request.getInputStream();

	        if (inputStream != null) {
	            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

	            char[] charBuffer = new char[128];
	            int bytesRead = -1;

	            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
	                stringBuilder.append(charBuffer, 0, bytesRead);
	            }
	        } else {
	            stringBuilder.append("");
	        }
	    } catch (IOException ex) {
	    	LOG.error("ERROR LEYENDO EL REQUEST BODY...");
	    } finally {
	        if (bufferedReader != null) {
	            try {
	                bufferedReader.close();
	            } catch (IOException ex) {
	            	LOG.error("ERROR CERRANDO EL BUFFEREDREADER...");
	            }
	        }
	    }

	    body = stringBuilder.toString();
	}
	
	@Override
	public ServletInputStream getInputStream () {
	    final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());

	    ServletInputStream inputStream = new ServletInputStream() {
	        @Override
	        public boolean isFinished() {
	            return false;
	        }

	        @Override
	        public boolean isReady() {
	            return false;
	        }

	        @Override
	        public void setReadListener(ReadListener readListener) {

	        }

	        public int read () throws IOException {
	            return byteArrayInputStream.read();
	        }
	    };

	    return inputStream;
	}
	
	@Override
	public BufferedReader getReader(){
	    return new BufferedReader(new InputStreamReader(this.getInputStream()));
	}

}

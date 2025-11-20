package com.yschoi.demoapp.common;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

/**
* @packageName   : com.yschoi.demoapp.common
* @fileName      : RequestWrapper.java
* @author        : YS CHOI
* @date          : 2025.11.20
* @description   : 요청 Body 캐싱을 위한 Wrapper
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2025.11.20        YS CHOI       최초 생성
*/

public class RequestWrapper extends HttpServletRequestWrapper {
	
	private final String cachedBody;

	public RequestWrapper(HttpServletRequest request) {
		super(request);
		
		StringBuilder stringBuilder = new StringBuilder();
		try (BufferedReader bufferedReader = request.getReader()) {
			char[] charBuffer = new char[128];
			int bytesRead;
			while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
				stringBuilder.append(charBuffer, 0, bytesRead);
			}
		} catch (IOException e) {
			// 예외발생 시 stringBuilder 초기화
			stringBuilder.delete(0, stringBuilder.length());
		}
		
        // 모든 공백·줄바꿈 제거
        this.cachedBody = stringBuilder.toString().replaceAll("\\s+", "");
	}
	
	@Override
	public ServletInputStream getInputStream() {
		final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.cachedBody.getBytes());
		return new ServletInputStream() {
			
			@Override
			public int read() throws IOException {
				return byteArrayInputStream.read();
			}
			
			@Override
			public void setReadListener(ReadListener listener) {
				throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
				
			}
			
			@Override
			public boolean isReady() {
				return false;
			}
			
			@Override
			public boolean isFinished() {
				return false;
			}
		};
	}

	@Override
	public BufferedReader getReader() {
		return new BufferedReader(new InputStreamReader(this.getInputStream()));
	}
	
	public String getCachedBody() {
		return this.cachedBody;
	}
}
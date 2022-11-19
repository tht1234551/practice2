package com.tourbest.erp.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

/**
 * io 관련 util class
 * @author
 *
 */
public class CmmIOUtil {

	public static final String CHARSET = "UTF-8";
	public static final int URL_INPUT_STREAM = 0;
	public static final int FILE_INPUT_STREAM = 1;
	public static final int HTTPS_URL_INPUT_STREAM = 2;

	/**
	 * 파일 또는 URL을 읽어 String으로 리턴
	 * @param path
	 * @param OPTION
	 * @return
	 */
	public static String getPageToString(String path, int OPTION, int timeout){
		String data = "";

		try {
			data = getInputStreamToString(getInputStream(path, OPTION, timeout));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

	/**
	 * 파일 또는 URL을 읽어 inputStream을 리턴
	 * @param path
	 * @param OPTION
	 * @return
	 */
	public static InputStream getInputStream(String path, int OPTION, int timeout){
		InputStream inputStream = null;

		try{
			if(OPTION == URL_INPUT_STREAM){
				URL url = new URL(path.replaceAll(" ", ""));
				URLConnection urlConnection = url.openConnection();
				urlConnection.setConnectTimeout(timeout);
				urlConnection.setReadTimeout(timeout);
				inputStream = urlConnection.getInputStream();

			}else if(OPTION == FILE_INPUT_STREAM){
			    File fileName = new File(path);
			    inputStream = new FileInputStream(fileName);
			}else if(OPTION == HTTPS_URL_INPUT_STREAM){
				URL url = new URL(path.replaceAll(" ", ""));
				HttpsURLConnection urlConnection = (HttpsURLConnection)url.openConnection();
				urlConnection.setConnectTimeout(timeout);
				urlConnection.setReadTimeout(timeout);
				urlConnection.setHostnameVerifier(new HostnameVerifier() {
					@Override
					public boolean verify(String hostname, SSLSession session) {
						// 항상 리턴 true
						return true;
					}
				});

				// SSL setting
				SSLContext context = SSLContext.getInstance("TLS");
				context.init(null, null, null);

				urlConnection.setSSLSocketFactory(context.getSocketFactory());

				// Connect to host
				urlConnection.connect();
				urlConnection.setInstanceFollowRedirects(true);

				inputStream = urlConnection.getInputStream();
			}

		}catch (ConnectException e) {
			e.printStackTrace();
		}catch (UnknownHostException e) {
			e.printStackTrace();
		}catch (MalformedURLException e) {
			e.printStackTrace();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}

		return inputStream;
	}

	/**
	 * InputStream을 받아 String 으로 리턴
	 * @param host
	 * @return
	 */
	public static String getInputStreamToString(InputStream inputStream) throws Exception{
		String data = "";

		StringBuffer stringBuffer = new StringBuffer();
		String inputLine;
		String line = System.getProperty("line.separator");   // 개행처리  2018-09-28 신문섭
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, CHARSET));
		while((inputLine = br.readLine()) != null) {
			if(inputLine.indexOf("/jquery-") > -1 && inputLine.indexOf("<script") > -1) {  // jquery 스크립트가 아닌경우
			}else
				stringBuffer.append(inputLine).append(line);
		}
		data = stringBuffer.toString();

		return data;
	}

	/**
	 * String을 받아 InputStream 으로 리턴
	 * @param host
	 * @return
	 */
	public static InputStream getStringToInputStream(String data, String charSet) throws Exception{

		InputStream inputStream = null;
		inputStream = new ByteArrayInputStream(data.getBytes(StringUtil.isEmpty(charSet) ? CHARSET : charSet));

		return inputStream;
	}

	/**
	 * 기본 물리 path와  파일 위치 URL을  비교하여 합쳐줌
	 */
	public static String getConcast(String filePath, String fileUrl) {
		String path = filePath;
		String url = fileUrl;
		String mode = "\\";   // 윈도우 파일 시스템
		if(path.indexOf("/") > -1) mode = "/";  // 리눅스 파일 시스템
		if(url.indexOf("static") > -1) {
			url = url.replaceAll("static", "") ;
			url = url.replaceAll("//", "/") ;
		}
		if(path.substring(path.length()-1, path.length()) == mode && url.substring(0, 1) == mode) {
			path = path.substring(0, path.length()-1) + url  ;
		}else if(path.substring(path.length()-1, path.length()) != mode && url.substring(0, 1) != mode) {
			path += mode + url  ;
		}else {
			path += url  ;
		}
		return path;
	};

}
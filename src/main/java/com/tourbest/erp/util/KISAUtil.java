package com.tourbest.erp.util;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tourbest.erp.common.ServerProfiles;


/**
 * KISA SEED ECB, KISA_SHA256 암호화 처리 
 *
 * </pre>
 */
@Service("kisaUtil")
public class KISAUtil {
	
	private static final Logger log = LoggerFactory.getLogger(KISAUtil.class);
	
	@Autowired
	private ServerProfiles serverProfiles;
	
	//********************************************************************************************[ SHA256 암호화 ]
	/**
     * KISA 권장 단반향 SHA256 암호화  :: 키사제공 암호문 사용
     * @param String 암호화 할 값
     * @return String 암호화 된 값
     */
	public String getEncriptForSHA256(String inputStr) throws UnsupportedEncodingException{

		String rtnResultStr = "";

		//when empty!!
		if(inputStr.equals("") || inputStr == null){
			inputStr = "tourbest.erp1.0";
		}

		try{

			byte inputDate[] =  inputStr.getBytes("UTF-8");
			int inputDateLen = inputStr.length();
			byte outResult[] = new byte[32];

			KISA_SHA256.SHA256_Encrpyt(inputDate,inputDateLen,outResult);

			for (int i = 0; i < 32; i++) {
				rtnResultStr += Integer.toHexString(0xff & outResult[i]);
			}
			log.info("암호화 결과 : "+inputStr+" / "+rtnResultStr);

		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return rtnResultStr;
	}
	
	
		
	//********************************************************************************************[  KISA SEED ECB 암호화 ]
	public String getEncordForSEEDECB(String inStr) throws UnsupportedEncodingException {    //  <== encorder
		if(StringUtil.isEmpty(inStr)) { inStr = "ERP1.0"; }
		String enCode = encodeSeed(inStr);
		log.info("enCode = " + enCode);
		String enCodeBs64 = encodeBase64String(enCode.getBytes()) ;
		log.info("enCodeBs64 = " + enCodeBs64);
		return enCodeBs64;
	};
	
	public String getDecordForSEEDECB(String inStr) throws UnsupportedEncodingException {   //  <== decorder
		if(StringUtil.isEmpty(inStr)) return "";
		
		String deCodeBs64 = decodeBase64String(inStr) ;
		log.info("deCodeBs64 = " + deCodeBs64);
		String deCode = decodeSeed(deCodeBs64);
		log.info("deCode = " + deCode);
		
		return deCode;
	};
	//-------------------------------------------------------------------------------------------
	
	/**
	 * 베이스64 인코딩 처리
	 * @param content 인코딩할 문자열
	 * @return 인코딩된 문자열
	 */
	public String encodeBase64String(byte[] content) {
	    Encoder encoder = Base64.getEncoder();
	    byte[] encodedBytes = encoder.encode(content);
	    String str = "";
	    try {
	        str = new String(encodedBytes, "UTF-8");
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    }
	    return str;
	}
	
	/**
	 * 베이스64 인코딩 처리
	 * @param content 인코딩할 문자열
	 * @return 인코딩된 바이트배열
	 */
	public byte[] encodeBase64Byte(byte[] content) {
	    Encoder encoder = Base64.getEncoder();
	    byte[] encodedBytes = encoder.encode(content);
	    
	    return encodedBytes;
	}
	
	/**
	 * 베이스64 디코딩 처리
	 * @param encodedContent 인코딩된 문자열
	 * @return 디코딩으로 원복된 문자열
	 */
	public String decodeBase64String(String encodedContent) {
	    Decoder decoder = Base64.getDecoder();
	    byte[] decodedBytes = decoder.decode(encodedContent);
	    String str = "";
	    try {
	     str = new String(decodedBytes, "UTF-8");
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    }
	    return str;
	}
	
	/**
	 * 베이스64 디코딩 처리
	 * @param encodedContent 인코딩된 바이트배열
	 * @return 디코딩으로 원복된 문자열
	 */
	public String decodeBase64Byte(byte[] encodedContent) {
	    Decoder decoder = Base64.getDecoder();
	    byte[] decodedBytes = decoder.decode(encodedContent);
	    String str = "";
	    try {
	     str = new String(decodedBytes, "UTF-8");
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        }
	        return str;
	    }
	    
	 
	    /**
	 * SEED_ECB_Encrypt(사용자가 지정하는 SEED KEY 16byte, 사용자가 입력한 평문 데이터의 Byte 값, 사용자가 입력한 평문 데이터의  시작 오프셋 0부터 시작, 사용자가 입력한 평문 데이터의 Byte값에 대한 길이)
	 * @param param
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String encodeSeed(String param) throws UnsupportedEncodingException{
			//User secret key
			  byte pbUserKey[]  = {(byte)0x88, (byte)0xE3, (byte)0x4F, (byte)0x8F, (byte)0x08, (byte)0x17, (byte)0x79, (byte)0xF1,
					  				(byte)0xE9, (byte)0xF3, (byte)0x94, (byte)0x37, (byte)0x0A, (byte)0xD4, (byte)0x05, (byte)0x89};
			  byte pbData[]     = {(byte)0xD7, (byte)0x6D, (byte)0x0D, (byte)0x18, (byte)0x32, (byte)0x7E, (byte)0xC5, (byte)0x62, 
					  				(byte)0xB1, (byte)0x5E, (byte)0x6B, (byte)0xC3, (byte)0x65, (byte)0xAC, (byte)0x0C, (byte)0x0F};
			  byte pbCipher[]   = new byte[100];
			  byte pbPlain[]    = new byte[16];
	       
			  KISA_SEED_ECB seed = new KISA_SEED_ECB();
  
			  String charset = "UTF-8";
			  String key = "tob.erp.1.0" + serverProfiles.getKisaSeedEcbKey() + "1207";  //SINBOMUL@GMAIL.COM
			  try{
				  pbUserKey = key.getBytes(charset);
			  }catch (UnsupportedEncodingException e) {
				  throw e;
			  }
	  
			  //SEED_ECB_Encrypt(사용자가 지정하는 SEED KEY 16byte, 사용자가 입력한 평문 데이터의 Byte 값, 사용자가 입력한 평문 데이터의  시작 오프셋 0부터 시작, 사용자가 입력한 평문 데이터의 Byte값에 대한 길이)
			  pbCipher = seed.SEED_ECB_Encrypt(pbUserKey, param.getBytes(charset), 0, param.getBytes(charset).length);
		 
			  String encstr = "";
			  Encoder encoder = Base64.getEncoder();
			  byte[] encCipher = encoder.encode(pbCipher);
			  try {
				  encstr = new String(encCipher, charset);
			  } catch (UnsupportedEncodingException e) {
				  throw e;
			  }
		    
			  return encstr;
	};
	
	/**
	 * SEED_ECB_Encrypt(사용자가 지정하는 SEED KEY 16byte, 사용자가 입력한 평문 데이터의 Byte 값, 사용자가 입력한 평문 데이터의  시작 오프셋 0부터 시작, 사용자가 입력한 평문 데이터의 Byte값에 대한 길이)
	 * @param param
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String decodeSeed(String encParam) throws UnsupportedEncodingException{
	 // User secret key
	    	byte pbUserKey[]  = {(byte)0x88, (byte)0xE3, (byte)0x4F, (byte)0x8F, (byte)0x08, (byte)0x17, (byte)0x79, (byte)0xF1,
	    					(byte)0xE9, (byte)0xF3, (byte)0x94, (byte)0x37, (byte)0x0A, (byte)0xD4, (byte)0x05, (byte)0x89};
	    	byte pbData[]     = {(byte)0xD7, (byte)0x6D, (byte)0x0D, (byte)0x18, (byte)0x32, (byte)0x7E, (byte)0xC5, (byte)0x62, 
	                   		(byte)0xB1, (byte)0x5E, (byte)0x6B, (byte)0xC3, (byte)0x65, (byte)0xAC, (byte)0x0C, (byte)0x0F};
	    	byte pbCipher[]   = new byte[100];
	    	byte pbPlain[]    = new byte[16];
	       
	    	KISA_SEED_ECB seed = new KISA_SEED_ECB();
	  
	    	String charset = "UTF-8";
	    	String key = "tob.erp.1.0" + serverProfiles.getKisaSeedEcbKey() + "1207";  //SINBOMUL@GMAIL.COM
	    	try{
	    		pbUserKey = key.getBytes(charset);
	    	}catch (UnsupportedEncodingException e) {
	    		throw e;
	    	}
	  
	  		Decoder decoder = Base64.getDecoder();
	        byte[] decCipher = decoder.decode(encParam);
	  
	        //SEED_ECB_Decrypt(사용자가 지정하는 SEED KEY 16byte, 사용자가 입력한 평문 데이터의 암호화된 Byte 값, 사용자가 입력한 평문 데이터의  시작 오프셋 0부터 시작, 사용자가 입력한 평문 데이터 암호화된 Byte값에 대한 길이)
		    pbPlain = seed.SEED_ECB_Decrypt(pbUserKey, decCipher, 0, decCipher.length);
		 
		    String decstr = "";
		    try {
		    	decstr = new String(pbPlain, charset);
		    } catch (UnsupportedEncodingException e) {
		    	throw e;
		    }
		    
		    return decstr;
	};
	
	
};


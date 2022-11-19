package com.tourbest.erp.common;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Class Name : DataBox
 * @Description : DB에서 select된 data를 관리 라이브러리
 *
 * @author
 * @since 2014. 9. 26.
 */
public class DataBox extends HashMap<String, Object> {

	/** SerialVersionUID*/
	private static final long serialVersionUID = 1L;

	/** 로거*/
	private static final Logger logger = LoggerFactory.getLogger(DataBox.class);

	/** 객체명*/
	private String name = null;



	/**
	 * 생성자
	 */
	public DataBox(){

		super();
	}

	/**
	 * 생성자
	 * @param name
	 */
	public DataBox(String name) {

		super();
		this.name = name;
	}



	/**
	 * 객체에 담긴 param value 의 String 타입으로 반환
	 * @param key
	 * @return
	 */
	public String get(String key) {

		return getString(key);
	}

	/**
	 * box 객체에 담긴 param value 의 boolean 타입으로 반환
	 * @param key
	 * @return
	 */
	public boolean getBoolean(String key) {

		String value = getString(key);
		boolean isTrue = false;
		try {

			isTrue = (new Boolean(value)).booleanValue();
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return isTrue;
	}

	/**
	 * 객체에 담긴 param value 의 double 타입으로 반환
	 * @param key
	 * @return
	 */
	public double getDouble(String key) {

		String value = removeComma(getString(key));
		if ( value.equals("") ) return 0;
		double num = 0;
		try {
			num = Double.valueOf(value).doubleValue();
		}catch(Exception e) {
			num = 0;
			logger.error(e.getMessage());
		}
		return num;
	}

	/**
	 * 객체에 담긴 param value 의 float 타입으로 반환
	 * @param key
	 * @return
	 */
	public float getFloat(String key) {

		return (float)getDouble(key);
	}

	/**
	 * 객체에 담긴 param value 의 int 타입으로 반환
	 * @param key
	 * @return
	 */
	public int getInt(String key) {

		double value = getDouble(key);
		return (int)value;
	}

	/**
	 * box 객체에 담긴 param value 의 int 타입으로 반환
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public int getInt(String key, int defaultValue) {

		double value = getDouble(key);
		if((int)value == 0){
			return defaultValue;
		}else{
			return (int)value;
		}
	}

	/**
	 * box 객체에 담긴 param value 의 long 타입으로 반환
	 * @param key
	 * @return
	 */
	public long getLong(String key) {

		String value = removeComma(getString(key));
		if(value.equals("")){
			return 0L;
		}

		long lvalue = 0L;
		try {
			lvalue = Long.valueOf(value).longValue();
		}catch(Exception e) {
			lvalue = 0L;
			logger.error(e.getMessage());
		}
		return lvalue;
	}

	/**
	 * box 객체에 담긴 param value 의 String 타입으로 반환
	 * @param key
	 * @return
	 */
	public String getString(String key) {

		String value = null;
		try {

			Object o = super.get(key);
			if(o != null) {
				Class<? extends Object> c = o.getClass();

				if(c.isArray()) {
					int length = Array.getLength(o);
					if ( length == 0 ){
						value = "";
					}else {
						Object item = Array.get(o, 0);
						if ( item == null ) value = "";
						else value = item.toString();
					}
				}else{
					value = o.toString();
				}

			}else{
				value="";
			}
		}catch(Exception e) {
			value = "";
			logger.error(e.getMessage());
		}
		return value;
	}

	/**
	 * box 객체에 담긴 param value 의 String 타입으로 반환
	 * @param key
	 * @return
	 */
	public Object getObject(String key) {

		Object value = null;
		try {
			value = (Object)super.get(key);
		}catch(Exception e) {
			value = null;
			logger.error(e.getMessage());
		}
		return value;
	}

	/**
	 * box 객체에 담긴 parameter value 의 String 타입으로 반환
	 * @param key
	 * @param defstr
	 * @return
	 */
	public String getString(String key, String defstr) {

		return (getString(key).equals("") ? defstr : getString(key));
	}

	/**
	 * 날짜 데이터에서 구분을 제거하고 반환
	 * @param key 키
	 */
	public String getDate( String key ) {

		return getDate( key, "" );
	}

	/**
	 * 날짜 데이터를 구분을 추가하여 반환
	 * @param key 키
	 * @param gubun 날짜패턴
	 * @return
	 */
	public String getDate( String key, String gubun ) {

		return getDate(key, gubun, "");
	}

	/**
	 * 날짜 데이터를 구분을 추가하여 반환
	 * @param key 키
	 * @param gubun 날짜패턴
	 * @return
	 */
	public String getDate( String key, String gubun, String temp ) {

		if("".equals(key) || getString( key ).equals("")){
			return temp;
		}

		String value = getString( key ).replaceAll("[-\\./]","");
		if( "".equals( gubun ) ) {
			return value;
		}else{

			if(value.length() < 8){
				return StringManager.formatDate( value, gubun );
			}else{
				return StringManager.formatDate( StringManager.substring(value, 0, 8), gubun );
			}
		}
	}

	/**
	 * 콤마를 제거
	 * @param str 대상문자열
	 * @return
	 */
	private static String removeComma(String str) {

		if(str != null){
			if(str.indexOf(",") != -1 ) {
				StringBuffer buf = new StringBuffer();
				for(int i=0;i<str.length();i++){
					char c = str.charAt(i);
					if(c != ',') buf.append(c);
				}
				return buf.toString();
			}
		}
		return str;
	}


	/**
	 * 객체 값을 출력
	 * @return
	 */
	@Override
	public String toString() {

		String key = "";
		Object obj = "";
		StringBuffer sb = new StringBuffer();
		Iterator<String> iter = this.keySet().iterator();
		List<String> list = new ArrayList<String>();

		while (iter.hasNext()) {
			list.add(iter.next());
		}

		Collections.sort(list);
		sb.append("DataBox [name=" + name + "]\n{\n");
		if(list != null){
			for(int i = 0; i < list.size(); i++){
				key = (String) list.get(i);
				obj = this.get(key);
				sb.append(key + "="+obj);
				sb.append(",\n");
			}
		}
		sb.append("}");
		return sb.toString();
	}



}

package com.tourbest.erp.common;

import java.lang.reflect.Array;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Class Name : RequestBox
 * @Description : request 에서 넘어오는 파라미터와 session 객체를 Hashtable 인 box 객체에 담아 관리
 *
 * @author
 * @since 2014. 9. 30.
 */
public class RequestBox extends Hashtable<Object, Object> {
	/**서블릿 UID*/
	private static final long serialVersionUID = -7729129710680680206L;

	/**객체명*/
	protected String name = null;

	/**
	 * 생성자
	 * @param name
	 */
	public RequestBox(String name) {
		super();
		this.name = name;
	}

	/**
	 * box 객체에 담긴 parameter value 의 String 타입을 얻는다.
	 * @param key
	 * @return
	 */
	public String get(String key) {
		return this.getString(key);
	}

	/**
	 * box 객체에 담긴 parameter value 의 boolean 타입을 얻는다.
	 * @param key
	 * @return
	 */
	public boolean getBoolean(String key) {
		String value = this.getString(key);
		boolean isTrue = false;
		try {
			isTrue = (new Boolean(value)).booleanValue();
		}
		catch(Exception e){
			ErrorManager.getErrorStackTrace(e);
		}
		return isTrue;
	}

	/**
	 * boolean 타입으로 클래스 변경
	 * @param key
	 * @return
	 */
	public boolean castBoolean(String key) {
		boolean result = false;
		try {
			boolean val = this.getBoolean(key);
			this.put(key, val);
			result = true;
		} catch (Exception e) {
			result = false;
			ErrorManager.getErrorStackTrace(e);
		}
		return result;
	}

	/**
	 * box 객체에 담긴 parameter value 의 double 타입을 얻는다.
	 * @param key
	 * @return
	 */
	public double getDouble(String key) {
		String value = removeComma(this.getString(key));
		if ( value == null || value.equals("") ) return 0;
		double num = 0;
		try {
			num = Double.valueOf(value).doubleValue();
		}
		catch(Exception e) {
			num = 0;
			ErrorManager.getErrorStackTrace(e);
		}
		return num;
	}

	/**
	 * Double 타입으로 클래스 변경
	 * @param key
	 * @return
	 */
	public boolean castDouble(String key) {
		boolean result = false;
		try {
			Double val = this.getDouble(key);
			this.put(key, val);
			result = true;
		} catch (Exception e) {
			result = false;
			ErrorManager.getErrorStackTrace(e);
		}
		return result;
	}

	/**
	 * box 객체에 담긴 parameter value 의 float 타입을 얻는다.
	 * @param key
	 * @return
	 */
	public float getFloat(String key) {
		return (float)getDouble(key);
	}

	/**
	 * Float 타입으로 클래스 변경
	 * @param key
	 * @return
	 */
	public boolean castFloat(String key) {
		boolean result = false;
		try {
			float val = this.getFloat(key);
			this.put(key, val);
			result = true;
		} catch (Exception e) {
			result = false;
			ErrorManager.getErrorStackTrace(e);
		}
		return result;
	}

	/**
	 * box 객체에 담긴 parameter value 의 int 타입을 얻는다.
	 * @param key
	 * @return
	 */
	public int getInt(String key) {
		double value = getDouble(key);
		return (int)value;
	}

	/**
	 * box 객체에 담긴 parameter value 의 int 타입을 얻는다.<br/>
	 * value 값이 0 일 경우에 기본 값을 반환한다.
	 * @param key parameter key
	 * @param defaultValue
	 * @return int value 를 반환한다.
	 */
	public int getInt(String key, int defaultValue) {
		double value = getDouble(key);

		if( (int)value == 0 ){
			return defaultValue;

		}else{
			return (int)value;
		}
	}

	/**
	 * int 타입으로 클래스 변경
	 * @param key
	 * @return
	 */
	public boolean castInt(String key) {
		boolean result = false;
		try {
			int val = this.getInt(key);
			this.put(key, val);
			result = true;
		} catch (Exception e) {
			result = false;
			ErrorManager.getErrorStackTrace(e);
		}
		return result;
	}

	/**
	 * box 객체에 담긴 parameter value 의 long 타입을 얻는다.
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
			ErrorManager.getErrorStackTrace(e);
		}
		return lvalue;
	}

	/**
	 * long 타입으로 클래스 변경
	 * @param key
	 * @return
	 */
	public boolean castLong(String key) {
		boolean result = false;
		try {
			long val = this.getLong(key);
			this.put(key, val);
			result = true;
		} catch (Exception e) {
			result = false;
			ErrorManager.getErrorStackTrace(e);
		}
		return result;
	}

	/**
	 * box 객체에 담긴 parameter value 의 String 타입을 얻는다.
	 * @param key
	 * @return
	 */
    public String getString(String key) {
        String value = null;
        try {
            Object o = (Object)super.get(key);
            Class c = o.getClass();
            if ( o == null ) {
                value = "";
            }
            else if( c.isArray() ) {
                int length = Array.getLength(o);
                if ( length == 0 ) value = "";
                else {
                    Object item = Array.get(o, 0);
                    if ( item == null ) value = "";
                    else value = item.toString();
                }
            }
            else {
                value = o.toString();
            }
        }
        catch(Exception e) {
        value = "";
        }
        return value;
    }


	/**
	 * long 타입으로 클래스 변경
	 * @param key
	 * @return
	 */
	public boolean castString(String key) {
		boolean result = false;
		try {
			String val = this.getString(key);
			this.put(key, val);
			result = true;
		} catch (Exception e) {
			result = false;
			ErrorManager.getErrorStackTrace(e);
		}
		return result;
	}

	/**
	 * box 객체에 담긴 parameter value 의 String 타입을 얻는다.
	 * @param key
	 * @return
	 */
	public Object getObject(String key) {
		Object value = null;
		try {
			value = (Object)super.get(key);
		}
		catch(Exception e) {
			value = null;
			ErrorManager.getErrorStackTrace(e);
		}
		return value;
	}

	/**
	 * 동일한 key 에 value 를 여러개 선택하여 넘길 경우
	 * 각 선택된 value의 list를 Vector에 담아 반환한다.
	 * @param key
	 * @return
	 */
	public Vector<String> getVector(String key) {
		Vector<String> vector = new Vector<String>();
		try {
			Object o = (Object)super.get(key);

			if(o != null){
				Class<? extends Object> c = o.getClass();
				if ( o != null ) {
					if( c.isArray() ) {
						int length = Array.getLength(o);
						if ( length != 0 ) {
							for(int i=0; i<length;i++) {
								Object tiem = Array.get(o, i);
								if (tiem == null ) vector.addElement("");
								else vector.addElement(tiem.toString());
							}
						}
					}else if(o instanceof Vector) {
						Vector<?> v = (Vector<?>)o;
						for(int i=0; i<v.size();i++) {
							vector.addElement((String)v.elementAt(i));
						}
					}else{
						vector.addElement(o.toString());
					}
				}
			}

		}
		catch(Exception e) {
			ErrorManager.getErrorStackTrace(e);
		}
		return vector;
	}

	/**
	 * box 객체에 담긴 parameter value 의 String 타입을 얻는다.
	 * @param key
	 * @param defstr
	 * @return
	 */
	public String getString(String key, String defstr) {
		return (getString(key).equals("") ? defstr : getString(key));
	}

	/**
	 * box 객체에 담긴 업로드된 원파일명을 반환한다. (단수)
	 * @param key
	 * @return
	 */
	public String getRealFileName(String key) {
		String realname = "";
		Vector<?> v = (Vector<?>)getObject(key + "_real");
		if(v != null) {
			for(int i = 0; i < v.size(); i++) {
				String tmp = (String)v.elementAt(i);
				if(tmp != null) {
					int idx = tmp.indexOf("|");
					String name = tmp.substring(0, idx);
					String filename = tmp.substring(idx+1);
					if(key.equals(name)) {
						realname = filename;
					}
				}
			}
		}
		return realname;
	}

	/**
	 * box 객체에 담겨 하드에 저장되는 새로운 업로드된 파일명을 반환한다. (단수)
	 * @param key
	 * @return
	 */
	public String getNewFileName(String key) {
		String newname = "";
		Vector<?> v = (Vector<?>)getObject(key + "_new");
		if(v != null) {
			for(int i = 0; i < v.size(); i++) {
				String tmp = (String)v.elementAt(i);
				if(tmp != null) {
					int idx = tmp.indexOf("|");
					String name = tmp.substring(0, idx);
					String filename = tmp.substring(idx+1);
					if(key.equals(name)) {
						newname = filename;
					}
				}
			}
		}
		return newname;
	}

	/**
	 * box 객체에 담긴 업로드된 원파일명들을 반환한다. (복수)
	 * @param key
	 * @return
	 */
	public Vector<String> getRealFileNames(String key) {
		Vector<String> realVector = new Vector<String>();
		Vector<?> v = (Vector<?>)getObject(key + "_real");
		if(v != null) {
			for(int i = 0; i < v.size(); i++) {
				String tmp = (String)v.elementAt(i);
				if(tmp != null) {
					int idx = tmp.indexOf("|");
					String name = tmp.substring(0, idx);
					String filename = tmp.substring(idx+1);
					if(key.equals(name)) {
						realVector.addElement(filename);
					}
				}
			}
		}
		return realVector;
	}

	/**
	 * box 객체에 담겨 하드에 저장되는 새로운 업로드된 파일명들을 반환한다. (복수)
	 * @param key
	 * @return
	 */
	public Vector<String> getNewFileNames(String key) {
		Vector<String> newVector = new Vector<String>();
		Vector<?> v = (Vector<?>)getObject(key + "_new");
		if(v != null) {
			for(int i = 0; i < v.size(); i++) {
				String tmp = (String)v.elementAt(i);
				if(tmp != null) {
					int idx = tmp.indexOf("|");
					String name = tmp.substring(0, idx);
					String filename = tmp.substring(idx+1);
					if(key.equals(name)) {
						newVector.addElement(filename);
					}
				}
			}
		}
		return newVector;
	}

	/**
	 * 콤마(,)를 제거한다.
	 * @param s
	 * @return
	 */
	private static String removeComma(String s) {
		if ( s == null ) return null;
		if ( s.indexOf(",") != -1 ) {
			StringBuffer buf = new StringBuffer();
			for(int i=0;i<s.length();i++){
				char c = s.charAt(i);
				if ( c != ',') buf.append(c);
			}
			return buf.toString();
		}
		return s;
	}

	/**
	 * Box 객체 전체 출력
	 */
	public synchronized String toString() {
		int max = this.size();
		Enumeration<?> keys = keys();
		Enumeration<?> objects = elements();

		StringBuffer buf = new StringBuffer();
		buf.append("{\n");
		for (int i = 0; i < max; i++) {
			String key = keys.nextElement().toString();
			String value = null;
			Object o = objects.nextElement();
			if ( o == null ) {
				value = "";
			}else {
				Class<? extends Object>  c = o.getClass();
				if( c.isArray() ) {
					int length = Array.getLength(o);

					if ( length == 0 ) 	{
						value = "";
					}else if ( length == 1 ) {
						Object item = Array.get(o, 0);
						if ( item == null ) value = "";
						else value = item.toString();
					}else {
						StringBuffer valueBuf = new StringBuffer();
						valueBuf.append("[");
						for ( int j=0;j<length;j++) {
							Object item = Array.get(o, j);
							if ( item != null ) valueBuf.append(item.toString());
							if ( j<length-1) valueBuf.append(",");
						}
						valueBuf.append("]");
						value = valueBuf.toString();
					}
				}else {
					value = o.toString();
				}
			}

			//request와 session값 출력안함
			if(!key.equals("request") && !key.equals("session") ){
				buf.append(key + "=" + value);
				if ((i+1) < max){
					buf.append(",\n");
				}
			}

		}
		buf.append("}");
		return "RequestBox["+name+"]=\n" + buf.toString();
	}

	/**
	 * box 객체에 담긴 request 객체를 반환한다.
	 * @return
	 */
	public HttpServletRequest getHttpServletRequest() {
		HttpServletRequest request = null;
		try {
			request = (HttpServletRequest)super.get("request");
		}catch(Exception e) {
			ErrorManager.getErrorStackTrace(e);
		}
		return request;
	}

	/**
	 * box 객체에 담긴 session 객체를 반환한다.
	 * @return
	 */
	public HttpSession getHttpSession() {
		HttpSession session = null;
		try {
			session = (HttpSession)super.get("session");
		}catch(Exception e) {
			ErrorManager.getErrorStackTrace(e);
		}
		return session;
	}

	/**
	 * String 타입의 세션변수을 저장한다.
	 * @param key
	 * @param s_value
	 */
	public void setSession(String key, String s_value) {
		HttpSession session = this.getHttpSession();
		if(session != null) {
			session.setAttribute(key, s_value);
		}
	}

	/**
	 * int 타입의 세션변수을 저장한다.
	 * @param key
	 * @param i_value
	 */
	public void setSession(String key, int i_value) {
		HttpSession session = this.getHttpSession();
		if(session != null)	{
			session.setAttribute(key, new Integer(i_value));
		}
	}

	/**
	 * String 타입의 세션변수을 가지고온다.
	 * @param key
	 * @return
	 */
	public String getSession(String key) {
		HttpSession session = this.getHttpSession();
		String s_value = "" ;

		if( session != null ) {
			Object obj = session.getAttribute(key);
			if ( obj != null) {
				s_value = obj.toString();
			}
		}
		return s_value ;
	}

	/**
	 * String 타입의 세션변수을 가지고온다.
	 * @param key
	 * @return
	 */
	public String getSession(String key, String defaultvalue) {
		HttpSession session = this.getHttpSession();
		String s_value = "" ;

		if( session != null ) {
			Object obj = session.getAttribute(key);
			if ( obj != null) {
				s_value = obj.toString();
			}
		}

		if(s_value.equals("")){
			s_value = defaultvalue;
		}


		return s_value ;
	}

	/**
	 * int 타입의 세션변수을 가지고온다.(해당값이 없을때 default 로 돌려줄 값을 파라메터로 넘겨받아야 한다.)
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public int getSession(String key, int defaultValue) {
		int i_value = defaultValue;

		String s_value = this.getSession(key);

		if (!s_value.equals("")) {
			try {
				i_value = Integer.parseInt(s_value);
			} catch (Exception e) {
				i_value = defaultValue;
				ErrorManager.getErrorStackTrace(e);
			}
		}
		return i_value;
	}


	/**
	 * 세션id 를 얻는다.
	 * @return
	 */
	public String getSessionId() {
		HttpSession session = this.getHttpSession();
		String sessionId = "" ;

		if( session != null ) {
			sessionId = session.getId();
		}
		return sessionId;
	}
}

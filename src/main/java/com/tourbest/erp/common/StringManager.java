package com.tourbest.erp.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @Class Name : StringManager
 * @Description : String 관리 라이브러리
 *
 * @author
 * @since 2014. 9. 30.
 */
public class StringManager {

	/**
	 * 해당 문자열에서 older String 을 newer String 으로 교체한다.
    @param original 전체 String
    @param older 전체 String 중 교체 전 문자 String
    @param newer 전체 String 중 교체 후 문자 String
    @return result 교체된 문자열을 반환함
	 */
	public static String replace(String original, String older, String newer) {
		String result = original;

		if(original != null) {
			int idx = result.indexOf(older);
			int newLength = newer.length();

			while(idx >= 0) {
				if (idx == 0) {
					result = newer + result.substring(older.length());
				}else {
					result = result.substring(0, idx) + newer + result.substring(idx + older.length());
				}
				idx = result.indexOf(older, idx + newLength);
			}
		}
		return result;
	}

	/**
	 * java.lang.String 패키지의 trim() 메소드와 기능은 동일, null 체크만 함
    @param str 전체 문자열
    @return result  trim 된 문자열을 반환함
	 */
	public static String trim(String str) throws Exception {
		String result = "";

		if(str != null)
			result = str.trim();

		return result;
	}

	/**
	 * java.lang.String 패키지의 substring() 메소드와 기능은 동일, null 체크만 함
    @param str 전체 문자열
    @param beginIndex
    @param endIndex
    @return result  substring 된 문자열을 반환함
	 */
	public static String substring(String str, int beginIndex, int endIndex) {
		String result = "";

		if(str != null)
			result = str.substring(beginIndex, endIndex);

		return result;
	}

	/**
	 * java.lang.String 패키지의 substring() 메소드와 기능은 동일, null 체크만 함
    @param str 전체 문자열
    @param beginIndex
    @return result  substring 된 문자열을 반환함
	 */
	public static String substring(String str, int beginIndex) {
		String result = "";

		if(str != null)
			result = str.substring(beginIndex);

		return result;
	}

	/**
	 *java.lang.String 패키지의 substring() 메소드와 기능은 동일한데 오른쪽 문자끝부터 count 를 해서 자름
    @param str 전체 문자열
    @param count  오른쪽 문자끝(1) 부터 count 까지
    @return result  substring 된 문자열을 반환함
	 */
	public static String rightstring(String str, int count) throws Exception {
		if (str == null) return null;

		String result = null;
		try {
			if (count == 0)     //      갯수가 0 이면 공백을
				result = "";
			else if (count > str.length())    //  문자열 길이보다 크면 문자열 전체를
				result = str;
			else
				result = str.substring(str.length()-count,str.length());  //  오른쪽 count 만큼 리턴
		}
		catch (Exception ex) {
			throw new Exception("StringManager.rightstring(\""+str+"\","+count+")\r\n"+ex.getMessage());
		}
		return result;
	}

	/**
	 * null 체크
    @param str 전체 문자열
    @return str  null 인경우 "" 을, 아니면 원래의 문자열을 반환한다.
	 */
	public static String chkNull(String str) {
		if (str == null)
			return "";
		else
			return str;
	}

	/**
	 * 빈문자열의 경우 &nbsp로 붙여준다.
     @param str 전체 문자열
     @return str  null이나 빈문자 인경우 &nbsp를, 아니면 원래의 문자열을 반환한다.
	 */
	public static String toWSpace(String str) {
		if (str == null || str.length()==0)
			return "&nbsp";
		else
			return str;
	}

	/**
	 * String 형을 int 형으로 변환, null 및 "" 체크
    @param str 전체 문자열
    @return null 및 "" 일 경우 0 반환
	 */
	public static int toInt(String str) {
		if (str == null || str.equals(""))
			return 0;
		else
			return Integer.parseInt(str);
	}

	/**
	 * String 형을 Double 형으로 변환, null 및 "" 체크
    @param str 전체 문자열
    @return null 및 "" 일 경우 0 반환
	 */
	public static double toDouble(String str) {
		if (str == null || str.equals(""))
			return 0;
		else
			return Double.parseDouble(str);
	}




	/**
	 * URLEncoder 로 암호화
    @param str 전체 문자열
    @return URLEncoder로 암호화된 문자열
	 */
	public static String URLEncode(String str) throws Exception {
		String result = "";
		try {
			if(str != null)
				result = URLEncoder.encode(str);
		}
		catch (Exception ex) {
			throw new Exception("StringManager.URLEncode(\""+str+"\")\r\n"+ex.getMessage());
		}
		return result;
	}


	/**
	 * URLDecode 로 암호화
    @param str 전체 문자열
    @return URLDecode로 복호화된 문자열
	 */
	public static String URLDecode(String str) throws Exception {
		String result = "";
		try {
			if(str != null)
				result = URLDecoder.decode(str,"UTF-8");
		}
		catch (Exception ex) {
			throw new Exception("StringManager.URLDecode(\""+str+"\")\r\n"+ex.getMessage());
		}
		return result;
	}



	public String encode(String str, String charset) {
		StringBuilder sb = new StringBuilder();
		try {
			byte[] key_source = str.getBytes(charset);
			for(byte b : key_source) {
				String hex = String.format("%02x", b).toUpperCase();
				sb.append("%");
				sb.append(hex);
			}
		} catch(UnsupportedEncodingException e) { }//Exception
		return sb.toString();
	}

	public String decode(String hex, String charset) {
		byte[] bytes = new byte[hex.length()/3];
		int len = hex.length();
		for(int i = 0 ; i < len ;) {
			int pos = hex.substring(i).indexOf("%");
			if(pos == 0) {
				String hex_code = hex.substring(i+1, i+3);
				bytes[i/3] = (byte)Integer.parseInt(hex_code, 16);
				i += 3;
			} else {
				i += pos;
			}
		}
		try {
			return new String(bytes, charset);
		} catch(UnsupportedEncodingException e) { }//Exception
		return "";
	}

	public static String korEncode(String str) throws UnsupportedEncodingException {
		if(str == null) return null;
		return new String(str.getBytes("8859_1"), "KSC5601");
	}

	public static String engEncode(String str) throws UnsupportedEncodingException {
		if(str == null) return null;
		return new String(str.getBytes("KSC5601"), "8859_1");
	}

	/**
	 * SQL Query 문에서 value 값의 ' ' 를 만들어 주기 위한 메소드
    @param str   ' ' 안에 들어갈 변수 값
    @return   'str' 로 리턴됨
	 */
	public static String makeSQL(String str) {
		String result = "";
		if(str != null)
			result = "'" + chkNull(replace(str, "'", "")) + "'";
		return result;
	}

	/**
	 * 제목을 보여줄때 제한된 길이를 초과하면 뒷부분을 짜르고 "..." 으로 대치한다.
    @param title(제목등의 문자열), max(최대길이)
    @return title(변경된 문자열)
	 */
	public static String formatTitle(String title, int max) {
		if (title==null) return null;

		int totbyte = 0;

		char[] string = title.toCharArray();
		String retitle = "";
		for (int j=0; j < string.length; j++) {
			if (string[j] >= 'A' && string[j] <= 'z') {
				totbyte++;
			}else if (string[j]>='\uAC00' && string[j]<='\uD7A3') {
				totbyte++;
				totbyte++;
			}else {
				totbyte++;
			}

			if(totbyte <= max){
				retitle += string[j];
			}

		}


		if (totbyte <= max){
			return retitle;
		}else{
			return retitle + "...";
		}
	}

	/**
	 * 제목을 보여줄때 제한된 길이를 초과하면 뒷부분을 짜르고 "..." 으로 대치한다.
    @param title(제목등의 문자열), max(최대길이)
    @return title(변경된 문자열)
	 */
	public static String cutZero(String seq) {
		String result = "";

		try {
			result = Integer.parseInt(seq)+"";
		}
		catch(Exception e) {}
		return result;
	}


	/**
	 * Html 변환
    @param title(제목등의 문자열), max(최대길이)
    @return title(변경된 문자열)
	 */
	public static String convertHTML(String _text){

		StringBuffer html = new StringBuffer();
		String text = _text;

		int startIndex = 0 ;
		int endIndex = 0 ;

		while( (endIndex = text.indexOf("\n",startIndex)) > -1 ){
			html.append(text.substring(startIndex,endIndex));
			html.append("<br />");
			startIndex = endIndex+1;
		}
		html.append(text.substring(startIndex,text.length()) );
		return html.toString();

	}

	/**
	 * 두개의 날짜를 비교해서 크다,같다,작다를 리턴한다.
	 * 단, 에러가 나는 경우에는 물음표를 리턴한다.
	 * @author sjkang
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static String compareDate( String date1, String date2 ) {
		date1 = date1.trim().replaceAll("[^0-9]","");
		date2 = date2.trim().replaceAll("[^0-9]","");

		int date1Int = 0;
		int date2Int = 0;
		try {
			date1Int = Integer.parseInt(date1);
			date2Int = Integer.parseInt(date2);
		} catch (NumberFormatException e) {
			return "?";
		}

		if( date1Int > date2Int )
			return ">";
		else if( date1Int == date2Int )
			return "=";
		else if( date1Int < date2Int )
			return "<";
		else
			return "?";
	}

	public static String formatDate( String strDate,String gubun){
		if(strDate == null)
			return "";

		strDate = strDate.trim();
		if(strDate.length() == 6){

			return strDate.substring(0, 2) + gubun + strDate.substring(2,4)+ gubun + strDate.substring(4, 6);
		}else if(strDate.length() >= 8){

			return strDate.substring(0, 4) + gubun + strDate.substring(4,6)+ gubun + strDate.substring(6);
		}else{

			return "";
		}
	}

	/**
	 * String date를 Date객체로 parsing하여 return하는 method.
	 * @param date       날짜를 나타내는 String 객체
	 * @param formate    Date의 format
	 * @return String 날짜를 parsing한 Date객체.
	 */
	public static Date dateParse(String date, String format){
		if ( date == null )
			return null;

		try {
			return new SimpleDateFormat(format).parse(date);
		} catch(Exception e) {
			return null;
		}
	}

	/**
	 * Date를 format에 맞는 String으로 return하는 method.
	 * @param date       Date객체
	 * @param formate    Date의 format
	 * @return format에 맞는 날짜 String.
	 */
	public static String dateFormat(Date date, String format){
		if ( date == null )
			return "";

		try {
			return new SimpleDateFormat(format).format(date);
		} catch(Exception e) {
			return "";
		}
	}

	/**
	 * 오늘 날자를 반환
	 *
	 * @param year 년수
	 * @param month 개월수
	 * @param day 일수
	 * @return yyyyMMdd 형식의 String
	 */
	public static String getTodayStr(){
		return dateFormat(getToday(),"yyyyMMdd");
	}

	/**
	 * 오늘 날자를 반환
	 *
	 * @param year 년수
	 * @param month 개월수
	 * @param day 일수
	 * @return yyyyMMdd 형식의 String
	 */
	public static String getTimeAfterHour(){
		return dateFormat(afterDate(0,0,0,1),"yyyyMMddHH");
	}

	/**
	 * 입력 년, 월, 일 이후의 날짜를 가져옴.
	 *
	 * @param year 년수
	 * @param month 개월수
	 * @param day 일수
	 * @return yyyyMMdd 형식의 String
	 */
	public static String getAfterDate(int year, int month, int day){
		GregorianCalendar aCal = new GregorianCalendar();
		aCal.add(Calendar.YEAR, year);
		aCal.add(Calendar.MONTH, month);
		aCal.add(Calendar.DATE, day);

		return dateFormat(new java.sql.Date(aCal.getTimeInMillis()),"yyyyMMdd");
	}

	/**
	 * 입력 년, 월, 일 이전의 날짜를 가져옴.
	 *
	 * @param year 년수
	 * @param month 개월수
	 * @param day 일수
	 * @return yyyyMMdd 형식의 String
	 */
	public static String getBeforeDate(int year, int month, int day){
		return dateFormat(afterDate(-year, -month, -day),"yyyyMMdd");
	}

	/**
	 * 입력 년, 월, 일 이후의 날짜를 가져옴.
	 *
	 * @param year 년수
	 * @param month 개월수
	 * @param day 일수
	 * @return 입력 년, 월, 일이 더해진 날짜
	 */
	public static Date afterDate(int year, int month, int day){
		GregorianCalendar aCal = new GregorianCalendar();
		aCal.add(Calendar.YEAR, year);
		aCal.add(Calendar.MONTH, month);
		aCal.add(Calendar.DATE, day);

		return new java.sql.Date(aCal.getTimeInMillis());
	}

	/**
	 * 입력 년, 월, 일 이후의 날짜를 가져옴.
	 *
	 * @param year 년수
	 * @param month 개월수
	 * @param day 일수
	 * @return 입력 년, 월, 일이 더해진 날짜
	 */
	public static Date afterDate(int year, int month, int day,int hour){
		GregorianCalendar aCal = new GregorianCalendar();
		aCal.add(Calendar.YEAR, year);
		aCal.add(Calendar.MONTH, month);
		aCal.add(Calendar.DATE, day);
		aCal.add(Calendar.HOUR, hour);

		return new java.sql.Date(aCal.getTimeInMillis());
	}

	/**
	 * 입력 년, 월, 일 이전의 날짜를 가져옴.
	 *
	 * @param year 년수
	 * @param month 개월수
	 * @param day 일수
	 * @return 입력 년, 월, 일이 빠진 날짜
	 */
	public static Date beforeDate(int year, int month, int day){
		return afterDate(-year, -month, -day);
	}

	/**
	 * String Date를 parsing하여 새로운 format에 맞는 Date String을 return하는 method.
	 *
	 * @param date       String의 날짜
	 * @param parse      String 날짜의 현재 format
	 * @param formate    새로운 Date의 format
	 *
	 * @return format에 맞는 날짜 String.<br>
	 */
	public static String dateFormat(String date, String parse, String format){
		if ( date == null ){
			return "";
		}
		try {
			return new SimpleDateFormat(format).format(new SimpleDateFormat(parse).parse(date));
		} catch(Exception e) {
			return "";
		}
	}


	/**
	 * 오늘 날짜를 Date타입으로 return
	 * @return
	 */
	public static Date getToday(){
		Calendar cd = Calendar.getInstance();
		Date     dt = cd.getTime();
		return dt;
	}

	/**
	 * 기준자(',')로 문자열을 자른후  Strign 배열로 리턴
	 * @param tokenVal String 대상문자열
	 * @return
	 */
	public static String[] getSplitStrToArray(String tokenVal){
		String strVal[] = null;
		strVal = tokenVal.split(",");
		return strVal;
	}

	/**
	 * Number를 지정한 format의 String으로변환
	 * @param num		long 대상숫자
	 * @param format	String 형식
	 * @return
	 */
	public static String numberFormat(long num, String format)
	{
		try {
			return new DecimalFormat(format).format(num);
		} catch(Exception e) {
			return "";
		}
	}

	/**
	 * Number를 지정한 format의 String으로변환
	 * @param num		double 대상숫자
	 * @param format	String 형식
	 * @return
	 */
	public static String numberFormatDouble(double num, String format)
	{
		try {
			return new DecimalFormat(format).format(num);
		} catch(Exception e) {
			return "";
		}
	}

	/**
	 * 제한적으로 보여줘야될 string인 경우 특정 길이 이상이면 [...]로 대체하는 메소드(한글의 경우는 compByte()를 사용해야 됨)
	 * @param str	String 대상문자열
	 * @param i		int 문자열길이
	 * @return
	 */
	public static String setTitle(String str, int i) {
		if(str==null)	return "";
		String tmp = str;
		if(tmp.length()>i)	tmp = tmp.substring(0,i)+"...";
		return tmp;
	}

	/**
	 * 날짜 형식 변경
	 * @param date				String 날짜
	 * @param orignalformat 	String 원래 날짜 형식
	 * @param wantformat		String 변경을 원하는 날짜 형식
	 * @return
	 */
	public static String getFormatDate(String date, String orignalformat, String wantformat){
		String day = "";
		try{
			SimpleDateFormat dd = new SimpleDateFormat(orignalformat,Locale.US);
			ParsePosition parse = new ParsePosition(0);
			Calendar cal = Calendar.getInstance();
			cal.setTime(dd.parse(date, parse));
			SimpleDateFormat sdf = new SimpleDateFormat(wantformat,Locale.US);
			day = sdf.format(cal.getTime());
		}catch(NullPointerException  e) {
			e.printStackTrace();
		}catch(IllegalArgumentException  e) {
			e.printStackTrace();
		}
		return day;
	}



	/**
	 * 특수문자를  HTML로치환
	 * @param str
	 * @return
	 */
	public static String htmlSpecialChar( String str ) {
		if ( str == null )
			return "";

		str = str.replaceAll("&", "&amp;");

		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("\'", "&apos;");
		str = str.replaceAll("\"", "&quot");
		str = str.replaceAll("\n", "<br/>");
		str = str.replaceAll("\r\n", "<br/>");


		return str;
	}


	/**
	 * NULL검사
	 * @param str
	 * @param Defaultvalue
	 * @return
	 * @throws Exception
	 */
	public static String nullcheck(String str,  String Defaultvalue ) throws Exception {
		String ReturnDefault = "" ;
		if (str == null){
			ReturnDefault =  Defaultvalue ;
		}
		else if (str == "" ){
			ReturnDefault =  Defaultvalue ;
		}
		else{
			ReturnDefault = str ;
		}
		return ReturnDefault ;
	}

	/**
	 * 앞에 자리수만금 숫자 채우기
	 * @param chkNumber
	 * @param chkLen
	 * @return
	 */
	public static String addZero (int chkNumber, int chkLen){
		String temp = null;
		temp = String.valueOf(chkNumber);
		int len = temp.length();

		if (len < chkLen){
			for(int i=1; i<=(chkLen-len); i++) {
				temp = "0" + temp;
			}
		}
		return temp;
	}

	/**
	 * 천단위콤마
	 * @param chkNumber
	 * @param chkLen
	 * @return
	 */
	public static String setComma (double num){

		DecimalFormat df = new DecimalFormat("#,##0");
		String temp = df.format(num);
		return temp;
	}


	/**
	 * 임시비밀번호 생성
	 * @param length
	 * @return
	 */
	public static String randomPassword (int length) {
		int index = 0;
		char[] charSet = new char[] {
				'0','1','2','3','4','5','6','7','8','9'
				,'A','B','C','D','E','F','G','H','I','J','K','L','M'
				,'N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
				,'a','b','c','d','e','f','g','h','i','j','k','l','m'
				,'n','o','p','q','r','s','t','u','v','w','x','y','z'};

		StringBuffer sb = new StringBuffer();
		for (int i=0; i<length; i++) {
			index =  (int) (charSet.length * Math.random());
			sb.append(charSet[index]);
		}

		return sb.toString();

	}


	/**
	 * 임시비밀번호 생성
	 * @param length
	 * @return
	 */
	public static String randomPassword () {
		return randomPassword(8);
	}



	/**
	 * MD5 얻어오기
	 * @param input
	 * @return
	 */
	public static String getMD5(String input) {
        byte[] source;
        try {
            //Get byte according by specified coding.
            source = input.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            source = input.getBytes();
        }
        String result = null;
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7',
                '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source);
            //The result should be one 128 integer
            byte temp[] = md.digest();
            char str[] = new char[16 * 2];
            int k = 0;
            for (int i = 0; i < 16; i++) {
                byte byte0 = temp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            result = new String(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


	/**
	 * 정규식 문자열 추출
	 * @param text:		문자열
	 * @param start:	시작문자열
	 * @param end:		종료문자열
	 * @return
	 */
	public static String getPatternMatch(String text, String start, String end){

		String result = "";
		String regex = start+"(.*?)"+end;
		Pattern pt = Pattern.compile(regex);
		Matcher mt = pt.matcher(text);

		if(mt.find()){
			result = mt.group().replaceAll(start, "").replaceAll(end, "");
		}else{
			result = text;
		}
		return result;
	}


	/**
	 * 전화번호 타입 변경
	 * @param number:	ex)010-1234-5678, 02-123-4567, 02-1234-5678...
	 * @param type:		ex)"-", "."...
	 * @return
	 */
	public static String phoneNumber(String number, String type){

		//1.숫자만 추출
		String convert_a = number.replaceAll("[^0-9]", "");

		//2.전화번호 형태 변경
		String convert_b = String.valueOf(convert_a).replaceFirst("(^02.{0}|^01.{1}|\\d{2,3})(\\d{3,4})(\\d{4}$)", "$1"+type+"$2"+type+"$3");

		return convert_b;
	}

	/**
	 * 이메일 정규식 유효성 체크
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {

		boolean result = false;
		String str = email.trim();

		if(str != null && !"".equals(str)){
			result = Pattern.matches("[0-9a-zA-Z]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$", str);
		}
		return result;
	}

	/**
	 * 휴대폰번호 정규식 유효성 체크
	 * @param cell
	 * @return
	 */
	public static boolean isCell(String cell) {

		boolean result = false;
		String str = cell.replaceAll("[^0-9]", "");

		if(str != null && !"".equals(str)){
			result = Pattern.matches("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", str);
		}
		return result;
	}


	/**
	 * 전화번호 정규식 유효성 체크
	 * @param tel
	 * @return
	 */
	public static boolean isTel(String tel) {

		boolean result = false;
		String str = tel.replaceAll("[^0-9]", "");

		if(str != null && !"".equals(str)){
			result = Pattern.matches("^\\d{2,3}\\d{3,4}\\d{4}$", str);
		}
		return result;
	}

}


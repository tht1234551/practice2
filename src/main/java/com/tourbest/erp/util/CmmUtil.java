package com.tourbest.erp.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 공통
 *
 * <pre>
 * 클래스 설명 :
 * 공통으로 사용할수 있는 메소드들을 구현한다.
 *
 * </pre>
 * @author
 * @date 2015. 6. 15.
 *
 */

public class CmmUtil {

	private static String env_Key;

	private CmmUtil(String envKey) throws Exception {
		env_Key = envKey;
	}
	/**
     *  현재 환경 반환 (local/ybdev/opt/prd)
     * @param request
     * @return
     */
	public static String envKey() throws Exception {
		//globals.properties의 Globals.Env.Key
		// _local /  _ybdev / _opt / _prd
        return env_Key;
    }

	/**
	 * xml root노드 파싱해서 List<Map>로 리턴
	 * @param xmlPath			: xml 요청 경로
	 * @param inputStreamOption	: CmmIOUtil 클래스 static 변수 이용
	 * @return
	 */
	public static List<Map<String,String>> parseXml(String xmlPath, int inputStreamOption){
		List<Map<String,String>> list = null;

		list = parseXml(xmlPath,inputStreamOption,"root", 1);

		return  list;
	}

	/**
	 * xml 파싱해서 List<Map>로 리턴
	 * @param xmlPath			: xml 요청 경로
	 * @param inputStreamOption	: CmmIOUtil 클래스 static 변수 이용
	 * @param tagName			: 리스트에 받아올 태그 이름
	 * @param parseOption		: 싱글노드 VS 멀티플노드
	 * @return
	 */
	public static List<Map<String,String>> parseXml(String xmlPath, int inputStreamOption, String tagName, int parseOption ){
		List<Map<String,String>> list = null;
		Map<String,String> map = null;

		try{
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

			Document document = documentBuilder.parse(CmmIOUtil.getInputStream(xmlPath, inputStreamOption, 5000)); // xml파일 전체 읽기
			document.getDocumentElement().normalize(); // 문서구조 안정화

			NodeList nodeList = null;
			Element element = document.getDocumentElement();
			//System.out.println(element.hasChildNodes());

			if(tagName.equals("")){
				nodeList = element.getChildNodes(); // 노드리스트 추출
			}else{
				nodeList = element.getElementsByTagName(tagName); // tag 이름으로 노드리스트 추출
			}

			String nodeName = "";
			String nodeValue = "";

			list = new ArrayList<Map<String,String>>();
			for(int i=0;i<nodeList.getLength();i++){
				Node row = nodeList.item(i);
				NodeList children = row.getChildNodes(); // 노드의 하위 노드를 가져옴
				map = new HashMap<String,String>();

				for(int j=0;j<children.getLength();j++){
					Node child = children.item(j); // 하위 노드를 하나씩 가져옴

					if(parseOption == 0){
						nodeName = child.getParentNode().getNodeName();
						nodeValue = child.getNodeValue();

						if(nodeValue != null && !nodeValue.equals("") ){
							map.put(nodeName, nodeValue);
						}

					}else if(parseOption == 1){
						if(child.getFirstChild() != null) {
							nodeName = child.getNodeName();
							nodeValue = child.getFirstChild().getNodeValue();
							map.put(nodeName, nodeValue);
						}
					}
				}
				list.add(map);
			}

		}catch(Exception e){
			e.printStackTrace();
		}

		return  list;
	}

	/**
	 * yyyyMMddHHmmssSSS 형식 String return
	 * @return
	 */
	public static String getCurrentTime(){
		long time = System.currentTimeMillis();
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String strDT = dayTime.format(new Date(time));

		return strDT;
	}

	/**
	 * String null 체크하기 null일 경우 "" 리턴
	 * @param str
	 * @return
	 */
	public static String checkNull(Object chk){
		return checkNull(chk, "");
	}

	/**
	 * String null 체크하기 null일 경우 rep 리턴
 	 * @param str
	 * @param rep
	 * @return
	 */
	public static String checkNull(Object chk, String rep){
		String rtn = "";
		try{
			if(chk == null){
				rtn = rep;
			}else{
				rtn = (String)chk;
				if(rtn.length() <= 0){
					rtn = rep;
				}
			}
		}catch(Exception e){
			rtn = "";
		}
		return rtn;
	}

	/**
	 * List 안 Map의 Key를 모두 lowerCase
	 * @param arrayList
	 * @return
	 */
	public static List<Map<String,Object>> setMapKeyLowerCase(List<Map<String,Object>> arrayList){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

		if(arrayList == null || arrayList.size() == 0){
			return arrayList;
		}

		Map<String,Object> map = new HashMap<String,Object>();
		for(int i=0;i<arrayList.size();i++){
			map = setKeyLowerCase(arrayList.get(i));
			list.add(map);
		}

		return list;
	}

	/**
	 * Map의 Key를 모두 lowerCase
	 * @param hashMap
	 * @return
	 */
	public static Map<String, Object> setKeyLowerCase(Map<String, Object> hashMap){
		Map<String, Object> map = new HashMap<String, Object>();
		Iterator<String> iterator = hashMap.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			map.put(key.toLowerCase(), hashMap.get(key));
		}
		return map;
	}

    /**
     *  IP 가져오기
     * @param request
     * @return
     */
    public static String getClientIP() {

        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();

        String ip = request.getHeader("X-FORWARDED-FOR");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0) {
            ip = checkNull(request.getRemoteAddr());
        }

        // ipv6 (ipv4 127.0.0.1) localhost로 접속했을시
        if("0:0:0:0:0:0:0:1".equals(ip)){
            ip = "127.0.0.1";
        }

        return ip;

    }

    /**
     * Object를 long 형으로 변환, null인 경우는 99999으로 반환
     *
     */

    public static long N2l(Object obj){
		long re = 99999;
		if(obj instanceof String){
			if(obj != null && !obj.equals("")){
				re = new Long((String)obj).longValue();
			}
		}else if(obj instanceof Long){
			if(obj != null) re = ((Long)obj).longValue();
		}

		return re;

    }

    /**
     * XSS 방지를 위해 문자열에 삽입된 스크립트 처리
     *
     * @param data java.lang.String
     * @return java.lang.String
     */
	public static String unscript(String data) {
		if (data == null || data.trim().equals("")) {
			return "";
		}

		String ret = data;

//		ret = ret.replaceAll("<", "&lt;");
//		ret = ret.replaceAll(">", "&gt;");
		ret = ret.replaceAll("'", "");

		ret = ret.replaceAll("(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "x-script");
//		ret = ret.replaceAll("/(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;/script");

		ret = ret.replaceAll("(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "x-object");
//		ret = ret.replaceAll("/(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;/object");

		ret = ret.replaceAll("(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "x-applet");
//		ret = ret.replaceAll("/(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;/applet");

		ret = ret.replaceAll("(E|e)(M|m)(B|b)(E|e)(D|d)", "x-embed");
//		ret = ret.replaceAll("/(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");

		ret = ret.replaceAll("(F|f)(O|o)(R|r)(M|m)", "x-form");
//		ret = ret.replaceAll("/(F|f)(O|o)(R|r)(M|m)", "&lt;form");


        ret = ret.replaceAll("(J|j)(A|a)(V|v)(A|a)(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "x-javascript");
        ret = ret.replaceAll("(I|i)(F|f)(R|r)(A|a)(M|m)(E|e)", "x-iframe");
        ret = ret.replaceAll("(D|d)(O|o)(C|c)(U|u)(M|m)(E|e)(N|n)(T|t)", "x-document");
        ret = ret.replaceAll("(V|v)(B|b)(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "x-vbscript");
        ret = ret.replaceAll("(F|f)(R|r)(A|a)(M|m)(E|e)", "x-frame");
        ret = ret.replaceAll("(G|g)(R|r)(A|a)(M|m)(E|e)(S|s)(E|e)(T|t)", "x-grameset");
        ret = ret.replaceAll("(L|l)(A|a)(Y|y)(E|e)(R|r)", "x-layer");
        ret = ret.replaceAll("(B|b)(G|g)(S|s)(O|o)(U|u)(N|n)(D|d)", "x-bgsound");
        ret = ret.replaceAll("(A|a)(L|l)(E|e)(R|r)(T|t)", "x-alert");
        ret = ret.replaceAll("(O|o)(N|n)(B|b)(L|l)(U|u)(R|r)", "x-onblur");
        ret = ret.replaceAll("(O|o)(N|n)(C|c)(H|h)(A|a)(N|n)(G|g)(E|e)", "x-onchange");
        ret = ret.replaceAll("(O|o)(N|n)(C|c)(L|l)(I|i)(C|c)(K|k)", "x-onclick");
        ret = ret.replaceAll("(O|o)(N|n)(D|d)(B|b)(L|l)(C|c)(L|l)(I|i)(C|c)(K|k)","x-ondblclick");
        ret = ret.replaceAll("(E|e)(N|n)(E|e)(R|r)(R|r)(O|o)(R|r)", "x-enerror");
        ret = ret.replaceAll("(O|o)(N|n)(F|f)(O|o)(C|c)(U|u)(S|s)", "x-onfocus");
        ret = ret.replaceAll("(O|o)(N|n)(L|l)(O|o)(A|a)(D|d)", "x-onload");
        ret = ret.replaceAll("(O|o)(N|n)(M|m)(O|o)(U|u)(S|s)(E|e)", "x-onmouse");
        ret = ret.replaceAll("(O|o)(N|n)(S|s)(C|c)(R|r)(O|o)(L|l)(L|l)", "x-onscroll");
        ret = ret.replaceAll("(O|o)(N|n)(S|s)(U|u)(B|b)(M|m)(I|i)(T|t)", "x-onsubmit");
        ret = ret.replaceAll("(O|o)(N|n)(U|u)(N|n)(L|l)(O|o)(A|a)(D|d)", "x-onunload");

		return ret;
	}

	/**
     * HTML 태그 삭제 처리(웹에디터를 이용한 CLOB타입 데이터를 받아서 삭제 처리 : 옐로트립 목록에서 사용)
     *
     * @param data java.lang.String
     * @return java.lang.String
     */
	public static String tagRemove(String contStr) {

		if (contStr == null || contStr.trim().equals("")) {
			return "";
		}

		String context = contStr.replaceAll("<[^>]*>", "");
		context = context.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
   		context = context.replaceAll("&nbsp;","");
   		context = context.replaceAll("&quot;","");
   		context = context.replaceAll("&hellip;","");
   		context = context.replaceAll("&hellip","");
   		context = context.replaceAll("&helli","");
   		context = context.replaceAll("&lsquo;","");
   		context = context.replaceAll("&lsquo","");
   		context = context.replaceAll("&rsquo;","");
   		context = context.replaceAll("&rsquo","");
   		context = context.replaceAll("&ldquo;","");
   		context = context.replaceAll("&ldquo","");
   		context = context.replaceAll("&rdquo;","");
   		context = context.replaceAll("&rdquo","");
   		context = context.replaceAll("&#39;","");
   		context = context.replaceAll("&amp;","");
   		context = context.replaceAll("&middot;","");

   		return context;
	}



	/**
     * 도메인 체크
     *
     * @param data java.lang.String
     * @return java.lang.String
     */
	public static String siteDomainCheck(String serverName) {

		String returnValue = "";

		if ("localhost".equals(serverName) || "127.0.0.1".equals(serverName) || "loc.tourbesterp.com".equals(serverName)){
			returnValue = "local";
		}else if ("devt.tourbesterp.com".equals(serverName)){
			returnValue = "dev";
		}else if ("www.tourbesterp.com".equals(serverName)){
			returnValue = "www";
		}else{
			returnValue = "test";
		}

		return returnValue;
	}

	/**
     * 나이 리턴
     *
     * @param data java.lang.String
     * @return java.lang.String
     */

	public static boolean calculateManAge(String ssn){

		ssn = ssn.replaceAll("-", ""); //'-' 제거

		String today = ""; //오늘 날짜
		int manAge = 0; //만 나이

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

		today = formatter.format(new Date()); //시스템 날짜를 가져와서 yyyyMMdd 형태로 변환

		//today yyyyMMdd
		int todayYear = Integer.parseInt( today.substring(0, 4) );
		int todayMonth = Integer.parseInt( today.substring(4, 6) );
		int todayDay = Integer.parseInt( today.substring(6, 8) );

		int ssnYear = Integer.parseInt( ssn.substring(0, 2) );
		int ssnMonth = Integer.parseInt( ssn.substring(2, 4) );
		int ssnDay = Integer.parseInt( ssn.substring(4, 6) );

		if( ssn.charAt( 6 ) == '0' || ssn.charAt( 6 ) == '9' ){
			ssnYear += 1800;
		}else if( ssn.charAt( 6 ) == '1' || ssn.charAt( 6 ) == '2' || ssn.charAt( 6 ) == '5' || ssn.charAt( 6 ) == '6' ){
			ssnYear += 1900;
		}else { //3, 4, 7, 8
			ssnYear += 2000;
		}

		manAge = todayYear - ssnYear;

		if( todayMonth < ssnMonth ){ //생년월일 "월"이 지났는지 체크
			manAge--;
		}else if( todayMonth == ssnMonth ){ //생년월일 "일"이 지났는지 체크
			if( todayDay < ssnDay ){
				manAge--; //생일 안지났으면 (만나이 - 1)
			}
		}

		return manAge < 14 ? true : false;

	}

	/**
	 * 숫자 콤마 리턴
	 *
     * @param data java.lang.String
     * @return java.lang.String
	 */
	public static String moneyComma(long num){

		DecimalFormat df = new DecimalFormat("#,###");
		return df.format(num);
	}

}

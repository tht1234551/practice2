package com.tourbest.erp.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.InputSource;

/**
 * XML Helper Util.
 * @author 서비스운영팀 김광일
 * @since 2015.10.20.
 * @version 1.3
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *	수정일			수정자			수정내용
 *	----------	--------	---------------------------
 *	2015.10.20	김광일			최초 생성 - 1.0
 *	2015.10.28	김광일			getElement(String tagName) 메소드 추가. - 1.1
 *							addElement(Element targetElement, String key, Object objData) 메소드 추가.
 *	2015.11.30	김광일			putStreamResult(StreamResult srTarget, String charSet):StreamResult 메소드 추가. - 1.2
 *							putStreamResult(StreamResult srTarget):StreamResult 메소드 추가.
 *	2015.12.01	김광일			XmlMake -> XmlUtil 변경. - 1.3
 *							parseXML(String strXML):Document 메소드 추가.
 *							parseXMLtoElement(String strXML):Element 메소드 추가.
 *							getElementText(String tagName, int idx):String 메소드 추가.
 *							getElementText(String tagName):String 메소드 추가.
 *							toMap(NodeList nodeList):Map 메소드 추가.
 *							toMap():Map 메소드 추가.
 *	2015.12.28	김광일			toMap() / toList() tag명으로 갖고오는 element에서 자식 노드 타입 구분이 명확치 않아 발생한 오류 수정.	- 1.4
 *	2015.12.29	김광일			toMap(String tagName, int idx) 메소드 추가.	- 1.5
 *							toList(String tagName, int idx) 메소드 추가.
 *							toMap(NodeList nodeList) -> makeMap(NodeList nodeList) 메서드명 변경.
 *							toList(NodeList nodeList) -> makeList(NodeList nodeList) 메서드명 변경.
 *							노드 타입 구분을 조금더 명확하게 변경 및 기타 오류 수정.
 *
 * </pre>
 */
public class XmlUtil {

	private Document document;
	private Element rootElement;

	/**
	 * XmlUtil 생성자.
	 */
	public XmlUtil() {
		super();
	}
	
	/**
	 * XmlUtil 생성자.
	 * @param rootElement "true"값을 지정 시, "ROOT"라는 태그 명을 갖는 Root element를 생성한다.
	 */
	public XmlUtil(boolean rootElement) {
		super();

		if (rootElement) {
			create("ROOT");
		}
	}

	/**
	 * XmlUtil 생성자.
	 * @param rootStr Root element의 태그 명을 지정하여 생성한다.
	 */
	public XmlUtil(String rootStr) {
		super();

		create(rootStr);
	}
	
//	public XmlUtil(Element rootElement) {
//		super();
//		
//		create(rootElement);
//	}

	/**
	 * Document객체 반환.
	 * @return
	 */
	public Document getDocument() {
		return document;
	}

	/**
	 * @param document the document to set
	 */
	public void setDocument(Document document) {
		this.document = document;
	}

	/**
	 * Document의 Root element를 반환.
	 * @return
	 */
	public Element getRootElement() {
		return rootElement;
	}


	/**
	 * Document 생성.
	 * @param rootStr 지정된 태그 명을 갖는 Root element를 생성한다.
	 * @return
	 */
	private Document create(String rootStr) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			document = dBuilder.newDocument();
			rootElement = document.createElement(rootStr);
			document.appendChild(rootElement);
		} catch (ParserConfigurationException e) {
			document = null;
			e.printStackTrace();
		}
		return document;
	}

	/**
	 * Get First Element By Tag Name
	 * @param tagName
	 * @return
	 */
	public Element getElement(String tagName) {
		return getElement(tagName, 0);
	}
	
	/**
	 * Get Element By Tag Name
	 * @param tagName
	 * @return
	 */
	public Element getElement(String tagName, int idx) {
		return (Element) document.getElementsByTagName(tagName).item(idx);
	}
	
	public String getElementText(String tagName) {
		return getElement(tagName).getTextContent();
	}
	
	public String getElementText(String tagName, int idx) {
		return getElement(tagName, idx).getTextContent();
	}

	/**
	 * Target Element에 신규 Element 추가.
	 * @param targetElement
	 * @param key
	 * @param objData
	 * @return
	 */
	public Element addElement(Element targetElement, String key, Object objData) {
		return addElement(targetElement, key, objData, null);
	}
	
	public Element addElement(Element targetElement, String key, Object objData, Map<String, String> attributes) {
		Element childElement = document.createElement(key);
		
		// 속성(Attribute) 추가.
		if (attributes != null && attributes.size() > 0) {
			for (Map.Entry<String, String> entry: attributes.entrySet()) {
				childElement.setAttribute(entry.getKey(), entry.getValue());
			}
		}
		
		if(objData instanceof LinkedHashMap) {
			addElement(childElement, (LinkedHashMap) objData);
		} else if (objData instanceof String) {
//			childElement.appendChild( document.createCDATASection( (String)objData ) );
			Pattern pattern = Pattern.compile("^[a-zA-Z0-9:.-]*$");
			Matcher matcher = pattern.matcher((String) objData);
			childElement.appendChild( matcher.matches() ? document.createTextNode( (String)objData ) : document.createCDATASection( (String)objData ) );
		} else {
			childElement.appendChild( objData == null ? document.createCDATASection("") : document.createTextNode(objData.toString()) );
		}
		
		targetElement.appendChild(childElement);
		return targetElement;
	}
	
	/**
	 * <pre>
	 * LinkedHashMap 타입의 데이터를 XML Document 객체에 추가.
	 * - 내부 테이터 타입도 모두 LinkedHashMap 타입이여야 함.
	 * </pre>
	 * @param targetElement
	 * @param mapData
	 * @return
	 */
	public Element addElement(Element targetElement, LinkedHashMap<String, Object> mapData) {
		Iterator<String> iter = mapData.keySet().iterator();
		while(iter.hasNext()) {
			String key = iter.next();
//			Element childElement = document.createElement(key);
//			
//			if(mapData.get(key) instanceof LinkedHashMap) {
//				addElement(childElement, (LinkedHashMap) mapData.get(key));
//			} else if (mapData.get(key) instanceof String) {
//				childElement.appendChild( document.createCDATASection( (String)mapData.get(key) ) );
//			} else {
//				childElement.appendChild( document.createCDATASection( mapData.get(key) == null ? "" : mapData.get(key).toString() ) );
//			}
//			targetElement.appendChild(childElement);
			
			if(mapData.get(key) instanceof LinkedHashMap) {
				Element childElement = document.createElement(key);
				addElement(childElement, (LinkedHashMap) mapData.get(key));
				targetElement.appendChild(childElement);
			} else {
				addElement(targetElement, key, mapData.get(key));
			}
		}
		return targetElement;
	}

//	public Element addElement(Element targetElement, String elementId, List<LinkedHashMap<String, Object>> list) {
//		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
//			LinkedHashMap<String, Object> linkedHashMap = (LinkedHashMap<String, Object>) iterator.next();
//			LinkedHashMap<String, Object> pMap = new LinkedHashMap<String, Object>();
//			pMap.put(elementId, linkedHashMap);
//			
//			addElement(targetElement, pMap);
//		}
//		return targetElement;
//	}
	
	/**
	 * Document의 XML 데이터를 파라미터로 받은 StreamResult 데이터에 변환하여 반환.
	 * @param srTarget
	 * @return
	 * @throws Exception
	 */
	public StreamResult putStreamResult(StreamResult srTarget) throws Exception {
		return putStreamResult(srTarget, "UTF-8");
	}
	
	/**
	 * Document의 XML 데이터를 파라미터로 받은 StreamResult 데이터에 변환하여 반환. 
	 * @param srTarget
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public StreamResult putStreamResult(StreamResult srTarget, String charSet) throws Exception {

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, charSet);
		
		DOMSource source = new DOMSource(getDocument());
		transformer.transform(source, srTarget);

		return srTarget;
	}
	
	/**
	 * String 데이터 형의 XML구문을 분석하여 Document 객체로 변환하여 반환.
	 * @param strXML
	 * @return
	 * @throws Exception
	 */
	public Document parseXML(String strXML) throws Exception {
		// 자신의 static 메서드를 가지고 객체를 생성 : 싱글톤 패턴
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		// 다른 클래스의 객체를 가지고, 객체를 생성하면 팩토리 패턴.
		DocumentBuilder docBuilder = factory.newDocumentBuilder();	// 팩토리 메서드 패턴  공장에서 찍어줌.
		// 문자열을 InputStream으로 변환
//		InputStream is = new ByteArrayInputStream(strXML.getBytes());
//		Document doc = docBuilder.parse(is);

		strXML = strXML.replaceAll("\r", "");
		strXML = strXML.replaceAll("\n", "");
		strXML = strXML.replaceAll("\r\n", "");
		strXML = strXML.replaceAll(System.getProperty("line.separator"), "");
		
//		document = docBuilder.parse(new ByteArrayInputStream(strXML.getBytes()));
		document = docBuilder.parse(new InputSource(new StringReader(strXML)));
		rootElement = document.getDocumentElement();
		rootElement.normalize();

		//----- String 변수 찍기 -----------------------------------------------------------------------
//		StreamResult result = new StreamResult(new StringWriter());		// 변수(String)에 넣기
//		putStreamResult(result);
//		String str1 = result.getWriter().toString();
//		System.out.println("Make XML : "+str1);
		//----- String 변수 찍기 -----------------------------------------------------------------------
		return document;
	}
	
	/**
	 * String 데이터 형의 XML구문을 분석하여 Document 객체의 Root element로 변환하여 반환.
	 * @param strXML
	 * @return
	 * @throws Exception
	 */
	public Element parseXMLtoElement(String strXML) throws Exception {
		parseXML(strXML);
//		return document.getDocumentElement();	// xml을 메모리에 펼쳐놓고 루트를 element 반환.
		return rootElement;
	}
	
	@SuppressWarnings("rawtypes")
	public Map toMap() throws Exception {
		NodeList childNodes = rootElement.getChildNodes();
		return isMap(childNodes) ? makeMap(childNodes) : null;
	}
	
	@SuppressWarnings("rawtypes")
	public Map toMap(String tagName) throws Exception {
		return toMap(tagName, 0);
	}
	
	@SuppressWarnings("rawtypes")
	public Map toMap(String tagName, int idx) throws Exception {
		Node node = rootElement.getElementsByTagName(tagName).item(idx);
		NodeList childNodes = node.getChildNodes();
		return isMap(childNodes) ? makeMap(childNodes) : null;
	}
	
	@SuppressWarnings("rawtypes")
	public List toList() throws Exception {
		NodeList nodes = rootElement.getChildNodes();
		return isMap(nodes) ? null : makeList(nodes);
	}
	
	@SuppressWarnings("rawtypes")
	public List toList(String tagName) throws Exception {
		return toList(tagName, 0);
	}
	
	@SuppressWarnings("rawtypes")
	public List toList(String tagName, int idx) throws Exception {
		Node node = rootElement.getElementsByTagName(tagName).item(idx);
		NodeList childNodes = node.getChildNodes();
		return isMap(childNodes) ? null : makeList(childNodes);
	}
	
	/**
	 * 노드의 데이터를 Map형으로 반환한다.
	 * @param nodeList
	 * @return
	 */
	private Map<String, Object> makeMap(NodeList nodeList) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			
			if (node.hasChildNodes()) {
				
				short nodeType = getFirstChildType(node.getChildNodes());//node.getFirstChild().getNodeType();
				if (nodeType == Node.TEXT_NODE || nodeType == Node.CDATA_SECTION_NODE) {
					map.put(node.getNodeName(), node.getTextContent());
				} else if (nodeType == Node.ELEMENT_NODE) {
					NodeList childNodes = node.getChildNodes();
					map.put(node.getNodeName(), isMap(childNodes) ? makeMap(childNodes) : makeList(childNodes));
				} else {
					System.out.println("NodeType : "+nodeType);
				}
				
			}
			
		}
		
		return map;
	}
	
	/**
	 * 노드의 데이터를 List형으로 반환한다.
	 * @param nodeList
	 * @return
	 */
	private List<Object> makeList(NodeList nodeList) {
		List<Object> list = new ArrayList<Object>();
		
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.hasChildNodes()) {
				
				short nodeType = getFirstChildType(node.getChildNodes());//node.getFirstChild().getNodeType();
				if (nodeType == Node.TEXT_NODE || nodeType == Node.CDATA_SECTION_NODE) {
					list.add(node.getTextContent());
				} else if (nodeType == Node.ELEMENT_NODE) {
					NodeList childNodes = node.getChildNodes();
					list.add(isMap(childNodes) ? makeMap(childNodes) : makeList(childNodes));
				}
				
			}
		}
		
		return list;
	}
	
	/**
	 * 첫번째 유효한 자식 노드 타입을 반환.
	 * @param childNodes
	 * @return
	 */
	private short getFirstChildType(NodeList childNodes) {
		short nodeType = 0;
		
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node childNode = childNodes.item(i);
			nodeType = childNode.getNodeType();

			// 노드타입이 null이면서 엘리먼트이거나 노트값이 비어 있지 않으면 유효한 노트이므로 노트타입을 리턴한다.
			if ( childNode.getNodeType() == Node.ELEMENT_NODE || !childNode.getNodeValue().replaceAll(" ", "").isEmpty() ) {
//			if ( (childNode.getNodeValue() == null && childNode.getNodeType() == Node.ELEMENT_NODE)
//					|| !childNode.getNodeValue().replaceAll(" ", "").isEmpty()) {
				return nodeType;			
			}
		}
		
		return nodeType;
	}
	
	/**
	 * 노드의 타입이 Map 데이터형인지 구분.
	 * @param nodeList
	 * @return
	 */
	private boolean isMap(NodeList nodeList) {
		int validCnt = 0;
		String nodeName = null;
		
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			
			if (node.hasChildNodes()) {
				short nodeType = getFirstChildType(node.getChildNodes());//node.getFirstChild().getNodeType();
				if (nodeType == Node.TEXT_NODE || nodeType == Node.CDATA_SECTION_NODE) {
					return true;
//				} else if (nodeType == Node.ELEMENT_NODE) {
//					NodeList childNode = node.getChildNodes();
//					Element el = (Element) node;
//					return el.getElementsByTagName(childNode.item(0).getNodeName()).getLength() > 1 ? false : true;
//					return nodeList.getLength() > 1 && nodeList.item(i).getNodeName() == nodeList.item(i+1).getNodeName() ? false : true;
//				} else {
//					return false;
				}
				validCnt++;
				
				if ( validCnt > 1 && !(nodeName).equals(node.getNodeName()) ) {
					return true;
				}
				nodeName = node.getNodeName();
			}
		}

		return false;
	}
}

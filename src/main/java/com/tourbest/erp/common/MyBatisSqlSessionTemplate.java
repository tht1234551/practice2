package com.tourbest.erp.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Class Name : MyBatisSqlSessionTemplate
 * @Description :  마이바티스작업 공통 처리 클래스
 *
 * @author
 * @since 2014. 9. 30.
 */
public class MyBatisSqlSessionTemplate extends SqlSessionTemplate {

	/**로거 */
	private static final Logger logger = LoggerFactory.getLogger(MyBatisSqlSessionTemplate.class);

	/**
	 * 생성자
	 * @param sqlSessionFactory
	 */
	public MyBatisSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
		// TODO Auto-generated constructor stub
	}

	/**
	 * DataBox 형태로 반환
	 * @param arg0
	 * @return
	 */
	public DataBox selectDataBox(String arg0){
		logger.debug("[QUERY ID] [ " + arg0 + " ]");
		HashMap hm = (HashMap) super.selectOne(arg0);
		return this.convertDataBox(hm);
	}

	/**
	 * DataBox 형태로 반환
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public DataBox selectDataBox(String arg0, Object arg1){
		logger.debug("[QUERY ID] [ " + arg0 + " ]");
		arg1 =parametersFilter(arg1);
		HashMap hm = (HashMap) super.selectOne(arg0, arg1);
		return this.convertDataBox(hm);
	}

	/**
	 * ArrayList<DataBox> 형태로 반환
	 * @param arg0
	 * @return
	 */
	public ArrayList<DataBox> selectDataBoxList(String arg0) {
		logger.debug("[QUERY ID] [ " + arg0 + " ]");
		ArrayList list = (ArrayList) super.selectList(arg0);
		return this.convertArrayList(list);
	}

	/**
	 * ArrayList<DataBox> 형태로 반환
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public ArrayList<DataBox> selectDataBoxList(String arg0, Object arg1) {
		logger.debug("[QUERY ID] [ " + arg0 + " ]");
		arg1 =parametersFilter(arg1);
		ArrayList list = (ArrayList) super.selectList(arg0, arg1);
		return this.convertArrayList(list);
	}

	/**
	 * ArrayList<DataBox> 형태로 반환
	 * @param arg0
	 * @param arg1
	 * @param arg3
	 * @return
	 */
	public ArrayList<DataBox> selectDataBoxList(String arg0, Object arg1, RowBounds arg3) {
		logger.debug("[QUERY ID] [ " + arg0 + " ]");
		arg1 =parametersFilter(arg1);
		ArrayList list = (ArrayList) super.selectList(arg0, arg1, arg3);

		return this.convertArrayList(list);
	}

	/**
	 * Insert 실행
	 * @param arg0
	 * @return
	 */
	public int insert(String arg0) {
		logger.debug("[QUERY ID] [ " + arg0 + " ]");
		return super.insert(arg0);
	}

	/**
	 * Insert 실행
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public int insert(String arg0, Object arg1) {
		logger.debug("[QUERY ID] [ " + arg0 + " ]");
		arg1 =parametersFilter(arg1);
		return super.insert(arg0, arg1);
	}

	/**
	 * Update 실행
	 * @param arg0
	 * @return
	 */
	public int update(String arg0) {
		logger.debug("[QUERY ID] [ " + arg0 + " ]");
		return super.update(arg0);
	}

	/**
	 * Update 실행
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public int update(String arg0, Object arg1) {
		logger.debug("[QUERY ID] [ " + arg0 + " ]");
		arg1 =parametersFilter(arg1);
		return super.update(arg0, arg1);

	}

	/**
	 * Delete 실행
	 * @param arg0
	 * @return
	 */
	public int delete(String arg0) {
		logger.debug("[QUERY ID] [ " + arg0 + " ]");
		return super.delete(arg0);
	}

	/**
	 * Delete 실행
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public int delete(String arg0, Object arg1) {
		logger.debug("[QUERY ID] [ " + arg0 + " ]");
		arg1 =parametersFilter(arg1);
		return super.delete(arg0, arg1);
	}

	/**
	 * Select 실행
	 * @param arg0
	 * @return
	 */
	public Object selectOne(String arg0) {
		logger.debug("[QUERY ID] [ " + arg0 + " ]");
		return super.selectOne(arg0);
	}

	/**
	 * Select 실행
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public Object selectOne(String arg0, Object arg1) {
		logger.debug("[QUERY ID] [ " + arg0 + " ]");
		arg1 =parametersFilter(arg1);
		return super.selectOne(arg0, arg1);
	}

	/**
	 * HashMap을 DataBox 형태로 변환
	 * @param hm
	 * @return
	 */
	private ArrayList<DataBox> convertArrayList(ArrayList list){

		ArrayList<DataBox> result = null;
		if(list != null){
			result = new ArrayList<DataBox>();
			int size = list.size();
			for (int i = 0; i < size; i++){
				result.add(this.convertDataBox((HashMap)list.get(i)));
			}
		}
		return result;
	}

	/**
	 * HashMap을 DataBox 형태로 변환
	 * @param hm
	 * @return
	 */
	private DataBox convertDataBox(HashMap hm){

		DataBox dbox = null;
		if(hm != null){
			dbox = new DataBox("responsebox");
			String key = "";
			Object obj = "";
			Iterator<String> iter = hm.keySet().iterator();
			while(iter.hasNext()){

				key = (String) iter.next();
				obj = hm.get(key);

				//dbox.put("d_"+key.toLowerCase(), obj.toString().trim());
				dbox.put("d_"+key.toLowerCase(), obj);
			}
		}
		return dbox;
	}

	/**
	 * SQL필터링 검사
	 * @param arg1
	 * @return
	 */
	private Object parametersFilter(Object arg1){
		if(		arg1 instanceof Map ||
				arg1 instanceof Hashtable||
				arg1 instanceof HashMap ||
				arg1 instanceof RequestBox ||
				arg1 instanceof DataBox){

			String key = "";
			Object obj = "";
			Iterator<String> iter = ((Map) arg1).keySet().iterator();
			while(iter.hasNext()){

				key = (String) iter.next().toString();
				obj = ((Map<String, Object>) arg1).get(key);
				if(obj instanceof String){
					obj = replaceFilter((String)obj);
					((Map) arg1).put(key, obj);
				}
	        	//logger.debug(key + "-->" + obj);
			}
		}else if(arg1 instanceof String){
			arg1 = replaceFilter((String)arg1);
		}
		return arg1;
	}

	/**
	 * SQL필터링처리
	 * @param param
	 * @return
	 */
	private String replaceFilter(String param){
		/*
		param = ((String) param).replace("'", "");
		param = ((String) param).replace("\"", "");
		param = ((String) param).replace("--", "");
		param = ((String) param).replace("(", "");
		param = ((String) param).replace(")", "");
		param = ((String) param).replace("%", "");
		param = ((String) param).replace("#", "");
		//param = ((String) param).replace("=", "");
		param = ((String) param).replace("&", "");
		param = ((String) param).replace("<", "");
		param = ((String) param).replace(">", "");
		param = ((String) param).replace("*", "");

		param = ((String) param).replace("&#39;", "");
		param = ((String) param).replace("&#34;", "");
		param = ((String) param).replace("&#45;", "");
		param = ((String) param).replace("&#40;", "");
		param = ((String) param).replace("&#41;", "");
		param = ((String) param).replace("&#37;", "");
		param = ((String) param).replace("&#35;", "");
		param = ((String) param).replace("&#38;", "");
		param = ((String) param).replace("&lt;", "");
		param = ((String) param).replace("&gt;", "");
		param = ((String) param).replace("&#42;", "");
		*/
		/*
		param = ((String) param).replace("select", "");
		param = ((String) param).replace("delete", "");
		param = ((String) param).replace("update", "");
		param = ((String) param).replace("insert", "");
		param = ((String) param).replace("create", "");
		param = ((String) param).replace("alter", "");
		param = ((String) param).replace("drop", "");
		param = ((String) param).replace("and", "");
		param = ((String) param).replace("or", "");
		param = ((String) param).replace("union", "");
		param = ((String) param).replace("where", "");

		param = ((String) param).replace("SELECT", "");
		param = ((String) param).replace("DELETE", "");
		param = ((String) param).replace("UPDATE", "");
		param = ((String) param).replace("INSERT", "");
		param = ((String) param).replace("CREATE", "");
		param = ((String) param).replace("ALERT", "");
		param = ((String) param).replace("DROP", "");
		param = ((String) param).replace("AND", "");
		param = ((String) param).replace("OR", "");
		param = ((String) param).replace("UNION", "");
		param = ((String) param).replace("WHERE", "");
		*/

		return param;
	}
}

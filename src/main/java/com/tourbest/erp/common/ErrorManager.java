package com.tourbest.erp.common;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Class Name : ErrorManager
 * @Description : 에러정보관리 라이브러리
 *
 * @author
 * @since 2014. 9. 26.
 */
public class ErrorManager {

	/**로거 */
	private static final Logger logger = LoggerFactory.getLogger(ErrorManager.class);



	/**
	 *  Error 메시지와 stackTrace 로그에 출력
	 * @param ex
	 */
	public static void getErrorStackTrace(Throwable ex) {

		ByteArrayOutputStream baos = null;
		PrintStream ps = null;
		String error_msg = "";

		try {
			baos = new ByteArrayOutputStream();
			ps = new PrintStream(baos);
			ex.printStackTrace(ps);
			error_msg = baos.toString();
			logger.error("StackTrace : " + error_msg);
		}
		catch (Exception e) {
			logger.error("ErrorManager.getErrorStackTrace(Throwable ex) is critical error\r\n" + e.getMessage());
		}
	}

	/**
	 * Error 메시지와 stackTrace 로그를 사용자 화면출력
	 * @param ex
	 * @param out
	 */
	public static void getErrorStackTrace(Throwable ex, PrintWriter out) {

		ByteArrayOutputStream baos = null;
		PrintStream ps = null;
		try {
			baos = new ByteArrayOutputStream();
			ps = new PrintStream(baos);
			ex.printStackTrace(ps);
			String error_msg = baos.toString();
			String html="";

			if ( out != null ) {

				html +="	<h3>죄송합니다.<br />작업처리 중 오류가 발생했습니다.</h3>";
				html +="	<strong class=\"date\">["+ FormatDate.getDate("yyyy년  MM월 dd일  HH시 mm분 ss초") +"]</strong>";
				html +="    <p><textarea style=\"display:none;width:100%;height:300px;\">"+ error_msg +"</textarea></p>";
				out.println(html);
			}
		}catch ( Exception e ) {

			logger.error("ErrorManager.getErrorStackTrace(Throwable ex, PrintWriter out) is critical error\r\n" + e.getMessage());
		}finally{

			if ( ps != null ) { try { ps.close(); } catch ( Exception e1 ) { } }
			if ( baos != null ) { try { baos.close(); } catch ( Exception e1 ) { } }
			if ( out != null ) { try { out.close(); } catch ( Exception e1 ) { } }
		}
	}


	/**
	 * 에러메세지 JSP출력
	 * @param ex
	 * @param isHtml
	 * @return
	 */
    public static String getErrorStackTrace(Throwable ex, boolean isHtml) {
        ByteArrayOutputStream baos = null;
        PrintStream ps = null;
        String error_msg = "";

        try {
           baos = new ByteArrayOutputStream();
           ps = new PrintStream(baos);
           ex.printStackTrace(ps);
           error_msg = baos.toString();

           if ( isHtml){
        	   error_msg = StringManager.convertHTML(error_msg);
           }

       } catch ( Exception e ) {

    	   logger.error("ErrorManager.getErrorStackTrace(Throwable ex,  boolean isHtml) is critical error\r\n" + e.getMessage());
       }

       return error_msg;
   }

}
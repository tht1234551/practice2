package com.tourbest.erp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Service
public class WebFactory {

	protected Log log = LogFactory.getLog(WebFactory.class);
	
	public void printHtmlAfterSSO(HttpServletResponse response, String msg,
			String forwardUrl) throws Exception {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<html>");
		out.println("<head>");		
		out.println("<meta HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=UTF-8\">");
        out.println("<meta http-equiv=\"refresh\" content=\"1;url=" + forwardUrl + " \">");
		out.println("<script type=\"text/javascript\">");
		if (msg!=null && !msg.equals("")){
		out.println("alert('" + msg + "');");
		}
		out.println("location.href='" + forwardUrl + "';");
		out.println("</script><noscript>");
		out.println("새로운 페이지로 이동하는 스크립트입니다.");
		out.println("<br/>자바스크립트를 지원하지 않아 일부기능이 동작을 하지 않을 수 있습니다.");
		out.println("<br/>1초 후 자동으로 이동합니다.");
		out.println("</noscript>");
		out.println("</head>");
		out.flush();
	}
	
	public void printHtml(HttpServletResponse response, String msg,
			String forwardUrl) throws Exception {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<html>");
		out.println("<head>");		
		out.println("<meta HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=UTF-8\">");
        out.println("<meta http-equiv=\"refresh\" content=\"1;url=" + forwardUrl + " \">");
		out.println("<script type=\"text/javascript\">");
		if (msg!=null && !msg.equals("")){
		out.println("alert('" + msg + "');");
		}
		out.println("location.href='" + forwardUrl + "';");
		out.println("</script><noscript>");
		out.println("새로운 페이지로 이동하는 스크립트입니다.");
		out.println("<br/>자바스크립트를 지원하지 않아 일부기능이 동작을 하지 않을 수 있습니다.");
		out.println("<br/>1초 후 자동으로 이동합니다.");
		out.println("</noscript>");
		out.println("</head>");
		
		log.error("log.error : "+out.toString());
		log.debug("log.debug : "+out.toString());
		
		out.flush();
	}
	
	public void printWinClose(HttpServletResponse response, String msg) throws Exception {
		printWinClose(response, msg, false);
	}
	
	public void printWinClose(HttpServletResponse response, String msg, boolean openerReload) throws Exception {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<html>");
		out.println("<head>");		
		out.println("<meta HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=UTF-8\">");
		out.println("<script type=\"text/javascript\">");
		if (openerReload){
			out.println("opener.location.reload();");				
		}
		if (msg!=null && !msg.equals("")){
		out.println("alert('" + msg + "');");
		}
		out.println("self.close();");
		out.println("</script><noscript>");
		out.println("창을 닫는 스크립트 입니다.");
		out.println("<br/>자바스크립트를 지원하지 않아 일부기능이 동작을 하지 않을 수 있습니다.");
		out.println("</noscript>");
		out.println("</head>");
		out.flush();
	}
	
	public void printHtml(HttpServletResponse response, String msg,
			String forwardUrl, String pre) throws Exception {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<html>");
		out.println("<head>");		
		out.println("<meta HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=UTF-8\">");
        out.println("<meta http-equiv=\"refresh\" content=\"1;url=" + forwardUrl + " \">");
		out.println("<script type=\"text/javascript\">");
		if (msg!=null && !msg.equals("")){
		out.println("alert('" + msg + "');");
		}
		if (pre!=null && !pre.equals("")){
		out.println("if("+pre+".location){");
		out.println(pre+".location.href='" + forwardUrl + "';");
		out.println("}");
		} else {
		out.println("location.href='" + forwardUrl + "';");
		}
		out.println("</script><noscript>");
		out.println("새로운 페이지로 이동하는 스크립트입니다.");
		out.println("<br/>자바스크립트를 지원하지 않아 일부기능이 동작을 하지 않을 수 있습니다.");
		out.println("<br/>1초 후 자동으로 이동합니다.");
		out.println("</noscript>");
		out.println("</head>");
		out.flush();
	}
	
	public void printHtmlBack(HttpServletResponse response, String msg) throws Exception {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<html>");
		out.println("<head>");		
		out.println("<meta HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=UTF-8\">");
		out.println("<script type=\"text/javascript\">");
		if (msg!=null && !msg.equals("")){
		out.println("alert('" + msg + "');");
		}
		out.println("history.back();");
		out.println("</script><noscript>");
		out.println("이전 페이지로 이동하는 스크립트입니다.");
		out.println("<br/>자바스크립트를 지원하지 않아 일부기능이 동작을 하지 않을 수 있습니다.");
		out.println("<br/><a href='/'>[바로 이동하기]</>");
		out.println("</noscript>");
		out.println("</head>");
		out.flush();
		
		
	}
	
	public void printHtmlReload(HttpServletResponse response, String msg) throws Exception {
		printHtmlReload(response, msg, null);
	}
	
	public void printHtmlReload(HttpServletResponse response, String msg, String pre) throws Exception {
		pre = (pre==null) ? "" : pre+".";
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<html>");
		out.println("<head>");		
		out.println("<meta HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=UTF-8\">");
		out.println("<script type=\"text/javascript\">");
		if (msg!=null && !msg.equals("")){
			out.println("alert('" + msg + "');");
		}
			out.println(pre + "location.reload();");
		if (pre.indexOf("opener")>-1){
			out.println(pre + "self.close();");
		}
		out.println("</script><noscript>");
		out.println("페이지를 새로고침 하는 이동하는 스크립트입니다.");
		out.println("<br/>자바스크립트를 지원하지 않아 일부기능이 동작을 하지 않을 수 있습니다.");
		out.println("<br/><a href='/'>[바로 이동하기]</>");
		out.println("</noscript>");
		out.println("</head>");
		out.flush();
	}
	

	public void setForwardPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forwardPage = request.getRequestURI() + "?" + request.getQueryString();
		HttpSession session = request.getSession();
		session.setAttribute("forwardPage", forwardPage);
		// printHtml(response, "로그인또는 실명인증이 필요한 페이지입니다.\n로그인 하시겠습니까?", )

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		out.print("<script>\n");
		out.print("if(confirm('로그인 또는 실명인증이 필요한 페이지입니다. 로그인 페이지로 이동하시겠습니까?'))\n");
		out.print("location.href='/admin/portal/member/loginPage.do'\n");
		out.print("else history.back();\n");
		out.print("</script>");
		return;
	}

	public void setUserLoinForwardPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forwardPage = request.getRequestURI() + "?" + request.getQueryString();
		HttpSession session = request.getSession();
		session.setAttribute("forwardPage", forwardPage);
		// printHtml(response, "로그인또는 실명인증이 필요한 페이지입니다.\n로그인 하시겠습니까?", )

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		out.print("<script>\n");
		out.print("if(confirm('로그인이 필요한 페이지입니다. 로그인 페이지로 이동하시겠습니까?'))\n");
		out.print("location.href='/admin/portal/member/loginPage.do'\n");
		out.print("else history.back();\n");
		out.print("</script>");
		return;
	}

	public void setUserConfirmForwardPage(HttpServletRequest request,
			HttpServletResponse response, String msg) throws Exception {
		
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		out.print("<script>\n");
		out.print("if(confirm('"+msg+"'))\n");
		out.print("location.href='/portal/member/loginPage.do?menuNo=271202'\n");
		out.print("else history.back();\n");
		out.print("</script>");
		return;
	}
	
	/**
     * 해당 게시물의 비번과 로그인한 사용자의 비번이 다른 경우
     * 
     * @param		HttpServletRequest
     * @param		HttpServletResponse
     * @return		void
     * @throws	Exception
     */
	public void setUserPasswordForwardPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String forwardPage = request.getRequestURI() + "?" + request.getQueryString();
		HttpSession session = request.getSession();
		session.setAttribute("forwardPage", forwardPage);

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		out.print("<script>\n");
		out.print("alert('작성자가 본인이 아닌 경우 수정이나 삭제할 수 없습니다.');");
		out.print("history.back();");
		out.print("</script>");
		return;
	}
	
	/**
     * 첨부파일 용량 초과에 따른 메시지
     * 
     * @param		HttpServletRequest
     * @param		HttpServletResponse
     * @return		void
     * @throws	Exception
     */
	public void setExcessFileSizeForwardPage(final MultipartHttpServletRequest multiRequest, HttpServletResponse response, String msg) throws Exception {
		String forwardPage = multiRequest.getRequestURI() + "?" + multiRequest.getQueryString();
		HttpSession session = multiRequest.getSession();
		session.setAttribute("forwardPage", forwardPage);

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		out.print("<script>\n");
		out.print("alert('" +msg+ "');");
		out.print("history.back();");
		out.print("</script>");
		return;
	}
	
	public String getForwardPage(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return (String) session.getAttribute("forwardPage");
	}

	public void removeForwardPage(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("forwardPage");
	}

	public String readPetitionWebContents(String urlString) 
	{	
		String htmlContents = "";
		URLConnection connection = null;
		try 
		{
			URL url = new URL(urlString);
			connection = url.openConnection();
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		BufferedReader in = null;
		try 
		{
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));

			String inputLine;
			while ((inputLine = in.readLine()) != null) 
			{
				htmlContents += inputLine;
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(in != null) in.close();
			}
			catch(Exception ignore)
			{
				
			}
		}
		return htmlContents;
	}
   
	
}

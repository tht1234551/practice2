package com.tourbest.erp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service("pageUtil")
public class PageUtil
{
	/** Logger */
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 사용자 화면 인클루드 페이지 네비게이션
	 * @param pageSize
	 * @param total
	 * @param cpage
	 * @return
	 */
	public String getFrontIncPageNavString(String url, long totalPage, long cpage, long pageSize, String param, String _linkScriptFuncNm){
		/**
		 * TODO :  함수를 지정해서 링크 이동 방법을 선택함
		 *
		 */
		// 이전10개, 다음10개
		// 이전 마지막 페이지 0 이면 이전10개 없음
		long prev10 =  (long) (Math.floor((cpage - 1) / pageSize) * pageSize);
		long next10 = prev10 + (pageSize + 1);  // 다음 첫페이지 totalPage 보다 크면 다음10개 없음
		
		if(!StringUtil.isEmpty(_linkScriptFuncNm)) 
			_linkScriptFuncNm = " onclick=\"javascript:" + _linkScriptFuncNm +  "\" " ;
		else
			_linkScriptFuncNm = "" ;

		String urlPage = "";
		if(url == null || "".equals(url)){ urlPage = "list.do";
		}else{ urlPage = url; }

		StringBuffer sbuf = new StringBuffer();

		if ( (cpage - 1) == 0 ){
			sbuf.append("<a class=\"first\">맨앞</a>");
			sbuf.append("<a class=\"prev\">이전</a>" );

		}else{
			sbuf.append("<a href=\"#n\" url=\""+urlPage+"?__pageIndex=1"+ param +"\" " + _linkScriptFuncNm + " class=\"first\">맨앞</a>");
			sbuf.append("<a href=\"#n\" url=\""+urlPage+"?__pageIndex="+(cpage - 1)+""+ param +"\" " + _linkScriptFuncNm + " class=\"prev\">이전</a>");
		}
		sbuf.append("<span>");
		for (long i = 1 + prev10; i < next10 && i <= totalPage; i++){
			if (i == cpage){
				sbuf.append("<a class=\"on\">"+i+"</a>");
			}else{
				sbuf.append("<a href=\"#n\" url=\""+urlPage+"?__pageIndex="+i+""+ param +"\" " + _linkScriptFuncNm + " title=\""+i+"\">"+i+"</a>");
			}
		}
		sbuf.append("</span>");

		if ( cpage < totalPage ){
			sbuf.append("<a href=\"#n\" url=\""+urlPage+"?__pageIndex="+(cpage + 1)+""+ param +"\" " + _linkScriptFuncNm + " class=\"next\">다음</a>");
			sbuf.append("<a href=\"#n\" url=\""+urlPage+"?__pageIndex="+totalPage+""+ param +"\" " + _linkScriptFuncNm + " class=\"last\">맨뒤</a>");

		}else{
			sbuf.append("<a class=\"next\">다음</a>");
			sbuf.append("<a class=\"last\">맨뒤</a>");
		}
		return sbuf.toString();
	};

};

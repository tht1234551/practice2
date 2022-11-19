package com.tourbest.erp.util;

import lombok.Data;

/**
 * PaginationInfo.java
 * <p><b>NOTE:</b><pre>
 *                페이징 처리를 위한 데이터가 담기는 빈.
 *                페이징 처리에 필요한 데이터를 Required Fields, Not Required Fields 로 나누었다.
 *                
 *                Required Fields
 *                : 사용자가 입력해야 하는 필드값이다.
 *                currentPageNo : 현재 페이지 번호.
 *                recordCountPerPage : 한 페이지당 게시되는 게시물 건 수.
 *                pageSize : 페이지 리스트에 게시되는 페이지 건수.
 *                totalRecordCount : 전체 게시물 건 수.
 *                
 *                Not Required Fields
 *                : 사용자가 입력한 Required Fields 값을 바탕으로 계산하여 정해지는 값이다.
 *                totalPageCount: 페이지 개수.
 *                firstPageNoOnPageList : 페이지 리스트의 첫 페이지 번호.
 *                lastPageNoOnPageList : 페이지 리스트의 마지막 페이지 번호.
 *                firstRecordIndex : 페이징 SQL의 조건절에 사용되는 시작 rownum. 
 *                lastRecordIndex : 페이징 SQL의 조건절에 사용되는 마지막 rownum.
 *                
 *                페이징 Custom 태그인 &lt;ui:pagination&gt; 사용시에 paginationInfo 필드에 PaginationInfo 객체를 값으로 주어야 한다.
 *                </pre>
 *<pre class="code">
 *&lt;ui:pagination paginationInfo = "${paginationInfo}"
 *     type="image"
 *     jsFunction="linkPage"
 *&gt;
 *</pre>                
 * 
 * @author 실행환경 개발팀 함철
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.05.30  함철            최초 생성
 *
 * </pre>
 */
@Data
public class PaginationInfo {
		
	/**
	 * Required Fields
	 * - 이 필드들은 페이징 계산을 위해 반드시 입력되어야 하는 필드 값들이다.  
	 * 
	 * currentPageNo : 현재 페이지 번호
	 * recordCountPerPage : 한 페이지당 게시되는 게시물 건 수
	 * pageSize : 페이지 리스트에 게시되는 페이지 건수,
	 * totalRecordCount : 전체 게시물 건 수. 
	 */
	
	private long currentPageNo;
	private long recordCountPerPage;
	private long pageSize = 10;   // 기본값  10개
	private long totalRecordCount;
	
		
	/**
	 * Not Required Fields
	 * - 이 필드들은 Required Fields 값을 바탕으로 계산해서 정해지는 필드 값이다.
	 * 
	 * totalPageCount: 페이지 개수
	 * firstPageNoOnPageList : 페이지 리스트의 첫 페이지 번호
	 * lastPageNoOnPageList : 페이지 리스트의 마지막 페이지 번호
	 * firstRecordIndex : 페이징 SQL의 조건절에 사용되는 시작 rownum. 
	 * lastRecordIndex : 페이징 SQL의 조건절에 사용되는 마지막 rownum.
	 */
	
	private long totalPageCount;
	private long firstPageNoOnPageList;
	private long lastPageNoOnPageList;
	private long firstRecordIndex;
	private long lastRecordIndex;	
	
	public long getTotalPageCount() {
		totalPageCount = ((getTotalRecordCount()-1)/getRecordCountPerPage()) + 1;
		return totalPageCount;
	}
	
	public long getFirstPageNo(){
		return 1;
	}
	
	public long getLastPageNo(){
		return getTotalPageCount();		
	}
	
	public long getFirstPageNoOnPageList() {
		firstPageNoOnPageList = ((getCurrentPageNo()-1)/getPageSize())*getPageSize() + 1;
		return firstPageNoOnPageList;
	}
	
	public long getLastPageNoOnPageList() {		
		lastPageNoOnPageList = getFirstPageNoOnPageList() + getPageSize() - 1;		
		if(lastPageNoOnPageList > getTotalPageCount()){
			lastPageNoOnPageList = getTotalPageCount();
		}
		return lastPageNoOnPageList;
	}

	public long getFirstRecordIndex() {
		firstRecordIndex = (getCurrentPageNo() - 1) * getRecordCountPerPage();
		return firstRecordIndex;
	}

	public long getLastRecordIndex() {
		lastRecordIndex = getCurrentPageNo() * getRecordCountPerPage();
		return lastRecordIndex;
	}	
}

package cn.itcast.oa.domain;

import java.util.List;

/**
 * 
 * 将分页用到的相关属性封装到pageBean中
 * 
 *  recordList		本页的数据列表
 *	currentPage		当前页
 *	pageCount		总页数
 *	pageSize		每页显示多少条
 *	recordCount		总记录数
 *	beginPageIndex	页码列表的开始索引
 *	endPageIndex	页码列表的结束索引
 * @author Administrator
 *
 */
public class PageBean {
	
	//默认或者用户指定的属性
	private int pageSize;		    //每页显示多少条
	private int currentPage;	    //当前页
	
	//查询数据库得到的数据
	private List recordList;		//本页的数据列表
	private int recordCount;	    //总记录数
	
	//通过计算的到的数据
	private int pageCount;		    //总页数
	private int beginPageIndex;	    //页码列表的开始索引（包含）
	private int endPageIndex;	    //页码列表的结束索引（包含）
	
	/*
	 * 通过构造器直接计算剩下的属性
	 */
	public PageBean(int currentPage, int pageSize,  List recordList, int recordCount) {
		this.pageSize=pageSize;
		this.currentPage=currentPage;
		this.recordList=recordList;
		this.recordCount=recordCount;
		
		pageCount= (recordCount-1)/pageSize+1;
		
		//如果页码小于等于10，则直接显示10页
		if(this.pageCount<=10){
			this.beginPageIndex=1;
			this.endPageIndex=this.pageCount;
		}else{    //如果页码数大于10
			this.beginPageIndex=currentPage-4;
			this.endPageIndex=currentPage+5;
			if(beginPageIndex<1){   //当前面页码不足四个时，则直接显示十个
				this.beginPageIndex=1;
				this.endPageIndex=10;
			}
			if(endPageIndex>pageCount){  //当后面页码不足5个时，则直接显示后十个
				this.endPageIndex=pageCount;
				this.beginPageIndex=pageCount-pageSize+1;
			}
			
		}
		
	}
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public List getRecordList() {
		return recordList;
	}
	public void setRecordList(List recordList) {
		this.recordList = recordList;
	}
	public int getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getBeginPageIndex() {
		return beginPageIndex;
	}
	public void setBeginPageIndex(int beginPageIndex) {
		this.beginPageIndex = beginPageIndex;
	}
	public int getEndPageIndex() {
		return endPageIndex;
	}
	public void setEndPageIndex(int endPageIndex) {
		this.endPageIndex = endPageIndex;
	}
	
	
	
	
	
}

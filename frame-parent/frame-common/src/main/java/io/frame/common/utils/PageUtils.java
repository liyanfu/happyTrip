
package io.frame.common.utils;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.github.pagehelper.Page;

/**
 * * 分页工具类 对Page<E>结果进行包装
 * 
 * @author fury
 * 
 * @param <T>
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class PageUtils<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	// 当前页
	private int pageNumber;
	// 每页的数量
	private int pageSize;
	// 总记录数
	private long total;
	// 总页数
	private int pages;
	// 结果集
	private List<T> list;
	// 是否为第一页
	private boolean isFirstPage = false;
	// 是否为最后一页
	private boolean isLastPage = false;

	// 状态结果
	private int code = 0;

	public PageUtils() {
	}

	/**
	 * 包装Page对象
	 *
	 * @param list
	 */
	public PageUtils(List<T> list) {
		if (list instanceof Page) {
			Page page = (Page) list;
			this.pageNumber = page.getPageNum();
			this.pageSize = page.getPageSize();

			this.pages = page.getPages();
			this.list = page;
			this.total = page.getTotal();
		} else if (list instanceof Collection) {
			this.pageNumber = 1;
			this.pageSize = list.size();

			this.pages = 1;
			this.list = list;
			this.total = list.size();
		}
		if (list instanceof Collection) {
			// 判断页面边界
			judgePageBoudary();
		}
	}

	/**
	 * 判定页面边界
	 */
	private void judgePageBoudary() {
		isFirstPage = pageNumber == 1;
		isLastPage = pageNumber == pages;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public boolean isIsFirstPage() {
		return isFirstPage;
	}

	public void setIsFirstPage(boolean isFirstPage) {
		this.isFirstPage = isFirstPage;
	}

	public boolean isIsLastPage() {
		return isLastPage;
	}

	public void setIsLastPage(boolean isLastPage) {
		this.isLastPage = isLastPage;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("PageInfo{");
		sb.append("pageNumber=").append(pageNumber);
		sb.append(", pageSize=").append(pageSize);
		sb.append(", total=").append(total);
		sb.append(", pages=").append(pages);
		sb.append(", list=").append(list);
		sb.append(", isFirstPage=").append(isFirstPage);
		sb.append(", isLastPage=").append(isLastPage);
		sb.append(", navigatepageNums=");
		sb.append('}');
		return sb.toString();
	}
}

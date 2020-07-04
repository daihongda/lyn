package com.fc.test.common.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: PageParam
 * @Package: com.yhl.ros.common
 * @Description: 描述
 * @Author: wei.liu@youhualin.com
 * @Date: 2020-02-12 01:04
 * @Copyright: 悠桦林信息科技（上海）有限公司
 */
@ApiModel(value = "分页查询参数")
public class PageParam {

	@ApiModelProperty(value = "页数", required = true)
	private int pageNum = 1;

	@ApiModelProperty(value = "每页数量", required = true)
	private int pageSize = 10;

	@ApiModelProperty(value = "排序字段", required = false)
	private String orderBy;

	@ApiModelProperty(value = "排序字段", required = false)
	private List<Sorter> sorters;

	public PageParam(int pageNum, int pageSize, String orderBy, List<Sorter> sorters) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.sorters = sorters;
		if (sorters != null && sorters.size() > 0) {
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < sorters.size(); i++) {
				Sorter sorter = sorters.get(i);
				list.add(sorter.getFieldName() + " " + sorter.getAscDesc().name());
			}
			this.orderBy = String.join(",", list);
		} else {
			this.orderBy = orderBy;
		}
	}

	public String getOrderBy() {
		if (sorters != null && sorters.size() > 0) {
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < sorters.size(); i++) {
				Sorter sorter = sorters.get(i);
				list.add(sorter.getFieldName() + " " + sorter.getAscDesc().name());
			}
			return String.join(",", list);
		} else {
			return this.orderBy;
		}
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public List<Sorter> getSorters() {
		return sorters;
	}

	public void setSorters(List<Sorter> sorters) {
		this.sorters = sorters;
	}
}

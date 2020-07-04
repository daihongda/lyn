package com.fc.test.common.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName: Sorter
 * @Package: com.yhl.ros.common
 * @Description: 描述
 * @Author: wei.liu@youhualin.com
 * @Date: 2020-02-12 01:04
 * @Copyright: 悠桦林信息科技（上海）有限公司
 */
@ApiModel("排序对象")
public class Sorter {

	@ApiModelProperty("排序字段")
	private String fieldName;

	@ApiModelProperty("升序降序")
	private AscDesc ascDesc;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public AscDesc getAscDesc() {
		return ascDesc;
	}

	public void setAscDesc(AscDesc ascDesc) {
		this.ascDesc = ascDesc;
	}
}

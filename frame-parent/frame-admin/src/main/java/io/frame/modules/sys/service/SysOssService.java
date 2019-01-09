
package io.frame.modules.sys.service;

import java.util.List;

import io.frame.common.utils.PageUtils;
import io.frame.dao.entity.SysOss;

/**
 * 文件上传
 * 
 * @author fury
 *
 */
public interface SysOssService {

	PageUtils<SysOss> queryPage(SysOss sysOss);

	void insert(SysOss sysOss);

	void deleteBatchIds(List<Long> asList);
}

package io.frame.modules.job.task;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.frame.dao.entity.SysUser;
import io.frame.modules.sys.service.SysUserService;

/**
 * 团队领导奖定时任务 每天凌晨0点跑
 * 
 * 
 * @author Fury
 *
 */
@Component("teamLeaderTask")
public class TeamLeaderTask {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SysUserService sysUserService;

	public void test(String params) {
		logger.info("我是带参数的test方法，正在被执行，参数为：" + params);

		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		SysUser user = sysUserService.getSysUserById(1L);
		System.out.println(ToStringBuilder.reflectionToString(user));

	}

	public void test2() {
		logger.info("我是不带参数的test2方法，正在被执行");
	}
}

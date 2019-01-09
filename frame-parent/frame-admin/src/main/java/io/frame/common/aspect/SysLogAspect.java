
package io.frame.common.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import io.frame.common.annotation.SysLog;
import io.frame.common.utils.HttpContextUtils;
import io.frame.common.utils.IPUtils;
import io.frame.dao.entity.Log;
import io.frame.dao.entity.SysUser;
import io.frame.modules.sys.service.SysLogService;

/**
 * 系统日志，切面处理类
 * 
 * @author fury
 *
 */
@Aspect
@Component
public class SysLogAspect {
	@Autowired
	private SysLogService sysLogService;

	@Pointcut("@annotation(io.frame.common.annotation.SysLog)")
	public void logPointCut() {

	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		// 执行方法
		Object result = point.proceed();
		// 执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;

		// 保存日志
		saveSysLog(point, time);

		return result;
	}

	private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		Log sysLog = new Log();
		SysLog syslog = method.getAnnotation(SysLog.class);
		if (syslog != null) {
			// 注解上的描述
			sysLog.setOperation(syslog.value());
		}

		// 请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		sysLog.setMethod(className + "." + methodName + "()");

		// 请求的参数
		Object[] args = joinPoint.getArgs();
		String params = "";
		try {
			StringBuffer sb = new StringBuffer();
			if (args.length > 1) {
				for (int i = 0; i < args.length; i++) {
					sb.append(new Gson().toJson(args[i]));
					sb.append("|");
				}
				params = sb.toString();
			} else {
				params = new Gson().toJson(args[0]);
			}
			sysLog.setParams(params);
		} catch (Exception e) {

		}

		// 获取request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		// 设置IP地址
		sysLog.setIp(IPUtils.getIpAddr(request));

		// 用户名
		String userName = ((SysUser) SecurityUtils.getSubject().getPrincipal()).getUserName();
		sysLog.setUserName(userName);
		sysLog.setSources(1);// 后台
		sysLog.setTime(time);
		sysLog.setCreateTime(new Date());
		// 保存系统日志
		sysLogService.insert(sysLog);
	}
}

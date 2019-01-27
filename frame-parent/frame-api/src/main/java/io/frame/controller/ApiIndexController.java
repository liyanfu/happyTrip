package io.frame.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;

import io.frame.annotation.Login;
import io.frame.common.utils.R;
import io.frame.service.ProductTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * topTab页标签
 * 
 * @author fury
 *
 */
@RestController
@RequestMapping("/api")
@Api(tags = "商品类别接口")
public class ApiIndexController {

	@Autowired
	ProductTypeService productTypeService;

	@Login
	@GetMapping("getTopTabList")
	@ApiOperation(notes = "{msg:消息提示,code:状态码,productTypeList:[{productTypeId:商品类别ID,productTypeName:商品类别名称}]}", value = "获取顶部Tab标签页")
	public R getTopTabList() {
		Map<String, Object> map = Maps.newHashMap();
		map.put("productTypeList", productTypeService.getProductTypeList());
		return R.ok(map);
	}

	/**
	 * 读取图片
	 * 
	 * @param path
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/readImg")
	public void readImg(String path, HttpServletResponse response) throws Exception {
		if (path != null && !"".equals(path)) {
			FileInputStream is = null;
			try {
				is = new FileInputStream(path);
				int i = is.available(); // 得到文件大小
				byte data[] = new byte[i];
				is.read(data); // 读数据
				is.close();
				response.setContentType("image/*"); // 设置返回的文件类型
				OutputStream toClient = response.getOutputStream(); // 得到向客户端输出二进制数据的对象
				toClient.write(data); // 输出数据
				toClient.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (is != null) {
					is.close();
				}

			}
		}
	}

}

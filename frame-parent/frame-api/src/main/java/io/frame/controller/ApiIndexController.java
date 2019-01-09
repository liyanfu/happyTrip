package io.frame.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

/**
 * tab页签比赛赛事接口
 * 
 * @author fury
 *
 */
@RestController
@RequestMapping("/api")
@Api(tags = "顶部tab页签接口")
public class ApiIndexController {

	// @Autowired
	// VsTypeService vsTypeService;

	// @GetMapping("getTopTabList")
	// @ApiOperation("获取顶部Tab标签页")
//	public List<VsType> getTopTabList() {
//		return vsTypeService.getTopTabList();
//	}

}

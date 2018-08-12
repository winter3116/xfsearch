package com.xyf.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.xuyuanfeng.utlis.CommonUtils;
import com.xuyuanfeng.utlis.RecordUtils;
import com.xyf.service.ElasticSearchRestFullService;

@Controller
@RequestMapping("")
public class CoreController {

	@Autowired
	private ElasticSearchRestFullService restService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("index");
		return modelAndView;
	}
	@RequestMapping(value = "/search.do", method = RequestMethod.GET)
	public ModelAndView search(String text,HttpServletRequest req) {
		if ( CommonUtils.isEmpty(text) ) {
			return new ModelAndView("500");
		}
		RecordUtils.record(req, text);
		ModelAndView modelAndView = new ModelAndView("index");
		Map<String, Object> msg=restService.search(text, 1, "moviesdb");
		//用于保存键,最后根据这些搜索的键来创建热搜
                  		
		
		modelAndView.addObject("msg",msg);
		return modelAndView;
	}
	@RequestMapping(value = "/pageSearch.do", method = RequestMethod.GET)
	public ModelAndView search(String text,Long cur,HttpServletRequest req) 
	{
		if (CommonUtils.isEmpty(cur) || CommonUtils.isEmpty(text)) {
			return new ModelAndView("500");
		}
		RecordUtils.record(req, text);
		ModelAndView modelAndView = new ModelAndView("index");
		int current=cur.intValue();
		Map<String, Object> msg=restService.search(text, current, "moviesdb");
		modelAndView.addObject("msg",msg);
		return modelAndView;
	}
	/**
	 * ok 创建所有索引 删除整个库 管理
	 */
	@RequestMapping("/deleteAll.do")
	public ModelAndView deleteAll() {

		restService.deleteIndex("xuanfengsearch");
		ModelAndView modelAndView = new ModelAndView("Success");
		return modelAndView;
	}
	/**
	 * 创建
	 */
	@RequestMapping(value = "/createAll.do", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView modelAndView = new ModelAndView("success");
		restService.createIndex("moviesdb");
		return modelAndView;
	}
	/**
	 * ok 创建所有索引 删除整个库 管理
	 */
	@RequestMapping("/download.do")
	public ModelAndView download() {

		ModelAndView modelAndView = new ModelAndView("download");
		return modelAndView;
	}
	
	

}

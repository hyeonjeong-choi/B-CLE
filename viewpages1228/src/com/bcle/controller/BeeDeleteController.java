package com.bcle.controller;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bcle.DAO.IBeeDAO;

public class BeeDeleteController implements Controller
{
	//주요 속성 구성
	// → 인터페이스 형태의 자료형을 속성으로 구성
	private IBeeDAO dao;

	// setter 구성
	public void setDao(IBeeDAO dao)
	{
		this.dao = dao;
	}
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView mav = new ModelAndView();

		// 세션 처리 과정 추가 ------------------------------------------
		HttpSession session = request.getSession();
		if (session.getAttribute("id") == null) // -- 로그인이 되어 있지 않은 상황
		{
			mav.setViewName("redirect:loginpage.action");
			return mav;
		}
		// ------------------------------------------ 세션 처리 과정 추가
		
		String beeId = request.getParameter("beeId");
		String cid = request.getParameter("cid");
		
		try
		{
			dao.remove(beeId);
			mav.setViewName("redirect:clubmain.action?cid="+cid);
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}

		return mav;
	}

}

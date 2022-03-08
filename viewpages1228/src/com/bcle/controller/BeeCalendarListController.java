package com.bcle.controller;


import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bcle.DAO.IBeeDAO;
import com.bcle.DTO.Bee;

public class BeeCalendarListController implements Controller
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

		HttpSession session = request.getSession();
		// 세션 처리 과정 추가 ------------------------------------------
		if (session.getAttribute("id") == null) // -- 로그인이 되어 있지 않은 상황
		{
			mav.setViewName("redirect:loginpage.action");
			return mav;
		}
		// ------------------------------------------ 세션 처리 과정 추가

		ArrayList<Bee> beeList = new ArrayList<Bee>();
		String cid = request.getParameter("cid");
		
		Object getId = session.getAttribute("id");
		String id = (String)getId;
		try
		{
			// 동아리의 모임 리스트 출력
			beeList = dao.list(cid);
			
			// 동아리의 회원 여부
			//System.out.println(dao.checkClubMem(id, cid));
			mav.addObject("checkClubMem", dao.checkClubMem(id, cid));
			
			mav.addObject("beeList", beeList);
			mav.addObject("cid", cid);

			mav.setViewName("/WEB-INF/view/ClubCalendarList.jsp");
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}

		return mav;
	}

}

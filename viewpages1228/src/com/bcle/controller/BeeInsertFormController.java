package com.bcle.controller;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bcle.DAO.IClubDAO;
import com.bcle.DAO.IMemberInfoDAO;
import com.bcle.DTO.Club;

public class BeeInsertFormController implements Controller
{
	private IMemberInfoDAO dao;
	
	private IClubDAO clubDao;
	

	public void setDao(IMemberInfoDAO dao)
	{
		this.dao = dao;
	}


	public void setClubDao(IClubDAO clubDao)
	{
		this.clubDao = clubDao;
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
		Object getId = session.getAttribute("id");
		String id = (String)getId;
		String nickname = null;
		String cid = request.getParameter("cid");
		
		Club club = new Club();
		try
		{
			//System.out.println("BeeInsertFormController" + cid);
			nickname = dao.searchNickname(id);
			club = clubDao.showClub(cid);
			mav.addObject("nickname", nickname);
			mav.addObject("cid", cid);
			mav.addObject("club", club);
			mav.setViewName("/WEB-INF/view/Meeting_enroll.jsp");
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}

		return mav;
	}

}

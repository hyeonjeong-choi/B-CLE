/* ==============================
 * AjaxCategoryController.java
 * - 사용자 정의 컨트롤러 
 * ==============================*/
package com.bcle.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bcle.DAO.IClubDAO;

public class ClubJoinDeleteController implements Controller
{
	private IClubDAO dao;
	
	public void setDao(IClubDAO dao)
	{
		this.dao = dao;
	}
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView mav = new ModelAndView();
		
		HttpSession session = request.getSession();
		if (session.getAttribute("id") == null) // -- 로그인이 되어 있지 않은 상황
		{
			mav.setViewName("redirect:loginpage.action");
			return mav;
		}
		// ------------------------------------------------- 세션 처리

		//데이터 수신 (ClubBeforeOpenedContents.jsp)
		String cid = request.getParameter("cid");
		
		Object getId = session.getAttribute("id");
		String id = (String)getId;;
		
		try
		{
			dao.joinClubDelete2(cid, id);
			
			mav.setViewName("redirect:clubmain.action?cid="+cid);
			
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
			return mav;
	}
	


}

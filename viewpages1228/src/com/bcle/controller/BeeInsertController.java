package com.bcle.controller;


import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bcle.DAO.IBeeDAO;
import com.bcle.DTO.Bee;

public class BeeInsertController implements Controller
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
		
		String typeId = request.getParameter("typeId");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String fee = request.getParameter("fee");
		String beeTime1 = request.getParameter("beeTime1");
		String beeTime2 = request.getParameter("beeTime2");
		String location = request.getParameter("location");
		String min = request.getParameter("min");
		String max = request.getParameter("max");
		
		String cid = request.getParameter("cid");
		Object getId = session.getAttribute("id");
		String id = (String)getId;
		System.out.println(id);
		try
		{
			Bee bee = new Bee();
			
			bee.setTypeId(typeId);
			bee.setTitle(title);
			bee.setContent(content);
			bee.setFee(Integer.parseInt(fee));
			bee.setBeeTime(beeTime1 + " " + beeTime2 + ":00");
			bee.setLocation(location);
			bee.setMin(Integer.parseInt(min));
			bee.setMax(Integer.parseInt(max));
			
			//bee.setClubmemId(clubmemId); // 나중에는 세션에서 받아가지고 함수 써서 쇼로록 넣기
			bee.setClubmemId(dao.searchClubmemId(id, cid));
			// 함수 만들기, ID랑 CID(동아리코드) 일치하는 사람의 CLUBMEM_ID 얻어서
			
			//System.out.println("BeeInsertController cid 확인 :" + cid);
			
			dao.add(bee);
			mav.setViewName("redirect:clubmain.action?cid="+cid);
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}

		return mav;
	}

}

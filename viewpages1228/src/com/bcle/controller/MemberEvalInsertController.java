package com.bcle.controller;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bcle.DAO.IMemberEvalDAO;
import com.bcle.DTO.MemberEval;

public class MemberEvalInsertController implements Controller
{
	//주요 속성 구성
	// → 인터페이스 형태의 자료형을 속성으로 구성
	
	private IMemberEvalDAO dao;

	// setter 구성
	public void setDao(IMemberEvalDAO dao)
	{
		this.dao = dao;
	}
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView mav = new ModelAndView();
		
		HttpSession session = request.getSession();
		// 세션 처리 과정 추가 ------------------------------------------
		/*
		 * 
		 * 
		 * if (session.getAttribute("name") == null) // -- 로그인이 되어 있지 않은 상황 {
		 * mav.setViewName("redirect:loginform.action"); return mav; } else if
		 * (session.getAttribute("admin") == null) // -- 로그인은 되었지만 관리자가 아닌 상황 {
		 * mav.setViewName("redirect:logout.action"); return mav; // -- 로그인은 되어 있지만 이 때
		 * 클라이언트는 // 일반 직원으로 로그인 되어 있는 상황이므로 // 로그아웃 액션 처리하여 다시 관리자로 로그인할 수 있도록 처리 }
		 */
		// ------------------------------------------ 세션 처리 과정 추가
		
		String reqId = request.getParameter("target");
		String writerId = request.getParameter("evaluator");
		//System.out.println(reqId);
		//System.out.println(writerId);
		
		//System.out.println(request.getParameter("evalList"));
		
		String cid = request.getParameter("cid");
		String beeId = request.getParameter("beeId");
		
		String[] list = request.getParameterValues("evalList");
		
		try
		{
			
			MemberEval dto = new MemberEval();
			
			dto.setWriterId(writerId);
			dto.setReqId(reqId);
			
			for (int i=0; i<list.length; i++)
			{
				dto.setEvalId(list[i]);
				dao.add(dto);
			}
			
			mav.setViewName("redirect:beedetailpage.action?beeId="+beeId+"&cid="+cid);
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}

		return mav;
	}

}

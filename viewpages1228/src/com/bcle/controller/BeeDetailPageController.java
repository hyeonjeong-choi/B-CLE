package com.bcle.controller;


import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bcle.DAO.IBeeDAO;
import com.bcle.DAO.IBeeEvalDAO;
import com.bcle.DAO.IBeePhotoDAO;
import com.bcle.DAO.IMemberEvalDAO;
import com.bcle.DAO.IMemberInfoDAO;
import com.bcle.DAO.IReqBeeDAO;
import com.bcle.DTO.Bee;
import com.bcle.DTO.BeeEval;
import com.bcle.DTO.ReqBee;

public class BeeDetailPageController implements Controller
{
	//주요 속성 구성
	// → 인터페이스 형태의 자료형을 속성으로 구성
	private IBeeDAO dao;
	private IReqBeeDAO reqBeeDao;
	private IMemberInfoDAO memberInfoDao;
	private IBeeEvalDAO beeEvalDao;
	private IBeePhotoDAO beePhotoDao;
	private IMemberEvalDAO memberEvalDao;

	// setter 구성
	public void setDao(IBeeDAO dao)
	{
		this.dao = dao;
	}
	
	public void setReqBeeDao(IReqBeeDAO reqBeeDao)
	{
		this.reqBeeDao = reqBeeDao;
	}

	public void setMemberInfoDao(IMemberInfoDAO memberInfoDao)
	{
		this.memberInfoDao = memberInfoDao;
	}

	public void setBeeEvalDao(IBeeEvalDAO beeEvalDao)
	{
		this.beeEvalDao = beeEvalDao;
	}

	public void setBeePhotoDao(IBeePhotoDAO beePhotoDao)
	{
		this.beePhotoDao = beePhotoDao;
	}

	public void setMemberEvalDao(IMemberEvalDAO memberEvalDao)
	{
		this.memberEvalDao = memberEvalDao;
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

		Bee bee = new Bee();
		ArrayList<ReqBee> reqBee = new ArrayList<ReqBee>();
		ArrayList<BeeEval> beeEvalList = new ArrayList<BeeEval>();
		ArrayList<String> urlList = new ArrayList<String>();
		
		// 로그인한 사람이 평가 남긴 사람 목록
		ArrayList<String> reqIdList = new ArrayList<String>();
		
		String beeId = request.getParameter("beeId");
		Object getId = session.getAttribute("id");
		String id = (String)getId;
		
		String cid = request.getParameter("cid");
		//System.out.println(cid);
		
		int count = 0;
		
		//System.out.println(beeId);
		try
		{
			bee = dao.showBee(beeId);
			
			reqBee = reqBeeDao.reqList(beeId);
			count = reqBeeDao.count(beeId);
			
			beeEvalList = beeEvalDao.list(beeId);
			urlList = beePhotoDao.beePic(beeId);
			//System.out.println(urlList);
			
			reqIdList = memberEvalDao.getReqId(beeId, dao.searchClubmemId(id, cid));
			
			mav.addObject("s_clubmemId", dao.searchClubmemId(id, cid));
			mav.addObject("cid", cid);
			mav.addObject("bee", bee);
			mav.addObject("reqBee", reqBee);
			mav.addObject("count", count);
			mav.addObject("nickname", memberInfoDao.searchNickname(id));
			mav.addObject("beeEvalList", beeEvalList);
			mav.addObject("urlList", urlList);
			mav.addObject("countPhoto", beePhotoDao.count(beeId));
			mav.addObject("reqIdList", reqIdList);
			

			mav.setViewName("/WEB-INF/view/Meeting_main.jsp");
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}

		return mav;
	}

}

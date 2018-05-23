package action;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;

import service.PerformancePlanService;
import util.Result;

public class VenueReleasePerformancePlanAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

	private static final long serialVersionUID = 7377659882887930649L;
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	
	@Override
	public void setServletRequest(HttpServletRequest requset) {
		this.request = requset;
	}
	
	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public String execute() throws Exception{
		
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		HttpSession session = request.getSession(false);
		PrintWriter out = response.getWriter();
		
		int vid = Integer.parseInt(String.valueOf(session.getAttribute("vid")));

		String ppname = request.getParameter("ppname");
		String briefIntro = request.getParameter("briefIntro");
		char performanceCategory = request.getParameter("performanceCategory").charAt(0);

		DateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTimeStr = request.getParameter("startTime")+":00";
		Timestamp startTime = new Timestamp(dateFormate.parse(startTimeStr).getTime());
		String endTimeStr = request.getParameter("endTime")+":00";
		Timestamp endTime = new Timestamp(dateFormate.parse(endTimeStr).getTime());
		
		int seatVipPrice = Integer.parseInt(request.getParameter("seatVipPrice"));
		int seatAPrice = Integer.parseInt(request.getParameter("seatAPrice"));
		int seatBPrice = Integer.parseInt(request.getParameter("seatBPrice"));
		int seatCPrice = Integer.parseInt(request.getParameter("seatCPrice"));
		int seatDPrice = Integer.parseInt(request.getParameter("seatDPrice"));
		
		PerformancePlanService performancePlanService = (PerformancePlanService)ctx.getBean("PerformancePlanService"); 
		Result releaseResult = performancePlanService.releasePlan(ppname, vid, startTime, endTime, performanceCategory, briefIntro, seatVipPrice, seatAPrice, seatBPrice, seatCPrice, seatDPrice);
		System.out.println("场馆发布计划结果："+releaseResult.getResult()+"   "+releaseResult.getDescription());
		if(releaseResult.getResult()) {
	        out.write(releaseResult.getDescription());
	        out.close();
	        return "success";
		}
		else {
			out.write("fail");
			out.close();
			return "fail";
		}
	}

}

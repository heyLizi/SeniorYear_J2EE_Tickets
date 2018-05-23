package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;

import service.VenueService;
import util.Result;

public class VenueUpdateVenueInfoAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

	private static final long serialVersionUID = -2598800704933872792L;
	
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

		String vname = request.getParameter("vname");
		String province = request.getParameter("province");
		String city = request.getParameter("city");
		String area = request.getParameter("area");
		String address = request.getParameter("address");
		int seatVipRowNum = Integer.parseInt(request.getParameter("seatVipRowNum"));
		int seatVipColNum = Integer.parseInt(request.getParameter("seatVipColNum"));
		int seatARowNum = Integer.parseInt(request.getParameter("seatARowNum"));
		int seatAColNum = Integer.parseInt(request.getParameter("seatAColNum"));
		int seatBRowNum = Integer.parseInt(request.getParameter("seatBRowNum"));
		int seatBColNum = Integer.parseInt(request.getParameter("seatBColNum"));
		int seatCRowNum = Integer.parseInt(request.getParameter("seatCRowNum"));
		int seatCColNum = Integer.parseInt(request.getParameter("seatCColNum"));
		int seatDRowNum = Integer.parseInt(request.getParameter("seatDRowNum"));
		int seatDColNum = Integer.parseInt(request.getParameter("seatDColNum"));
		
		VenueService venueService = (VenueService)ctx.getBean("VenueService"); 
		Result updateResult = venueService.updateVenue(vid, vname, province, city, area, address, 
														seatVipRowNum, seatVipColNum, seatARowNum, seatAColNum, seatBRowNum, 
														seatBColNum, seatCRowNum, seatCColNum, seatDRowNum, seatDColNum);
		System.out.println("场馆提交更新结果："+updateResult.getResult()+"   "+updateResult.getDescription());
		if(updateResult.getResult()) {
	        out.write(updateResult.getDescription());
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

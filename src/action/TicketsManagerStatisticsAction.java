package action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;

import po.MemberBean;
import po.VenueBean;
import service.MemberService;
import service.TicketsCompanyInfoService;
import service.VenueService;

public class TicketsManagerStatisticsAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{
	
	private static final long serialVersionUID = -4529047630742310471L;
	
	private HttpServletRequest request;
	private HttpServletResponse response;

	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public String execute() throws Exception{
		
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = response.getWriter();

		MemberService memberService = (MemberService)ctx.getBean("MemberService");
		VenueService venueService = (VenueService)ctx.getBean("VenueService");
		TicketsCompanyInfoService ticketsCompService = (TicketsCompanyInfoService)ctx.getBean("TicketsCompanyInfoService");
		List<MemberBean> members = memberService.showAllMembers();
		List<VenueBean> venues = venueService.showAllVenues();

		JSONObject json = new JSONObject();
		
		if(members != null && venues != null) {

			json.put("memberTotalNum",members.size());
			json.put("venueTotalNum", venues.size());
			json.put("profitMoney", ticketsCompService.showTicketsInfo().getBalance());
				
			out.write(json.toString());
			out.close();
			return "success";
		}else{
			out.write("fail");
			out.close();
			return "fail";
		}
	}

}

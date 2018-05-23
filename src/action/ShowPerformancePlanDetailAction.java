package action;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;

import po.PerformancePlanBean;
import service.PerformancePlanService;
import service.VenueService;

public class ShowPerformancePlanDetailAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

	private static final long serialVersionUID = 852030321153603858L;
	
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
		
		PrintWriter out = response.getWriter();

		int ppid = Integer.parseInt(request.getParameter("ppid"));
		
		PerformancePlanService performancePlanService = (PerformancePlanService)ctx.getBean("PerformancePlanService");
		VenueService venueService = (VenueService)ctx.getBean("VenueService");
		PerformancePlanBean performancePlan = performancePlanService.showPerformancePlanDetail(ppid);

		JSONObject json = new JSONObject();
		
		if(performancePlan != null) {
				
			json.put("ppid", performancePlan.getPpid());
			json.put("category", getStringCategory(performancePlan.getCategory()));
			json.put("ppname", performancePlan.getPpname());
			json.put("briefIntro", performancePlan.getBriefIntro());
			json.put("vname", venueService.showVenueDetail(performancePlan.getVid()).getVname());
			json.put("startTime", getStringTimestamp(performancePlan.getStartTime()));
			json.put("endTime", getStringTimestamp(performancePlan.getEndTime()));
			json.put("seatVipPrice", performancePlan.getSeatVipPrice());
			json.put("seatAPrice", performancePlan.getSeatAPrice());
			json.put("seatBPrice", performancePlan.getSeatBPrice());
			json.put("seatCPrice", performancePlan.getSeatCPrice());
			json.put("seatDPrice", performancePlan.getSeatDPrice());
  
			out.write(json.toString());
	        out.close();
	        return "success";
		}
		else {
			out.write("fail");
			out.close();
			return "fail";
		}
	}
	
	public String getStringCategory(char category) {
		switch(category) {
			case 'C': return "演唱会";
			case 'M': return "音乐会";
			case 'D': return "话剧";
			default: return "无类型";
		}
	}

	public String getStringTimestamp(Timestamp time) {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		return sdf.format(time);
	}
	
}

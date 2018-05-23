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

public class VenueShowPerformancePlanDetailAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

	private static final long serialVersionUID = -4456110694103194417L;
	
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
		PerformancePlanBean plan = performancePlanService.showPerformancePlanDetail(ppid);
		JSONObject json = new JSONObject();

		if(plan != null) {
				
			json.put("ppid", plan.getPpid());
			json.put("ppname", plan.getPpname());
			json.put("ppCategory", getStringCategory(plan.getCategory()));
			json.put("briefIntro", plan.getBriefIntro());
			json.put("startTime", getStringTimestamp(plan.getStartTime()));
			json.put("endTime", getStringTimestamp(plan.getEndTime()));
			json.put("seatVipPrice", plan.getSeatVipPrice());
			json.put("seatAPrice", plan.getSeatAPrice());
			json.put("seatBPrice", plan.getSeatBPrice());
			json.put("seatCPrice", plan.getSeatCPrice());
			json.put("seatDPrice", plan.getSeatDPrice());

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

	public String getStringCategory(char state) {
		switch(state) {
			case 'C': return "演唱会";
			case 'D': return "音乐会";
			case 'M': return "话剧";
			default: return "无类型";
		}
	}
	
	public String getStringTimestamp(Timestamp time) {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		return sdf.format(time);
	}
	
}

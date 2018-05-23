package action;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;

import po.PerformancePlanBean;
import service.PerformancePlanService;

public class VenueShowAvailPerformancePlanListAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

	private static final long serialVersionUID = 5453969166347828104L;
	
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

		PerformancePlanService performancePlanService = (PerformancePlanService)ctx.getBean("PerformancePlanService"); 
		List<PerformancePlanBean> plans = performancePlanService.showAllAvailPerformancePlansOfAVenue(vid);

		JSONArray array = new JSONArray();

		if(plans != null && plans.size() > 0) {
			for(int i=0; i<plans.size(); i++) {
				JSONObject tempJson = new JSONObject();
				
				tempJson.put("ppid", plans.get(i).getPpid());
				tempJson.put("ppname", plans.get(i).getPpname());
				tempJson.put("ppCategory", getStringCategory(plans.get(i).getCategory()));
				tempJson.put("briefIntro", plans.get(i).getBriefIntro());
				tempJson.put("startTime", getStringTimestamp(plans.get(i).getStartTime()));
				tempJson.put("endTime", getStringTimestamp(plans.get(i).getEndTime()));
				tempJson.put("seatVipPrice", plans.get(i).getSeatVipPrice());
				tempJson.put("seatAPrice", plans.get(i).getSeatAPrice());
				tempJson.put("seatBPrice", plans.get(i).getSeatBPrice());
				tempJson.put("seatCPrice", plans.get(i).getSeatCPrice());
				tempJson.put("seatDPrice", plans.get(i).getSeatDPrice());
				
				array.put(tempJson);
			}    
			out.write(array.toString());
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

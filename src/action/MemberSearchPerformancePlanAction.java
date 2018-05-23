package action;

import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;

import po.PerformancePlanBean;
import service.PerformancePlanService;
import service.VenueService;

public class MemberSearchPerformancePlanAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

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

		String province = request.getParameter("province");
		String city = request.getParameter("city");
		String area = request.getParameter("area");
		char performanceCategory = request.getParameter("performanceCategory").charAt(0);
		
		DateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd");
		String performanceDateStr = request.getParameter("performanceDate");
		Date performanceDate = new Date(dateFormate.parse(performanceDateStr).getTime()); 
		System.out.println(province+" "+city+" "+area+" "+performanceCategory+" "+performanceDate);
		
		PerformancePlanService performancePlanService = (PerformancePlanService)ctx.getBean("PerformancePlanService");
		VenueService venueService = (VenueService)ctx.getBean("VenueService");
		List<PerformancePlanBean> performancePlans = performancePlanService.showAllPerformancePlansInThisCondition(performanceCategory, performanceDate, province, city, area);

		JSONArray array = new JSONArray();

		if(performancePlans != null && performancePlans.size() > 0) {
			for(int i=0; i<performancePlans.size(); i++) {
				JSONObject tempJson = new JSONObject();
				
				tempJson.put("ppid", performancePlans.get(i).getPpid());
				tempJson.put("category", getStringCategory(performancePlans.get(i).getCategory()));
				tempJson.put("ppname", performancePlans.get(i).getPpname());
				tempJson.put("briefIntro", performancePlans.get(i).getBriefIntro());
				tempJson.put("vname", venueService.showVenueDetail(performancePlans.get(i).getVid()).getVname());
				tempJson.put("startTime", getStringTimestamp(performancePlans.get(i).getStartTime()));
				tempJson.put("endTime", getStringTimestamp(performancePlans.get(i).getEndTime()));
				tempJson.put("seatVipPrice", performancePlans.get(i).getSeatVipPrice());
				tempJson.put("seatAPrice", performancePlans.get(i).getSeatAPrice());
				tempJson.put("seatBPrice", performancePlans.get(i).getSeatBPrice());
				tempJson.put("seatCPrice", performancePlans.get(i).getSeatCPrice());
				tempJson.put("seatDPrice", performancePlans.get(i).getSeatDPrice());

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

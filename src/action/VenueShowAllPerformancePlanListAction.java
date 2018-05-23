package action;

import java.io.PrintWriter;
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

public class VenueShowAllPerformancePlanListAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

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
		
		HttpSession session = request.getSession(false);
		PrintWriter out = response.getWriter();

		int vid = Integer.parseInt(String.valueOf(session.getAttribute("vid")));

		PerformancePlanService performancePlanService = (PerformancePlanService)ctx.getBean("PerformancePlanService"); 
		List<PerformancePlanBean> plans = performancePlanService.showAllPerformancePlansOfAVenue(vid);

		JSONArray array = new JSONArray();

		if(plans != null && plans.size() > 0) {
			for(int i=0; i<plans.size(); i++) {
				JSONObject tempJson = new JSONObject();
				
				tempJson.put("ppid", plans.get(i).getPpid());
				tempJson.put("ppname", plans.get(i).getPpname());
				tempJson.put("ppCategory", getStringCategory(plans.get(i).getCategory()));
				
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
	
}

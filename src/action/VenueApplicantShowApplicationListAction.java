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

import po.VenueApplicationBean;
import service.VenueService;

public class VenueApplicantShowApplicationListAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

	private static final long serialVersionUID = 263660199043375504L;
	
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

		int vaerid = Integer.parseInt(String.valueOf(session.getAttribute("vaerid")));

		VenueService venueService = (VenueService)ctx.getBean("VenueService"); 
		List<VenueApplicationBean> applications = venueService.showAllVenueApplicationsOfAnApplicant(vaerid);

		JSONArray array = new JSONArray();

		if(applications != null && applications.size() > 0) {
			for(int i=0; i<applications.size(); i++) {
				JSONObject tempJson = new JSONObject();
				
				tempJson.put("vaid", applications.get(i).getVaid()+"");
				tempJson.put("vaState", getStringState(applications.get(i).getVaState()));
				tempJson.put("vname", applications.get(i).getVname());
				
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

	public String getStringState(char state) {
		switch(state) {
			case 'A': return "已通过";
			case 'R': return "未通过";
			case 'U': return "待审批";
			default: return "无状态";
		}
	}
	
}

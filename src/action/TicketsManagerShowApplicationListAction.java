package action;

import java.io.PrintWriter;
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

import po.VenueApplicationBean;
import service.VenueService;

public class TicketsManagerShowApplicationListAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{
	
	private static final long serialVersionUID = -5066007334691660301L;
	
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

		VenueService venueService = (VenueService) ctx.getBean("VenueService");
		List<VenueApplicationBean> venueApplications = venueService.showAllUncheckedVenueApplications();

		JSONArray array = new JSONArray();

		if(venueApplications != null && venueApplications.size() > 0) {
			for(int i=0; i<venueApplications.size(); i++) {
				JSONObject tempJson = new JSONObject();
				
				tempJson.put("vaid", venueApplications.get(i).getVaid());
				tempJson.put("vaerid", getStringID(venueApplications.get(i).getVaerid()+""));
				tempJson.put("vname", venueApplications.get(i).getVname());
				
				array.put(tempJson);
			}    
			out.write(array.toString());
	        out.close();
	        return "success";
		}
		else{
			out.write("fail");
			out.close();
			return "fail";
		}
	}

	public String getStringID(String id){
		String result = id;
		for(int i=id.length(); i<7; i++){
			result = "0"+result;
		}
		return result;
	}
	
}

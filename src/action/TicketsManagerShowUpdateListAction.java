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

import po.VenueUpdateBean;
import service.VenueService;

public class TicketsManagerShowUpdateListAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{
	
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
		List<VenueUpdateBean> venueUpdates = venueService.showAllUncheckedVenueUpdates();

		JSONArray array = new JSONArray();

		if(venueUpdates != null && venueUpdates.size() > 0) {
			for(int i=0; i<venueUpdates.size(); i++) {
				JSONObject tempJson = new JSONObject();
				
				tempJson.put("vuid", venueUpdates.get(i).getVuid());
				tempJson.put("vid", getStringID(venueUpdates.get(i).getVid()+""));
				tempJson.put("vname", venueUpdates.get(i).getVname());
				
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

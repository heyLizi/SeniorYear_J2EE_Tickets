package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;

import po.VenueApplicantBean;
import service.VenueService;

public class VenueApplicantShowApplicantDetailAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

	private static final long serialVersionUID = -5379007258611290146L;
	
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
		VenueApplicantBean venueApplicant = venueService.showApplicantDetail(vaerid);

		JSONObject json = new JSONObject();
		
		if(venueApplicant != null) {
			json.put("vaerid", getStringID(venueApplicant.getVaerid()+""));
			json.put("telephone", venueApplicant.getTelephone());
			json.put("vaerPasswd", getStringPassword(venueApplicant.getVaerPasswd()));
			
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
	
	public String getStringID(String id){
		String result = id;
		for(int i=id.length(); i<7; i++){
			result = "0" + result;
		}
		return result;
	}
	
	public String getStringPassword(String password){
		String result = "";
		for(int i=0; i<password.length(); i++){
			result = "*" + result;
		}
		return result;
	}
	
}

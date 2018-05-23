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

import po.VenueAccountBean;
import po.VenueBean;
import service.VenueService;

public class VenueShowVenueDetailAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

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

		int vid = Integer.parseInt(String.valueOf(session.getAttribute("vid")));

		VenueService venueService = (VenueService)ctx.getBean("VenueService"); 
		VenueBean venue = venueService.showVenueDetail(vid);
		VenueAccountBean venueAccount = venueService.showVenueAccountDetail(vid);

		JSONObject json = new JSONObject();
		
		if(venue != null && venueAccount != null) {												

			json.put("vid", getStringID(venue.getVid()+""));
			json.put("vname", venue.getVname());
			json.put("vPasswd", getStringPassword(venue.getVpasswd()));
			json.put("province", venue.getProvince());
			json.put("city", venue.getCity());
			json.put("area", venue.getArea());
			json.put("address", venue.getAddress());
			json.put("seatVipRowNum", venue.getSeatVipRowNum());
			json.put("seatVipColNum", venue.getSeatVipColNum());
			json.put("seatARowNum", venue.getSeatARowNum());
			json.put("seatAColNum", venue.getSeatAColNum());
			json.put("seatBRowNum", venue.getSeatBRowNum());
			json.put("seatBColNum", venue.getSeatBColNum());
			json.put("seatCRowNum", venue.getSeatCRowNum());
			json.put("seatCColNum", venue.getSeatCColNum());
			json.put("seatDRowNum", venue.getSeatDRowNum());
			json.put("seatDColNum", venue.getSeatDColNum());
			json.put("balance", venueAccount.getBalance());
			
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

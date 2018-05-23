package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;

import po.VenueApplicationBean;
import service.VenueService;

public class TicketsManagerShowApplicationDetailAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{
	
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

		int vaid = Integer.parseInt(request.getParameter("vaid"));

		VenueService venueService = (VenueService) ctx.getBean("VenueService");
		VenueApplicationBean venueApplication = venueService.showVenueApplicationDetail(vaid);

		JSONObject json = new JSONObject();

		if(venueApplication != null) {
		
			json.put("vaid", venueApplication.getVaid());
			json.put("vaerid", getStringID(venueApplication.getVaerid()+""));
			json.put("vname", venueApplication.getVname());
			json.put("province", venueApplication.getProvince());
			json.put("city", venueApplication.getCity());
			json.put("area", venueApplication.getArea());
			json.put("address", venueApplication.getAddress());
			json.put("seatVipRowNum", venueApplication.getSeatVipRowNum());
			json.put("seatVipColNum", venueApplication.getSeatVipColNum());
			json.put("seatARowNum", venueApplication.getSeatARowNum());
			json.put("seatAColNum", venueApplication.getSeatAColNum());
			json.put("seatBRowNum", venueApplication.getSeatBRowNum());
			json.put("seatBColNum", venueApplication.getSeatBColNum());
			json.put("seatCRowNum", venueApplication.getSeatCRowNum());
			json.put("seatCColNum", venueApplication.getSeatCColNum());
			json.put("seatDRowNum", venueApplication.getSeatDRowNum());
			json.put("seatDColNum", venueApplication.getSeatDColNum());
		
			out.write(json.toString());
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

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

import po.VenueUpdateBean;
import service.VenueService;

public class TicketsManagerShowUpdateDetailAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{
	
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

		int vuid = Integer.parseInt(request.getParameter("vuid"));

		VenueService venueService = (VenueService) ctx.getBean("VenueService");
		VenueUpdateBean venueUpdate = venueService.showVenueUpdateDetail(vuid);

		JSONObject json = new JSONObject();

		if(venueUpdate != null) {
		
			json.put("vuid", venueUpdate.getVuid());
			json.put("vid", getStringID(venueUpdate.getVid()+""));
			json.put("vname", venueUpdate.getVname());
			json.put("province", venueUpdate.getProvince());
			json.put("city", venueUpdate.getCity());
			json.put("area", venueUpdate.getArea());
			json.put("address", venueUpdate.getAddress());
			json.put("seatVipRowNum", venueUpdate.getSeatVipRowNum());
			json.put("seatVipColNum", venueUpdate.getSeatVipColNum());
			json.put("seatARowNum", venueUpdate.getSeatARowNum());
			json.put("seatAColNum", venueUpdate.getSeatAColNum());
			json.put("seatBRowNum", venueUpdate.getSeatBRowNum());
			json.put("seatBColNum", venueUpdate.getSeatBColNum());
			json.put("seatCRowNum", venueUpdate.getSeatCRowNum());
			json.put("seatCColNum", venueUpdate.getSeatCColNum());
			json.put("seatDRowNum", venueUpdate.getSeatDRowNum());
			json.put("seatDColNum", venueUpdate.getSeatDColNum());
		
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

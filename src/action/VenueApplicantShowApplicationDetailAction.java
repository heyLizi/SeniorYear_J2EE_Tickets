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

public class VenueApplicantShowApplicationDetailAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

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
		
		PrintWriter out = response.getWriter();

		int vaid = Integer.parseInt(request.getParameter("vaid"));

		VenueService venueService = (VenueService)ctx.getBean("VenueService"); 
		VenueApplicationBean venueApplication = venueService.showVenueApplicationDetail(vaid);

		JSONObject json = new JSONObject();
		
		if(venueApplication != null) {

			json.put("vaid", venueApplication.getVaid()+"");
			json.put("vaState", getStringState(venueApplication.getVaState()));
			json.put("vname", venueApplication.getVname());
			json.put("vPasswd", getStringPassword(venueApplication.getVpasswd()));
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
			if(venueApplication.getVid() > 0) {
				json.put("vid", venueApplication.getVid()+"");
			}
			else {
				json.put("vid", "无");
			}
			
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
	
	public String getStringPassword(String password){
		String result = "";
		for(int i=0; i<password.length(); i++){
			result = "*" + result;
		}
		return result;
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

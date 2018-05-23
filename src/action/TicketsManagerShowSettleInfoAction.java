package action;

import java.io.PrintWriter;
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

import po.VenueBean;
import service.SettleIncomeService;
import service.VenueService;

public class TicketsManagerShowSettleInfoAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{
	
	private static final long serialVersionUID = -4529047630742310471L;
	
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

		VenueService venueService = (VenueService)ctx.getBean("VenueService");
		SettleIncomeService settleIncomeService = (SettleIncomeService)ctx.getBean("SettleIncomeService");
		List<VenueBean> venues = venueService.showAllVenues();

		JSONArray array = new JSONArray();

		if(venues != null && venues.size() > 0) {
			for(int i=0; i<venues.size(); i++) {
				JSONObject tempJson = new JSONObject();
				
				tempJson.put("vid", venues.get(i).getVid());
				tempJson.put("vname", venues.get(i).getVname());
				tempJson.put("lastSettleTime", getStringTimestamp(settleIncomeService.showLastSettleTime(venues.get(i).getVid())));
				tempJson.put("balance", venueService.showVenueAccountDetail(venues.get(i).getVid()).getBalance());
					
				array.put(tempJson);
			}
			out.write(array.toString());
			out.close();
			return "success";
		}else{
			out.write("fail");
			out.close();
			return "fail";
		}
	}

	public String getStringTimestamp(Timestamp time) {
		if(time != null) {
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			return sdf.format(time);
		}
		else {
			return "无";
		}
	}
	
}

package action;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;

import po.OrderBean;
import service.OrderService;
import service.VenueService;

public class VenueStatisticsAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

	private static final long serialVersionUID = -7812986312586651793L;
	
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
		OrderService orderService = (OrderService)ctx.getBean("OrderService");
		List<OrderBean> orders = orderService.showAllOrdersOfAVenue(vid);
		List<OrderBean> refunds = orderService.showAllRefundOrdersOfAVenue(vid);

		JSONObject json = new JSONObject();
		
		if(orders != null && refunds != null) {
			
			json.put("orderTotalNum",orders.size());
			json.put("refundTotalNum", refunds.size());
			if(orders.size() > 0) {
				json.put("refundPercent", getStringPercent((double)refunds.size() * 1.0 / orders.size()));
			}
			else {
				json.put("refundPercent", 0);
			}
			json.put("profitMoney", venueService.showVenueAccountDetail(vid).getBalance());
				
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

	public String getStringPercent(double num) {
		BigDecimal bd = new BigDecimal(num);
		String numStr = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()+"";
		return numStr;
	}
	
}

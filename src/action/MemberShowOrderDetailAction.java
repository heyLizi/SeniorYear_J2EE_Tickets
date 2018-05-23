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
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;

import po.OrderBean;
import po.SoldSeatBean;
import service.OrderService;
import service.PerformancePlanService;
import service.VenueService;

public class MemberShowOrderDetailAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

	private static final long serialVersionUID = 6565442448338520798L;

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

		int oid = Integer.parseInt(request.getParameter("oid"));

		PerformancePlanService performancePlanService = (PerformancePlanService)ctx.getBean("PerformancePlanService"); 
		OrderService orderService = (OrderService)ctx.getBean("OrderService"); 
		VenueService venueService = (VenueService)ctx.getBean("VenueService");
		OrderBean order = orderService.showOrderDetail(oid);

		JSONObject json = new JSONObject();
		
		if(order != null) {
			
			json.put("oid", order.getOid());
			json.put("ppName", performancePlanService.showPerformancePlanDetail(order.getPpid()).getPpname());
			json.put("venue", venueService.showVenueDetail(performancePlanService.showPerformancePlanDetail(order.getPpid()).getVid()).getVname());
			json.put("orderState", getStringState(order.getOrderState()));
			json.put("orderType", getStringType(order.getOrderType()));
			json.put("orderTime", getStringTimestamp(order.getOrderTime()));
			json.put("startTime", getStringTimestamp(performancePlanService.showPerformancePlanDetail(order.getPpid()).getStartTime()));
			json.put("seatCategory", getStringCategory(order.getSeatCategory()));
			json.put("seatNum", order.getSeatNum());
			json.put("totalMoney", order.getTotalMoney());
			json.put("realMoney", order.getRealMoney());
			json.put("seats", getStringSeats(orderService.showSoldSeatsOfAOrder(order.getOid())));
				
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

	public String getStringState(char state) {
		switch(state) {
			case 'S': return "待付款";
			case 'P': return "已付款";
			case 'R': return "已退款";
			case 'E': return "已完成";
			default: return "无状态";
		}
	}
	
	public String getStringType(char type) {
		switch(type) {
			case 'S': return "选座购买";
			case 'R': return "不选座购买";
			default: return "无类型";
		}
	}
	
	public String getStringCategory(char category) {
		switch(category) {
			case 'V': return "VIP类座位";
			case 'A': return "A类座位";
			case 'B': return "B类座位";
			case 'C': return "C类座位";
			case 'D': return "D类座位";
			default: return "无类型";
		}
	}
	
	public String getStringTimestamp(Timestamp time) {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		return sdf.format(time);
	}
	
	public String getStringSeats(List<SoldSeatBean> soldSeats) {
		String seatsStr = "";
		if(soldSeats.size() > 0) {
			for(int i=0; i<soldSeats.size()-1; i++) {
				seatsStr += soldSeats.get(i).getSeatRow()+"排"+soldSeats.get(i).getSeatCol()+"座,";
			}
			seatsStr += soldSeats.get(soldSeats.size()-1).getSeatRow()+"排"+soldSeats.get(soldSeats.size()-1).getSeatCol()+"座";
		}
		return seatsStr;
	}
	
}

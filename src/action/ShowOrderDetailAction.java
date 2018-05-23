package action;

import java.io.PrintWriter;
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

public class ShowOrderDetailAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

	private static final long serialVersionUID = 852030321153603858L;
	
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

		int ppid = Integer.parseInt(request.getParameter("oid"));
		
		OrderService orderService = (OrderService)ctx.getBean("OrderService");
		OrderBean order = orderService.showOrderDetail(ppid);

		JSONObject json = new JSONObject();
		
		if(order != null) {
				
			json.put("oid", order.getOid());
			json.put("seatCategory", getStringCategory(order.getSeatCategory()));
			json.put("seatPrice", order.getTotalMoney() / order.getSeatNum());
			json.put("seatNum", order.getSeatNum());
			json.put("originMoney",order.getTotalMoney());
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

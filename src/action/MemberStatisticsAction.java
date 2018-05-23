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
import service.MemberCardService;
import service.OrderService;

public class MemberStatisticsAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

	private static final long serialVersionUID = -8805292623653992110L;
	
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

		int mid = Integer.parseInt(String.valueOf(session.getAttribute("mid")));

		MemberCardService memberCardService = (MemberCardService)ctx.getBean("MemberCardService");
		OrderService orderService = (OrderService)ctx.getBean("OrderService");
		List<OrderBean> orders = orderService.showAllOrdersOfAMember(mid);
		List<OrderBean> refunds = orderService.showAllRefundOrdersOfAMember(mid);

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
			json.put("costTotalMoney", memberCardService.showCardInfo(mid).getTotalPay());
				
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

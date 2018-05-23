package action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;

import po.OrderBean;
import service.OrderService;
import service.PerformancePlanService;

public class MemberShowOrderListAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

	private static final long serialVersionUID = 4413071991344278063L;

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

		PerformancePlanService performancePlanService = (PerformancePlanService)ctx.getBean("PerformancePlanService"); 
		OrderService orderService = (OrderService)ctx.getBean("OrderService"); 
		List<OrderBean> orders = orderService.showAllOrdersOfAMember(mid);

		JSONArray array = new JSONArray();

		if(orders != null && orders.size() > 0) {
			for(int i=0; i<orders.size(); i++) {
				JSONObject tempJson = new JSONObject();
				
				tempJson.put("oid", orders.get(i).getOid());
				tempJson.put("orderState", getStringState(orders.get(i).getOrderState()));
				tempJson.put("ppName", performancePlanService.showPerformancePlanDetail(orders.get(i).getPpid()).getPpname());
				
				array.put(tempJson);
			}    
			out.write(array.toString());
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
	
}

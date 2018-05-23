package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;

import service.OrderService;
import util.Result;

public class MemberPayOrderAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

	private static final long serialVersionUID = 379001247347992076L;

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
		int oid = Integer.parseInt(request.getParameter("oid"));
		int cpnId = Integer.parseInt(request.getParameter("cpnId"));
		char category = request.getParameter("category").charAt(0);
		String acntName = request.getParameter("acntName");
		String passwd = request.getParameter("password");

		OrderService orderService = (OrderService)ctx.getBean("OrderService");
		
		Result payResult;
		
		if(cpnId > 0) {
			payResult = orderService.payOrderWithCoupon(oid, cpnId, mid, category, acntName, passwd);
		}
		else {
			payResult = orderService.payOrderWithoutCoupon(oid, mid, category, acntName, passwd);
		}
		
		System.out.println("会员支付结果："+payResult.getResult()+"   "+payResult.getDescription());
		if(payResult.getResult()) {
			out.write("success");
	        out.close();
	        return "success";
		}
		else {
			out.write(payResult.getDescription());
			out.close();
			return "fail";
		}
	}

}

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

public class VenueCheckinAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

	private static final long serialVersionUID = -4270664012628357876L;
	
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

		int ppid = Integer.parseInt(request.getParameter("ppid"));
		int seatRow = Integer.parseInt(request.getParameter("seatRow"));
		int seatCol = Integer.parseInt(request.getParameter("seatCol"));
		
		OrderService orderService = (OrderService)ctx.getBean("OrderService"); 
		Result checkinResult = orderService.checkin(ppid, vid, seatRow, seatCol);
		System.out.println("场馆检票进场结果："+checkinResult.getResult()+"   "+checkinResult.getDescription());
		if(checkinResult.getResult()) {
	        out.write("success");
	        out.close();
	        return "success";
		}
		else {
			out.write(checkinResult.getDescription());
			out.close();
			return "fail";
		}
	}

}

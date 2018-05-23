package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;

import service.VenueService;
import util.Result;

public class VenueLoginAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

	private static final long serialVersionUID = 5328378423684119774L;

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
		
		int vid = Integer.parseInt(request.getParameter("vid"));
		String vpassword = request.getParameter("vpassword");
		
		VenueService venueService = (VenueService)ctx.getBean("VenueService");
		Result loginResult = venueService.venueLogin(vid, vpassword);
		System.out.println("场馆登录结果："+loginResult.getResult()+"   "+loginResult.getDescription());
		if(loginResult.getResult()){
			request.getSession().setAttribute("vid", getStringID(vid+""));
			request.getSession().setAttribute("superId", "V"+getStringID(vid+""));
	        out.write("success");
	        out.close();
	        return "success";
		}else{
			out.write(loginResult.getDescription());
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

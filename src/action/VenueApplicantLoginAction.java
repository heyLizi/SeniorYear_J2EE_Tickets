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

public class VenueApplicantLoginAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

	private static final long serialVersionUID = 965392513612884523L;
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
		
		int vaerid = Integer.parseInt(request.getParameter("vaerid"));
		String vaerPassword = request.getParameter("vaerPassword");
		
		VenueService venueService = (VenueService) ctx.getBean("VenueService");
		Result loginResult = venueService.applicantLogin(vaerid, vaerPassword);
		System.out.println("申请者登录结果："+loginResult.getResult()+"   "+loginResult.getDescription());
		if(loginResult.getResult()){
			request.getSession().setAttribute("vaerid", getStringID(vaerid+""));
			request.getSession().setAttribute("superId", "A"+getStringID(vaerid+""));
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

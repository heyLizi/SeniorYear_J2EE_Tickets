package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;

import service.TicketsCompanyInfoService;
import util.Result;

public class TicketsManagerLoginAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

	private static final long serialVersionUID = 5851686993482554898L;
	
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
		
		String mgrId = request.getParameter("mgrId");
		String mgrPassword = request.getParameter("mgrPassword");
		
		TicketsCompanyInfoService ticketsCompanyInfoService = (TicketsCompanyInfoService) ctx.getBean("TicketsCompanyInfoService");
		Result loginResult = ticketsCompanyInfoService.login(mgrId, mgrPassword);
		System.out.println("经理登录结果："+loginResult.getResult()+"   "+loginResult.getDescription());
		if(loginResult.getResult()){
			request.getSession().setAttribute("mgrId", getStringID(mgrId+""));
			request.getSession().setAttribute("superId", "T"+getStringID(mgrId+""));
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

package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;

import service.MemberService;
import util.Result;

public class MemberRegisterAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

	private static final long serialVersionUID = 5852390569173747415L;
	
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

		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		MemberService memberService = (MemberService)ctx.getBean("MemberService"); 
		Result registerResult = memberService.register(email, password);
		System.out.println("会员注册结果："+registerResult.getResult()+"   "+registerResult.getDescription());
		if(registerResult.getResult()) {
			request.getSession().setAttribute("email", email);
	        out.write("success");
	        out.close();
	        return "success";
		}
		else {
			out.write(registerResult.getDescription());
			out.close();
			return "fail";
		}
	}

}

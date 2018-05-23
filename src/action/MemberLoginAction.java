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

public class MemberLoginAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

	private static final long serialVersionUID = -6691873636254406661L;

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
		
		int mid = Integer.parseInt(request.getParameter("mid"));
		String mpassword = request.getParameter("mpassword");

		MemberService memberService = (MemberService)ctx.getBean("MemberService");
		Result loginResult = memberService.login(mid, mpassword);
		System.out.println("会员登录结果："+loginResult.getResult()+"   "+loginResult.getDescription());
		if(loginResult.getResult()){
			request.getSession().setAttribute("mid", getStringID(mid+""));
			request.getSession().setAttribute("superId", "M"+getStringID(mid+""));
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

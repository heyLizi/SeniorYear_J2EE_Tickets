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

import service.MemberService;
import util.Result;

public class MemberResetPasswordAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

	private static final long serialVersionUID = 2416717087114020866L;

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
		String passwd = request.getParameter("passwd");

		MemberService memberService = (MemberService)ctx.getBean("MemberService"); 
		Result resetPasswdResult = memberService.resetPassword(mid, passwd);
		System.out.println("会员重置密码结果："+resetPasswdResult.getResult()+"   "+resetPasswdResult.getDescription());
		if(resetPasswdResult.getResult()) {
			out.write("success");
		    out.close();
		    return "success";
		}
		else {
			out.write(resetPasswdResult.getDescription());
			out.close();
			return "fail";
		}
	}

}

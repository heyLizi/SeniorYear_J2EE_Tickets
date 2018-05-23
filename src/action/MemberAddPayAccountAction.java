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

public class MemberAddPayAccountAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

	private static final long serialVersionUID = 3298540981997232703L;

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
		
		char acntCategory = request.getParameter("bankCategory").charAt(0);
		String acntName = request.getParameter("acntName");
		String acntPasswd = request.getParameter("acntPasswd");
		
		MemberService memberService = (MemberService)ctx.getBean("MemberService"); 
		Result bindPayAcntResult = memberService.bindPayAccount(mid, acntCategory, acntName, acntPasswd);
		System.out.println("会员绑定支付账户结果："+bindPayAcntResult.getResult()+"   "+bindPayAcntResult.getDescription());
		if(bindPayAcntResult.getResult()) {
			out.write("success");
		    out.close();
		    return "success";
		}
		else {
			out.write(bindPayAcntResult.getDescription());
			out.close();
			return "fail";
		}
	}

}

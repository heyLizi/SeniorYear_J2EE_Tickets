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

import service.MemberCardService;
import util.Result;

public class MemberStopCardAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

	private static final long serialVersionUID = -656784963623285084L;

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

		MemberCardService memberCardService = (MemberCardService)ctx.getBean("MemberCardService");
		Result stopCardResult = memberCardService.stopCard(mid);
		System.out.println("会员停止会员卡结果："+stopCardResult.getResult()+"   "+stopCardResult.getDescription());
		if(stopCardResult.getResult()) {
			out.write("success");
		    out.close();
		    return "success";
		}
		else {
			out.write(stopCardResult.getDescription());
			out.close();
			return "fail";
		}
	}

}

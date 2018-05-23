package action;

import java.io.PrintWriter;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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

public class MemberSetInfoAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

	private static final long serialVersionUID = 5293040666973430594L;
	
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
		String mname = request.getParameter("mname");
		
		char sex;
		String categoryCount = request.getParameter("sexCategory");
		if(categoryCount.equals("男")) {
			sex = 'M';
		}
		else {
			sex = 'F';
		}

		DateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd");
		String birthDateStr = request.getParameter("birthDate");
		Date birthDate = new Date(dateFormate.parse(birthDateStr).getTime()); 

		MemberService memberService = (MemberService)ctx.getBean("MemberService"); 
		Result setInfoResult = memberService.setInfo(mid, mname, sex, birthDate);
		System.out.println("会员设置个人信息结果："+setInfoResult.getResult()+"   "+setInfoResult.getDescription());
		if(setInfoResult.getResult()) {
			out.write("success");
		    out.close();
		    return "success";
		}
		else {
			out.write(setInfoResult.getDescription());
			out.close();
			return "fail";
		}
	}

}

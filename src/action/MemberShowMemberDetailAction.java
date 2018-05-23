package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;

import po.MemberBean;
import service.MemberService;

public class MemberShowMemberDetailAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

	private static final long serialVersionUID = -7598706527550368691L;
	
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

		MemberService memberService = (MemberService)ctx.getBean("MemberService"); 
		MemberBean member = memberService.showMemberDetail(mid);
		
		JSONObject json = new JSONObject();
		
		if(member != null) {
			json.put("mid", getStringID(member.getMid()+""));
			json.put("email", member.getEmail());
			json.put("mpasswd", getStringPassword(member.getMpasswd()));
			json.put("mname", member.getMname()+"");
			json.put("sex", getStringSex(member.getSex()));
			json.put("birthDate", member.getBirthDate()+"");
	        
			out.write(json.toString());
	        out.close();
	        return "success";
		}
		else {
			out.write("fail");
			out.close();
			return "fail";
		}
	}

	public String getStringID(String id){
		String result = id;
		for(int i=id.length(); i<7; i++){
			result = "0" + result;
		}
		return result;
	}
	
	public String getStringPassword(String password){
		String result = "";
		for(int i=0; i<password.length(); i++){
			result = "*" + result;
		}
		return result;
	}
	
	public String getStringSex(char sex){
		switch(sex) {
			case 'M': return "男";
			case 'F': return "女";
			default: return "未设置";
		}
	}
	
}

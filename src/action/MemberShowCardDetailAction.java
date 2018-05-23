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

import po.MemberCardBean;
import service.MemberCardService;

public class MemberShowCardDetailAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

	private static final long serialVersionUID = -7251430254543240783L;
	
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
		MemberCardBean memberCard = memberCardService.showCardInfo(mid);

		JSONObject json = new JSONObject();
		
		if(memberCard != null) {
			json.put("level", memberCard.getLevel());
			json.put("totalPay", memberCard.getTotalPay());
			json.put("credit", memberCard.getCredit());
			json.put("discount", memberCardService.showRuleInfo(memberCard.getLevel()).getDiscount());
			json.put("creditRule", memberCardService.showRuleInfo(memberCard.getLevel()).getCreditRule());
	        
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

}

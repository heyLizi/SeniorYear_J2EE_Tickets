package action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;

import po.MemberPayBean;
import service.MemberService;

public class MemberShowPayAccountListAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

	private static final long serialVersionUID = -4761432576303774959L;

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
		List<MemberPayBean> payAcnts = memberService.showMyPayAccounts(mid);

		JSONArray array = new JSONArray();

		if(payAcnts != null && payAcnts.size() > 0) {
			for(int i=0; i<payAcnts.size(); i++) {
				JSONObject tempJson = new JSONObject();
				
				tempJson.put("category", getStringCategory(payAcnts.get(i).getCategory()));
				tempJson.put("acntName", payAcnts.get(i).getAcntName());
				
				array.put(tempJson);
			}    
			out.write(array.toString());
	        out.close();
	        return "success";
		}
		else {
			out.write("fail");
			out.close();
			return "fail";
		}
	}

	public String getStringCategory(char category) {
		switch(category) {
			case 'B': return "银行卡";
			case 'W': return "微信";
			case 'Z': return "支付宝";
			default: return "无类型";
		}
	}
	
}

package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;

import service.VenueService;
import util.Result;

public class TicketsManagerCheckUpdateAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{
	
	private static final long serialVersionUID = -5066007334691660301L;
	
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

		int vuid = Integer.parseInt(request.getParameter("vuid"));
		boolean isPass = Boolean.parseBoolean(request.getParameter("isPass"));
		
		VenueService venueService = (VenueService) ctx.getBean("VenueService");
		Result checkResult = venueService.checkUpdate(vuid, isPass);
		System.out.println("经理审批场馆更新申请结果："+checkResult.getResult()+"   "+checkResult.getDescription());
		if(checkResult.getResult()){
			out.write("success");
			out.close();
			return "success";
		}else{
			out.write("fail");
			out.close();
			return "fail";
		}
	}

}

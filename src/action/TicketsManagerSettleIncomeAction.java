package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;

import service.TicketsCompanyInfoService;
import util.Result;

public class TicketsManagerSettleIncomeAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{
	
	private static final long serialVersionUID = 8179256973416887230L;
	
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

		TicketsCompanyInfoService ticketsCompService = (TicketsCompanyInfoService) ctx.getBean("TicketsCompanyInfoService");
		Result settleResult = ticketsCompService.settleIncome();
		if(settleResult.getResult()){
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

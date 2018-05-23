package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;

public class IsAuthorizedAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

	private static final long serialVersionUID = -3045126505958699607L;

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

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession(false);
		
		if(session == null){
			out.write("fail");
			out.close();
			return "success";
		}
		
		String superId = String.valueOf(session.getAttribute("superId"));
		
		if(superId.equals("null")){
			out.write("fail");
			out.close();
			return "success";
		}
		else {
			out.write(superId);
			out.close();
			return "success";
		}
	}

}

package action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;

import service.OrderService;
import vo.SeatVO;

public class ShowEmptySeatsOfAPerformanceAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

	private static final long serialVersionUID = 852030321153603858L;
	
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
			
		PrintWriter out = response.getWriter();

		int ppid = Integer.parseInt(request.getParameter("ppid"));
		char seatCategory = request.getParameter("seatCategory").charAt(0);
		
		OrderService orderService = (OrderService)ctx.getBean("OrderService");
		List<SeatVO> emptySeats = orderService.showEmptySeats(ppid, seatCategory);

		JSONArray array = new JSONArray();

		if(emptySeats != null) {
			for(int i=0; i<emptySeats.size(); i++) {
				JSONObject tempJson = new JSONObject();
				
				tempJson.put("row", emptySeats.get(i).getRow());
				tempJson.put("col", emptySeats.get(i).getCol());
				
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

}

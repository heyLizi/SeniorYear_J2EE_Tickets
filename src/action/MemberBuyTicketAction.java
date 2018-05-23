package action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;

import service.OrderService;
import util.Result;
import vo.SeatVO;

public class MemberBuyTicketAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

	private static final long serialVersionUID = 3532549305188678325L;
	
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
		int ppid = Integer.parseInt(request.getParameter("ppid"));
		char buyCategory = request.getParameter("buyCategory").charAt(0);
		char seatCategory = request.getParameter("seatCategory").charAt(0);
		
		OrderService orderService = (OrderService)ctx.getBean("OrderService");
		
		Result buyTicketResult;
		
		if(buyCategory == 'S') {//选座购买
			//解析前台传来的“选择座位”
			String selectedSeatsStr = request.getParameter("selectedSeats");
			String[] selectedSeatsStrParts = selectedSeatsStr.split(",");
			
			List<SeatVO> selectedSeats = new ArrayList<SeatVO>();
			for(int i=0; i<selectedSeatsStrParts.length; i++) {
				int rowNum = Integer.parseInt(selectedSeatsStrParts[i].split("排")[0]);
				int colNum = Integer.parseInt((selectedSeatsStrParts[i].split("排")[1]).split("座")[0]);
				SeatVO tempSeat = new SeatVO(rowNum, colNum);
				selectedSeats.add(tempSeat);
			}
			
			buyTicketResult = orderService.buyTicketsWithChoice(mid, ppid, seatCategory, selectedSeats);
		}
		else {//不选座购买
			//获取前台传来的“购买张数”
			int seatNum = Integer.parseInt(request.getParameter("seatNum"));
			
			buyTicketResult = orderService.buyTicketsWithoutChoice(mid, ppid, seatCategory, seatNum);
		}
		
		System.out.println("会员买票结果："+buyTicketResult.getResult()+"   "+buyTicketResult.getDescription());
		if(buyTicketResult.getResult()) {
			out.write(buyTicketResult.getDescription());
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

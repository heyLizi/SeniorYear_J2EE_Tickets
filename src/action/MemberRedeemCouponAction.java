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

import service.CouponService;
import service.MemberCardService;
import util.Result;

public class MemberRedeemCouponAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

	private static final long serialVersionUID = 8687271544303900314L;
	
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

		int cpnMoney;
		double redeemedCredit;
		String categoryCount = request.getParameter("redeemCategory");
		if(categoryCount.equals("500")) {
			cpnMoney = 10;
			redeemedCredit = 500;
		}
		else if(categoryCount.equals("1000")) {
			cpnMoney = 25;
			redeemedCredit = 1000;
		}
		else if(categoryCount.equals("1500")) {
			cpnMoney = 40;
			redeemedCredit = 1500;
		}
		else if(categoryCount.equals("2000")) {
			cpnMoney = 60;
			redeemedCredit = 2000;
		}
		else {
			cpnMoney = 90;
			redeemedCredit = 2500;
		}

		
		CouponService couponService = (CouponService)ctx.getBean("CouponService"); 
		MemberCardService memberCardService = (MemberCardService)ctx.getBean("MemberCardService"); 
		Result redeemCouponResult = couponService.redeemCoupon(mid, cpnMoney);
		System.out.println("会员兑换优惠券结果："+redeemCouponResult.getResult()+"   "+redeemCouponResult.getDescription());
		Result updateCardResult = memberCardService.updateCardAfterRedeem(mid, redeemedCredit);
		System.out.println("会员更新会员卡信息结果："+updateCardResult.getResult()+"   "+updateCardResult.getDescription());
		if(redeemCouponResult.getResult() && updateCardResult.getResult()) {
			out.write("success");
		    out.close();
		    return "success";
		}
		else {
			out.write(redeemCouponResult.getDescription()+"，"+updateCardResult.getDescription());
			out.close();
			return "fail";
		}
	}

}

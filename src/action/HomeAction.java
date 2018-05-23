package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;

public class HomeAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

	private static final long serialVersionUID = -7253697164910121154L;
	
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
		
		String pageId = request.getParameter("pageId");
		
		if(pageId.equals("0")){ //返回主页面
			return "home";
		}
		else if(pageId.equals("1")){ //返回会员注册页面
			return "memberRegister";
		}
		else if(pageId.equals("2")){ //返回验证邮箱页面
			return "verifyEmail";
		}
		else if(pageId.equals("3")){ //返回会员登录页面
			return "memberLogin";
		}
		else if(pageId.equals("4")){ //返回会员主页面
			return "memberMain";
		}
		else if(pageId.equals("5")){ //返回会员个人设置页面
			return "memberPersonalSetting";
		}
		else if(pageId.equals("6")){ //返回会员卡信息页面
			return "memberCardInfo";
		}
		else if(pageId.equals("7")){ //返回会员查询演出页面
			return "memberSearchPerformance";
		}
		else if(pageId.equals("8")){ //返回会员购票页面
			return "memberBuyTicket";
		}
		else if(pageId.equals("9")){ //返回会员订单管理页面
			return "memberManageOrder";
		}
		else if(pageId.equals("10")){ //返回会员统计页面
			return "memberStatistics";
		}
		else if(pageId.equals("11")){ //返回申请者注册页面
			return "applicantRegister";
		}
		else if(pageId.equals("12")){ //返回申请者登录页面
			return "applicantLogin";
		}
		else if(pageId.equals("13")){ //返回申请者主页面
			return "applicantMain";
		}
		else if(pageId.equals("14")){ //返回申请者个人设置页面
			return "applicantPersonalSetting";
		}
		else if(pageId.equals("15")){ //返回申请者申请页面
			return "applicantApply";
		}
		else if(pageId.equals("16")){ //返回场馆登录页面
			return "venueLogin";
		}
		else if(pageId.equals("17")){ //返回场馆主页面
			return  "venueMain";
		}
		else if(pageId.equals("18")){ //返回场馆设置页面
			return "venueSetting";
		}
		else if(pageId.equals("19")){ //返回场馆发布计划页面
			return "venueReleasePlan";
		}
		else if(pageId.equals("20")){ //返回场馆查询演出页面
			return "venueSearchPerformance";
		}
		else if(pageId.equals("21")){ //返回场馆售票页面
			return "venueSellTicket";
		}
		else if(pageId.equals("22")){ //返回场馆检票页面
			return "venueCheckIn";
		}
		else if(pageId.equals("23")){ //返回场馆统计页面
			return "venueStatistics";
		}
		else if(pageId.equals("24")){ //返回集团经理登录页面
			return "managerLogin";
		}
		else if(pageId.equals("25")){ //返回集团经理主页面
			return "managerMain";
		}
		else if(pageId.equals("26")){ //返回集团经理审批申请页面
			return "managerCheckApplication";
		}
		else if(pageId.equals("27")){ //返回集团经理结算收入页面
			return "managerSettleIncome";
		}
		else if(pageId.equals("28")){ //返回集团经理统计页面
			return "managerStatistics";
		}
		
		return "success";
	}

}

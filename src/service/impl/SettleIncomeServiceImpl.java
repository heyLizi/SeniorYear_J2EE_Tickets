package service.impl;

import java.sql.Timestamp;
import java.util.List;

import dao.SettleIncomeDao;
import dao.SettleRuleDao;
import po.OrderBean;
import po.RefundBean;
import po.VenueBean;
import service.OrderService;
import service.RefundService;
import service.SettleIncomeService;
import service.TicketsCompanyInfoService;
import service.VenueService;
import util.Result;

/**
 * 结算服务实现
 **/

public class SettleIncomeServiceImpl implements SettleIncomeService {

	private OrderService orderService;
	private RefundService refundService;
	private TicketsCompanyInfoService ticketsCompanyInfoService;
	private VenueService venueService;
	
	private SettleIncomeDao settleIncomeDao;
	private SettleRuleDao settleRuleDao;
	
	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	
	public RefundService getRefundService() {
		return refundService;
	}

	public void setRefundService(RefundService refundService) {
		this.refundService = refundService;
	}

	public TicketsCompanyInfoService getTicketsCompanyInfoService() {
		return ticketsCompanyInfoService;
	}

	public void setTicketsCompanyInfoService(TicketsCompanyInfoService ticketsCompanyInfoService) {
		this.ticketsCompanyInfoService = ticketsCompanyInfoService;
	}
	
	public VenueService getVenueService() {
		return venueService;
	}
	
	public void setVenueService(VenueService venueService) {
		this.venueService = venueService;
	}
	
	public SettleIncomeDao getSettleIncomeDao() {
		return settleIncomeDao;
	}

	public void setSettleIncomeDao(SettleIncomeDao settleIncomeDao) {
		this.settleIncomeDao = settleIncomeDao;
	}
	
	public SettleRuleDao getSettleRuleDao() {
		return settleRuleDao;
	}

	public void setSettleRuleDao(SettleRuleDao settleRuleDao) {
		this.settleRuleDao = settleRuleDao;
	}
	
	
	/**结算各个场馆收入
	 * 
	 * @return 结算结果:Result的result属性若为true，表示结算成功，description属性无意义；
	 * 				 Result的result属性若为false，表示结算失败，description属性表示失败原因（账户不存在等）
	 */
	@Override
	public Result settleIncome() {
		
		Result result;
		
		//初始化循环中需要用到的变量
		boolean settleFailed = false;
		boolean venueAcntFailed = false;
		
		double ticketsCompanyTotalSettleMoney = 0;//Tickets集团这次结算的总金额
		
		//查询所有场馆信息
		List<VenueBean> venueList = venueService.showAllVenues();
		for(int i=0; i<venueList.size(); i++) {//对于每一个场馆
			VenueBean thisVenue = venueList.get(i);
			int thisVenueId = thisVenue.getVid();//该场馆的场馆编号
			int thisVenueSeatTotalNum = thisVenue.getSeatVipRowNum() * thisVenue.getSeatVipColNum() +
								   thisVenue.getSeatARowNum() * thisVenue.getSeatAColNum() +
								   thisVenue.getSeatBRowNum() * thisVenue.getSeatBColNum() +
								   thisVenue.getSeatCRowNum() * thisVenue.getSeatCColNum() +
								   thisVenue.getSeatDRowNum() * thisVenue.getSeatDColNum();//该场馆的座位总数
			
			Timestamp lastSettleTime = settleIncomeDao.showLastSettleTime(thisVenueId);//该场馆上次结算的时间

			//获取该场馆对应的结算比例
			int settleLevel = 1;
			while(thisVenueSeatTotalNum > settleRuleDao.showRuleDetail(settleLevel).getBoundMax()) {
				settleLevel += 1;
			}
			double settlePercent = settleRuleDao.showRuleDetail(settleLevel).getSettlePercent();
			
			//获取该场馆从上次结算以来的所有订单列表、所有退款订单列表
			List<OrderBean> orderList = orderService.showAllOrdersOfAVenueFromATime(thisVenueId, lastSettleTime);
			List<OrderBean> refundOrderList = orderService.showAllRefundOrdersOfAVenueFromATime(thisVenueId, lastSettleTime);
			
			//获取订单的实际总收入
			OrderBean order;
			double incomeMoney = 0;
			for(int j=0; j<orderList.size(); j++) {
				order = orderList.get(j);
				incomeMoney += order.getRealMoney();
			}
			
			//获取退款订单的实际总支出
			RefundBean refund;
			double refundMoney = 0;
			for(int k=0; k<refundOrderList.size(); k++) {
				int refundOid = (refundOrderList.get(k).getOid());
				refund = refundService.showOrderRefundDetail(refundOid);
				refundMoney += refund.getRefundMoney();
			}
			
			//该场馆从上次结算以来的总盈利=订单总收入-退款总支出
			double totalMoney = incomeMoney - refundMoney;
			//结算收入 = 总收入*结算比例
			double settleMoney = totalMoney * settlePercent;
			
			//将这次的结算收入累加到Tickets集团这次结算的总金额中
			ticketsCompanyTotalSettleMoney += settleMoney;
			
			//获取当前时间，即为这次结算的时间
			Timestamp thisSettleTime = new java.sql.Timestamp(System.currentTimeMillis());
			
			//添加结算收入
			int addSettleIncomeResult = settleIncomeDao.addSettleIncome(thisVenueId, thisSettleTime, settleMoney);
			if(addSettleIncomeResult > 0) {//添加结算收入成功，更新场馆账户
				Result updateVenueAcnt = venueService.updateVenueBalance(thisVenueId, settleMoney);
				if(updateVenueAcnt.getResult() == false) {//更新场馆账户失败，修改循环中的变量，便于结果的返回
					venueAcntFailed = true;
				}
			}
			else {//添加结算收入失败，修改循环中的变量，便于结果的返回
				settleFailed = true;
			}
		}
	
		//将循环的中间结果汇总为结算场馆收入的总结果
		boolean settleVenueResultRslt = (!settleFailed) & (!venueAcntFailed);
		String settleVenueResultDesc = "";
		if(settleFailed) {
			settleVenueResultDesc += "添加结算收入失败 ";
		}
		if(venueAcntFailed) {
			settleVenueResultDesc += "更新场馆账户失败 ";
		}
		Result settleVenueResult = new Result(settleVenueResultRslt, settleVenueResultDesc);
		
		if(settleVenueResult.getResult() == true) {//结算全部场馆收入成功，更新Tickets集团信息，将更新结果作为返回值
			result = ticketsCompanyInfoService.spendMoney(ticketsCompanyTotalSettleMoney);
		}
		else {//结算全部场馆收入失败，返回错误信息
			result = settleVenueResult;
		}
		
		return result;
	}
	
	/**显示某场馆上一次结算收入的时间
	 * 
	 * @param vid 场馆编号
	 * @return 该场馆上一次结算收入的时间
	 */
	@Override
	public Timestamp showLastSettleTime(int vid) {
		
		return settleIncomeDao.showLastSettleTime(vid);
	
	}
	
}

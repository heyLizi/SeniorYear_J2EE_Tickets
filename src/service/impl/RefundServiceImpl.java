package service.impl;

import java.sql.Timestamp;

import dao.RefundDao;
import dao.RefundRuleDao;
import po.OrderPayBean;
import po.RefundBean;
import service.OrderService;
import service.PerformancePlanService;
import service.RefundService;
import util.Result;

/**
 * 退款服务实现
 **/

public class RefundServiceImpl implements RefundService {

	private OrderService orderService;
	private PerformancePlanService performancePlanService;
	
	private RefundDao refundDao;
	private RefundRuleDao refundRuleDao;
	
	public OrderService getOrderService() {
		return orderService;
	}
	
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	
	public PerformancePlanService getPerformancePlanService() {
		return performancePlanService;
	}
	
	public void setPerformancePlanService(PerformancePlanService performancePlanService) {
		this.performancePlanService = performancePlanService;
	}

	public RefundDao getRefundDao() {
		return refundDao;
	}
	
	public void setRefundDao(RefundDao refundDao) {
		this.refundDao = refundDao;
	}
	
	public RefundRuleDao getRefundRuleDao() {
		return refundRuleDao;
	}
	
	public void setRefundRuleDao(RefundRuleDao refundRuleDao) {
		this.refundRuleDao = refundRuleDao;
	}
	
	
	/**退款
	 * 
	 * @param oid
	 * @return 退款结果:Result的result属性若为true，表示退款成功，description属性表示实际退款金额；
	 * 				 Result的result属性若为false，表示退款失败，description属性表示失败原因（订单编号不存在等）
	 */
	@Override
	public Result refund(int oid) {
		
		Result result;
		
		//查询订单支付
		OrderPayBean orderPay = orderService.showOrderPayDetail(oid);
		if(orderPay == null) {//订单支付对象不存在，返回错误信息
			result = new Result(false, "订单支付不存在");
		}
		else {//订单支付对象存在

			//获取演出开始时间
			int ppid = orderService.showOrderDetail(oid).getPpid();
			Timestamp performanceTime = performancePlanService.showPerformancePlanDetail(ppid).getStartTime();
			//获取当前时间，即为退款时间
			Timestamp refundTime = new java.sql.Timestamp(System.currentTimeMillis());
			//计算两个时间之间的差值（以分钟为单位）
			Long leftMinutes = (performanceTime.getTime() - refundTime.getTime()) / (1000*60);
			
			//获取在当前退款下的退款比例
			int refundLevel = 1;
			while(leftMinutes > refundRuleDao.showRuleDetail(refundLevel).getLeftMinuteBoundMax()) {
				refundLevel += 1;
			}
			double refundPercent = refundRuleDao.showRuleDetail(refundLevel).getRefundPercent();
			
			//获取支付金额
			double realPayMoney = orderPay.getPayMoney();
			//计算实际退款金额
			double refundMoney = realPayMoney * refundPercent;
			
			//添加退款
			int addRefundResult = refundDao.addRefund(oid, refundTime, refundMoney);
			if(addRefundResult > 0) {//添加退款成功，返回成功信息+退款金额
				result = new Result(true, refundMoney+"");
			}
			else {//添加退款失败，返回错误信息
				result = new Result(false, "添加退款失败");
			}
		}
		
		return result;
	}
	
	
	/**根据订单编号查看退款
	 * 
	 * @param oid 订单编号
	 * @return 退款对象
	 */
	@Override
	public RefundBean showOrderRefundDetail(int oid) {
		
		return refundDao.showRefundDetailByOrder(oid);
	
	}
	
}

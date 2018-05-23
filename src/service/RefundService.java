package service;

import po.RefundBean;
import util.Result;

/**
 * 退款服务接口
 **/

public interface RefundService {

	/**退款
	 * 
	 * @param oid
	 * @return 退款结果:Result的result属性若为true，表示退款成功，description属性表示实际退款金额；
	 * 				 Result的result属性若为false，表示退款失败，description属性表示失败原因（订单编号不存在等）
	 */
	public Result refund(int oid);
	
	
	/**根据订单编号查看退款
	 * 
	 * @param oid 订单编号
	 * @return 退款对象
	 */
	public RefundBean showOrderRefundDetail(int oid); 
	
}

package dao;

import java.sql.Timestamp;

import po.OrderPayBean;

/**
 * 订单支付数据访问接口
 **/

public interface OrderPayDao {

	/**根据各种参数添加订单支付
	 * 
	 * @param oid 订单编号
	 * @param mid 会员编号
	 * @param isPayAcnt 是否使用会员支付账户付款
	 * @param payCategory 支付账户类型
	 * @param payAcntName 支付账户名称
	 * @param payTime 支付时间
	 * @param payMoney 支付金额
	 * @return 添加结果：若结果大于0，则添加成功，订单支付编号同订单编号；若结果小于等于0，则添加失败
	 */
	public int addOrderPay(int oid, int mid, char isPayAcnt, char payCategory, String payAcntName, Timestamp payTime, double payMoney); 
	
	/**删除订单支付
	 * 
	 * @param oid 订单支付编号
	 * @return 删除结果，若结果大于0，则删除成功；若结果小于等于0，则删除失败
	 */
	public int deleteOrderPay(int oid);
	
	/**更新订单支付
	 * 
	 * @param newOrderPay 新的订单支付
	 * @return 更新结果：若结果大于0，则更新成功；若结果小于等于0，则更新失败
	 */
	public int updateOrderPay(OrderPayBean newOrderPay);
	
	/**查看订单支付信息
	 * 
	 * @param oid 订单支付编号
	 * @return 订单支付对象
	 */
	public OrderPayBean showOrderPayDetail(int oid);

}

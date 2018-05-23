package dao;

import java.sql.Timestamp;

import po.RefundBean;

/**
 * 退款数据访问接口
 **/

public interface RefundDao {

	/**根据订单编号增加退款
	 * 
	 * @param oid 订单编号
	 * @param refundTime 退款时间
	 * @param refundMoney 退款金额
	 * @return 添加结果：若结果大于0，则添加成功，返回值为新添加的退款对应的退款编号；若结果小于等于0，则添加失败
	 */
	public int addRefund(int oid, Timestamp refundTime, double refundMoney);
	
	/**删除退款
	 * 
	 * @param rid 退款编号
	 * @return 删除结果，若结果大于0，则删除成功；若结果小于等于0，则删除失败
	 */
	public int deleteRefund(int rid);
	
	/**更新退款
	 * 
	 * @param newRefund 新的退款
	 * @return 更新结果：若结果大于0，则更新成功；若结果小于等于0，则更新失败
	 */
	public int updateRefund(RefundBean newRefund);
	
	/**查看退款
	 * 
	 * @param rid 退款编号
	 * @return 退款对象
	 */
	public RefundBean showRefundDetail(int rid);
	
	/**根据订单编号查看退款
	 * 
	 * @param oid 订单编号
	 * @return 退款对象
	 */
	public RefundBean showRefundDetailByOrder(int oid);
	
}

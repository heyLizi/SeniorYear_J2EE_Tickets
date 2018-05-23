package dao;

import java.sql.Timestamp;
import java.util.List;

import po.OrderBean;

/**
 * 订单数据访问接口
 **/

public interface OrderDao {

	/**根据各种参数添加订单
	 * 
	 * @param mid 会员编号
	 * @param ppid 演出计划编号
	 * @param orderType 订单类型
	 * @param orderTime 订单完成时间
	 * @param seatCategory 座位类型
	 * @param seatNum 座位数量
	 * @param totalMoney 订单总额 
	 * @param realMoney 订单实际消费金额
	 * @return 添加结果：若结果大于0，则添加成功，返回值为新添加的订单对应的订单编号；若结果小于等于0，则添加失败
	 */
	public int addOrder(int mid, int ppid, char orderType, Timestamp orderTime, char seatCategory, 
						 int seatNum, double totalMoney, double realMoney); 
	
	/**删除订单
	 * 
	 * @param oid 订单编号
	 * @return 删除结果，若结果大于0，则删除成功；若结果小于等于0，则删除失败
	 */
	public int deleteOrder(int oid);
	
	/**更新订单
	 * 
	 * @param newOrder 新的订单
	 * @return 更新结果：若结果大于0，则更新成功；若结果小于等于0，则更新失败
	 */
	public int updateOrder(OrderBean newOrder);
	
	/**查看订单信息
	 * 
	 * @param oid 订单编号
	 * @return 订单对象
	 */
	public OrderBean showOrderDetail(int oid);
	
	/**查看某会员的所有订单
	 * 
	 * @param mid 会员编号
	 * @return 该会员的所有订单对象列表
	 */
	public List<OrderBean> showAllOrdersOfAMember(int mid);
	
	/**查看某会员的所有退款订单
	 * 
	 * @param mid 会员编号
	 * @return 该会员的所有退款订单对象列表
	 */
	public List<OrderBean> showAllRefundOrdersOfAMember(int mid);
	
	/**查看某场馆的所有订单
	 * 
	 * @param vid 场馆编号
	 * @return 该场馆的所有订单对象列表
	 */
	public List<OrderBean> showAllOrdersOfAVenue(int vid);
	
	/**查看某场馆的所有退款订单
	 * 
	 * @param vid 场馆编号
	 * @return 该场馆的所有退款订单对象列表
	 */
	public List<OrderBean> showAllRefundOrdersOfAVenue(int vid);
	
	/**查看某场馆从某段时间开始的所有订单
	 * 
	 * @param vid 场馆编号
	 * @param time 指定时间
	 * @return 该场馆从这段时间开始的所有订单对象列表
	 */
	public List<OrderBean> showAllOrdersOfAVenueFromATime(int vid, Timestamp time);
	
	/**查看某场馆从某段时间开始的所有退款订单
	 * 
	 * @param vid 场馆编号
	 * @param time 指定时间
	 * @return 该场馆从这段时间开始的所有退款订单对象列表
	 */
	public List<OrderBean> showAllRefundOrdersOfAVenueFromATime(int vid, Timestamp time);
	
}

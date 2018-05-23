package service;

import java.sql.Timestamp;
import java.util.List;

import po.OrderBean;
import po.OrderPayBean;
import po.SoldSeatBean;
import util.Result;
import vo.SeatVO;

/**
 * 订单服务接口
 **/

public interface OrderService {

	/**显示某场演出某个座位类型的所剩空余座位
	 * 
	 * @param ppid 演出编号
	 * @param seatCategory 座位类型
	 * @return 该场演出该座位类型的所有空余座位对象列表
	 */
	public List<SeatVO> showEmptySeats(int ppid, char seatCategory);
	
	
	/**选座购买演出票（会员用）
	 * 
	 * @param mid 会员编号
	 * @param ppid 演出计划编号
	 * @param seatCategory 座位类型
	 * @param seats 选择的座位列表
	 * @return 购买结果:Result的result属性若为true，表示购买成功，description属性表示购买成功后的订单号；
	 * 				 Result的result属性若为false，表示购买失败，description属性表示失败原因（属性值不合理等）
	 */
	public Result buyTicketsWithChoice(int mid, int ppid, char seatCategory, List<SeatVO> seats);
	
	
	/**不选票购买演出票（会员用）
	 * 
	 * @param mid 会员编号
	 * @param ppid 演出计划编号
	 * @param seatCategory 座位类型
	 * @param seatNum 演出票张数
	 * @return 购买结果:Result的result属性若为true，表示购买成功，description属性表示购买成功后的订单号；
	 * 				 Result的result属性若为false，表示购买失败，description属性表示失败原因（属性值不合理等）
	 */
	public Result buyTicketsWithoutChoice(int mid, int ppid, char seatCategory, int seatNum);
	
	
	/**选座出售演出票（场馆用）
	 * 
	 * @param mid 会员编号:会员编号若为0，表示非会员
	 * @param ppid 演出计划编号
	 * @param seatCategory 座位类型
	 * @param seats 选择的座位列表
	 * @return 购买结果:Result的result属性若为true，表示购买成功，description属性表示购买成功后的订单号；
	 * 				 Result的result属性若为false，表示购买失败，description属性表示失败原因（属性值不合理等）
	 */
	public Result sellTicketsWithChoice(int mid, int ppid, char seatCategory, List<SeatVO> seats);
	
	
	/**不选票出售演出票（场馆用）
	 * 
	 * @param mid 会员编号:会员编号若为0，表示非会员
	 * @param ppid 演出计划编号
	 * @param seatCategory 座位类型
	 * @param seatNum 演出票张数
	 * @return 购买结果:Result的result属性若为true，表示购买成功，description属性表示购买成功后的订单号；
	 * 				 Result的result属性若为false，表示购买失败，description属性表示失败原因（属性值不合理等）
	 */
	public Result sellTicketsWithoutChoice(int mid, int ppid, char seatCategory, int seatNum);
	
	
	/**使用优惠券来付款（会员购票时，使用支付账户付款用）
	 * 
	 * @param oid 订单编号
	 * @param cpnId 优惠券编号
	 * @param mid 会员编号
	 * @param category 账户类型
	 * @param acntName 账户名称
	 * @param passwd 账户密码
	 * @return 支付结果:Result的result属性若为true，表示支付成功，description属性无意义；
	 * 				 Result的result属性若为false，表示支付失败，description属性表示失败原因（订单编号不存在、优惠券编号不存在、会员编号不存在等）
	 */
	public Result payOrderWithCoupon(int oid, int cpnId, int mid, char category, String acntName, String passwd);
	
	
	/**不使用优惠券来付款（会员购票时，使用支付账户付款用）
	 * 
	 * @param oid 订单编号
	 * @param mid 会员编号
	 * @param category 账户类型
	 * @param acntName 账户名称
	 * @param passwd 账户密码
	 * @return 支付结果:Result的result属性若为true，表示支付成功，description属性无意义；
	 * 				 Result的result属性若为false，表示支付失败，description属性表示失败原因（订单编号不存在、优惠券编号不存在、会员编号不存在等）
	 */
	public Result payOrderWithoutCoupon(int oid, int mid, char category, String acntName, String passwd);
	
	
	/**使用现金付款（场馆售票时用）
	 * 
	 * @param oid 订单编号
	 * @param mid 会员编号:会员编号若为0，表示非会员
	 * @return 支付结果:Result的result属性若为true，表示支付成功，description属性无意义；
	 * 				 Result的result属性若为false，表示支付失败，description属性表示失败原因（订单编号不存在、优惠券编号不存在、会员编号不存在等）
	 */
	public Result payOrderUseCurrency(int oid, int mid);
	
	
	/**为订单退款（仅限会员使用支付账户付款的订单用）
	 * 
	 * @param oid 订单编号
	 * @return 退款结果:Result的result属性若为true，表示退款成功，description属性表示退款比例和实际退款金额；
	 * 				 Result的result属性若为false，表示退款失败，description属性表示失败原因（订单编号不存在等）
	 */
	public Result refundOrder(int oid);
	
	
	/**检票入场
	 * 
	 * @param ppid 演出计划编号
	 * @param vid 场馆编号
	 * @param seatRow 座位排号
	 * @param seatCol 座位列号
	 * @return 检票结果:Result的result属性若为true，表示检票成功，description属性无意义；
	 * 				 Result的result属性若为false，表示检票失败，description属性表示失败原因（会员编号不存在、演出计划编号不存在、场馆编号不存在等）
	 */
	public Result checkin(int ppid, int vid, int seatRow, int seatCol);
	
	
	/**删除过期订单
	 * 
	 * @param time 当前时间
	 * @return
	 */
	public Result deleteUnpaidOrder();
	
	
	/**查看订单信息
	 * 
	 * @param oid 订单编号
	 * @return 订单对象
	 */
	public OrderBean showOrderDetail(int oid);
	
	
	/**查看订单支付信息
	 * 
	 * @param oid 订单支付编号
	 * @return 订单支付对象
	 */
	public OrderPayBean showOrderPayDetail(int oid);
	
	
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
	 * @param mid 场馆编号
	 * @return 该场馆的所有订单对象列表
	 */
	public List<OrderBean> showAllOrdersOfAVenue(int vid);
	
	
	/**查看某场馆的所有退款订单
	 * 
	 * @param mid 场馆编号
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
	
	
	/**查看某个订单的所有售出座位
	 * 
	 * @param oid 订单编号
	 * @return 该订单的所有售出座位
	 */
	public List<SoldSeatBean> showSoldSeatsOfAOrder(int oid);
	
}

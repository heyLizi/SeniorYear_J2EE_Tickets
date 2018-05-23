package service;

import java.util.List;

import po.CouponBean;
import util.Result;

/**
 * 优惠券服务接口
 **/

public interface CouponService {

	/**兑换优惠券
	 * 
	 * @param mid 会员编号
	 * @param cpnMoney 优惠券金额
	 * @return 兑换结果:Result的result属性若为true，表示兑换成功，description属性表示兑换而来的优惠券编号；
	 * 				 Result的result属性若为false，表示兑换失败，description属性表示失败原因（会员编号不存在等）
	 */
	public Result redeemCoupon(int mid, int cpnMoney);
	
	
	/**使用优惠券（优惠券状态从可用(A)变为不可用(U)）
	 * 
	 * @param cpnId 优惠券编号
	 * @return 使用结果:Result的result属性若为true，表示使用成功，description属性无意义；
	 * 				 Result的result属性若为false，表示使用失败，description属性表示失败原因（优惠券编号不存在等）
	 */
	public Result useCoupon(int cpnId);
	
	
	/**显示优惠券信息
	 * 
	 * @param cpnId 优惠券编号
	 * @return 优惠券对象:若优惠券编号不存在，则返回null
	 */
	public CouponBean showCouponDetail(int cpnId);
	
	
	/**显示某用户的所有可用优惠券
	 * 
	 * @param mid 会员编号
	 * @return 该用户的所有可用优惠券对象列表
	 */
	public List<CouponBean> showAllAvaiCoupons(int mid);
	
}

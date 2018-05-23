package dao;

import java.util.List;

import po.CouponBean;

/**
 * 优惠券数据访问接口
 **/

public interface CouponDao {
	
	/**根据会员编号和优惠金额添加优惠券
	 * 
	 * @param mid 会员编号
	 * @param money 优惠金额
	 * @return 添加结果：若结果大于0，则添加成功，返回值为新添加的优惠券对应的优惠券编号；若结果小于等于0，则添加失败
	 */
	public int addCoupon(int mid, double money);
	
	/**删除优惠券
	 * 
	 * @param cpnId 优惠券编号
	 * @return 删除结果，若结果大于0，则删除成功；若结果小于等于0，则删除失败
	 */
	public int deleteCoupon(int cpnId);
	
	/**更新优惠券
	 * 
	 * @param newCoupon 新的优惠券
	 * @return 更新结果：若结果大于0，则更新成功；若结果小于等于0，则更新失败
	 */
	public int updateCoupon(CouponBean newCoupon);
	
	/**查看优惠券
	 * 
	 * @param cpnId 优惠券编号
	 * @return 优惠券对象
	 */
	public CouponBean showCouponDetail(int cpnId);
	
	/**查看某会员的所有可用优惠券
	 * 
	 * @param mid 会员编号
	 * @return 该会员的所有可用优惠券对象列表
	 */
	public List<CouponBean> showAllAvailableCoupons(int mid);
	
}

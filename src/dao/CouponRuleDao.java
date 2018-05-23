package dao;

import po.CouponRuleBean;

/**
 * 优惠券规则数据访问接口
 **/

public interface CouponRuleDao {

	/**查看优惠券规则
	 * 
	 * @param credit 会员积分
	 * @return 优惠券规则对象
	 */
	public CouponRuleBean showRuleDetail(int credit);
	
}

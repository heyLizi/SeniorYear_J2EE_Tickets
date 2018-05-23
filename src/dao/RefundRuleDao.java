package dao;

import po.RefundRuleBean;

/**
 * 退款规则数据访问接口
 **/

public interface RefundRuleDao {

	/**查看退款规则
	 * 
	 * @param level 退款等级
	 * @return 退款规则对象
	 */
	public RefundRuleBean showRuleDetail(int level);
	
}

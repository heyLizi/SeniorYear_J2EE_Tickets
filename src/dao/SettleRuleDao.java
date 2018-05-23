package dao;

import po.SettleRuleBean;

/**
 * 结算规则数据访问接口
 **/

public interface SettleRuleDao {

	/**查看结算规则
	 * 
	 * @param level 结算等级
	 * @return 结算规则对象
	 */
	public SettleRuleBean showRuleDetail(int level);
	
}

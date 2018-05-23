package dao;

import po.MemberRuleBean;

/**
 * 会员规则数据访问接口
 **/

public interface MemberRuleDao {

	/**查看会员规则
	 * 
	 * @param level 会员等级
	 * @return 会员规则对象
	 */
	public MemberRuleBean showRuleDetail(int level);
	
}

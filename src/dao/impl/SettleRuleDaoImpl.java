package dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.SettleRuleDao;
import po.SettleRuleBean;

/**
 * 结算规则数据访问实现
 **/

public class SettleRuleDaoImpl implements SettleRuleDao {

	private SessionFactory sessionFactory;
	
	private SettleRuleBean settleRule;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void setSettleRule(SettleRuleBean settleRule) {
		this.settleRule = settleRule;
	}

	
	/**查看结算规则
	 * 
	 * @param level 结算等级
	 * @return 结算规则对象
	 */
	@Override
	public SettleRuleBean showRuleDetail(int level) {
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();
		
		settleRule = (SettleRuleBean)sess.get(SettleRuleBean.class, level);

		tx.commit();
		sess.close();
		
		return settleRule;
	}
	
}

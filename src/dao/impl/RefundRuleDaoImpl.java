package dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.RefundRuleDao;
import po.RefundRuleBean;

/**
 * 退款规则数据访问实现
 **/

public class RefundRuleDaoImpl implements RefundRuleDao {

	private SessionFactory sessionFactory;
	
	private RefundRuleBean refundRule;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void setRefundRule(RefundRuleBean refundRule) {
		this.refundRule = refundRule;
	}

	
	/**查看退款规则
	 * 
	 * @param level 退款等级
	 * @return 退款规则对象
	 */
	@Override
	public RefundRuleBean showRuleDetail(int level) {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();
		
		refundRule = (RefundRuleBean)sess.get(RefundRuleBean.class, level);

		tx.commit();
		sess.close();
		
		return refundRule;
	}
	
}

package dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.MemberRuleDao;
import po.MemberRuleBean;

/**
 * 会员规则数据访问实现
 **/

public class MemberRuleDaoImpl implements MemberRuleDao {
	
	private SessionFactory sessionFactory;
	
	private MemberRuleBean memberRule;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void setMemberRule(MemberRuleBean memberRule) {
		this.memberRule = memberRule;
	}

	
	/**查看会员规则
	 * 
	 * @param level 会员等级
	 * @return 会员规则对象
	 */
	@Override
	public MemberRuleBean showRuleDetail(int level) {
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();
		
		memberRule = (MemberRuleBean)sess.get(MemberRuleBean.class, level);

		tx.commit();
		sess.close();
		
		return memberRule;
	}

}

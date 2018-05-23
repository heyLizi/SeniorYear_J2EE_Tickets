package dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.CouponRuleDao;
import po.CouponRuleBean;

/**
 * 优惠券规则数据访问实现
 **/

public class CouponRuleDaoImpl implements CouponRuleDao {

	private SessionFactory sessionFactory;
	
	private CouponRuleBean couponRule;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void setCouponRule(CouponRuleBean couponRule) {
		this.couponRule = couponRule;
	}
	
	
	/**查看优惠券规则
	 * 
	 * @param credit 会员积分
	 * @return 优惠券规则对象
	 */
	@Override
	public CouponRuleBean showRuleDetail(int credit) {
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();
		
		couponRule = (CouponRuleBean)sess.get(CouponRuleBean.class, credit);

		tx.commit();
		sess.close();
		
		return couponRule;
	}

}

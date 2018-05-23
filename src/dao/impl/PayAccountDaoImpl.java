package dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.PayAccountDao;
import po.PayAccountBean;

/**
 * 支付账户数据访问实现
 **/

public class PayAccountDaoImpl implements PayAccountDao {

	private SessionFactory sessionFactory;
	
	private PayAccountBean payAccount;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void setPayAccount(PayAccountBean payAccount) {
		this.payAccount = payAccount;
	}
	

	/**添加支付账户
	 * 
	 * @param payAcnt 支付账户
	 * @return 添加结果：若结果大于0，则添加成功；若结果小于等于0，则添加失败
	 */
	public int addPayAcnt(PayAccountBean payAcnt) {

		int returnNum = 1;
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		payAccount = payAcnt;
		sess.save(payAccount);

		tx.commit();
		sess.close();
		
		return returnNum;
	}
	
	/**删除支付账户
	 * 
	 * @param category 账户类型
	 * @param acntName 账户名称
	 * @return 删除结果，若结果大于0，则删除成功；若结果小于等于0，则删除失败
	 */
	public int deletePayAcnt(char category, String acntName) {

		int returnNum = 0;
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		@SuppressWarnings("unchecked")
		List<PayAccountBean> list = sess.createQuery("select pab from PayAccountBean pab where pab.category=? and pab.acntName=?")
									 .setParameter(0, category).setParameter(1, acntName).list();
		if(list.size() != 0) {
			payAccount = list.get(0);
			
			sess.delete(payAccount);
			
			returnNum = 1;
		}

		tx.commit();
		sess.close();
		
		return returnNum;
	}
	
	/**更新支付账户
	 * 
	 * @param payAcnt 新的支付账户
	 * @return 更新结果：若结果大于0，则更新成功；若结果小于等于0，则更新失败
	 */
	public int updatePayAcnt(PayAccountBean payAcnt) {

		int returnNum = 0;
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		@SuppressWarnings("unchecked")
		List<PayAccountBean> list = sess.createQuery("select pab from PayAccountBean pab where pab.category=? and pab.acntName=?")
									 .setParameter(0, payAcnt.getCategory()).setParameter(1, payAcnt.getAcntName()).list();
		if(list.size() != 0) {
			payAccount = list.get(0);
			payAccount.setAcntPasswd(payAcnt.getAcntPasswd());
			payAccount.setBalance(payAcnt.getBalance());
			
			sess.update(payAccount);
			
			returnNum = 1;
		}

		tx.commit();
		sess.close();
		
		return returnNum;
	}
	
	/**查看支付账户
	 * 
	 * @param category 账户类型
	 * @param acntName 账户名称
	 * @return 支付账户对象
	 */
	public PayAccountBean showPayAcntDetail(char category, String acntName) {
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		@SuppressWarnings("unchecked")
		List<PayAccountBean> list = sess.createQuery("select pab from PayAccountBean pab where pab.category=? and pab.acntName=?")
									 .setParameter(0, category).setParameter(1, acntName).list();
		if(list.size() != 0) {
			payAccount = list.get(0);
		}
		else {
			payAccount = null;
		}
		
		tx.commit();
		sess.close();
		
		return payAccount;
	}
	
}

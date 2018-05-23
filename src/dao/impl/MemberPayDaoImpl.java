package dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.MemberPayDao;
import po.MemberPayBean;

/**
 * 用户绑定支付账户数据访问实现
 **/

public class MemberPayDaoImpl implements MemberPayDao {
	
	private SessionFactory sessionFactory;
	
	private MemberPayBean memberPay;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void setMemberPay(MemberPayBean memberPay) {
		this.memberPay = memberPay;
	}
	
	
	/**添加用户绑定支付账户
	 * 
	 * @param mid 会员编号
	 * @param category 账户类型
	 * @param acntName 账户名称
	 * @return 添加结果：若结果大于0，则添加成功；若结果小于等于0，则添加失败
	 */
	public int addMemberPay(int mid, char category, String acntName) {

		int returnNum = 0;//初始化返回结果
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		@SuppressWarnings("unchecked")
		List<MemberPayBean> list = sess.createQuery("select mpb from MemberPayBean mpb where mpb.mid=? "+
								   "and mpb.category=? and mpb.acntName=?").setParameter(0, mid).setParameter(1, category)
								   .setParameter(2, acntName).list();
		if(list.size() == 0) {
			memberPay = new MemberPayBean();
			memberPay.setMid(mid);
			memberPay.setCategory(category);
			memberPay.setAcntName(acntName);
			
			sess.save(memberPay);
			
			returnNum = 1;
		}
		
		tx.commit();
		sess.close();
		
		return returnNum;
	}
	
	/**删除用户绑定支付账户
	 * 
	 * @param mid 会员编号
	 * @param category 账户类型
	 * @param acntName 账户名称
	 * @return 删除结果：若结果大于0，则删除成功；若结果小于等于0，则删除失败
	 */
	public int deleteMemberPay(int mid, char category, String acntName) {

		int returnNum = 0;//初始化返回结果
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		@SuppressWarnings("unchecked")
		List<MemberPayBean> list = sess.createQuery("select mpb from MemberPayBean mpb where mpb.mid=? "+
								   "and mpb.category=? and mpb.acntName=?").setParameter(0, mid).setParameter(1, category)
								   .setParameter(2, acntName).list();
		if(list.size() == 1) {
			memberPay = list.get(0);
			
			sess.delete(memberPay);
			
			returnNum = 1;
		}
		
		tx.commit();
		sess.close();
		
		return returnNum;
	}
	
	/**查看会员绑定支付账户
	 * 
	 * @param mid 会员编号
	 * @param category 账户类型
	 * @param acntName 账户名称
	 * @return 会员绑定支付账户对象
	 */
	@Override
	public MemberPayBean showMemberPay(int mid, char category, String acntName) {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		@SuppressWarnings("unchecked")
		List<MemberPayBean> list = sess.createQuery("select mpb from MemberPayBean mpb where mpb.mid=? and mpb.category=? and mpb.acntName=?")
								   .setParameter(0, mid).setParameter(1, category).setParameter(2, acntName).list();
		if(list.size() == 1) {
			memberPay = list.get(0);
		}
		else {
			memberPay = null;
		}
		
		tx.commit();
		sess.close();
		
		return memberPay;
	}
	
	/**查看某会员的所有绑定支付账户
	 * 
	 * @param mid 会员编号
	 * @return 该会员的所有绑定支付账户对象列表
	 */
	public List<MemberPayBean> showAllMemberPays(int mid) {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		@SuppressWarnings("unchecked")
		List<MemberPayBean> list = sess.createQuery("select mpb from MemberPayBean mpb where mpb.mid=?")
								   .setParameter(0, mid).list();

		tx.commit();
		sess.close();
		
		return list;
	}
	
}

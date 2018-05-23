package dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.CouponDao;
import po.CouponBean;

/**
 * 优惠券数据访问实现
 **/

public class CouponDaoImpl implements CouponDao {

	private SessionFactory sessionFactory;
	
	private CouponBean coupon;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void setCoupon(CouponBean coupon) {
		this.coupon = coupon;
	}
	
	
	/**根据会员编号和优惠金额添加优惠券
	 * 
	 * @param mid 会员编号
	 * @param money 优惠金额
	 * @return 添加结果：若结果大于0，则添加成功，返回值为新添加的优惠券对应的优惠券编号；若结果小于等于0，则添加失败
	 */
	@Override
	public int addCoupon(int mid, double money) {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		coupon.setMid(mid);
		coupon.setCpnMoney(money);
		coupon.setCpnState('A');//刚兑换的优惠券状态为可用
		
		sess.save(coupon);
		int cpnId = coupon.getCpnId();
		
		tx.commit();
		sess.close();
		
		return cpnId;
	}
	
	/**删除优惠券
	 * 
	 * @param cpnId 优惠券编号
	 * @return 删除结果，若结果大于0，则删除成功；若结果小于等于0，则删除失败
	 */
	@Override
	public int deleteCoupon(int cpnId) {

		int returnNum = 0;//初始化返回结果
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		coupon = (CouponBean)sess.get(CouponBean.class, cpnId);
		if(coupon != null) {
			sess.delete(coupon);
			
			returnNum = 1;
		}
		
		tx.commit();
		sess.close();
		
		return returnNum;
	}
	
	/**更新优惠券
	 * 
	 * @param newCoupon 新的优惠券
	 * @return 更新结果：若结果大于0，则更新成功；若结果小于等于0，则更新失败
	 */
	public int updateCoupon(CouponBean newCoupon) {

		int returnNum = 0;//初始化返回结果
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		coupon = (CouponBean)sess.get(CouponBean.class, newCoupon.getCpnId());
		if(coupon != null) {
			coupon.setMid(newCoupon.getMid());
			coupon.setCpnMoney(newCoupon.getCpnMoney());
			coupon.setCpnState(newCoupon.getCpnState());
			
			sess.update(coupon);
			
			returnNum = 1;
		}
		
		tx.commit();
		sess.close();
		
		return returnNum;
	}
	
	/**查看优惠券
	 * 
	 * @param cpnId 优惠券编号
	 * @return 优惠券对象
	 */
	@Override
	public CouponBean showCouponDetail(int cpnId) {
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		coupon = (CouponBean)sess.get(CouponBean.class, cpnId);
		
		tx.commit();
		sess.close();
		
		return coupon;
	}
	
	/**查看某会员的所有可用优惠券
	 * 
	 * @param mid 会员编号
	 * @return 该会员的所有优惠券对象列表
	 */
	@Override
	public List<CouponBean> showAllAvailableCoupons(int mid) {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		@SuppressWarnings("unchecked")
		List<CouponBean> list = sess.createQuery("select cb from CouponBean cb where cb.mid=? and cb.cpnState='A'")
								.setParameter(0, mid).list();
		
		tx.commit();
		sess.close();
		
		return list;
	}
	
}

package dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.RefundDao;
import po.RefundBean;

/**
 * 退款数据访问实现
 **/

public class RefundDaoImpl implements RefundDao {

	private SessionFactory sessionFactory;
	
	private RefundBean refund;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void setRefund(RefundBean refund) {
		this.refund = refund;
	}


	/**根据订单编号增加退款
	 * 
	 * @param oid 订单编号
	 * @param refundTime 退款时间
	 * @param refundMoney 退款金额
	 * @return 添加结果：若结果大于0，则添加成功，返回值为新添加的退款对应的退款编号；若结果小于等于0，则添加失败
	 */
	@Override
	public int addRefund(int oid, Timestamp refundTime, double refundMoney) {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		refund.setOid(oid);
		refund.setRefundTime(refundTime);
		refund.setRefundMoney(refundMoney);
	
		sess.save(refund);
		int rid = refund.getRid();
		
		tx.commit();
		sess.close();
		
		return rid;
	}
	
	/**删除退款
	 * 
	 * @param rid 退款编号
	 * @return 删除结果，若结果大于0，则删除成功；若结果小于等于0，则删除失败
	 */
	@Override
	public int deleteRefund(int rid) {

		int returnNum = 0;//初始化返回结果
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		refund = (RefundBean)sess.get(RefundBean.class, rid);
		if(refund != null) {
			sess.delete(refund);
			
			returnNum = 1;
		}
		
		tx.commit();
		sess.close();
		
		return returnNum;
	}
	
	/**更新退款
	 * 
	 * @param newRefund 新的退款
	 * @return 更新结果：若结果大于0，则更新成功；若结果小于等于0，则更新失败
	 */
	@Override
	public int updateRefund(RefundBean newRefund) {

		int returnNum = 0;//初始化返回结果
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		refund = (RefundBean)sess.get(RefundBean.class, newRefund.getRid());
		if(refund != null) {
			refund.setOid(newRefund.getOid());
			refund.setRefundTime(newRefund.getRefundTime());
			refund.setRefundMoney(newRefund.getRefundMoney());
			
			sess.update(refund);
			
			returnNum = 1;
		}
		
		tx.commit();
		sess.close();
		
		return returnNum;
	}
	
	/**查看退款
	 * 
	 * @param rid 退款编号
	 * @return 退款对象
	 */
	@Override
	public RefundBean showRefundDetail(int rid) {
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		refund = (RefundBean)sess.get(RefundBean.class, rid);

		tx.commit();
		sess.close();
		
		return refund;
	}
	
	/**根据订单编号查看退款
	 * 
	 * @param oid 订单编号
	 * @return 退款对象
	 */
	@Override
	public RefundBean showRefundDetailByOrder(int oid) {
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		@SuppressWarnings("unchecked")
		List<RefundBean> list = sess.createQuery("select rb from RefundBean rb where rb.oid=?").setParameter(0, oid).list();
		if(list.size() != 0) {
			refund = list.get(0);
		}
		else {
			refund = null;
		}
		
		tx.commit();
		sess.close();
		
		return refund;
	}
	
}

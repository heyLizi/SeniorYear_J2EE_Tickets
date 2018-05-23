package dao.impl;

import java.sql.Timestamp;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.OrderPayDao;
import po.OrderPayBean;

/**
 * 订单支付数据访问实现
 **/

public class OrderPayDaoImpl implements OrderPayDao {

	private SessionFactory sessionFactory;
	
	private OrderPayBean orderPay;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void setOrderPay(OrderPayBean orderPay) {
		this.orderPay = orderPay;
	}
	
	
	/**根据各种参数添加订单支付
	 * 
	 * @param oid 订单编号
	 * @param mid 会员编号
	 * @param isPayAcnt 是否使用会员支付账户付款
	 * @param payCategory 支付账户类型
	 * @param payAcntName 支付账户名称
	 * @param payTime 支付时间
	 * @param payMoney 支付金额
	 * @return 添加结果：若结果大于0，则添加成功，订单支付编号同订单编号；若结果小于等于0，则添加失败
	 */
	@Override
	public int addOrderPay(int oid, int mid, char isPayAcnt, char payCategory, String payAcntName, Timestamp payTime, double payMoney) {

		int returnNum = 0;//初始化返回结果
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		orderPay = (OrderPayBean)sess.get(OrderPayBean.class, oid);
		if(orderPay == null) {
			orderPay = new OrderPayBean();
			orderPay.setOid(oid);
			orderPay.setMid(mid);
			orderPay.setIsPayAcnt(isPayAcnt);
			orderPay.setPayCategory(payCategory);
			orderPay.setPayAcntName(payAcntName);
			orderPay.setPayTime(payTime);
			orderPay.setPayMoney(payMoney);
			
			sess.save(orderPay);
			
			returnNum = 1;
		}
		
		tx.commit();
		sess.close();
		
		return returnNum;
	} 
	
	/**删除订单支付
	 * 
	 * @param oid 订单支付编号
	 * @return 删除结果，若结果大于0，则删除成功；若结果小于等于0，则删除失败
	 */
	@Override
	public int deleteOrderPay(int oid) {

		int returnNum = 0;//初始化返回结果
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		orderPay = (OrderPayBean)sess.get(OrderPayBean.class, oid);
		if(orderPay != null) {
			sess.delete(orderPay);
			
			returnNum = 1;
		}
		
		tx.commit();
		sess.close();
		
		return returnNum;
	}
	
	/**更新订单支付
	 * 
	 * @param newOrderPay 新的订单支付
	 * @return 更新结果：若结果大于0，则更新成功；若结果小于等于0，则更新失败
	 */
	@Override
	public int updateOrderPay(OrderPayBean newOrderPay) {

		int returnNum = 0;//初始化返回结果
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		orderPay = (OrderPayBean)sess.get(OrderPayBean.class, newOrderPay.getOid());
		if(orderPay != null) {
			orderPay.setMid(newOrderPay.getMid());
			orderPay.setIsPayAcnt(newOrderPay.getIsPayAcnt());
			orderPay.setPayCategory(newOrderPay.getPayCategory());
			orderPay.setPayAcntName(newOrderPay.getPayAcntName());
			orderPay.setPayTime(newOrderPay.getPayTime());
			orderPay.setPayMoney(newOrderPay.getPayMoney());
			
			sess.update(orderPay);
			
			returnNum = 1;
		}
		
		tx.commit();
		sess.close();
		
		return returnNum;
	}
	
	/**查看订单支付信息
	 * 
	 * @param oid 订单支付编号
	 * @return 订单支付对象
	 */
	@Override
	public OrderPayBean showOrderPayDetail(int oid) {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		orderPay = (OrderPayBean)sess.get(OrderPayBean.class, oid);
		
		tx.commit();
		sess.close();
		
		return orderPay;
	}
	
}

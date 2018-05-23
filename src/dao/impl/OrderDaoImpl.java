package dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.OrderDao;
import po.OrderBean;

/**
 * 订单数据访问实现
 **/

public class OrderDaoImpl implements OrderDao {

	private SessionFactory sessionFactory;
	
	private OrderBean order;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void setOrder(OrderBean order) {
		this.order = order;
	}
	

	/**根据各种参数添加订单
	 * 
	 * @param mid 会员编号
	 * @param ppid 演出计划编号
	 * @param orderType 订单类型
	 * @param orderTime 订单完成时间
	 * @param seatCategory 座位类型
	 * @param seatNum 座位数量
	 * @param totalMoney 订单总额 
	 * @param realMoney 订单实际消费金额
	 * @return 添加结果：若结果大于0，则添加成功，返回值为新添加的订单对应的订单编号；若结果小于等于0，则添加失败
	 */
	@Override
	public int addOrder(int mid, int ppid, char orderType, Timestamp orderTime, char seatCategory, 
						 int seatNum, double totalMoney, double realMoney) {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		order.setMid(mid);
		order.setPpid(ppid);
		order.setOrderType(orderType);
		order.setOrderState('S');//刚创建的订单的状态为已提交尚未付款
		order.setOrderTime(orderTime);
		order.setSeatCategory(seatCategory);
		order.setSeatNum(seatNum);
		order.setTotalMoney(totalMoney);
		order.setRealMoney(realMoney);
		
		sess.save(order);
		int oid = order.getOid();
		
		tx.commit();
		sess.close();
		
		return oid;
	}
	
	/**删除订单
	 * 
	 * @param oid 订单编号
	 * @return 删除结果，若结果大于0，则删除成功；若结果小于等于0，则删除失败
	 */
	@Override
	public int deleteOrder(int oid) {

		int returnNum = 0;//初始化返回结果
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		order = (OrderBean)sess.get(OrderBean.class, oid);
		if(order != null) {
			sess.delete(order);
			
			returnNum = 1;
		}
		
		tx.commit();
		sess.close();
		
		return returnNum;
	}
	
	/**更新订单
	 * 
	 * @param newOrder 新的订单
	 * @return 更新结果：若结果大于0，则更新成功；若结果小于等于0，则更新失败
	 */
	@Override
	public int updateOrder(OrderBean newOrder) {

		int returnNum = 0;//初始化返回结果
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		order = (OrderBean)sess.get(OrderBean.class, newOrder.getOid());
		if(order != null) {
			order.setMid(newOrder.getMid());
			order.setPpid(newOrder.getPpid());
			order.setOrderType(newOrder.getOrderType());
			order.setOrderState(newOrder.getOrderState());
			order.setOrderTime(newOrder.getOrderTime());
			order.setSeatCategory(newOrder.getSeatCategory());
			order.setSeatNum(newOrder.getSeatNum());
			order.setTotalMoney(newOrder.getTotalMoney());
			order.setRealMoney(newOrder.getRealMoney());
			
			sess.update(order);
			
			returnNum = 1;
		}
		
		tx.commit();
		sess.close();
		
		return returnNum;
	}
	
	/**查看订单信息
	 * 
	 * @param oid 订单编号
	 * @return 订单对象
	 */
	@Override
	public OrderBean showOrderDetail(int oid) {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		order = (OrderBean)sess.get(OrderBean.class, oid);
		
		tx.commit();
		sess.close();
		
		return order;
	}
	
	/**查看某会员的所有订单
	 * 
	 * @param mid 会员编号
	 * @return 该会员的所有订单对象列表
	 */
	@Override
	public List<OrderBean> showAllOrdersOfAMember(int mid) {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		@SuppressWarnings("unchecked")
		List<OrderBean> list = sess.createQuery("select ob from OrderBean ob where ob.mid=?")
							   .setParameter(0, mid).list();
		
		tx.commit();
		sess.close();
		
		return list;
	}
	
	/**查看某会员的所有退款订单
	 * 
	 * @param mid 会员编号
	 * @return 该会员的所有退款订单对象列表
	 */
	@Override
	public List<OrderBean> showAllRefundOrdersOfAMember(int mid) {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		@SuppressWarnings("unchecked")
		List<OrderBean> list = sess.createQuery("select ob from OrderBean ob where ob.mid=? and ob.orderState='R'")
							   .setParameter(0, mid).list();
		
		tx.commit();
		sess.close();
		
		return list;
	}
	
	/**查看某场馆的所有订单
	 * 
	 * @param vid 场馆编号
	 * @return 该场馆的所有订单对象列表
	 */
	@Override
	public List<OrderBean> showAllOrdersOfAVenue(int vid) {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		@SuppressWarnings("unchecked")
		List<OrderBean> list = sess.createQuery("select ob from OrderBean ob, PerformancePlanBean ppb where ob.ppid=ppb.ppid and ppb.vid=?")
							   .setParameter(0, vid).list();
		
		tx.commit();
		sess.close();
		
		return list;
	}
	
	/**查看某场馆的所有退款订单
	 * 
	 * @param vid 场馆编号
	 * @return 该场馆的所有退款订单对象列表
	 */
	@Override
	public List<OrderBean> showAllRefundOrdersOfAVenue(int vid) {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		@SuppressWarnings("unchecked")
		List<OrderBean> list = sess.createQuery("select ob from OrderBean ob,PerformancePlanBean ppb where ob.orderState='R' and ob.ppid=ppb.ppid and ppb.vid=?")
							   .setParameter(0, vid).list();
		
		tx.commit();
		sess.close();
		
		return list;
	}

	/**查看某场馆从某段时间开始的所有订单
	 * 
	 * @param vid 场馆编号
	 * @param time 指定时间
	 * @return 该场馆从这段时间开始的所有订单对象列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderBean> showAllOrdersOfAVenueFromATime(int vid, Timestamp time) {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		List<OrderBean> list;
		if(time != null) {
			list = sess.createQuery("select ob from OrderBean ob, PerformancePlanBean ppb where ob.orderTime>? and ob.ppid=ppb.ppid and ppb.vid=?")
							   .setParameter(0, time).setParameter(1, vid).list();
		}
		else {
			list = sess.createQuery("select ob from OrderBean ob, PerformancePlanBean ppb where ob.ppid=ppb.ppid and ppb.vid=?")
					   		   .setParameter(0, vid).list();
		}
		tx.commit();
		sess.close();
		
		return list;
	}
	
	/**查看某场馆从某段时间开始的所有退款订单
	 * 
	 * @param vid 场馆编号
	 * @param time 指定时间
	 * @return 该场馆从这段时间开始的所有退款订单对象列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderBean> showAllRefundOrdersOfAVenueFromATime(int vid, Timestamp time) {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();
		
		List<OrderBean> list;
		if(time != null) {
			list = sess.createQuery("select ob from OrderBean ob, PerformancePlanBean ppb where ob.orderTime>? and ob.orderState='R' and ob.ppid=ppb.ppid and ppb.vid=?")
				 			   .setParameter(0, time).setParameter(1, vid).list();
		}
		else {
			list = sess.createQuery("select ob from OrderBean ob, PerformancePlanBean ppb where ob.orderState='R' and ob.ppid=ppb.ppid and ppb.vid=?")
		 			   		   .setParameter(0, vid).list();
		}
		
		tx.commit();
		sess.close();
		
		return list;
	}
	
}

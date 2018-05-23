package dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.TicketsInfoDao;
import po.TicketsInfoBean;

/**
 * Tickets集团信息数据访问实现
 **/

public class TicketsInfoDaoImpl implements TicketsInfoDao {

	private SessionFactory sessionFactory;
	
	private TicketsInfoBean ticketsInfo;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void setTicketsInfo(TicketsInfoBean ticketsInfo) {
		this.ticketsInfo = ticketsInfo;
	}

	
	/**更新集团信息
	 * 
	 * @param newTicketsInfo 新的集团信息
	 * @return 更新结果：若结果大于0，则更新成功；若结果小于等于0，则更新失败
	 */
	@Override
	public int updateTicketsInfo(TicketsInfoBean newTicketsInfo) {

		int returnNum = 0;//初始化返回结果
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		ticketsInfo = (TicketsInfoBean)sess.get(TicketsInfoBean.class, newTicketsInfo.getMgrId());
		if(ticketsInfo != null) {
			ticketsInfo.setMgrPasswd(newTicketsInfo.getMgrPasswd());
			ticketsInfo.setBalance(newTicketsInfo.getBalance());
			
			sess.update(ticketsInfo);
			
			returnNum = 1;
		}
		
		tx.commit();
		sess.close();
		
		return returnNum;
	}
	
	/**查看集团信息
	 * 
	 * @return 集团信息对象
	 */
	@Override
	public TicketsInfoBean showTicketsInfoDetail() {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		@SuppressWarnings("unchecked")
		List<TicketsInfoBean> list = sess.createQuery("select tib from TicketsInfoBean tib").list();
		if(list.size() != 0) {
			ticketsInfo = list.get(0);
		}
		else {
			ticketsInfo = null;
		}

		tx.commit();
		sess.close();
		
		return ticketsInfo;
	}
	
}

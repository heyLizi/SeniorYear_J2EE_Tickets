package dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.SettleIncomeDao;
import po.SettleIncomeBean;

/**
 * 结算收入数据访问接口
 **/

public class SettleIncomeDaoImpl implements SettleIncomeDao {

	private SessionFactory sessionFactory;
	
	private SettleIncomeBean settleIncome;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void setSettleIncome(SettleIncomeBean settleIncome) {
		this.settleIncome = settleIncome;
	}

	
	/**根据各种参数增加结算收入
	 * 
	 * @param vid 场馆编号
	 * @param settleTime 结算时间
	 * @param income 收入金额
	 * @return 添加结果：若结果大于0，则添加成功，返回值为新添加的结算收入对应的结算收入编号；若结果小于等于0，则添加失败
	 */
	@Override
	public int addSettleIncome(int vid, Timestamp settleTime, double income) {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		settleIncome.setVid(vid);
		settleIncome.setSettleTime(settleTime);
		settleIncome.setIncome(income);
	
		sess.save(settleIncome);
		int siid = settleIncome.getSiid();
		
		tx.commit();
		sess.close();
		
		return siid;
	}
	
	/**删除结算收入
	 * 
	 * @param siid 结算收入编号
	 * @return 删除结果，若结果大于0，则删除成功；若结果小于等于0，则删除失败
	 */
	@Override
	public int deleteSettleIncome(int siid) {

		int returnNum = 0;//初始化返回结果
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		settleIncome = (SettleIncomeBean)sess.get(SettleIncomeBean.class, siid);
		if(settleIncome != null) {
			sess.delete(settleIncome);
			
			returnNum = 1;
		}
		
		tx.commit();
		sess.close();
		
		return returnNum;
	}
	
	/**更新结算收入
	 * 
	 * @param newSettleIncome 新的结算收入
	 * @return 更新结果：若结果大于0，则更新成功；若结果小于等于0，则更新失败
	 */
	public int updateSettleIncome(SettleIncomeBean newSettleIncome) {

		int returnNum = 0;//初始化返回结果
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		settleIncome = (SettleIncomeBean)sess.get(SettleIncomeBean.class, newSettleIncome.getSiid());
		if(settleIncome != null) {
			settleIncome.setVid(newSettleIncome.getVid());
			settleIncome.setSettleTime(newSettleIncome.getSettleTime());
			settleIncome.setIncome(newSettleIncome.getIncome());
			
			sess.update(settleIncome);
			
			returnNum = 1;
		}
		
		tx.commit();
		sess.close();
		
		return returnNum;
	}
	
	/**查看结算收入
	 * 
	 * @param siid 结算收入编号
	 * @return 结算收入对象
	 */
	public SettleIncomeBean showSettleIncomeDetail(int siid) {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		settleIncome = (SettleIncomeBean)sess.get(SettleIncomeBean.class, siid);

		tx.commit();
		sess.close();
		
		return settleIncome;
	}
	
	/**查看某场馆上一次结算收入的时间
	 * 
	 * @param vid 场馆编号
	 * @return 该场馆上一次结算收入的时间
	 */
	@Override
	public Timestamp showLastSettleTime(int vid) {

		Timestamp time;
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		@SuppressWarnings("unchecked")
		List<SettleIncomeBean> list = sess.createQuery("select sib from SettleIncomeBean sib where sib.vid=? order by settleTime desc")
									  	  .setParameter(0, vid).list();

		if(list.size() != 0) {
			settleIncome = list.get(0);
			time =settleIncome.getSettleTime();
		}
		else {
			time = null;
		}
		
		tx.commit();
		sess.close();
		
		return time;
	}
	
}

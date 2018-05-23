package dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.VenueAccountDao;
import po.VenueAccountBean;

/**
 * 场馆账户数据访问实现
 **/

public class VenueAccountDaoImpl implements VenueAccountDao {
	
	private SessionFactory sessionFactory;
	
	private VenueAccountBean venueAccount;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void setVenueAccount(VenueAccountBean venueAccount) {
		this.venueAccount = venueAccount;
	}
	

	/**根据场馆编号增加场馆账户，场馆账户编号同场馆编号
	 * 
	 * @param vid 场馆编号
	 * @return 添加结果：若结果大于0，则添加成功；若结果小于等于0，则添加失败
	 */
	@Override
	public int addVenueAccount(int vid) {

		int returnNum = 0;//初始化返回结果
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		venueAccount = (VenueAccountBean)sess.get(VenueAccountBean.class, vid);
		if(venueAccount == null) {
			venueAccount = new VenueAccountBean();
			venueAccount.setVid(vid);
			venueAccount.setBalance(0);//刚添加的场馆账户金额为0元
			
			sess.save(venueAccount);
			
			returnNum = 1;
		}
		
		tx.commit();
		sess.close();
		
		return returnNum;
	}
	
	/**删除场馆账户
	 * 
	 * @param vid 场馆账户编号
	 * @return 删除结果，若结果大于0，则删除成功；若结果小于等于0，则删除失败
	 */
	@Override
	public int deleteVenueAccount(int vid) {

		int returnNum = 0;//初始化返回结果
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		venueAccount = (VenueAccountBean)sess.get(VenueAccountBean.class, vid);
		if(venueAccount != null) {
			sess.delete(venueAccount);
			
			returnNum = 1;
		}
		
		tx.commit();
		sess.close();
		
		return returnNum;
	}
	
	/**更新场馆账户
	 * 
	 * @param newVenueAccount 新的场馆账户
	 * @return 更新结果：若结果大于0，则更新成功；若结果小于等于0，则更新失败
	 */
	@Override
	public int updateVenueAccount(VenueAccountBean newVenueAccount) {

		int returnNum = 0;//初始化返回结果
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		venueAccount = (VenueAccountBean)sess.get(VenueAccountBean.class, newVenueAccount.getVid());
		if(venueAccount != null) {
			venueAccount.setBalance(newVenueAccount.getBalance());
			
			sess.update(venueAccount);
			
			returnNum = 1;
		}
		
		tx.commit();
		sess.close();
		
		return returnNum;
	}
	
	/**查看场馆账户
	 * 
	 * @param vid 场馆账户编号
	 * @return 场馆账户对象
	 */
	@Override
	public VenueAccountBean showVenueAccountDetail(int vid) {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		venueAccount = (VenueAccountBean)sess.get(VenueAccountBean.class, vid);

		tx.commit();
		sess.close();
		
		return venueAccount;
	}
	
}

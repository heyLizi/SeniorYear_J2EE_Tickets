package dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.VenueApplicantDao;
import po.VenueApplicantBean;

/**
 * 场馆申请者数据访问实现
 **/

public class VenueApplicantDaoImpl implements VenueApplicantDao {

	private SessionFactory sessionFactory;
	
	private VenueApplicantBean venueApplicant;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void setVenueApplicant(VenueApplicantBean venueApplicant) {
		this.venueApplicant = venueApplicant;
	}
	

	/**根据联系电话和预设密码添加场馆申请者
	 * 
	 * @param telephone 联系电话
	 * @param vaerPasswd 预设密码

	 * @return 添加结果：若结果大于0，则添加成功，返回值为新添加的场馆申请者对应的场馆申请者编号；若结果小于等于0，则添加失败
	 */
	@Override
	public int addVenueApplicant(String telephone, String vaerPasswd) {

		int returnNum = 0;//初始化返回结果
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		@SuppressWarnings("unchecked")
		List<VenueApplicantBean> list = sess.createQuery("select vab from VenueApplicantBean vab where vab.telephone=?").setParameter(0, telephone).list();
		if(list.size() == 0) {
			venueApplicant = new VenueApplicantBean();
			venueApplicant.setTelephone(telephone);
			venueApplicant.setVaerPasswd(vaerPasswd);
		
			sess.save(venueApplicant);
			int vaerid = venueApplicant.getVaerid();
			returnNum = vaerid;
		}
		
		tx.commit();
		sess.close();
		
		return returnNum;
	}
	
	/**删除场馆申请者
	 * 
	 * @param vaerid 场馆申请者编号
	 * @return 删除结果，若结果大于0，则删除成功；若结果小于等于0，则删除失败
	 */
	@Override
	public int deleteVenueApplicant(int vaerid) {

		int returnNum = 0;//初始化返回结果
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		venueApplicant = (VenueApplicantBean)sess.get(VenueApplicantBean.class, vaerid);
		if(venueApplicant != null) {
			sess.delete(venueApplicant);
			
			returnNum = 1;
		}
		
		tx.commit();
		sess.close();
		
		return returnNum;
	}
	
	/**更新场馆申请者
	 * 
	 * @param newVenueApplicant 新的场馆申请
	 * @return 更新结果：若结果大于0，则更新成功；若结果小于等于0，则更新失败
	 */
	@Override
	public int updateVenueApplicant(VenueApplicantBean newVenueApplicant) {

		int returnNum = 0;//初始化返回结果
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		venueApplicant = (VenueApplicantBean)sess.get(VenueApplicantBean.class, newVenueApplicant.getVaerid());
		if(venueApplicant != null) {
			venueApplicant.setTelephone(newVenueApplicant.getTelephone());
			venueApplicant.setVaerPasswd(newVenueApplicant.getVaerPasswd());
			
			sess.update(venueApplicant);
			
			returnNum = 1;
		}
		
		tx.commit();
		sess.close();
		
		return returnNum;
	}
	
	/**查看场馆申请者
	 * 
	 * @param vaerid 场馆申请者编号
	 * @return 场馆申请者对象
	 */
	@Override
	public VenueApplicantBean showVenueApplicantDetail(int vaerid) {
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		venueApplicant = (VenueApplicantBean)sess.get(VenueApplicantBean.class, vaerid);

		tx.commit();
		sess.close();
		
		return venueApplicant;
	}
	
}

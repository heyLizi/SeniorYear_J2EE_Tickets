package dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.VenueApplicationDao;
import po.VenueApplicationBean;

/**
 * 场馆申请数据访问实现
 **/

public class VenueApplicationDaoImpl implements VenueApplicationDao {

	private SessionFactory sessionFactory;
	
	private VenueApplicationBean venueApplication;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void setVenueApplication(VenueApplicationBean venueApplication) {
		this.venueApplication = venueApplication;
	}
	
	
	/**根据各种参数添加场馆申请
	 * 
	 * @param vaerid 场馆申请者编号
	 * @param vname 场馆名称
	 * @param vpasswd 场馆账号密码
	 * @param province 场馆所在省
	 * @param city 场馆所在市
	 * @param area 场馆所在区
	 * @param address 场馆地址
	 * @param seatVipRowNum VIP类座位排数
	 * @param seatVipColNum VIP类座位列数
	 * @param seatARowNum A类座位排数
	 * @param seatAColNum A类座位列数
	 * @param seatBRowNum B类座位排数
	 * @param seatBColNum B类座位列数
	 * @param seatCRowNum C类座位排数
	 * @param seatCColNum C类座位列数
	 * @param seatDRowNum D类座位排数
	 * @param seatDColNum D类座位列数
	 * @return 添加结果：若结果大于0，则添加成功，返回值为新添加的场馆申请对应的场馆申请编号；若结果小于等于0，则添加失败
	 */
	@Override
	public int addVenueApplication(int vaerid, String vname, String vpasswd, String province, String city, String area, String address,
						int seatVipRowNum, int seatVipColNum, int seatARowNum, int seatAColNum, int seatBRowNum, 
						int seatBColNum, int seatCRowNum, int seatCColNum, int seatDRowNum,int seatDColNum) {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		venueApplication.setVaerid(vaerid);
		venueApplication.setVaState('U');//刚提交的场馆申请状态为未审核
		venueApplication.setVname(vname);
		venueApplication.setVpasswd(vpasswd);
		venueApplication.setProvince(province);
		venueApplication.setCity(city);
		venueApplication.setArea(area);
		venueApplication.setAddress(address);
		venueApplication.setSeatVipRowNum(seatVipRowNum);
		venueApplication.setSeatVipColNum(seatVipColNum);
		venueApplication.setSeatARowNum(seatARowNum);
		venueApplication.setSeatAColNum(seatAColNum);
		venueApplication.setSeatBRowNum(seatBRowNum);
		venueApplication.setSeatBColNum(seatBColNum);
		venueApplication.setSeatCRowNum(seatCRowNum);
		venueApplication.setSeatCColNum(seatCColNum);
		venueApplication.setSeatDRowNum(seatDRowNum);
		venueApplication.setSeatDColNum(seatDColNum);
		
		sess.save(venueApplication);
		int vaid = venueApplication.getVaid();
		
		tx.commit();
		sess.close();
		
		return vaid;
	}
	
	/**删除场馆申请
	 * 
	 * @param vaid 场馆申请编号
	 * @return 删除结果，若结果大于0，则删除成功；若结果小于等于0，则删除失败
	 */
	@Override
	public int deleteVenueApplication(int vaid) {

		int returnNum = 0;//初始化返回结果
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		venueApplication = (VenueApplicationBean)sess.get(VenueApplicationBean.class, vaid);
		if(venueApplication != null) {
			sess.delete(venueApplication);
			
			returnNum = 1;
		}
		
		tx.commit();
		sess.close();
		
		return returnNum;
	}
	
	/**更新场馆申请
	 * 
	 * @param newVenueApplication 新的场馆申请
	 * @return 更新结果：若结果大于0，则更新成功；若结果小于等于0，则更新失败
	 */
	@Override
	public int updateVenueApplication(VenueApplicationBean newVenueApplication) {

		int returnNum = 0;//初始化返回结果
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		venueApplication = (VenueApplicationBean)sess.get(VenueApplicationBean.class, newVenueApplication.getVaid());
		if(venueApplication != null) {
			venueApplication.setVaerid(newVenueApplication.getVaerid());
			venueApplication.setVaState(newVenueApplication.getVaState());
			venueApplication.setVname(newVenueApplication.getVname());
			venueApplication.setVpasswd(newVenueApplication.getVpasswd());
			venueApplication.setProvince(newVenueApplication.getProvince());
			venueApplication.setCity(newVenueApplication.getCity());
			venueApplication.setArea(newVenueApplication.getArea());
			venueApplication.setAddress(newVenueApplication.getAddress());
			venueApplication.setSeatVipRowNum(newVenueApplication.getSeatVipRowNum());
			venueApplication.setSeatVipColNum(newVenueApplication.getSeatVipColNum());
			venueApplication.setSeatARowNum(newVenueApplication.getSeatARowNum());
			venueApplication.setSeatAColNum(newVenueApplication.getSeatAColNum());
			venueApplication.setSeatBRowNum(newVenueApplication.getSeatBRowNum());
			venueApplication.setSeatBColNum(newVenueApplication.getSeatBColNum());
			venueApplication.setSeatCRowNum(newVenueApplication.getSeatCRowNum());
			venueApplication.setSeatCColNum(newVenueApplication.getSeatCColNum());
			venueApplication.setSeatDRowNum(newVenueApplication.getSeatDRowNum());
			venueApplication.setSeatDColNum(newVenueApplication.getSeatDColNum());
			
			sess.update(venueApplication);
			
			returnNum = 1;
		}
		
		tx.commit();
		sess.close();
		
		return returnNum;
	}
	
	/**查看场馆申请
	 * 
	 * @param vaid 场馆申请编号
	 * @return 场馆申请对象
	 */
	@Override
	public VenueApplicationBean showVenueApplicationDetail(int vaid) {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		venueApplication = (VenueApplicationBean)sess.get(VenueApplicationBean.class, vaid);
		
		tx.commit();
		sess.close();
		
		return venueApplication;
	}
	
	/**查看某申请者的所有场馆申请
	 * 
	 * @param vaerid 场馆申请者编号
	 * @return 该申请者的所有场馆申请对象
	 */
	public List<VenueApplicationBean> showAllVenueApplicationsOfAnApplicant(int vaerid) {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		@SuppressWarnings("unchecked")
		List<VenueApplicationBean> list = sess.createQuery("select vab from VenueApplicationBean vab where vab.vaerid=?").setParameter(0, vaerid).list();
		
		tx.commit();
		sess.close();
		
		return list;
	}
	
	/**查看所有的场馆申请
	 * 
	 * @return 所有场馆申请对象列表
	 */
	@Override
	public List<VenueApplicationBean> showAllVenueApplications() {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		@SuppressWarnings("unchecked")
		List<VenueApplicationBean> list = sess.createQuery("select vab from VenueApplicationBean vab").list();
		
		tx.commit();
		sess.close();
		
		return list;
	}
	
	/**查看所有未审批的场馆申请
	 * 
	 * @return 所有未审批的场馆申请对象列表
	 */
	@Override
	public List<VenueApplicationBean> showAllUncheckedVenueApplications() {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		@SuppressWarnings("unchecked")
		List<VenueApplicationBean> list = sess.createQuery("select vab from VenueApplicationBean vab where vab.vaState='U'").list();
		
		tx.commit();
		sess.close();
		
		return list;
	}
	
}

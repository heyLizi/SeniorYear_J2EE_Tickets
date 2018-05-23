package dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.VenueUpdateDao;
import po.VenueUpdateBean;

/**
 * 场馆更新数据访问实现
 **/

public class VenueUpdateDaoImpl implements VenueUpdateDao {

	private SessionFactory sessionFactory;
	
	private VenueUpdateBean venueUpdate;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void setVenueUpdate(VenueUpdateBean venueUpdate) {
		this.venueUpdate = venueUpdate;
	}
	
	
	/**根据各种参数添加场馆更新
	 * 
	 * @param vid 场馆编号
	 * @param vname 场馆名称
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
	 * @return 添加结果：若结果大于0，则添加成功，返回值为新添加的场馆更新对应的场馆更新编号；若结果小于等于0，则添加失败
	 */
	@Override
	public int addVenueUpdate(int vid, String vname, String province, String city, String area, String address,
						int seatVipRowNum, int seatVipColNum, int seatARowNum, int seatAColNum, int seatBRowNum, 
						int seatBColNum, int seatCRowNum, int seatCColNum, int seatDRowNum,int seatDColNum) {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		venueUpdate.setVid(vid);
		venueUpdate.setVuState('U');//刚提交的场馆更新状态为未审核
		venueUpdate.setVname(vname);
		venueUpdate.setProvince(province);
		venueUpdate.setCity(city);
		venueUpdate.setArea(area);
		venueUpdate.setAddress(address);
		venueUpdate.setSeatVipRowNum(seatVipRowNum);
		venueUpdate.setSeatVipColNum(seatVipColNum);
		venueUpdate.setSeatARowNum(seatARowNum);
		venueUpdate.setSeatAColNum(seatAColNum);
		venueUpdate.setSeatBRowNum(seatBRowNum);
		venueUpdate.setSeatBColNum(seatBColNum);
		venueUpdate.setSeatCRowNum(seatCRowNum);
		venueUpdate.setSeatCColNum(seatCColNum);
		venueUpdate.setSeatDRowNum(seatDRowNum);
		venueUpdate.setSeatDColNum(seatDColNum);
		
		sess.save(venueUpdate);
		int vuid = venueUpdate.getVuid();
		
		tx.commit();
		sess.close();
		
		return vuid;
	}
	
	/**删除场馆更新
	 * 
	 * @param vuid 场馆更新编号
	 * @return 删除结果，若结果大于0，则删除成功；若结果小于等于0，则删除失败
	 */
	@Override
	public int deleteVenueUpdate(int vuid) {

		int returnNum = 0;//初始化返回结果
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		venueUpdate = (VenueUpdateBean)sess.get(VenueUpdateBean.class, vuid);
		if(venueUpdate != null) {
			sess.delete(venueUpdate);
			
			returnNum = 1;
		}
		
		tx.commit();
		sess.close();
		
		return returnNum;
	}
	
	/**更新场馆更新
	 * 
	 * @param newVenueUpdate 新的场馆更新
	 * @return 更新结果：若结果大于0，则更新成功；若结果小于等于0，则更新失败
	 */
	@Override
	public int updateVenueUpdate(VenueUpdateBean newVenueUpdate) {

		int returnNum = 0;//初始化返回结果
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		venueUpdate = (VenueUpdateBean)sess.get(VenueUpdateBean.class, newVenueUpdate.getVuid());
		if(venueUpdate != null) {
			venueUpdate.setVuState(newVenueUpdate.getVuState());
			venueUpdate.setVname(newVenueUpdate.getVname());
			venueUpdate.setProvince(newVenueUpdate.getProvince());
			venueUpdate.setCity(newVenueUpdate.getCity());
			venueUpdate.setArea(newVenueUpdate.getArea());
			venueUpdate.setAddress(newVenueUpdate.getAddress());
			venueUpdate.setSeatVipRowNum(newVenueUpdate.getSeatVipRowNum());
			venueUpdate.setSeatVipColNum(newVenueUpdate.getSeatVipColNum());
			venueUpdate.setSeatARowNum(newVenueUpdate.getSeatARowNum());
			venueUpdate.setSeatAColNum(newVenueUpdate.getSeatAColNum());
			venueUpdate.setSeatBRowNum(newVenueUpdate.getSeatBRowNum());
			venueUpdate.setSeatBColNum(newVenueUpdate.getSeatBColNum());
			venueUpdate.setSeatCRowNum(newVenueUpdate.getSeatCRowNum());
			venueUpdate.setSeatCColNum(newVenueUpdate.getSeatCColNum());
			venueUpdate.setSeatDRowNum(newVenueUpdate.getSeatDRowNum());
			venueUpdate.setSeatDColNum(newVenueUpdate.getSeatDColNum());
			
			sess.update(venueUpdate);
			
			returnNum = 1;
		}
		
		tx.commit();
		sess.close();
		
		return returnNum;
	}
	
	/**查看场馆更新
	 * 
	 * @param vuid 场馆更新编号
	 * @return 场馆更新对象
	 */
	@Override
	public VenueUpdateBean showVenueUpdateDetail(int vuid) {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		venueUpdate = (VenueUpdateBean)sess.get(VenueUpdateBean.class, vuid);
		
		tx.commit();
		sess.close();
		
		return venueUpdate;
	}

	/**查看所有的场馆更新
	 * 
	 * @return 所有场馆更新对象列表
	 */
	@Override
	public List<VenueUpdateBean> showAllVenueUpdates() {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		@SuppressWarnings("unchecked")
		List<VenueUpdateBean> list = sess.createQuery("select vub from VenueUpdateBean vub").list();
		
		tx.commit();
		sess.close();
		
		return list;
	}
	
	/**查看所有未审批的场馆更新
	 * 
	 * @return 所有未审批的场馆更新对象列表
	 */
	@Override
	public List<VenueUpdateBean> showAllUncheckedVenueUpdates() {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		@SuppressWarnings("unchecked")
		List<VenueUpdateBean> list = sess.createQuery("select vub from VenueUpdateBean vub where vub.vuState='U'").list();
		
		tx.commit();
		sess.close();
		
		return list;
	}
	
}

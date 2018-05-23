package dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.VenueDao;
import po.VenueBean;

/**
 * 场馆数据访问实现
 **/

public class VenueDaoImpl implements VenueDao {

	private SessionFactory sessionFactory;
	
	private VenueBean venue;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void setVenue(VenueBean venue) {
		this.venue = venue;
	}

	
	/**根据各种参数添加场馆
	 * 
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
	 * @return 添加结果：若结果大于0，则添加成功，返回值为新添加的场馆对应的场馆编号；若结果小于等于0，则添加失败
	 */
	@Override
	public int addVenue(String vname, String vpasswd, String province, String city, String area, String address,
						int seatVipRowNum, int seatVipColNum, int seatARowNum, int seatAColNum, int seatBRowNum, 
						int seatBColNum, int seatCRowNum, int seatCColNum, int seatDRowNum,int seatDColNum) {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		venue.setVname(vname);
		venue.setVpasswd(vpasswd);
		venue.setProvince(province);
		venue.setCity(city);
		venue.setArea(area);
		venue.setAddress(address);
		venue.setSeatVipRowNum(seatVipRowNum);
		venue.setSeatVipColNum(seatVipColNum);
		venue.setSeatARowNum(seatARowNum);
		venue.setSeatAColNum(seatAColNum);
		venue.setSeatBRowNum(seatBRowNum);
		venue.setSeatBColNum(seatBColNum);
		venue.setSeatCRowNum(seatCRowNum);
		venue.setSeatCColNum(seatCColNum);
		venue.setSeatDRowNum(seatDRowNum);
		venue.setSeatDColNum(seatDColNum);
		
		sess.save(venue);
		int vid = venue.getVid();
		
		tx.commit();
		sess.close();
		
		return vid;
	}
	
	/**删除场馆
	 * 
	 * @param vid 场馆编号
	 * @return 删除结果，若结果大于0，则删除成功；若结果小于等于0，则删除失败
	 */
	@Override
	public int deleteVenue(int vid) {

		int returnNum = 0;//初始化返回结果
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		venue = (VenueBean)sess.get(VenueBean.class, vid);
		if(venue != null) {
			sess.delete(venue);
			
			returnNum = 1;
		}
		
		tx.commit();
		sess.close();
		
		return returnNum;
	}
	
	/**更新场馆
	 * 
	 * @param newVenue 新的场馆
	 * @return 更新结果：若结果大于0，则更新成功；若结果小于等于0，则更新失败
	 */
	public int updateVenue(VenueBean newVenue) {

		int returnNum = 0;//初始化返回结果
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		venue = (VenueBean)sess.get(VenueBean.class, newVenue.getVid());
		if(venue != null) {
			venue.setVname(newVenue.getVname());
			venue.setVpasswd(newVenue.getVpasswd());
			venue.setProvince(newVenue.getProvince());
			venue.setCity(newVenue.getCity());
			venue.setArea(newVenue.getArea());
			venue.setAddress(newVenue.getAddress());
			venue.setSeatVipRowNum(newVenue.getSeatVipRowNum());
			venue.setSeatVipColNum(newVenue.getSeatVipColNum());
			venue.setSeatARowNum(newVenue.getSeatARowNum());
			venue.setSeatAColNum(newVenue.getSeatAColNum());
			venue.setSeatBRowNum(newVenue.getSeatBRowNum());
			venue.setSeatBColNum(newVenue.getSeatBColNum());
			venue.setSeatCRowNum(newVenue.getSeatCRowNum());
			venue.setSeatCColNum(newVenue.getSeatCColNum());
			venue.setSeatDRowNum(newVenue.getSeatDRowNum());
			venue.setSeatDColNum(newVenue.getSeatDColNum());
			
			sess.update(venue);
			
			returnNum = 1;
		}
		
		tx.commit();
		sess.close();
		
		return returnNum;
	}
	
	/**查看场馆
	 * 
	 * @param vid 场馆编号
	 * @return 场馆对象
	 */
	@Override
	public VenueBean showVenueDetail(int vid) {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		venue = (VenueBean)sess.get(VenueBean.class, vid);
		
		tx.commit();
		sess.close();
		
		return venue;
	}
	
	/**显示所有场馆
	 * 
	 * @return 所有场馆对象列表
	 */
	@Override
	public List<VenueBean> showAllVenues() {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		@SuppressWarnings("unchecked")
		List<VenueBean> list = sess.createQuery("select vb from VenueBean vb").list();
		
		tx.commit();
		sess.close();
		
		return list;
	}

}

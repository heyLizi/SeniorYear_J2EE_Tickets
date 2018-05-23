package dao.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.PerformancePlanDao;
import po.PerformancePlanBean;

/**
 * 演出计划数据访问实现
 **/

public class PerformancePlanDaoImpl implements PerformancePlanDao {

	private SessionFactory sessionFactory;
	
	private PerformancePlanBean performancePlan;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void setPerformancePlan(PerformancePlanBean performancePlan) {
		this.performancePlan = performancePlan;
	}
	
	
	/**根据各种参数添加演出计划
	 * 
	 * @param ppname 演出计划名称
	 * @param vid 场馆编号
	 * @param startTime 演出开始时间
	 * @param endTime 演出结束时间
	 * @param category 演出类型
	 * @param briefIntro 演出简要介绍
	 * @param seatVipPrice VIP类座位的价格
	 * @param seatAPrice A类座位的价格
	 * @param seatBPrice B类座位的价格
	 * @param seatCPrice C类座位的价格
	 * @param seatDPrice D类座位的价格
	 * @return 添加结果：若结果大于0，则添加成功，返回值为新添加的演出计划对应的演出计划编号；若结果小于等于0，则添加失败
	 */
	@Override
	public int addPerformancePlan(String ppname, int vid, Timestamp startTime, Timestamp endTime, char category, String briefIntro,
							  int seatVipPrice, int seatAPrice, int seatBPrice, int seatCPrice, int seatDPrice) {
		
		Session sess = sessionFactory.openSession();
		Transaction tx = sess.beginTransaction();
		
		performancePlan.setPpname(ppname);
		performancePlan.setVid(vid);
		performancePlan.setStartTime(startTime);
		performancePlan.setEndTime(endTime);
		performancePlan.setCategory(category);
		performancePlan.setBriefIntro(briefIntro);
		performancePlan.setSeatVipPrice(seatVipPrice);
		performancePlan.setSeatAPrice(seatAPrice);
		performancePlan.setSeatBPrice(seatBPrice);
		performancePlan.setSeatCPrice(seatCPrice);
		performancePlan.setSeatDPrice(seatDPrice);
		
		sess.save(performancePlan);
		int ppid = performancePlan.getPpid();
		
		tx.commit();
		sess.close();
		
		return ppid;
	}
	
	/**删除演出计划
	 * 
	 * @param ppid 演出计划编号
	 * @return 删除结果，若结果大于0，则删除成功；若结果小于等于0，则删除失败
	 */
	@Override
	public int deletePerformancePlan(int ppid) {

		int returnNum = 0;//初始化返回结果
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		performancePlan = (PerformancePlanBean)sess.get(PerformancePlanBean.class, ppid);
		if(performancePlan != null) {
			sess.delete(performancePlan);
			
			returnNum = 1;
		}
		
		tx.commit();
		sess.close();
		
		return returnNum;
	}
	
	/**更新演出计划
	 * 
	 * @param newPerformancePlan 新的演出计划
	 * @return 更新结果：若结果大于0，则更新成功；若结果小于等于0，则更新失败
	 */
	@Override
	public int updatePerformancePlan(PerformancePlanBean newPerformancePlan) {

		int returnNum = 0;//初始化返回结果
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		performancePlan = (PerformancePlanBean)sess.get(PerformancePlanBean.class, newPerformancePlan.getPpid());
		if(performancePlan != null) {
			performancePlan.setPpname(newPerformancePlan.getPpname());
			performancePlan.setVid(newPerformancePlan.getVid());
			performancePlan.setStartTime(newPerformancePlan.getStartTime());
			performancePlan.setEndTime(newPerformancePlan.getEndTime());
			performancePlan.setCategory(newPerformancePlan.getCategory());
			performancePlan.setBriefIntro(newPerformancePlan.getBriefIntro());
			performancePlan.setSeatVipPrice(newPerformancePlan.getSeatVipPrice());
			performancePlan.setSeatAPrice(newPerformancePlan.getSeatAPrice());
			performancePlan.setSeatBPrice(newPerformancePlan.getSeatBPrice());
			performancePlan.setSeatCPrice(newPerformancePlan.getSeatCPrice());
			performancePlan.setSeatDPrice(newPerformancePlan.getSeatDPrice());
			
			sess.update(performancePlan);
			
			returnNum = 1;
		}
		
		tx.commit();
		sess.close();
		
		return returnNum;
	}
	
	/**查看演出计划
	 * 
	 * @param ppid 演出计划编号
	 * @return 演出计划对象
	 */
	@Override
	public PerformancePlanBean showPerformancePlanDetail(int ppid) {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		performancePlan = (PerformancePlanBean)sess.get(PerformancePlanBean.class, ppid);
		
		tx.commit();
		sess.close();
		
		return performancePlan;
	}
	
	/**查看所有的演出计划
	 * 
	 * @return 所有演出计划对象列表
	 */
	@Override
	public List<PerformancePlanBean> showAllPerformancePlans() {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		@SuppressWarnings("unchecked")
		List<PerformancePlanBean> list = sess.createQuery("select ppb from PerformancePlanBean ppb").list();
		
		tx.commit();
		sess.close();
		
		return list;
	}

	/**查看所有可获取的演出计划（可获取的演出计划：当前时间还未结束且距离开始时间不足14天的演出计划）
	 * 
	 * @param currentTime 当前时间
	 * @return 所有可获取的演出计划对象列表
	 */
	@Override
	public List<PerformancePlanBean> showAllAvailPerformancePlans(Timestamp currentTime) {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		//timestampdiff函数是用后面的日期减去前面的日期，这里设置的结果按天数计算。即便是时间的形式，若两个时间差值不足24小时，也不算1天
		@SuppressWarnings("unchecked")
		List<PerformancePlanBean> list = sess.createQuery("select ppb from PerformancePlanBean ppb where ppb.endTime>? and timestampdiff(day,?,ppb.startTime)<14")
										 .setParameter(0, currentTime).setParameter(1, currentTime).list();
		
		tx.commit();
		sess.close();
		
		return list;
	}
	
	/**查看某个场馆所有的演出计划
	 * 
	 * @param vid 场馆编号
	 * @return 该场馆的所有演出计划对象列表
	 */
	@Override
	public List<PerformancePlanBean> showAllPerformancePlansOfAVenue(int vid) {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		@SuppressWarnings("unchecked")
		List<PerformancePlanBean> list = sess.createQuery("select ppb from PerformancePlanBean ppb where ppb.vid=?")
									 .setParameter(0, vid).list();
		
		tx.commit();
		sess.close();
		
		return list;
	}
	
	/**查看某个场馆所有可获取的演出计划（可获取的演出计划：当前时间还未结束且距离开始时间不足14天的演出计划）
	 *
	 * @param vid 场馆编号
	 * @param currentTime 当前时间 
	 * @return 该场馆的所有可获取的演出计划对象列表
	 */
	@Override
	public List<PerformancePlanBean> showAllAvailPerformancePlansOfAVenue(int vid, Timestamp currentTime) {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		//timestampdiff函数是用后面的日期减去前面的日期，这里设置的结果按天数计算。即便是时间的形式，若两个时间差值不足24小时，也不算1天
		@SuppressWarnings("unchecked")
		List<PerformancePlanBean> list = sess.createQuery("select ppb from PerformancePlanBean ppb where ppb.vid=? and ppb.endTime>? and timestampdiff(day,?,ppb.startTime)<14").
									 	 setParameter(0, vid).setParameter(1, currentTime).setParameter(2, currentTime).list();
		
		tx.commit();
		sess.close();
		
		return list;
	}
	
	/**查看某查询条件下的所有演出计划
	 * 
	 * @param category 演出类型
	 * @param date 演出日期
	 * @param province 演出所在省
	 * @param city 演出所在市
	 * @param area 演出所在区
	 * @return 满足该查询条件下的所有演出计划对象列表
	 */
	@Override
	public List<PerformancePlanBean> showAllPerformancePlansInThisCondition(char category, Date date, 
																	String province, String city, String area) {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		@SuppressWarnings("unchecked")
		List<PerformancePlanBean> list = sess.createQuery("select ppb from PerformancePlanBean ppb, VenueBean vb where ppb.category=? and timestampdiff(day,?,ppb.startTime)<1 and ppb.vid=vb.vid and vb.province=? and vb.city=? and vb.area=?")
													  .setParameter(0, category).setParameter(1, date).setParameter(2, province).setParameter(3, city).setParameter(4, area).list();
		
		tx.commit();
		sess.close();
		
		return list;
	}
	
}

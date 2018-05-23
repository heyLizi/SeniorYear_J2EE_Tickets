package service.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import dao.PerformancePlanDao;
import po.PerformancePlanBean;
import service.PerformancePlanService;
import util.Result;

/**
 * 演出计划服务实现
 **/

public class PerformancePlanServiceImpl implements PerformancePlanService {

	private PerformancePlanDao performancePlanDao;

	public PerformancePlanDao getPerformancePlanDao() {
		return performancePlanDao;
	}

	public void setPerformancePlanDao(PerformancePlanDao performancePlanDao) {
		this.performancePlanDao = performancePlanDao;
	}
	
	
	/**发布演出计划
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
	 * @return 发布结果:Result的result属性若为true，表示发布成功，description属性表示发布成功后的演出计划编号；
	 * 				 Result的result属性若为false，表示发布失败，description属性表示失败原因（属性值不合理等）
	 */
	@Override
	public Result releasePlan(String ppname, int vid, Timestamp startTime, Timestamp endTime, char category, String briefIntro,
			  				  int seatVipPrice, int seatAPrice, int seatBPrice, int seatCPrice, int seatDPrice) {
		
		Result result;
		
		Timestamp currentTime = new java.sql.Timestamp(System.currentTimeMillis());//获取当前时间，即为订单时间
		if(currentTime.getTime() > startTime.getTime() || currentTime.getTime() > endTime.getTime() ) {
			result = new Result(false, "演出计划时间已过，请重新设置");
		}
		else {
			//添加演出计划
			int addPlanResult = performancePlanDao.addPerformancePlan(ppname, vid, startTime, endTime, category, briefIntro, 
																	  seatVipPrice, seatAPrice, seatBPrice, seatCPrice, seatDPrice);
			if(addPlanResult > 0) {//添加演出计划成功，返回成功结果+演出计划编号 
				result = new Result(true, addPlanResult+"");
			}
			else {//添加演出计划失败，返回错误信息
				result = new Result(false, "添加演出计划失败");
			}
		}
		
		return result;
	}
	
	
	/**撤销演出计划
	 * 
	 * @param ppid 演出计划编号
	 * @return 撤销结果:Result的result属性若为true，表示撤销成功，description属性无意义；
	 * 				 Result的result属性若为false，表示撤销失败，description属性表示失败原因（演出计划编号不存在等）
	 */
	@Override
	public Result deletePlan(int ppid) {
		
		Result result;
		
		//删除演出计划
		int deletePlanResult = performancePlanDao.deletePerformancePlan(ppid);
		if(deletePlanResult > 0) {//删除演出计划成功，返回成功结果
			result = new Result(true);
		}
		else {//删除演出计划失败，返回错误信息
			result = new Result(false, "删除演出计划失败");
		}
		
		return result;
	}
	
	
	/**更新演出计划
	 * 
	 * @param ppid 演出计划编号
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
	 * @return 更新结果:Result的result属性若为true，表示更新成功，description属性无意义；
	 * 				 Result的result属性若为false，表示更新失败，description属性表示失败原因（属性值不合理等）
	 */
	@Override
	public Result updatePlan(int ppid, String ppname, int vid, Timestamp startTime, Timestamp endTime, char category, String briefIntro,
			  				 int seatVipPrice, int seatAPrice, int seatBPrice, int seatCPrice, int seatDPrice) {
		
		Result result;
		
		//生成演出计划对象
		PerformancePlanBean performancePlan = new PerformancePlanBean();
		performancePlan.setPpid(ppid);
		performancePlan.setPpname(ppname);
		performancePlan.setStartTime(startTime);
		performancePlan.setEndTime(endTime);
		performancePlan.setCategory(category);
		performancePlan.setBriefIntro(briefIntro);
		performancePlan.setSeatVipPrice(seatVipPrice);
		performancePlan.setSeatAPrice(seatAPrice);
		performancePlan.setSeatBPrice(seatBPrice);
		performancePlan.setSeatCPrice(seatCPrice);
		performancePlan.setSeatDPrice(seatDPrice);
		
		//更新演出计划
		int updatePlanResult = performancePlanDao.updatePerformancePlan(performancePlan);
		if(updatePlanResult > 0) {//更新演出计划成功，返回成功结果
			result = new Result(true);
		}
		else {//更新演出计划失败，返回错误信息
			result = new Result(false, "更新演出计划失败");
		}
		
		return result;
	}
	
	
	/**显示演出计划信息
	 * 
	 * @param ppid 演出计划编号
	 * @return 演出计划对象
	 */
	@Override
	public PerformancePlanBean showPerformancePlanDetail(int ppid) {
		
		return performancePlanDao.showPerformancePlanDetail(ppid);
		
	}
	
	
	/**显示所有的演出计划
	 * 
	 * @return 所有演出计划对象列表
	 */
	@Override
	public List<PerformancePlanBean> showAllPerformancePlans() {
		
		return performancePlanDao.showAllPerformancePlans();
		
	}
	
	
	/**显示所有可获取的演出计划（可获取的演出计划：当前时间还未结束且距离开始时间不足14天的演出计划）
	 * 
	 * @return 所有可获取的演出计划对象列表
	 */
	@Override
	public List<PerformancePlanBean> showAllAvailPerformancePlans() {

		Timestamp currentTime = new java.sql.Timestamp(System.currentTimeMillis());//获取当前时间
		
		return performancePlanDao.showAllAvailPerformancePlans(currentTime);
		
	}
	
	
	/**显示某个场馆所有的演出计划
	 * 
	 * @param vid 场馆编号
	 * @return 该场馆的所有演出计划对象列表
	 */
	@Override
	public List<PerformancePlanBean> showAllPerformancePlansOfAVenue(int vid) {
		
		return performancePlanDao.showAllPerformancePlansOfAVenue(vid);
		
	}
	
	
	/**显示某个场馆所有可获取的演出计划（可获取的演出计划：当前时间还未结束且距离开始时间不足14天的演出计划）
	 *
	 * @param vid 场馆编号 
	 * @return 该场馆的所有可获取的演出计划对象列表
	 */
	@Override
	public List<PerformancePlanBean> showAllAvailPerformancePlansOfAVenue(int vid) {
		
		Timestamp currentTime = new java.sql.Timestamp(System.currentTimeMillis());//获取当前时间
		
		return performancePlanDao.showAllAvailPerformancePlansOfAVenue(vid, currentTime);
		
	}
	
	
	/**显示某查询条件下的所有演出计划
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

		Timestamp currentTime = new java.sql.Timestamp(System.currentTimeMillis());//获取当前时间，即为订单时间
		Long timeSpan = currentTime.getTime() - date.getTime();
		if(Math.abs(timeSpan/1000/60/60/24) > 14) {
			return new ArrayList<PerformancePlanBean>();
		}
		else {
			return performancePlanDao.showAllPerformancePlansInThisCondition(category, date, province, city, area);
		}
	}
	
}

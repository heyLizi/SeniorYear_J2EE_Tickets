package service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import po.PerformancePlanBean;
import util.Result;

/**
 * 演出计划服务接口
 **/

public interface PerformancePlanService {

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
	public Result releasePlan(String ppname, int vid, Timestamp startTime, Timestamp endTime, char category, String briefIntro,
			  				  int seatVipPrice, int seatAPrice, int seatBPrice, int seatCPrice, int seatDPrice);
	
	
	/**撤销演出计划
	 * 
	 * @param ppid 演出计划编号
	 * @return 撤销结果:Result的result属性若为true，表示撤销成功，description属性无意义；
	 * 				 Result的result属性若为false，表示撤销失败，description属性表示失败原因（演出计划编号不存在等）
	 */
	public Result deletePlan(int ppid);
	
	
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
	public Result updatePlan(int ppid, String ppname, int vid, Timestamp startTime, Timestamp endTime, char category, String briefIntro,
			  				 int seatVipPrice, int seatAPrice, int seatBPrice, int seatCPrice, int seatDPrice);
	
	
	/**显示演出计划信息
	 * 
	 * @param ppid 演出计划编号
	 * @return 演出计划对象
	 */
	public PerformancePlanBean showPerformancePlanDetail(int ppid);
	
	
	/**显示所有的演出计划
	 * 
	 * @return 所有演出计划对象列表
	 */
	public List<PerformancePlanBean> showAllPerformancePlans();
	
	
	/**显示所有可获取的演出计划（可获取的演出计划：当前时间还未结束且距离开始时间不足14天的演出计划）
	 * 
	 * @return 所有可获取的演出计划对象列表
	 */
	public List<PerformancePlanBean> showAllAvailPerformancePlans();
	
	
	/**显示某个场馆所有的演出计划
	 * 
	 * @param vid 场馆编号
	 * @return 该场馆的所有演出计划对象列表
	 */
	public List<PerformancePlanBean> showAllPerformancePlansOfAVenue(int vid);
	
	
	/**显示某个场馆所有可获取的演出计划（可获取的演出计划：当前时间还未结束且距离开始时间不足14天的演出计划）
	 *
	 * @param vid 场馆编号 
	 * @return 该场馆的所有可获取的演出计划对象列表
	 */
	public List<PerformancePlanBean> showAllAvailPerformancePlansOfAVenue(int vid);
	
	
	/**显示某查询条件下的所有演出计划
	 * 
	 * @param category 演出类型
	 * @param date 演出日期
	 * @param province 演出所在省
	 * @param city 演出所在市
	 * @param area 演出所在区
	 * @return 满足该查询条件下的所有演出计划对象列表
	 */
	public List<PerformancePlanBean> showAllPerformancePlansInThisCondition(char category, Date date, 
																	String province, String city, String area);
	
}

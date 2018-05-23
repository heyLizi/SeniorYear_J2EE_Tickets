package dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import po.PerformancePlanBean;

/**
 * 演出计划数据访问接口
 **/

public interface PerformancePlanDao {

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
	public int addPerformancePlan(String ppname, int vid, Timestamp startTime, Timestamp endTime, char category, String briefIntro,
							  int seatVipPrice, int seatAPrice, int seatBPrice, int seatCPrice, int seatDPrice);
	
	/**删除演出计划
	 * 
	 * @param ppid 演出计划编号
	 * @return 删除结果，若结果大于0，则删除成功；若结果小于等于0，则删除失败
	 */
	public int deletePerformancePlan(int ppid);
	
	/**更新演出计划
	 * 
	 * @param newReleasePlan 新的演出计划
	 * @return 更新结果：若结果大于0，则更新成功；若结果小于等于0，则更新失败
	 */
	public int updatePerformancePlan(PerformancePlanBean newPerformancePlan);
	
	/**查看演出计划
	 * 
	 * @param ppid 演出计划编号
	 * @return 演出计划对象
	 */
	public PerformancePlanBean showPerformancePlanDetail(int ppid);
	
	/**查看所有的演出计划
	 * 
	 * @return 所有演出计划对象列表
	 */
	public List<PerformancePlanBean> showAllPerformancePlans();
	
	/**查看所有可获取的演出计划（可获取的演出计划：当前时间还未结束且距离开始时间不足14天的演出计划）
	 * 
	 * @param currentTime 当前时间
	 * @return 所有可获取的演出计划对象列表
	 */
	public List<PerformancePlanBean> showAllAvailPerformancePlans(Timestamp currentTime);
	
	/**查看某个场馆所有的演出计划
	 * 
	 * @param vid 场馆编号
	 * @return 该场馆的所有演出计划对象列表
	 */
	public List<PerformancePlanBean> showAllPerformancePlansOfAVenue(int vid);
	
	/**查看某个场馆所有可获取的演出计划（可获取的演出计划：当前时间还未结束且距离开始时间不足14天的演出计划）
	 *
	 * @param vid 场馆编号
	 * @param currentTime 当前时间 
	 * @return 该场馆的所有可获取的演出计划对象列表
	 */
	public List<PerformancePlanBean> showAllAvailPerformancePlansOfAVenue(int vid, Timestamp currentTime);
	
	/**查看某查询条件下的所有演出计划
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

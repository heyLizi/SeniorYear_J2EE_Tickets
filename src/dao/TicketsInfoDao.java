package dao;

import po.TicketsInfoBean;

/**
 * Tickets集团信息数据访问接口
 **/

public interface TicketsInfoDao {
	
	/**更新集团信息
	 * 
	 * @param newTicketsInfo 新的集团信息
	 * @return 更新结果：若结果大于0，则更新成功；若结果小于等于0，则更新失败
	 */
	public int updateTicketsInfo(TicketsInfoBean newTicketsInfo);
	
	/**查看集团信息
	 * 
	 * @return 集团信息对象
	 */
	public TicketsInfoBean showTicketsInfoDetail();
	
}

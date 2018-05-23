package dao;

import java.util.List;

import po.VenueUpdateBean;

/**
 * 场馆更新数据访问接口
 **/

public interface VenueUpdateDao {
	
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
	 * @return 添加结果：若结果大于0，则添加成功，返回值为新添加的会员对应的会员编号；若结果小于等于0，则添加失败
	 */
	public int addVenueUpdate(int vid, String vname, String province, String city, String area, String address,
						int seatVipRowNum, int seatVipColNum, int seatARowNum, int seatAColNum, int seatBRowNum, 
						int seatBColNum, int seatCRowNum, int seatCColNum, int seatDRowNum,int seatDColNum);
	
	/**删除场馆更新
	 * 
	 * @param vuid 场馆更新编号
	 * @return 删除结果，若结果大于0，则删除成功；若结果小于等于0，则删除失败
	 */
	public int deleteVenueUpdate(int vuid);
	
	/**更新场馆更新
	 * 
	 * @param newVenue 新的场馆更新
	 * @return 更新结果：若结果大于0，则更新成功；若结果小于等于0，则更新失败
	 */
	public int updateVenueUpdate(VenueUpdateBean newVenueUpdate);
	
	/**查看场馆更新
	 * 
	 * @param vuid 场馆更新编号
	 * @return 场馆更新对象
	 */
	public VenueUpdateBean showVenueUpdateDetail(int vuid);

	/**查看所有的场馆更新
	 * 
	 * @return 所有场馆更新对象列表
	 */
	public List<VenueUpdateBean> showAllVenueUpdates();
	
	/**查看所有未审批的场馆更新
	 * 
	 * @return 所有未审批的场馆更新对象列表
	 */
	public List<VenueUpdateBean> showAllUncheckedVenueUpdates();
	
}

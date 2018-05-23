package dao;

import java.util.List;

import po.VenueBean;

/**
 * 场馆数据访问接口
 **/

public interface VenueDao {
	
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
	public int addVenue(String vname, String vpasswd, String province, String city, String area, String address,
						int seatVipRowNum, int seatVipColNum, int seatARowNum, int seatAColNum, int seatBRowNum, 
						int seatBColNum, int seatCRowNum, int seatCColNum, int seatDRowNum,int seatDColNum);
	
	/**删除场馆
	 * 
	 * @param vid 场馆编号
	 * @return 删除结果，若结果大于0，则删除成功；若结果小于等于0，则删除失败
	 */
	public int deleteVenue(int vid);
	
	/**更新场馆
	 * 
	 * @param newVenue 新的场馆
	 * @return 更新结果：若结果大于0，则更新成功；若结果小于等于0，则更新失败
	 */
	public int updateVenue(VenueBean newVenue);
	
	/**查看场馆
	 * 
	 * @param vid 场馆编号
	 * @return 场馆对象
	 */
	public VenueBean showVenueDetail(int vid);

	/**查看所有场馆
	 * 
	 * @return 所有场馆对象列表
	 */
	public List<VenueBean> showAllVenues();
	
}

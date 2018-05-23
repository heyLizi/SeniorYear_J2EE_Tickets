package dao;

import po.VenueAccountBean;

/**
 * 场馆账户数据访问接口
 **/

public interface VenueAccountDao {
	
	/**根据场馆编号增加场馆账户，场馆账户编号同场馆编号
	 * 
	 * @param vid 场馆编号
	 * @return 添加结果：若结果大于0，则添加成功；若结果小于等于0，则添加失败
	 */
	public int addVenueAccount(int vid);
	
	/**删除场馆账户
	 * 
	 * @param vid 场馆账户编号
	 * @return 删除结果，若结果大于0，则删除成功；若结果小于等于0，则删除失败
	 */
	public int deleteVenueAccount(int vid);
	
	/**更新场馆账户
	 * 
	 * @param newVenueAccount 新的场馆账户
	 * @return 更新结果：若结果大于0，则更新成功；若结果小于等于0，则更新失败
	 */
	public int updateVenueAccount(VenueAccountBean newVenueAccount);
	
	/**查看场馆账户
	 * 
	 * @param vid 场馆账户编号
	 * @return 场馆账户对象
	 */
	public VenueAccountBean showVenueAccountDetail(int vid);
	
}

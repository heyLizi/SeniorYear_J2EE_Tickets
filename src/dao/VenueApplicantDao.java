package dao;

import po.VenueApplicantBean;

/**
 * 场馆申请者数据访问接口
 **/

public interface VenueApplicantDao {

	/**根据联系电话和预设密码添加场馆申请者
	 * 
	 * @param telephone 联系电话
	 * @param vaerPasswd 预设密码

	 * @return 添加结果：若结果大于0，则添加成功，返回值为新添加的场馆申请者对应的场馆申请者编号；若结果小于等于0，则添加失败
	 */
	public int addVenueApplicant(String telephone, String vaerPasswd);
	
	/**删除场馆申请者
	 * 
	 * @param vaerid 场馆申请者编号
	 * @return 删除结果，若结果大于0，则删除成功；若结果小于等于0，则删除失败
	 */
	public int deleteVenueApplicant(int vaerid);
	
	/**更新场馆申请者
	 * 
	 * @param newVenueApplicant 新的场馆申请
	 * @return 更新结果：若结果大于0，则更新成功；若结果小于等于0，则更新失败
	 */
	public int updateVenueApplicant(VenueApplicantBean newVenueApplicant);
	
	/**查看场馆申请者
	 * 
	 * @param vaerid 场馆申请者编号
	 * @return 场馆申请者对象
	 */
	public VenueApplicantBean showVenueApplicantDetail(int vaerid);
	
}

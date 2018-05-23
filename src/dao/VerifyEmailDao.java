package dao;

import po.VerifyEmailBean;

/**
 * 验证邮箱数据访问接口
 **/

public interface VerifyEmailDao {

	/**添加验证邮箱
	 * 
	 * @param vrfyEmail 验证邮箱
	 * @return 添加结果：若结果大于0，则添加成功；若结果小于等于0，则添加失败
	 */
	public int addVrfyEmail(VerifyEmailBean vrfyEmail);
	
	/**删除验证邮箱
	 * 
	 * @param email 电子邮箱
	 * @return 删除结果，若结果大于0，则删除成功；若结果小于等于0，则删除失败
	 */
	public int deleteVrfyEmail(String email);
	
	/**更新验证邮箱
	 * 
	 * @param vrfyEmail 新的验证邮箱
	 * @return 更新结果：若结果大于0，则更新成功；若结果小于等于0，则更新失败
	 */
	public int updateVrfyEmail(VerifyEmailBean vrfyEmail);
	
	/**查看验证邮箱
	 * 
	 * @param email 电子邮箱
	 * @return 验证邮箱对象
	 */
	public VerifyEmailBean showVrfyEmailDetail(String email);
	
}

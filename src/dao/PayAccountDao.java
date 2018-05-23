package dao;

import po.PayAccountBean;

/**
 * 支付账户数据访问接口
 **/

public interface PayAccountDao {
	
	/**添加支付账户
	 * 
	 * @param payAcnt 支付账户
	 * @return 添加结果：若结果大于0，则添加成功；若结果小于等于0，则添加失败
	 */
	public int addPayAcnt(PayAccountBean payAcnt);
	
	/**删除支付账户
	 * 
	 * @param category 账户类型
	 * @param acntName 账户名称
	 * @return 删除结果，若结果大于0，则删除成功；若结果小于等于0，则删除失败
	 */
	public int deletePayAcnt(char category, String acntName);
	
	/**更新支付账户
	 * 
	 * @param payAcnt 新的支付账户
	 * @return 更新结果：若结果大于0，则更新成功；若结果小于等于0，则更新失败
	 */
	public int updatePayAcnt(PayAccountBean payAcnt);
	
	/**查看支付账户
	 * 
	 * @param category 账户类型
	 * @param acntName 账户名称
	 * @return 支付账户对象
	 */
	public PayAccountBean showPayAcntDetail(char category, String acntName);
	
}

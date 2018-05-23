package dao;

import java.util.List;

import po.MemberPayBean;

/**
 * 用户绑定支付账户数据访问接口
 **/

public interface MemberPayDao {

	/**添加用户绑定支付账户
	 * 
	 * @param mid 会员编号
	 * @param category 账户类型
	 * @param acntName 账户名称
	 * @return 添加结果：若结果大于0，则添加成功；若结果小于等于0，则添加失败
	 */
	public int addMemberPay(int mid, char category, String acntName);
	
	/**删除用户绑定支付账户
	 * 
	 * @param mid 会员编号
	 * @param category 账户类型
	 * @param acntName 账户名称
	 * @return 删除结果：若结果大于0，则删除成功；若结果小于等于0，则删除失败
	 */
	public int deleteMemberPay(int mid, char category, String acntName);
	
	/**查看会员绑定支付账户
	 * 
	 * @param mid 会员编号
	 * @param category 账户类型
	 * @param acntName 账户名称
	 * @return 会员绑定支付账户对象
	 */
	public MemberPayBean showMemberPay(int mid, char category, String acntName);
	
	/**查看某会员的所有绑定支付账户
	 * 
	 * @param mid 会员编号
	 * @return 该会员的所有绑定支付账户对象列表
	 */
	public List<MemberPayBean> showAllMemberPays(int mid);
	
}

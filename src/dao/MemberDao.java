package dao;

import java.util.List;

import po.MemberBean;

/**
 * 会员数据访问接口
 **/

public interface MemberDao {

	/**根据注册邮箱和预设密码添加会员
	 * 
	 * @param email 电子邮箱
	 * @param mPasswd 预设密码
	 * @return 添加结果：若结果大于0，则添加成功，返回值为新添加的会员对应的会员编号；若结果小于等于0，则添加失败
	 */
	public int addMember(String email, String mPasswd);
	
	/**删除会员
	 * 
	 * @param mid 会员编号
	 * @return 删除结果，若结果大于0，则删除成功；若结果小于等于0，则删除失败
	 */
	public int deleteMember(int mid);
	
	/**更新会员
	 * 
	 * @param newMember 新的会员
	 * @return 更新结果：若结果大于0，则更新成功；若结果小于等于0，则更新失败
	 */
	public int updateMember(MemberBean newMember);
	
	/**查看会员
	 * 
	 * @param mid 会员编号
	 * @return 会员对象
	 */
	public MemberBean showMemberDetail(int mid);
	
	/**查看所有的会员
	 * 
	 * @return 所有的会员对象列表
	 */
	public List<MemberBean> showAllMembers();
	
}

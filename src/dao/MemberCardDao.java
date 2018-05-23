package dao;

import po.MemberCardBean;

/**
 * 会员卡数据访问接口
 **/

public interface MemberCardDao {

	/**根据会员编号添加会员卡，会员卡编号同会员编号
	 * 
	 * @param mid 会员编号
	 * @return 添加结果：若结果大于0，则添加成功；若结果小于等于0，则添加失败
	 */
	public int addCard(int mid);
	
	/**删除会员卡
	 * 
	 * @param mid 会员卡编号
	 * @return 删除结果，若结果大于0，则删除成功；若结果小于等于0，则删除失败
	 */
	public int deleteCard(int mid);
	
	/**更新会员卡
	 * 
	 * @param newMemberCard 新的会员卡
	 * @return 更新结果：若结果大于0，则更新成功；若结果小于等于0，则更新失败
	 */
	public int updateCard(MemberCardBean newMemberCard);
	
	/**查看会员卡
	 * 
	 * @param mid 会员卡编号
	 * @return 会员卡对象
	 */
	public MemberCardBean showCardDetail(int mid);
	
}


package dao;

import java.sql.Timestamp;

import po.SettleIncomeBean;

/**
 * 结算收入数据访问接口
 **/

public interface SettleIncomeDao {
	
	/**根据各种参数增加结算收入
	 * 
	 * @param vid 场馆编号
	 * @param settleTime 结算时间
	 * @param income 收入金额
	 * @return 添加结果：若结果大于0，则添加成功，返回值为新添加的结算收入对应的结算收入编号；若结果小于等于0，则添加失败
	 */
	public int addSettleIncome(int vid, Timestamp settleTime, double income);
	
	/**删除结算收入
	 * 
	 * @param siid 结算收入编号
	 * @return 删除结果，若结果大于0，则删除成功；若结果小于等于0，则删除失败
	 */
	public int deleteSettleIncome(int siid);
	
	/**更新结算收入
	 * 
	 * @param newSettleIncome 新的结算收入
	 * @return 更新结果：若结果大于0，则更新成功；若结果小于等于0，则更新失败
	 */
	public int updateSettleIncome(SettleIncomeBean newSettleIncome);
	
	/**查看结算收入
	 * 
	 * @param siid 结算收入编号
	 * @return 结算收入对象
	 */
	public SettleIncomeBean showSettleIncomeDetail(int siid);
	
	/**查看某场馆上一次结算收入的时间
	 * 
	 * @param vid 场馆编号
	 * @return 该场馆上一次结算收入的时间
	 */
	public Timestamp showLastSettleTime(int vid);
	
}

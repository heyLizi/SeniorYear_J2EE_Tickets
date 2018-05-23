package service;

import java.sql.Timestamp;

import util.Result;

/**
 * 结算服务接口
 **/

public interface SettleIncomeService {
	
	/**结算各个场馆收入
	 * 
	 * @return 结算结果:Result的result属性若为true，表示结算成功，description属性无意义；
	 * 				 Result的result属性若为false，表示结算失败，description属性表示失败原因（账户不存在等）
	 */
	public Result settleIncome();
	
	
	/**显示某场馆上一次结算收入的时间
	 * 
	 * @param vid 场馆编号
	 * @return 该场馆上一次结算收入的时间
	 */
	public Timestamp showLastSettleTime(int vid);
	
}

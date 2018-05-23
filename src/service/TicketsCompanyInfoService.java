package service;

import po.TicketsInfoBean;
import util.Result;

/**
 * Tickets集团信息服务接口
 **/

public interface TicketsCompanyInfoService {
	
	/**登录
	 * 
	 * @param mgrId 经理编号
	 * @param mgrPasswd 经理密码
	 * @return 登录结果:Result的result属性若为true，表示登录成功，description属性无意义；
	 * 				 Result的result属性若为false，表示登录失败，description属性表示失败原因（密码错误、账户不存在等）
	 */
	public Result login(String mgrId, String mgrPasswd);
	
	
	/**重置密码
	 * 
	 * @param newMgrPasswd 新的经理密码
	 * @return 重置结果:Result的result属性若为true，表示重置成功，description属性无意义；
	 * 				 Result的result属性若为false，表示重置失败，description属性表示失败原因（账户不存在等）
	 */
	public Result resetPasswd(String newMgrPasswd);
	
	
	/**显示Tickets集团信息
	 * 
	 * @return Tickets集团信息对象:若经理编号不存在，则返回null
	 */
	public TicketsInfoBean showTicketsInfo();
	
	
	/**获取收入
	 * 
	 * @param income 收入金额
	 * @return 获取结果:Result的result属性若为true，表示获取成功，description属性无意义；
	 * 				 Result的result属性若为false，表示获取失败，description属性表示失败原因（账户不存在等）
	 */
	public Result acquireMoney(double income);
	
	
	/**花费支出
	 * 
	 * @param cost 支出金额
	 * @return 花费结果:Result的result属性若为true，表示花费成功，description属性无意义；
	 * 				 Result的result属性若为false，表示花费失败，description属性表示失败原因（账户不存在等）
	 */
	public Result spendMoney(double cost);
	
	
	/**结算收入
	 * 
	 * @return 结算结果:Result的result属性若为true，表示结算成功，description属性无意义；
	 * 				 Result的result属性若为false，表示结算失败，description属性表示失败原因（账户不存在等）
	 */
	public Result settleIncome();
	
}

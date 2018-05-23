package service;

import po.PayAccountBean;
import util.Result;

/**
 * 支付账户服务接口
 **/

public interface PayAccountService {

	/**查看支付账户
	 * 
	 * @param category 账户类型
	 * @param acntName 账户名称
	 * @return 支付账户对象
	 */
	public PayAccountBean showPayAcntDetail(char category, String acntName);
	
	
	/**支付
	 * 
	 * @param category 账户类型
	 * @param acntName 账户名称
	 * @param money 支付金额
	 * @return 支付结果:Result的result属性若为true，表示支付成功，description属性无意义；
	 * 				 Result的result属性若为false，表示支付失败，description属性表示失败原因（支付账户不存在、账户余额不足等）
	 */
	public Result pay(char category, String acntName, double money);
	
	
	/**退款
	 * 
	 * @param category 账户类型
	 * @param acntName 账户名称
	 * @param money 支付金额
	 * @return 退款结果:Result的result属性若为true，表示退款成功，description属性无意义；
	 * 				 Result的result属性若为false，表示退款失败，description属性表示失败原因（支付账户不存在等）
	 */
	public Result refund(char category, String acntName, double money);
	
}

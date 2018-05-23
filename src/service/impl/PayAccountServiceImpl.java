package service.impl;

import dao.PayAccountDao;
import po.PayAccountBean;
import service.PayAccountService;
import util.Result;

/**
 * 支付账户服务实现
 **/

public class PayAccountServiceImpl implements PayAccountService {

	private PayAccountDao payAccountDao;

	public PayAccountDao getPayAccountDao() {
		return payAccountDao;
	}

	public void setPayAccountDao(PayAccountDao payAccountDao) {
		this.payAccountDao = payAccountDao;
	}
	
	
	/**查看支付账户
	 * 
	 * @param category 账户类型
	 * @param acntName 账户名称
	 * @return 支付账户对象
	 */
	@Override
	public PayAccountBean showPayAcntDetail(char category, String acntName) {
		
		return payAccountDao.showPayAcntDetail(category, acntName);
		
	}
	
	
	/**支付
	 * 
	 * @param category 账户类型
	 * @param acntName 账户名称
	 * @param money 支付金额
	 * @return 支付结果:Result的result属性若为true，表示支付成功，description属性无意义；
	 * 				 Result的result属性若为false，表示支付失败，description属性表示失败原因（支付账户不存在、账户余额不足等）
	 */
	@Override
	public Result pay(char category, String acntName, double money) {
		
		Result result;
		
		//查询支付账户信息
		PayAccountBean payAccount = payAccountDao.showPayAcntDetail(category, acntName);
		if(payAccount == null) {//支付账户对象不存在，返回错误信息
			result = new Result(false, "支付账户不存在");
		}
		else {//支付账户对象存在，查询支付账户余额
			double balance = payAccount.getBalance();
			if(balance < money) {//余额不足需要支付的金额，返回错误信息
				result = new Result(false, "余额不足");
			}
			else {//余额充足，进行支付，更新支付账户
				payAccount.setBalance(balance - money);
				int updatePayAcntResult = payAccountDao.updatePayAcnt(payAccount);
				if(updatePayAcntResult > 0) {//更新支付账户成功，返回成功信息
					result = new Result(true);
				}
				else {//更新支付账户失败，返回错误信息
					result = new Result(false, "更新支付账户失败");
				}
			}
		}
		
		return result;
	}
	
	
	/**退款
	 * 
	 * @param category 账户类型
	 * @param acntName 账户名称
	 * @param money 支付金额
	 * @return 退款结果:Result的result属性若为true，表示退款成功，description属性无意义；
	 * 				 Result的result属性若为false，表示退款失败，description属性表示失败原因（支付账户不存在等）
	 */
	@Override
	public Result refund(char category, String acntName, double money) {

		Result result;
		
		//查询支付账户信息
		PayAccountBean payAccount = payAccountDao.showPayAcntDetail(category, acntName);
		if(payAccount == null) {//支付账户对象不存在，返回错误信息
			result = new Result(false, "支付账户不存在");
		}
		else {//支付账户对象存在，更新支付账户
			double balance = payAccount.getBalance();
			payAccount.setBalance(balance + money);
			
			int updatePayAcntResult = payAccountDao.updatePayAcnt(payAccount);
			if(updatePayAcntResult > 0) {//更新支付账户成功，返回成功信息
				result = new Result(true);
			}
			else {//更新支付账户失败，返回错误信息
				result = new Result(false, "更新支付账户失败");
			}
		}
		
		return result;
	}
	
}

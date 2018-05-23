package service.impl;

import dao.TicketsInfoDao;
import po.TicketsInfoBean;
import service.SettleIncomeService;
import service.TicketsCompanyInfoService;
import util.Result;

/**
 * Tickets集团信息服务实现
 **/

public class TicketsCompanyInfoServiceImpl implements TicketsCompanyInfoService {

	private SettleIncomeService settleIncomeService;
	
	private TicketsInfoDao ticketsInfoDao;

	public SettleIncomeService getSettleIncomeService() {
		return settleIncomeService;
	}

	public void setSettleIncomeService(SettleIncomeService settleIncomeService) {
		this.settleIncomeService = settleIncomeService;
	}
	
	public TicketsInfoDao getTicketsInfoDao() {
		return ticketsInfoDao;
	}

	public void setTicketsInfoDao(TicketsInfoDao ticketsInfoDao) {
		this.ticketsInfoDao = ticketsInfoDao;
	}
	
	
	/**登录
	 * 
	 * @param mgrId 经理编号
	 * @param mgrPasswd 经理密码
	 * @return 登录结果:Result的result属性若为true，表示登录成功，description属性无意义；
	 * 				 Result的result属性若为false，表示登录失败，description属性表示失败原因（密码错误、账户不存在等）
	 */
	@Override
	public Result login(String mgrId, String mgrPasswd) {

		Result result;
		
		//查询Tickets集团信息信息
		TicketsInfoBean ticketsInfo = ticketsInfoDao.showTicketsInfoDetail();
		if(ticketsInfo != null) {//Tickets集团信息对象存在
			String trueMgrId = ticketsInfo.getMgrId();//真正的经理编号
			String truePasswd = ticketsInfo.getMgrPasswd();//真正的密码
			if(mgrId.equals(trueMgrId)) {//经理编号正确，检查密码
				if(mgrPasswd.equals(truePasswd)) {//密码正确，返回成功结果
					result = new Result(true);
				}
				else {//密码错误，返回错误信息
					result = new Result(false, "密码错误");
				}
			}
			else {//经理编号错误，返回错误信息
				result = new Result(false, "经理编号错误");
			}
		}
		else {//Tickets集团信息对象不存在，返回错误信息
			result = new Result(false, "经理编号不存在");
		}
		
		return result;
	}

	
	/**重置密码
	 * 
	 * @param newMgrPasswd 新的经理密码
	 * @return 重置结果:Result的result属性若为true，表示重置成功，description属性无意义；
	 * 				 Result的result属性若为false，表示重置失败，description属性表示失败原因（账户不存在等）
	 */
	@Override
	public Result resetPasswd(String newMgrPasswd) {

		Result result;
		
		//查询Tickets集团信息信息
		TicketsInfoBean ticketsInfo = ticketsInfoDao.showTicketsInfoDetail();
		if(ticketsInfo != null) {//Tickets集团信息对象存在，更新Tickets集团信息
			ticketsInfo.setMgrPasswd(newMgrPasswd);
			
			int updateTicketsInfoResult = ticketsInfoDao.updateTicketsInfo(ticketsInfo);
			if(updateTicketsInfoResult > 0) {//更新Tickets集团信息成功，返回成功结果
				result = new Result(true);
			}
			else {//更新Tickets集团信息失败，返回错误信息
				result = new Result(false, "更新Tickets集团信息失败");
			}
		}
		else {//Tickets集团信息对象不存在，返回错误信息
			result = new Result(false, "经理编号不存在");
		}
		
		return result;
	}
	
	
	/**显示Tickets集团信息
	 * 
	 * @return Tickets集团信息对象:若经理编号不存在，则返回null
	 */
	@Override
	public TicketsInfoBean showTicketsInfo() {
		
		return ticketsInfoDao.showTicketsInfoDetail();
		
	}
	
	
	/**获取收入
	 * 
	 * @param income 收入金额
	 * @return 获取结果:Result的result属性若为true，表示获取成功，description属性无意义；
	 * 				 Result的result属性若为false，表示获取失败，description属性表示失败原因（账户不存在等）
	 */
	public Result acquireMoney(double income) {

		Result result;
		
		//查询Tickets集团信息信息
		TicketsInfoBean ticketsInfo = ticketsInfoDao.showTicketsInfoDetail();
		if(ticketsInfo != null) {//Tickets集团信息对象存在，更新Tickets集团信息
			double oldBalance = ticketsInfo.getBalance();
			double newBalance = oldBalance + income;
			ticketsInfo.setBalance(newBalance);
			
			int updateTicketsInfoResult = ticketsInfoDao.updateTicketsInfo(ticketsInfo);
			if(updateTicketsInfoResult > 0) {//更新Tickets集团信息成功，返回成功结果
				result = new Result(true);
			}
			else {//更新Tickets集团信息失败，返回错误信息
				result = new Result(false, "更新Tickets集团信息失败");
			}
		}
		else {//Tickets集团信息对象不存在，返回错误信息
			result = new Result(false, "经理编号不存在");
		}
		
		return result;
	}
	
	
	/**花费支出
	 * 
	 * @param cost 支出金额
	 * @return 花费结果:Result的result属性若为true，表示花费成功，description属性无意义；
	 * 				 Result的result属性若为false，表示花费失败，description属性表示失败原因（账户不存在等）
	 */
	@Override
	public Result spendMoney(double cost) {

		Result result;
		
		//查询Tickets集团信息信息
		TicketsInfoBean ticketsInfo = ticketsInfoDao.showTicketsInfoDetail();
		if(ticketsInfo != null) {//Tickets集团信息对象存在，更新Tickets集团信息
			double oldBalance = ticketsInfo.getBalance();
			double newBalance = oldBalance - cost;
			ticketsInfo.setBalance(newBalance);
			
			int updateTicketsInfoResult = ticketsInfoDao.updateTicketsInfo(ticketsInfo);
			if(updateTicketsInfoResult > 0) {//更新Tickets集团信息成功，返回成功结果
				result = new Result(true);
			}
			else {//更新Tickets集团信息失败，返回错误信息
				result = new Result(false, "更新Tickets集团信息失败");
			}
		}
		else {//Tickets集团信息对象不存在，返回错误信息
			result = new Result(false, "经理编号不存在");
		}
		
		return result;
	}
	
	
	/**结算收入
	 * 
	 * @return 结算结果:Result的result属性若为true，表示结算成功，description属性无意义；
	 * 				 Result的result属性若为false，表示结算失败，description属性表示失败原因（账户不存在等）
	 */
	@Override
	public Result settleIncome() {
		
		return settleIncomeService.settleIncome();
		
	}
	
}

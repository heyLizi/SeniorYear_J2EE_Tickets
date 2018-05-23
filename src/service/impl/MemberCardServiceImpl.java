package service.impl;

import java.math.BigDecimal;

import dao.MemberCardDao;
import dao.MemberRuleDao;
import po.MemberBean;
import po.MemberCardBean;
import po.MemberRuleBean;
import service.MemberCardService;
import service.MemberService;
import util.Result;

/**
 * 会员卡服务实现
 **/

public class MemberCardServiceImpl implements MemberCardService {

	private MemberService memberService;
	
	private MemberCardDao memberCardDao;
	private MemberRuleDao memberRuleDao;
	
	public MemberService getMemberService() {
		return memberService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	
	public MemberCardDao getMemberCardDao() {
		return memberCardDao;
	}

	public void setMemberCardDao(MemberCardDao memberCardDao) {
		this.memberCardDao = memberCardDao;
	}

	public MemberRuleDao getMemberRuleDao() {
		return memberRuleDao;
	}

	public void setMemberRuleDao(MemberRuleDao memberRuleDao) {
		this.memberRuleDao = memberRuleDao;
	}
	
	
	/**办理会员卡
	 * 
	 * @param mid 会员编号
	 * @return 办理结果:Result的result属性若为true，表示办理成功，description属性无意义；
	 * 				 Result的result属性若为false，表示办理失败，description属性表示失败原因（会员编号不存在等）
	 */
	@Override
	public Result signUpCard(int mid) {
		
		Result result;
		
		//查询该会员编号对应的会员是否存在
		MemberBean member = memberService.showMemberDetail(mid);
		if(member != null) {//会员对象存在，添加会员卡
			int addCardResult = memberCardDao.addCard(mid);
			if(addCardResult > 0) {//添加会员卡成功，返回成功结果
				result = new Result(true);
			}
			else {//添加会员卡失败，返回错误信息
				result = new Result(false, "添加会员卡失败");
			}
		}
		else {//会员对象不存在，无法办理对应的会员卡，返回错误信息
			result = new Result(false, "会员编号不存在");
		}
		
		return result;
	}

	
	/**停用、取消会员卡
	 * 
	 * @param mid 会员编号
	 * @return 取消结果:Result的result属性若为true，表示取消成功，description属性无意义；
	 * 				 Result的result属性若为false，表示取消失败，description属性表示失败原因（会员编号不存在等）
	 */
	@Override
	public Result stopCard(int mid) {
		
		Result result;
		
		//查询会员卡信息
		MemberCardBean memberCard = memberCardDao.showCardDetail(mid);
		if(memberCard != null) {//会员卡对象存在，更新会员卡
			memberCard.setLevel(0);//被停用的会员卡等级为0
			memberCard.setTotalPay(0);//被停用的会员卡总消费金额为0
			memberCard.setCredit(0);//被停用的会员卡积分为0
			
			int updateCardResult = memberCardDao.updateCard(memberCard);
			if(updateCardResult > 0) {//更新会员卡成功，返回成功结果
				result = new Result(true);
			}
			else {//更新会员卡失败，返回错误信息
				result = new Result(false, "停用会员卡失败");
			}
		}
		else {//会员卡对象不存在，返回错误信息
			result = new Result(false, "会员卡编号不存在");
		}
		
		return result;
	}

	
	/**更新会员卡
	 * 
	 * @param mid 会员编号
	 * @param level 会员等级
	 * @param totalPay 总消费金额
	 * @param credit 会员积分
	 * @return 更新结果:Result的result属性若为true，表示更新成功，description属性无意义；
	 * 				 Result的result属性若为false，表示更新失败，description属性表示失败原因（会员编号不存在等）
	 */
	@Override
	public Result updateCard(int mid, int level, double totalPay, double credit) {

		Result result;
		
		//查询会员卡信息
		MemberCardBean memberCard = memberCardDao.showCardDetail(mid);
		if(memberCard != null) {//会员卡对象存在，更新会员卡
			memberCard.setLevel(level);
			memberCard.setTotalPay(totalPay);
			memberCard.setCredit(credit);
			
			int updateCardResult = memberCardDao.updateCard(memberCard);
			if(updateCardResult > 0) {//更新会员卡成功，返回成功结果
				result = new Result(true);
			}
			else {//更新会员卡失败，返回错误信息
				result = new Result(false, "更新会员卡失败");
			}
		}
		else {//会员卡对象不存在，返回错误信息
			result = new Result(false, "会员卡编号不存在");
		}
		
		return result;
	}
	
	
	/**显示会员卡信息
	 * 
	 * @param mid 会员编号
	 * @return 会员卡对象:若会员编号不存在，则返回null
	 */
	@Override
	public MemberCardBean showCardInfo(int mid) {

		return memberCardDao.showCardDetail(mid);
		
	}
	

	/**显示会员规则信息
	 * 
	 * @param level 会员等级
	 * @return 会员规则对象:若会员等级不存在，则返回null
	 */
	@Override
	public MemberRuleBean showRuleInfo(int level) {
	
		return memberRuleDao.showRuleDetail(level);
		
	}
	
	
	/**获取折扣后的实际消费金额
	 * 
	 * @param mid 会员编号
	 * @param originCostMoney 原始消费金额
	 * @return 折扣后的实际消费金额
	 */
	public double getRealCost(int mid, double originCostMoney) {
		
		//查询会员卡信息
		MemberCardBean memberCard = memberCardDao.showCardDetail(mid);
		if(memberCard != null) {//会员卡对象存在
			
			int oldLevel = memberCard.getLevel(); //消费之前的会员等级			
			MemberRuleBean memberRule = memberRuleDao.showRuleDetail(oldLevel);//获取当前等级下的会员规则
			double realCost = originCostMoney * memberRule.getDiscount();//这次的实际消费金额=原始消费金额*当前等级下的折扣值
			
			//将这次的实际消费金额保留两位小数
			BigDecimal bd = new BigDecimal(realCost);    
			realCost = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
			
			return realCost;
		}
		else {//会员卡对象不存在，按照实际消费金额计算
			return originCostMoney;
		}
		
	}
	
	
	/**在消费之后更新会员卡
	 * 
	 * @param mid 会员编号
	 * @param realCostMoney 实际消费金额（折扣后的金额）
	 * @return 更新结果:Result的result属性若为true，表示更新成功，description属性无意义；
	 * 				 Result的result属性若为false，表示更新失败，description属性表示失败原因（会员编号不存在等）
	 */
	@Override
	public Result updateCardAfterCost(int mid, double realCostMoney) {

		Result result;
		
		//查询会员卡信息
		MemberCardBean memberCard = memberCardDao.showCardDetail(mid);
		if(memberCard != null) {//会员卡对象存在
			
			int oldLevel = memberCard.getLevel();//消费之前的会员等级

			MemberRuleBean memberRule = memberRuleDao.showRuleDetail(oldLevel);//获取当前等级下的会员规则
			
			double oldTotalPay = memberCard.getTotalPay();//消费之前的消费总金额
			double newTotalPay = oldTotalPay + realCostMoney;//消费之后的消费总金额
			
			double oldCredit = memberCard.getCredit();//消费之前的会员积分
			double thisCredit = (int)realCostMoney * memberRule.getCreditRule();//这次的会员积分（消费不足1元的部分不计）
			double newCredit = oldCredit + thisCredit;//消费之后的会员积分
			
			//判断消费之后的会员卡等级
			int newLevel = oldLevel;
			double levelBoundMax = memberRule.getBoundMax();
			while((newTotalPay > levelBoundMax) && (newLevel < 5)) {
				newLevel += 1;
				levelBoundMax = memberRuleDao.showRuleDetail(newLevel).getBoundMax();
			}
			
			//更新会员卡
			memberCard.setLevel(newLevel);
			memberCard.setTotalPay(newTotalPay);
			memberCard.setCredit(newCredit);
			
			int updateCardResult = memberCardDao.updateCard(memberCard);
			if(updateCardResult > 0) {//更新会员卡成功，返回成功结果
				result = new Result(true);
			}
			else {//更新会员卡失败，返回错误信息
				result = new Result(false, "更新会员卡失败");
			}
		}
		else {//会员卡对象不存在，返回错误信息
			result = new Result(false, "会员卡编号不存在");
		}
		
		return result;
	}
	
	
	/**在退款之后更新会员卡
	 * 
	 * @param mid 会员编号
	 * @param refundMoney 退款金额
	 * @return 更新结果:Result的result属性若为true，表示更新成功，description属性无意义；
	 * 				 Result的result属性若为false，表示更新失败，description属性表示失败原因（会员编号不存在等）
	 */
	@Override
	public Result updateCardAfterRefund(int mid, double refundMoney) {

		Result result;
		
		//查询会员卡信息
		MemberCardBean memberCard = memberCardDao.showCardDetail(mid);
		if(memberCard != null) {//会员卡对象存在
			
			int oldLevel = memberCard.getLevel();//退款之前的会员等级

			MemberRuleBean memberRule = memberRuleDao.showRuleDetail(oldLevel);//获取当前等级下的会员规则
			
			double oldTotalPay = memberCard.getTotalPay();//退款之前的消费总金额
			double newTotalPay = oldTotalPay - refundMoney;//退款之后的消费总金额
			
			double oldCredit = memberCard.getCredit();//退款之前的会员积分
			double thisCredit = (int)refundMoney * memberRule.getCreditRule();//退款对应消费的会员积分（消费不足1元的部分不计）
			double newCredit = oldCredit - thisCredit;//退款之后的会员积分
			
			//判断退款之后的会员卡等级
			int newLevel = oldLevel;
			double levelBoundMin = memberRule.getBoundMin();
			while((newTotalPay < levelBoundMin) && (newLevel > 1)) {
				newLevel -= 1;
				levelBoundMin = memberRuleDao.showRuleDetail(newLevel).getBoundMin();
			}
			
			//更新会员卡
			memberCard.setLevel(newLevel);
			memberCard.setTotalPay(newTotalPay);
			memberCard.setCredit(newCredit);
			
			int updateCardResult = memberCardDao.updateCard(memberCard);
			if(updateCardResult > 0) {//更新会员卡成功，返回成功结果
				result = new Result(true);
			}
			else {//更新会员卡失败，返回错误信息
				result = new Result(false, "更新会员卡失败");
			}
		}
		else {//会员卡对象不存在，返回错误信息
			result = new Result(false, "会员卡编号不存在");
		}
		
		return result;
	}
	
	
	/**在兑换优惠券之后更新会员卡
	 * 
	 * @param mid 会员编号
	 * @param redeemedCredit 被兑换的积分
	 * @return 更新结果:Result的result属性若为true，表示更新成功，description属性无意义；
	 * 				 Result的result属性若为false，表示更新失败，description属性表示失败原因（会员编号不存在等）
	 */
	@Override
	public Result updateCardAfterRedeem(int mid, double redeemedCredit) {

		Result result;
		
		//查询会员卡信息
		MemberCardBean memberCard = memberCardDao.showCardDetail(mid);
		if(memberCard != null) {//会员卡对象存在
			
			double oldCredit = memberCard.getCredit();//兑换之前的会员积分
			double newCredit = oldCredit - redeemedCredit;//兑换之后的会员积分
			
			//更新会员卡
			memberCard.setCredit(newCredit);
			
			int updateCardResult = memberCardDao.updateCard(memberCard);
			if(updateCardResult > 0) {//更新会员卡成功，返回成功结果
				result = new Result(true);
			}
			else {//更新会员卡失败，返回错误信息
				result = new Result(false, "更新会员卡失败");
			}
		}
		else {//会员卡对象不存在，返回错误信息
			result = new Result(false, "会员卡编号不存在");
		}
		
		return result;
	}
	
}

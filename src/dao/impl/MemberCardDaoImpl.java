package dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.MemberCardDao;
import po.MemberCardBean;

/**
 * 会员卡数据访问实现
 **/

public class MemberCardDaoImpl implements MemberCardDao {
	
	private SessionFactory sessionFactory;
	
	private MemberCardBean memberCard;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void setMemberCard(MemberCardBean memberCard) {
		this.memberCard = memberCard;
	}
	
	
	/**根据会员编号添加会员卡，会员卡编号同会员编号
	 * 
	 * @param mid 会员编号
	 * @return 添加结果：若结果大于0，则添加成功；若结果小于等于0，则添加失败
	 */
	@Override
	public int addCard(int mid) {

		int returnNum = 0;//初始化返回结果
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		memberCard = (MemberCardBean)sess.get(MemberCardBean.class, mid);
		if(memberCard == null) {
			memberCard = new MemberCardBean();
			memberCard.setMid(mid);
			memberCard.setLevel(1); //刚办理的会员卡的会员等级为1
			memberCard.setTotalPay(0);//刚办理的会员卡的总消费金额为0元
			memberCard.setCredit(0);//刚办理的会员卡的积分为0
		
			sess.save(memberCard);
			
			returnNum = 1;
		}
		
		tx.commit();
		sess.close();
		
		return returnNum;
	}
	
	/**删除会员卡
	 * 
	 * @param mid 会员卡编号
	 * @return 删除结果，若结果大于0，则删除成功；若结果小于等于0，则删除失败
	 */
	@Override
	public int deleteCard(int mid) {

		int returnNum = 0;//初始化返回结果
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		memberCard = (MemberCardBean)sess.get(MemberCardBean.class, mid);
		if(memberCard != null) {
			sess.delete(memberCard);
			
			returnNum = 1;
		}
		
		tx.commit();
		sess.close();
		
		return returnNum;
	}
	
	/**更新会员卡
	 * 
	 * @param newMemberCard 新的会员卡
	 * @return 更新结果：若结果大于0，则更新成功；若结果小于等于0，则更新失败
	 */
	@Override
	public int updateCard(MemberCardBean newMemberCard) {

		int returnNum = 0;//初始化返回结果
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		memberCard = (MemberCardBean)sess.get(MemberCardBean.class, newMemberCard.getMid());
		if(memberCard != null) {
			memberCard.setLevel(newMemberCard.getLevel());
			memberCard.setTotalPay(newMemberCard.getTotalPay());
			memberCard.setCredit(newMemberCard.getCredit());
			
			sess.update(memberCard);
			
			returnNum = 1;
		}
		
		tx.commit();
		sess.close();
		
		return returnNum;
	}
	
	/**查看会员卡
	 * 
	 * @param mid 会员卡编号
	 * @return 会员卡对象
	 */
	@Override
	public MemberCardBean showCardDetail(int mid) {
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		memberCard = (MemberCardBean)sess.get(MemberCardBean.class, mid);

		tx.commit();
		sess.close();
		
		return memberCard;
	}

}

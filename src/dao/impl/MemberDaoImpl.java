package dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.MemberDao;
import po.MemberBean;

/**
 * 会员数据访问实现
 **/

public class MemberDaoImpl implements MemberDao {
	
	private SessionFactory sessionFactory;
	
	private MemberBean member;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void setMember(MemberBean member) {
		this.member = member;
	}
	
	
	/**根据注册邮箱和预设密码添加会员
	 * 
	 * @param email 电子邮箱
	 * @param mPasswd 预设密码
	 * @return 添加结果：若结果大于0，则添加成功，返回值为新添加的会员对应的会员编号；若结果小于等于0，则添加失败
	 */
	@Override
	public int addMember(String email, String mPasswd) {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		member.setEmail(email);
		member.setMpasswd(mPasswd);
		
		sess.save(member);
		int mid = member.getMid();
		
		tx.commit();
		sess.close();
		
		return mid;
	}
	
	/**删除会员
	 * 
	 * @param mid 会员编号
	 * @return 删除结果，若结果大于0，则删除成功；若结果小于等于0，则删除失败
	 */
	@Override
	public int deleteMember(int mid) {

		int returnNum = 0;//初始化返回结果
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		member = (MemberBean)sess.get(MemberBean.class, mid);
		if(member != null) {
			sess.delete(member);
			
			returnNum = 1;
		}
		
		tx.commit();
		sess.close();
		
		return returnNum;
	}
	
	/**更新会员
	 * 
	 * @param newMember 新的会员
	 * @return 更新结果：若结果大于0，则更新成功；若结果小于等于0，则更新失败
	 */
	@Override
	public int updateMember(MemberBean newMember) {

		int returnNum = 0;//初始化返回结果
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		member = (MemberBean)sess.get(MemberBean.class, newMember.getMid());
		if(member != null) {
			member.setEmail(newMember.getEmail());
			member.setMpasswd(newMember.getMpasswd());
			member.setMname(newMember.getMname());
			member.setSex(newMember.getSex());
			member.setBirthDate(newMember.getBirthDate());
			
			sess.update(member);
			
			returnNum = 1;
		}
		
		tx.commit();
		sess.close();
		
		return returnNum;
	}
	
	/**查看会员
	 * 
	 * @param mid 会员编号
	 * @return 会员对象
	 */
	@Override
	public MemberBean showMemberDetail(int mid) {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		member = (MemberBean)sess.get(MemberBean.class, mid);
		
		tx.commit();
		sess.close();
		
		return member;
	}
	
	/**查看所有的会员
	 * 
	 * @return 所有的会员对象列表
	 */
	@Override
	public List<MemberBean> showAllMembers() {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		@SuppressWarnings("unchecked")
		List<MemberBean> list = sess.createQuery("select mb from MemberBean mb").list();
		
		tx.commit();
		sess.close();
		
		return list;
	}
	
}

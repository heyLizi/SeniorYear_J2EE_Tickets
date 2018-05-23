package dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.VerifyEmailDao;
import po.VerifyEmailBean;

/**
 * 验证邮箱数据访问实现
 **/

public class VerifyEmailDaoImpl implements VerifyEmailDao {

	private SessionFactory sessionFactory;
	
	private VerifyEmailBean verifyEmail;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void setVerifyEmail(VerifyEmailBean verifyEmail) {
		this.verifyEmail = verifyEmail;
	}

	
	/**添加验证邮箱
	 * 
	 * @param vrfyEmail 验证邮箱
	 * @return 添加结果：若结果大于0，则添加成功；若结果小于等于0，则添加失败
	 */
	@Override
	public int addVrfyEmail(VerifyEmailBean vrfyEmail) {

		int returnNum = 0;//初始化返回结果
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		verifyEmail = (VerifyEmailBean)sess.get(VerifyEmailBean.class, vrfyEmail.getEmail());
		if(verifyEmail == null) {
			verifyEmail = new VerifyEmailBean();
			verifyEmail.setEmail(vrfyEmail.getEmail());;
			verifyEmail.setVePasswd(vrfyEmail.getVePasswd());
			verifyEmail.setVerifyCode(vrfyEmail.getVerifyCode());
			
			sess.save(verifyEmail);
			
			returnNum = 1;
		}
		
		tx.commit();
		sess.close();
		
		return returnNum;
	}

	/**删除验证邮箱
	 * 
	 * @param email 电子邮箱
	 * @return 删除结果，若结果大于0，则删除成功；若结果小于等于0，则删除失败
	 */
	@Override
	public int deleteVrfyEmail(String email) {

		int returnNum = 0;//初始化返回结果
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		verifyEmail = (VerifyEmailBean)sess.get(VerifyEmailBean.class, email);
		if(verifyEmail != null) {
			sess.delete(verifyEmail);
			
			returnNum = 1;
		}
		
		tx.commit();
		sess.close();
		
		return returnNum;
	}

	/**更新验证邮箱
	 * 
	 * @param vrfyEmail 新的验证邮箱
	 * @return 更新结果：若结果大于0，则更新成功；若结果小于等于0，则更新失败
	 */
	@Override
	public int updateVrfyEmail(VerifyEmailBean vrfyEmail) {

		int returnNum = 0;//初始化返回结果
		
		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		verifyEmail = (VerifyEmailBean)sess.get(VerifyEmailBean.class, vrfyEmail.getEmail());
		if(verifyEmail != null) {
			verifyEmail.setVePasswd(vrfyEmail.getVePasswd());
			verifyEmail.setVerifyCode(vrfyEmail.getVerifyCode());
			
			sess.update(verifyEmail);
			
			returnNum = 1;
		}
		
		tx.commit();
		sess.close();
		
		return returnNum;
	}

	/**查看验证邮箱
	 * 
	 * @param email 电子邮箱
	 * @return 验证邮箱对象
	 */
	@Override
	public VerifyEmailBean showVrfyEmailDetail(String email) {

		Session sess = sessionFactory.openSession();		
		Transaction tx = sess.beginTransaction();

		verifyEmail = (VerifyEmailBean)sess.get(VerifyEmailBean.class, email);
		
		tx.commit();
		sess.close();
		
		return verifyEmail;
	}

}

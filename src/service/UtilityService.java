package service;

import util.Result;

/**
 * 通用服务接口
 **/

public interface UtilityService {
	
	/**发送邮件
	 * 
	 * @param email 电子邮箱
	 * @param content 邮件内容
	 * @return 发送结果:Result的result属性为true，表示发送成功，description属性无意义；
	 * 				 Result的result属性若为false，表示发送失败，description属性表示失败原因（邮箱不存在等）
	 */
	public Result sendEmail(String email, String content);

}

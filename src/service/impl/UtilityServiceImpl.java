package service.impl;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;

import service.UtilityService;
import util.Result;

/**
 * 通用服务实现
 **/

public class UtilityServiceImpl  implements UtilityService {

	/**发送邮件
	 * 
	 * @param email 电子邮箱
	 * @param content 邮件内容
	 * @return 发送结果:Result的result属性为true，表示发送成功，description属性无意义；
	 * 				 Result的result属性若为false，表示发送失败，description属性表示失败原因（邮箱不存在等）
	 */
	@Override
	public Result sendEmail(String email, String content) {
		
		Result result;
		
		try{
			Properties prop = new Properties();
			prop.setProperty("mail.debug", "true"); //开启debug调试，以便在控制台查看
			prop.setProperty("mail.smtp.auth", "true"); //表示SMTP发送邮件，必须进行身份验证
			prop.setProperty("mail.smtp.host", "smtp.qq.com"); //发送邮件的服务器地址
			prop.setProperty("mail.transport.protocol", "smtp"); //
	        //prop.setProperty("mail.smtp.port", "25"); // 端口号：如果是QQ或163的smtp服务，那port是25，而gmail的port是587
			
	        //开启SSL加密，否则会失败
	        MailSSLSocketFactory sf = new MailSSLSocketFactory();
	        sf.setTrustAllHosts(true);
	        prop.put("mail.smtp.ssl.enable", "true");
	        prop.put("mail.smtp.ssl.socketFactory", sf);
	        
	        Session mailSession = Session.getInstance(prop); //使用环境属性创建邮件会话
			Transport ts = mailSession.getTransport(); //通过session得到transport对象
			ts.connect("smtp.qq.com", "917871352", "uqorfwcmxmjmbbfe");
			
	        //创建邮件消息
			MimeMessage message = new MimeMessage(mailSession);
			message.setFrom(new InternetAddress("917871352@qq.com"));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
			message.setSubject("Tickets注册成功，请及时激活");
			message.setContent(content, "text/html;charset=UTF-8");
			message.saveChanges();
			
			ts.sendMessage(message, message.getAllRecipients());
			ts.close();
			
			result = new Result(true); 
			
		}catch (Exception e) {
			result = new Result(false, "发送邮件失败"); 
			throw new RuntimeException(e);
		}
		return result;		
		
	}

}


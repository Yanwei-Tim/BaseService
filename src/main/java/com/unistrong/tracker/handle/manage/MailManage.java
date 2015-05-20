package com.unistrong.tracker.handle.manage;

import javax.annotation.Resource;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailManage {

	@Resource
	private JavaMailSender javaMailSender;

	@Resource
	private MailConf mailConf;

	private void sendMail(String email, String text) throws Exception {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom(mailConf.getFrom());
		mail.setTo(email);
		mail.setSubject("凯步关爱(capcare)-找回密码");
		mail.setText(text);
		javaMailSender.send(mail);
	}

	public void sendMailText(String userName, String email, String pwdToken) throws Exception {
		StringBuffer sb = new StringBuffer();
		String url = mailConf.getUrl();
		sb.append("请点击下面的链接:\r\n");
		sb.append(url + "?");
		sb.append("userName=" + userName + "&pwdToken=" + pwdToken);
		sb.append("\r\n如果上述链接不能点击,请手动复制到浏览器地址!");
		sendMail(email, sb.toString());
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public JavaMailSender getJavaMailSender() {
		return javaMailSender;
	}
}

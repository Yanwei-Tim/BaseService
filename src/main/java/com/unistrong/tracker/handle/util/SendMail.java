package com.unistrong.tracker.handle.util;

import com.unistrong.tracker.handle.manage.MailManage;

public class SendMail extends Thread {

	private MailManage mailManage;
	private String userName;
	private String email;
	private String pwdToken;

	public void run() {
		try {
			mailManage.sendMailText(userName, email, pwdToken);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public SendMail(MailManage mailManage, String userName, String email, String pwdToken) {
		super();
		this.mailManage = mailManage;
		this.userName = userName;
		this.email = email;
		this.pwdToken = pwdToken;
	}
}

package cn.qq.pojo;

public class UcstarMomsg {
    
	//远程客户
    private String remote;
    //账号
    private String account;
    //内容
    private String immsg;
    //发送人
    private String sender;
    //发送人姓名
    private String sendername;
    //发送时间
    private String sendtime;
	public String getRemote() {
		return remote;
	}
	public void setRemote(String remote) {
		this.remote = remote;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getImmsg() {
		return immsg;
	}
	public void setImmsg(String immsg) {
		this.immsg = immsg;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getSendername() {
		return sendername;
	}
	public void setSendername(String sendername) {
		this.sendername = sendername;
	}
	public String getSendtime() {
		return sendtime;
	}
	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}
	@Override
	public String toString() {
		return "UcstarMomsg [remote=" + remote + ", account=" + account + ", immsg=" + immsg + ", sender=" + sender
				+ ", sendername=" + sendername + ", sendtime=" + sendtime + "]";
	}
    
}

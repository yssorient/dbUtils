package cn.qq;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import cn.qq.pojo.UcstarMomsg;
import cn.qq.utils.DBUtils;
import cn.qq.utils.ExportExcel;
import cn.qq.utils.UcstarStringUtils;

public class Demo01 {
    public static void main(String[] args) throws Exception {
    	
    	//数据库路径
    	String dburl = "C:\\Users\\HP\\Desktop\\20201209_01"+"\\Immsg.db";
    	//生成文件路径
    	String fileUrl = "E:\\";
    	
    	
    	//单人会话
		ArrayList<UcstarMomsg> list = getSingleSession(dburl);
		//多人会话
		ArrayList<UcstarMomsg> list2 = getMostSession(dburl);
		//把单人会话和多人会话放到一个list中
		for (UcstarMomsg ucstarMomsg : list2) {
			//System.out.println(ucstarMomsg);
			list.add(ucstarMomsg);
		}
		// 打印成excel
		ExportExcel.ExportExc(list,fileUrl);
       
    }
    
    //多人会话
    public static ArrayList<UcstarMomsg> getMostSession(String dburl) throws Exception{
    	System.out.println("-----多人会话-------");
    	
    	Connection conn = DBUtils.getConnect(dburl);
        Statement stat = conn.createStatement();
        //单人会话
        String sql2 = "select * from qim_MulTalk_msg";
        //多人会话
        ArrayList<UcstarMomsg> list = new ArrayList<UcstarMomsg>();
        
        ResultSet rs1 = stat.executeQuery(sql2);
        //多人会话
        while(rs1.next()) {
     	UcstarMomsg msg = new UcstarMomsg();
        	msg.setRemote("多人");
        	msg.setAccount("多人");
        	//聊天内容
        	String immsg = rs1.getString("immsg").replaceAll("&lt;", "<").replaceAll("&quot;", "\"").replaceAll("&gt;", ">");
        	immsg = UcstarStringUtils.formatRichTextToContent(immsg);
        	msg.setImmsg(immsg);
        	
        	//发送人姓名
        	msg.setSender(rs1.getString("sender"));
        	//发送人真实姓名
        	msg.setSendername(rs1.getString("sendername"));
        	
        	//发送日期
        	Long sendtime = rs1.getLong("sendtime");
        	sendtime = Long.parseLong(sendtime+"000");
        	DateFormat smp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	String sendtim = smp.format(new Date(sendtime));
        	
        	
        	msg.setSendtime(sendtim);
        	
        	list.add(msg);
        }
        
        DBUtils.close(rs1, stat, conn);
        return list;
    }
    
    //单人会话
    public static ArrayList<UcstarMomsg> getSingleSession(String dburl) throws Exception{
    	System.out.println("-----单人会话-------");
    	Connection conn = DBUtils.getConnect(dburl);
    	Statement stat = conn.createStatement();
    	//单人会话
    	String sql1 = "select * from qim_Im_msg";
    	//多人会话
    	ArrayList<UcstarMomsg> list = new ArrayList<UcstarMomsg>();
    	
    	ResultSet rs1 = stat.executeQuery(sql1);
    	//单人会话
    	while(rs1.next()) {
    		UcstarMomsg msg = new UcstarMomsg();
    		
    		//远处账号
    		msg.setRemote(rs1.getString("remote"));
    		//账号
    		msg.setAccount(rs1.getString("account"));
			//聊天内容
    		String immsg = rs1.getString("immsg").replaceAll("&lt;", "<").replaceAll("&quot;", "\"").replaceAll("&gt;", ">");
    		immsg = UcstarStringUtils.formatRichTextToContent(immsg);
    		
    		msg.setImmsg(immsg);
    		
    		//发送者账号
    		msg.setSender(rs1.getString("sender"));
    		//发送者真实姓名
    		msg.setSendername(rs1.getString("sendername"));

    		//发送日期
    		Long sendtime = rs1.getLong("sendtime");
    		sendtime = Long.parseLong(sendtime+"000");
    		DateFormat smp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		String sendtim = smp.format(new Date(sendtime));
    		msg.setSendtime(sendtim);
    		
    		list.add(msg);
    	}
    	
    	DBUtils.close(rs1, stat, conn);
    	return list;
    }
}

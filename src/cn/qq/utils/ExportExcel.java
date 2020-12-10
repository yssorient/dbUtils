package cn.qq.utils;
import cn.qq.pojo.UcstarMomsg;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ExportExcel {

    public static void ExportExc(ArrayList<UcstarMomsg> list,String fileUrl) throws IOException {

    	//1.创建一个工作簿
        XSSFWorkbook wb=new XSSFWorkbook();
        //2.创建一个工作表sheet
        Sheet sheet=wb.createSheet();

        Row nRow;
        Cell nCell;
        //3.创建一个行对象   第一行数据
        nRow=sheet.createRow(0); //从0开始
        String[] title= new String[]{"发送人姓名","接受人姓名","聊天内容","发送时间"};
        for(int  i=0;i<title.length;i++){
            nCell=nRow.createCell(i);
            nCell.setCellValue(title[i]);
        }
        //3.填充除了第一行的其他数据
		for (int i = 0; i < list.size(); i++) {
			nRow = sheet.createRow(i + 1);
			UcstarMomsg ucstarMomsg = list.get(i);
			String message2 = null;
			for (int j = 0; j < title.length; j++) {
				nCell = nRow.createCell(j);
				// 发送人姓名
				if (j == 0) {
					String sender = ucstarMomsg.getSender();
					int ii = sender.indexOf("@");
					sender = sender.substring(0, ii);
					String sendername = ucstarMomsg.getSendername();
					sender = sender + "(" + sendername + ")";
					nCell.setCellValue(sender);
				}
				// 接受人姓名
				if (j == 1) {
					String sender = ucstarMomsg.getSender();
					String account = ucstarMomsg.getAccount();
					if (sender.equals(account)) {
						String remote = ucstarMomsg.getRemote();
						int ii = remote.indexOf("@");
						if (ii > 0) {
							remote = remote.substring(0, ii);
						}

						nCell.setCellValue(remote);
					} else {
						int ii = account.indexOf("@");
						if (ii > 0) {
							account = account.substring(0, ii);
						}
						nCell.setCellValue(account);
					}

				}
				// 聊天内容
				if (j == 2) {
					try {
						String immsg = ucstarMomsg.getImmsg();
						nCell.setCellValue(immsg);
					} catch (Exception e) {
						System.out.println("---超出长度没有打印的对象---immsg----"+ucstarMomsg.getImmsg());
						// continue;
						e.printStackTrace();
					}

				}
				// 发送时间
				if (j == 3) {
					nCell.setCellValue(ucstarMomsg.getSendtime());
				}

			}
		}

        //6.保存
        OutputStream stream= null;
        SimpleDateFormat simp = new SimpleDateFormat("yyyyMMddHHmmssss");
        String currentDate = simp.format(new Date());
        
        try {
        	fileUrl = fileUrl + "test"+currentDate+".xlsx";
        	//String uri = "F:\\test1"+currentDate+".xlsx";
        	String uri = fileUrl;
            stream = new FileOutputStream(new File(uri));
            System.out.println("-----打印完成");
            System.out.println("-----路径："+uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        wb.write(stream);
      //7.关闭
        stream.close();
    }
}

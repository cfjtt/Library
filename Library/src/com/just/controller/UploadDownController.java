package com.just.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.formula.eval.UnaryMinusEval;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import MD5.MD5Util;

import com.just.bean.BaseResult;
import com.just.bean.EasySearchResult;
import com.just.bean.UserInfo;
import com.just.service.BookService;
import com.just.service.UserInfoService;

import cn.itcast.commons.CommonUtils;

/**
 * 上传下载操作
 * 
 * @author Administrator
 * 
 */
@Controller
public class UploadDownController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource
	private UserInfoService userinfoService;
	
	@Resource
	private BookService bookService;
	/**
	 * 图片上传操作
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/uploadImg.do")
	@ResponseBody
	public BaseResult uploadImg(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 文件上传
		// 1.获得工厂
		DiskFileItemFactory disk = new DiskFileItemFactory();
		// 2.通过工厂获得解析器
		ServletFileUpload upload = new ServletFileUpload(disk);
		// 解析request得到FileItem集合对象
		@SuppressWarnings("unchecked")
		List<FileItem> file = upload.parseRequest(request);
		// 解析FileItem对象
		for (FileItem item : file) {
			// 判断是否是文件表单项
			if (!item.isFormField()) {
				
				// 获取上传文件的头类型
				// String contentType=item.getContentType();
				// 获取上传的文件名字
				String name = item.getName().substring(
						item.getName().indexOf("."));
				// 解决文件上传时的同名问题
				String realName = CommonUtils.uuid() + name;
				// 设置上传的文件位置
				String path = this.getClass().getResource("/").getPath();
				path = path.substring(0, path.indexOf("classes"));
				String ImgPath = path + "img/" + realName;
				// 生成文件
				File imgFile = new File(ImgPath);
				// 实现文件上传
				item.write(imgFile);
				// 把文件路径写入到数据库中
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("imgPath", ImgPath);
				map.put("id", request.getSession().getAttribute("id"));
				map.put("loadId", request.getSession().getAttribute("loadId"));
				userinfoService.updateImage(map);

			}

		}
		return new BaseResult();
	}

	// 实现图像的下载
	@RequestMapping("/img.do")
	@ResponseBody
	public void downImg(String loadId, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		if ("null".equals(loadId)) {
			loadId = request.getSession().getAttribute("loadId") + "";
		}

		String imgPath = userinfoService.findUserInfoByLoadId(loadId)
				.getImgPath();
		String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);
		// 设置两个头
		String contentType = "multipart/form-data";
		String contentDispostion = "inline;filename=" + imgName;
		response.setHeader("Content-Type", contentType);
		response.setHeader("Content-Disposition", contentDispostion);
		// 实现下载
		FileInputStream input = new FileInputStream(imgPath);
		ServletOutputStream out = response.getOutputStream();
		IOUtils.copy(input, out);
		out.close();
		input.close();
	}

	/**
	 * 下载读者信息
	 * 
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	@RequestMapping("/downUserMsg.do")
	@ResponseBody
	public void downUserMsg(Integer type, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// 先读取模版文件
		String path = this.getClass().getResource("/").getPath();
		path = path.substring(0, path.lastIndexOf("/classes"))
				+ "/template/UserMsg.xls";
		FileInputStream tpls = new FileInputStream(path);
		if (type == 1) {
			String contentType = "application/vnd.ms-excel";
			String contentDispostion = "attachment;filename="
					+ java.net.URLEncoder.encode("读者信息统计表.xls", "UTF-8");
			response.setContentType(contentType);
			response.setHeader("Content-disposition", contentDispostion);
			Workbook book = null;
			try {
				book = new XSSFWorkbook(tpls);
			} catch (Exception ex) {
				book = new HSSFWorkbook(tpls);
			}
			// 下载读者信息
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("root", "AllUser");
			List<UserInfo> list = userinfoService.findAllUser(map);
			// 将信息写入到excel中实现下载
			CellStyle style = book.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			// 获取sheet
			Sheet usersheet = book.getSheetAt(0);
			int rows = usersheet.getLastRowNum();
			for (int i = 0; i < list.size(); i++) {
				UserInfo info = list.get(i);
				// 从第rows+1行开始写数据
				Row row = usersheet.createRow(rows + i);
				row.setHeight((short) 500);
				Cell cell = row.createCell(0);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(info.getLoadId());

				cell = row.createCell(1);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(info.getRealName());

				cell = row.createCell(2);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(info.getPhone());

				cell = row.createCell(3);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(info.getIdCard());

				cell = row.createCell(4);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(info.getIsAvail() == 0 ? "激活" : "冻结");

			}

			OutputStream ouputStream = response.getOutputStream();
			book.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
			book.close();

		} else {
			String contentType = "multipart/form-data";
			String contentDispostion = "attachment;filename="
					+ java.net.URLEncoder.encode("读者信息统计表.xls", "UTF-8");
			response.setContentType(contentType);
			response.setHeader("Content-disposition", contentDispostion);
			// 实现文件下载
			OutputStream ouputStream = response.getOutputStream();
			IOUtils.copy(tpls, ouputStream);
			ouputStream.close();
			tpls.close();

		}
	}

	/**
	 * 上传文件并解析
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	@RequestMapping("/parseExcel.do")
	@ResponseBody
	public BaseResult parseExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String phone = "[1-9]{1}[0-9]{10}";
		String idCard = "(\\d{18,18}|\\d{15,15}|\\d{17,17}(x|X))";
		String message = "";
		int total = 0;
		int success = 0;
		// 先下载文件，然后进行解析
		// 得到工厂
		DiskFileItemFactory disk = new DiskFileItemFactory();
		// 通过工厂得到解析器
		ServletFileUpload fileUpload = new ServletFileUpload(disk);
		// 通过解析器得到FileItem对象集合
		List<FileItem> list = fileUpload.parseRequest(request);
		// 遍历集合，下载excel文件，获取到文件，进行解析
		for (FileItem item : list) {
			if (!item.isFormField()) {
				// 获取文件名字
				String name = item.getName();
				String path = this.getClass().getResource("/").getPath();
				path = path.substring(0, path.lastIndexOf("/classes"))
						+ "/import/" + CommonUtils.uuid()
						+ name.substring(name.indexOf("."));
				File file = new File(path);
				item.write(file);
				FileInputStream in = new FileInputStream(file);
				Workbook book = null;
				try {
					book = new XSSFWorkbook(in);
				} catch (Exception ex) {
					book = new HSSFWorkbook(in);
				}
				// 解析excel文件，并解析该文件，实现下载到数据库,如果有错误信息则将错误信息输出的前台
				// 从第六行开始解析
				Sheet sheet = book.getSheetAt(0);
				int rows = total = sheet.getLastRowNum();
				// 遍历实现解析
				for (int i = 5; i < rows + 1; i++) {
					Row row = sheet.getRow(i);
					int cells = 5;

					boolean flag = true;
					for (int j = 0; j < cells; j++) {

						if (j == (cells - 1)) {
							continue;
						}
						row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
						if (row.getCell(j) == null) {
							message += "导入第" + i + "行,第" + (j + 1)
									+ "列失败,原因:该列为空<br/>";
							flag = false;
						} else {
							if (j == 2) {
								if (!row.getCell(j).getStringCellValue()
										.matches(phone)) {
									message += "导入第" + i + "行,第" + (j + 1)
											+ "列失败,原因:所填的电话号码不符合格式<br/>";
									flag = false;
								}

							}
							if (j == 3) {
								if (!row.getCell(j).getStringCellValue()
										.matches(idCard)) {
									message += "导入第" + i + "行,第" + (j + 1)
											+ "列失败,原因:所填身份证错误<br/>";
									flag = false;
								}

							}

						}
					}
					if (flag) {
						UserInfo userinfo = new UserInfo();
						userinfo.setLoadId(row.getCell(0).getStringCellValue());
						userinfo.setRealName(row.getCell(1)
								.getStringCellValue());
						userinfo.setPhone(row.getCell(2).getStringCellValue());
						userinfo.setIdCard(row.getCell(3).getStringCellValue());
						userinfo.setUserName(row.getCell(0)
								.getStringCellValue());
						userinfo.setRoot(2);
						userinfo.setPassword(MD5Util.MD5(row.getCell(0)
								.getStringCellValue()));
						userinfo.setIsMiss(0);
						userinfo.setIsAvail(0);
						// 先查找是否导入的用户是否已经存在
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("root", "AllUser");
						map.put("condition", "loadId");
						map.put("value", row.getCell(0).getStringCellValue());
						List<UserInfo> users = userinfoService.findAllUser(map);
						if (users.size() > 0) {
							message += "导入第" + i
									+ "行数据失败,原因为:该用户已经存在,或者借书证重复<br/>";
						} else {
							int n = userinfoService.addUsers(userinfo);
							if (n < 0) {
								message += "导入第" + i + "行数据失败,原因为:系统错误<br/>";
							} else {
								success += 1;
							}
						}
					}
				}
			}
		}
		return new BaseResult(true, "总共" + (total - 4) + "条记录,成功" + success
				+ "失败" + (total - 4 - success) + "<br/>" + message, null);
	}

	/**
	 * 下载书籍挂失统计表
	 * @throws IOException 
	 */
	@RequestMapping("/downReturn.do")
	@ResponseBody
	public void downReturn(Integer type,HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		// 读取分页的配置文件
		Properties prop = new Properties();
		InputStream input = this.getClass().getClassLoader()
				.getResourceAsStream("/pay.properties");
		prop.load(input);
		int PAGESIZE = Integer.parseInt(prop.getProperty("PAGESIZE"));
		int isAllow = Integer.parseInt(prop.getProperty("isAllow"));
		double day = Double.parseDouble(prop.getProperty("day"));
		int miss = Integer.parseInt(prop.getProperty("miss"));
		Map<String,Object> map=new HashMap<String, Object>();
		Map<String,Object>  resultMap=new HashMap<String, Object>();
		map.put("operate", 1);
		if(type==0){
			
			String contentType = "application/vnd.ms-excel";
			String contentDispostion = "attachment;filename="
					+ java.net.URLEncoder.encode("催还统计表.xls", "UTF-8");
			response.setContentType(contentType);
			response.setHeader("Content-disposition", contentDispostion);
			//超期但是为欠款
			List<EasySearchResult> list=bookService.findAllBorrowRecord(map);
			Iterator<EasySearchResult> it=list.iterator();
			while(it.hasNext()){
				EasySearchResult easy=it.next();
				if((new Date().getTime()-easy.getEndTime().getTime())<=0){
					it.remove();
				}
			}
			
			//读取模版文件
			String path=this.getClass().getResource("/").getPath();
			path=path.substring(0,path.indexOf("/classes"))+"/template/return.xls";
			FileInputStream tpls = new FileInputStream(path);
			Workbook book = null;
			try {
				book = new XSSFWorkbook(tpls);
			} catch (Exception ex) {
				book = new HSSFWorkbook(tpls);
			}
			CellStyle style = book.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			// 获取sheet
			Sheet usersheet = book.getSheetAt(0);
			int rows = usersheet.getLastRowNum();
			for (int i = 0; i < list.size(); i++) {
				EasySearchResult result = list.get(i);
				// 从第rows+1行开始写数据
				Row row = usersheet.createRow(rows + i);
				row.setHeight((short) 500);
				Cell cell = row.createCell(0);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(result.getLoadId());

				cell = row.createCell(1);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(result.getRealName());

				cell = row.createCell(2);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(result.getPhone());

				cell = row.createCell(3);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(result.getIdCard());
				
				cell=row.createCell(4);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(sdf.format(result.getStartTime()));
				
				cell=row.createCell(5);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(sdf.format(result.getEndTime()));
			}
			//下载
			OutputStream out= response.getOutputStream();
			book.write(out);
			out.flush();
			out.close();
			book.close();
		} 
		if(type==1){
			String contentType = "application/vnd.ms-excel";
			String contentDispostion = "attachment;filename="
					+ java.net.URLEncoder.encode("赔偿统计表.xls", "UTF-8");
			response.setContentType(contentType);
			response.setHeader("Content-disposition", contentDispostion);
			//超期欠款
			List<EasySearchResult> list=bookService.findAllBorrowRecord(map);
			Calendar cal=Calendar.getInstance();
			//使用迭代器进行集合的删除
			Iterator<EasySearchResult> it=list.iterator();
			while(it.hasNext()){
				EasySearchResult easy=it.next();
				cal.setTime(easy.getEndTime());
				cal.add(Calendar.DAY_OF_YEAR,isAllow);
				Date lastDate=cal.getTime();
				Date nowDate=new Date();
				if((nowDate.getTime()-lastDate.getTime())<=0){
					it.remove();
				} 
				
			}
			
			//如果是个人中心展示，查询出书刊遗失
			map.put("operate",3);
			list.addAll(bookService.findAllBorrowRecord(map));
			for(EasySearchResult esay : list){
				cal.setTime(esay.getEndTime());
				cal.add(Calendar.DAY_OF_YEAR,isAllow);
				Date lastDate=cal.getTime();
				int lastDay=cal.get(Calendar.DAY_OF_YEAR);
				Date nowDate=new Date();
				cal.setTime(nowDate);
				int nowDay=cal.get(Calendar.DAY_OF_YEAR);
				if(esay.getOperate()==1){
					esay.setMoney((nowDay-lastDay)*day);
				} else if(esay.getOperate()==3){
					esay.setMoney(esay.getPrice()*miss*1.0);
				}
			}
			//实现下载
			String path=this.getClass().getResource("/").getPath();
			path=path.substring(0,path.indexOf("/classes"))+"/template/pay.xls";
			FileInputStream tpls=new FileInputStream(path);
			Workbook book = null;
			try {
				book = new XSSFWorkbook(tpls);
			} catch (Exception ex) {
				book = new HSSFWorkbook(tpls);
			}
			CellStyle style = book.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			// 获取sheet
			Sheet usersheet = book.getSheetAt(0);
			int rows = usersheet.getLastRowNum();
			for (int i = 0; i < list.size(); i++) {
				EasySearchResult result = list.get(i);
				// 从第rows+1行开始写数据
				Row row = usersheet.createRow(rows + i);
				row.setHeight((short) 500);
				Cell cell = row.createCell(0);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(result.getLoadId());

				cell = row.createCell(1);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(result.getRealName());

				cell = row.createCell(2);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(result.getPhone());

				cell = row.createCell(3);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(result.getIdCard());
				
				cell=row.createCell(4);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(result.getOperate()==1 ? "超期欠款" : "书籍丢失");
				
				cell=row.createCell(5);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(result.getMoney());
			}
			//下载
			OutputStream out= response.getOutputStream();
			book.write(out);
			out.flush();
			out.close();
			book.close();
		}
		
		if(type==2){
			String contentType = "application/vnd.ms-excel";
			String contentDispostion = "attachment;filename="
					+ java.net.URLEncoder.encode("图书遗失.xls", "UTF-8");
			response.setContentType(contentType);
			response.setHeader("Content-disposition", contentDispostion);
			map.put("operate",3);
			List<EasySearchResult> list=bookService.findAllBorrowRecord(map);
			//读取配置文件
			String path=this.getClass().getResource("/").getPath();
			path=path.substring(0,path.indexOf("/classes"))+"/template/miss.xls";
			FileInputStream tpls=new FileInputStream(path);
			Workbook book = null;
			try {
				book = new XSSFWorkbook(tpls);
			} catch (Exception ex) {
				book = new HSSFWorkbook(tpls);
			}
			CellStyle style = book.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			// 获取sheet
			Sheet usersheet = book.getSheetAt(0);
			int rows = usersheet.getLastRowNum();
			for (int i = 0; i < list.size(); i++) {
				EasySearchResult result = list.get(i);
				// 从第rows+1行开始写数据
				Row row = usersheet.createRow(rows + i);
				row.setHeight((short) 500);
				Cell cell = row.createCell(0);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(result.getLoadId());

				cell = row.createCell(1);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(result.getRealName());

				cell = row.createCell(2);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(result.getBookId());

				cell = row.createCell(3);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(result.getBookName());
				
				cell=row.createCell(4);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(result.getPublishHouse()+" "+result.getPublishTime());
				
			}
			//下载
			OutputStream out= response.getOutputStream();
			book.write(out);
			out.flush();
			out.close();
			book.close();
		}
	}
}

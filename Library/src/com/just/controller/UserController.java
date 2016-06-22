package com.just.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import MD5.MD5Util;

import com.just.bean.BaseResult;
import com.just.bean.EasySearchResult;
import com.just.bean.UserInfo;
import com.just.service.BookService;
import com.just.service.UserInfoService;

/**
 * 登录注册控制器
 * 
 * @author Administrator
 * 
 */
@Controller
public class UserController {
	private static final Integer PAGESIZE = 2;
	@Resource
	private UserInfoService userinfoService;
	@Resource
	private BookService bookService;

	@RequestMapping("/login.do")
	@ResponseBody
	public BaseResult login(@RequestParam("loadId") String loadId,
			@RequestParam("password") String password,
			HttpServletRequest request) {
		BaseResult base = new BaseResult();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loadId", loadId);
		map.put("password", MD5Util.MD5(password));
		UserInfo userinfo = userinfoService.findByLoadId(map);
		if (userinfo == null) {
			base.setFlag(false);
			base.setMessage("用户名或密码错误");

		} else if (userinfo.getIsAvail() == 1) {
			base.setFlag(false);
			base.setMessage("此账户以冻结,请联系管理员");
		} else {
			base.setFlag(true);
			base.setMessage("登录成功");
			base.setResult(userinfo);
			HttpSession session = request.getSession(true);
			session.setAttribute("id", userinfo.getId());
			session.setAttribute("loadId", userinfo.getLoadId());
		}
		return base;
	}

	@RequestMapping("/regist.do")
	@ResponseBody
	public BaseResult regist(String loadId, String userName, String realName,
			String idCard, String phone, String password) {
		BaseResult base = new BaseResult();
		UserInfo user = new UserInfo();
		user.setLoadId(loadId);
		user.setUserName(userName);
		user.setRealName(realName);
		user.setRoot(1);
		user.setPassword(MD5Util.MD5(password));
		user.setPhone(phone);
		user.setIdCard(idCard);
		user.setIsMiss(0);
		// 判断是否借书证是否存在
		int n = userinfoService.findLoadIdIsExit(loadId);
		if (n > 0) {
			base.setFlag(false);
			base.setMessage("此借书证已经存在");

		} else {
			userinfoService.addUsers(user);
			base.setFlag(true);
			base.setResult(user);
		}
		return base;
	}

	/**
	 * 判断借书证是否存在
	 */
	@RequestMapping("/checkIsExit.do")
	@ResponseBody
	public BaseResult checkIsExit(String loadId) {
		int n = userinfoService.findLoadIdIsExit(loadId);
		if (n > 0) {
			return new BaseResult(false, "此借书证已存在", null);
		} else {
			return new BaseResult(true, null, null);
		}
	}

	@RequestMapping("/addUser.do")
	@ResponseBody
	public BaseResult addUserInfo(String loadId, String realName, String phone,
			Integer root) {
		UserInfo userinfo = new UserInfo();
		userinfo.setLoadId(loadId);
		userinfo.setUserName(loadId);
		userinfo.setRealName(realName);
		userinfo.setRoot(root);
		userinfo.setPassword(MD5Util.MD5(loadId));
		userinfo.setPhone(phone);
		userinfo.setIsMiss(0);
		userinfo.setIsAvail(0);
		if (userinfoService.findLoadIdIsExit(loadId) > 0) {
			return new BaseResult(false, null, null);
		} else {
			int n = userinfoService.addUsers(userinfo);
			if (n > 0) {
				return new BaseResult(true, null, null);
			} else {
				return new BaseResult(false, null, null);
			}
		}
	}

	/**
	 * 查找用户
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	@RequestMapping("/findAllUser.do")
	@ResponseBody
	public BaseResult findAllUser(Integer startPage, String condition,
			String value, String root) {

		/**
		 * 首先查找出总共有多少用户
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("root", root);
		map.put("condition", condition);
		map.put("value", value);
		map.put("type", 1);
		List<UserInfo> list = userinfoService.findAllUser(map);
		// 计算出总共有多少页
		double totalPage = Math.ceil((double) list.size() / PAGESIZE);
		/**
		 * 分页查询
		 */
		map.put("type", 0);
		map.put("startPage", (startPage - 1) * PAGESIZE);
		map.put("pageSize", PAGESIZE);
		list = userinfoService.findAllUser(map);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("totalPage", totalPage);
		result.put("result", list);
		return new BaseResult(true, null, result);
	}

	/**
	 * 删除用户
	 */
	@RequestMapping("/deleteUserInfo.do")
	@ResponseBody
	public BaseResult deleteUserInfo(Integer id) {
		int n = userinfoService.deleteUserInfo(id);
		if (n > 0) {
			return new BaseResult(true, null, null);
		} else {
			return new BaseResult(false, null, null);
		}
	}

	/**
	 * 更新用户表
	 */
	@RequestMapping("/modifyUserInfo.do")
	@ResponseBody
	public BaseResult modifyUserInfo(String loadId, String realName,
			String phone, Integer root, Integer isAvail, Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loadId", loadId);
		map.put("realName", realName);
		map.put("phone", phone);
		map.put("root", root);
		map.put("isAvail", isAvail);
		map.put("id", id);
		int n = userinfoService.modifyUserInfo(map);
		if (n > 0) {
			return new BaseResult(true, null, null);
		} else {
			return new BaseResult(false, "修改失败", null);
		}
	}

	/**
	 * 根据id查找用户是否存在
	 */
	@RequestMapping("/findUserByLoadId.do")
	@ResponseBody
	public BaseResult findUserInfoByLoadId(String loadId) {
		UserInfo userInfo = userinfoService.findUserInfoByLoadId(loadId);
		return new BaseResult(true, null, userInfo);
	}

	/**
	 * 根据传入的bookid,loadId,operate查找当前用户是否违规
	 * 
	 * @throws IOException
	 */
	@RequestMapping("/findUserIsIlleg.do")
	@ResponseBody
	public BaseResult findUserIsIlleg(String bookId, String loadId,
			Integer operate) throws IOException {
		// 先读取配置文件，找出罚金标准
		InputStream input = this.getClass().getClassLoader()
				.getResourceAsStream("pay.properties");
		Properties prop = new Properties();
		prop.load(input);
		Integer day = Integer.parseInt(prop.getProperty("day"));
		Integer miss = Integer.parseInt(prop.getProperty("miss"));
		Integer isAllow = Integer.parseInt(prop.getProperty("isAllow"));
		Calendar cal = Calendar.getInstance();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bookId", bookId);
		map.put("loadId", loadId);
		map.put("operate", 3);
		//先查看当前用户是否遗失数据
		List<EasySearchResult> list = bookService.findAllBorrowRecord(map);
		if(list.size()>0){
			return new BaseResult(false, "您已遗失此书,请先缴纳相应罚金"+list.get(0).getPrice()*miss+"元", list.get(0));
		} else {
				map.put("operate", operate);
				list=bookService.findAllBorrowRecord(map);
				if(list.size()>0){
					cal.setTime(list.get(0).getEndTime());
					int a = cal.get(Calendar.DAY_OF_YEAR);
					cal.setTime(new Date());
					int b = cal.get(Calendar.DAY_OF_YEAR);
					if ((b - a - isAllow) > 0) {
						return new BaseResult(false, "您已超期欠款" + (b - a - 1) * day
								+ "元,请先缴纳相应罚金", list.get(0));
					}
				} else {
					return new  BaseResult(false, "您未借阅此书", null);
				}
				// 说明为欠费,此时将修改borrowrecord表中的operate为2
				map.put("changeOperate", 2);
				int n = bookService.modifyOperate(map);
				//修改图书的可用数目
				int m=bookService.modifyAvailNumAdd(bookId);
				if (m>0&&n > 0) {
					return new BaseResult(true, "所借图书未超期<br/>还书成功", null);
				} else {
					return new BaseResult(true, "您尚未欠款,但是由于系统繁忙,请稍候还书", null);
				}
			}
		} 
	

	/**
	 * 查找当前用于的违规记录以及所需赔偿的金额
	 * 
	 * @throws IOException
	 */
	@RequestMapping("/findAllIlleg.do")
	@ResponseBody
	public BaseResult payMoneny(String loadId, String bookId)
			throws IOException {
		// 先读取配置文件，获得惩罚的相关信息
		InputStream input = this.getClass().getClassLoader()
				.getResourceAsStream("pay.properties");
		Properties prop = new Properties();
		prop.load(input);
		Integer day = Integer.parseInt(prop.getProperty("day"));
		Integer miss = Integer.parseInt(prop.getProperty("miss"));
		Integer isAllow = Integer.parseInt(prop.getProperty("isAllow"));
		Calendar cal = Calendar.getInstance();
		// 查找用于违规相关信息
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bookId", bookId);
		map.put("loadId", loadId);
		// 先查找出借阅未归还
		map.put("operate", 1);
		List<EasySearchResult> outofdate = bookService.findAllBorrowRecord(map);
		Iterator<EasySearchResult> it=outofdate.iterator();
		while(it.hasNext()){
			EasySearchResult easy=it.next();
			cal.setTime(easy.getEndTime());
			int start = cal.get(Calendar.DAY_OF_YEAR);
			cal.setTime(new Date());
			int end = cal.get(Calendar.DAY_OF_YEAR);
			if ((end - start - isAllow) > 0) {
				easy.setMoney((end - start - isAllow) * day * 1.0);
			} else {
				it.remove();
			}
		}
		
		// 在查找出书籍丢失的
		map.put("operate", 3);
		List<EasySearchResult> ismiss = bookService.findAllBorrowRecord(map);
		// 遍历书籍丢失，算出价格
		for (EasySearchResult result : ismiss) {
			result.setMoney(result.getPrice() * miss * 1.0);
		}
		outofdate.addAll(ismiss);
		return new BaseResult(true, null, outofdate);

	}

	/**
	 * 赔偿处理,修改borrowrecord表中的operate为2
	 */
	@RequestMapping("/modifyOperate.do")
	@ResponseBody
	public BaseResult modifyOperate(String loadId, String bookId,
			Integer operate, Integer id, Integer changeOperate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loadId", loadId);
		map.put("bookId", bookId);
		map.put("operate", operate);
		map.put("id", id);
		map.put("changeOperate", changeOperate);
		int n = bookService.modifyOperate(map);
		if (n > 0) {
			return new BaseResult(true, null, null);
		} else {
			return new BaseResult(false, "修改失败,请稍候重试", null);
		}
	}

	/**
	 * 个人信息中心
	 */
	@RequestMapping("/ModifyUserMsg.do")
	@ResponseBody
	public BaseResult modifyUserMsg(String loadId, String password,
			String username, String phone, String idCard, String email) {
		UserInfo userinfo = new UserInfo();
		userinfo.setLoadId(loadId);
		userinfo.setPassword(MD5Util.MD5(password));
		userinfo.setUserName(username);
		userinfo.setPhone(phone);
		userinfo.setIdCard(idCard);
		userinfo.setEmail(email);
		int n = userinfoService.updateUserMsg(userinfo);
		if (n > 0) {
			return new BaseResult(true, null, null);
		} else {
			return new BaseResult(false, null, null);
		}
	}
	
	/**
	 * 查找密码是否和原密码一致
	 */
	@RequestMapping("/checkPwd.do")
	@ResponseBody
	public BaseResult checkPwd(String password,String loadId){
		UserInfo userInfo = userinfoService.findUserInfoByLoadId(loadId);
		if(MD5Util.MD5(password).equals(userInfo.getPassword())){
			return new BaseResult(true,null,userInfo);
		} else {
			return new BaseResult(false,null,null);
		}
	}
	
	/**
	 * 直接传入时间，判断是否违规
	 * @throws IOException 
	 */
	@RequestMapping("/checkIsIlle.do")
	@ResponseBody
	public BaseResult checkIsIlle(long endtime) throws IOException{
		//读取配置文件中的允许过期时间
		Properties prop=new Properties();
		InputStream input=this.getClass().getClassLoader().getResourceAsStream("/pay.properties");
		prop.load(input);
		int isAllow=Integer.parseInt(prop.getProperty("isAllow"));
		double dayMoney=Integer.parseInt(prop.getProperty("day"));
		Date date=new Date();
		Date endTime=new Date(endtime);
		int endDays=endTime.getDay()+isAllow;
		int nowDays=date.getDay();
		if(nowDays>endDays){
			return new BaseResult(false, "您已违规,所欠金额为:"+(nowDays-endDays)*dayMoney+",请先缴纳相应罚金，才能借阅",null);
		} else {
			return new BaseResult(true,null,null);
		}
	}
	
	/**
	 * 修改isAvail
	 */
	@RequestMapping("/updateIsAvail.do")
	@ResponseBody
	public BaseResult updateIsAvail(String loadId,Integer isAvail){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("loadId",loadId);
		map.put("isAvail",isAvail);
		int n=userinfoService.updateIsAvail(map);
		if(n>0) {
			return new BaseResult(true,null,null);
		} else {
			return new BaseResult(false, null, null);
		}
	}
	
	/**
	 * 查找所有的违规记录(个人信息展示)
	 * @throws IOException 
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("/findAllIllegal.do")
	@ResponseBody
	public BaseResult findAllIllegal(String loadId,Integer type,Integer startPage,Integer role) throws IOException{
		Calendar cal=Calendar.getInstance();
		//读取分页的配置文件
		Properties prop=new Properties();
		InputStream input=this.getClass().getClassLoader().getResourceAsStream("/pay.properties");
		prop.load(input);
		int PAGESIZE=Integer.parseInt(prop.getProperty("PAGESIZE"));
		int isAllow=Integer.parseInt(prop.getProperty("isAllow"));
		double day=Double.parseDouble(prop.getProperty("day"));
		int miss=Integer.parseInt(prop.getProperty("miss"));
		Map<String,Object> map=new HashMap<String, Object>();
		Map<String,Object>  resultMap=new HashMap<String, Object>();
		map.put("loadId",loadId);
		map.put("operate", 1);
		//超期，但是未欠款(用于信息发布展示)
		if(type==0){
			//查询出总数量
			List<EasySearchResult> list=bookService.findAllBorrowRecord(map);
			if(list.size()==0){
				return new BaseResult(true,null,list);
			}
			
			Iterator<EasySearchResult> it=list.iterator();
			while(it.hasNext()){
				EasySearchResult easy=it.next();
				//cal.setTime(easy.getEndTime());
				//cal.add(Calendar.DAY_OF_YEAR, isAllow);
				//Date endTime=cal.getTime();
				if((new Date().getTime()-easy.getEndTime().getTime())<=0){
					it.remove();
				}
			}
			int totalPage=(int)Math.ceil(list.size()*1.0/PAGESIZE);
			//分页查询
			map.put("isLimit",true);
			map.put("startPage",(startPage-1)*PAGESIZE);
			map.put("pageSize",PAGESIZE);
			list=bookService.findAllBorrowRecord(map);
			 it=list.iterator();
			while(it.hasNext()){
				EasySearchResult easy=it.next();
				//cal.setTime(easy.getEndTime());
				//cal.add(Calendar.DAY_OF_YEAR, isAllow);
				//Date endTime=cal.getTime();
				if((new Date().getTime()-easy.getEndTime().getTime())<=0){
					it.remove();
				}
				
			}
			resultMap.put("totalPage",totalPage);
			resultMap.put("result", list);
		} else if(type==1){
			//超期欠款
			List<EasySearchResult> list=bookService.findAllBorrowRecord(map);
			if(list.size()==0){
				//如果是个人中心展示，查询出书刊遗失
				if(role!=null){
					map.put("operate",3);
					List<EasySearchResult> ismiss=bookService.findAllBorrowRecord(map);
					for(EasySearchResult money : ismiss){
						money.setMoney(money.getPrice()*miss*1.0);
					}
					list.addAll(ismiss);
				}
				return new BaseResult(true,null,list);
			}
			
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
			if(role!=null){
				map.put("operate",3);
				list.addAll(bookService.findAllBorrowRecord(map));
			}
			int totalPage=(int)Math.ceil(list.size()*1.0/PAGESIZE);
			//开始分页
			map.put("isLimit",true);
			map.put("startPage",(startPage-1)*PAGESIZE);
			map.put("pageSize",PAGESIZE);
			map.put("operate", 1);
			list=bookService.findAllBorrowRecord(map);
			it=list.iterator();
			while(it.hasNext()){
				EasySearchResult easy=it.next();
				cal.setTime(easy.getEndTime());
				cal.add(Calendar.DAY_OF_YEAR,isAllow);
				Date lastDate=cal.getTime();
				int lastDay=cal.get(Calendar.DAY_OF_YEAR);
				Date nowDate=new Date();
				cal.setTime(nowDate);
				int nowDay=cal.get(Calendar.DAY_OF_YEAR);
				
				//计算出所欠金额
				if(easy.getOperate()==1){
					if((nowDate.getTime()-lastDate.getTime())<=0){
						it.remove();
					}
					easy.setMoney((nowDay-lastDay)*day);
				} else if(easy.getOperate()==3){
					easy.setMoney(easy.getPrice()*miss*1.0);
				}
			}
			
			resultMap.put("totalPage",totalPage);
			resultMap.put("result", list);
			
		}
		return new BaseResult(true,null,resultMap);
	}
	
}

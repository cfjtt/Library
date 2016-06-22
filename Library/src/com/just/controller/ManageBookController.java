package com.just.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.just.bean.BaseResult;
import com.just.bean.BookClassInfo;
import com.just.bean.BookInfo;
import com.just.bean.BookType;
import com.just.bean.BorrowRecord;
import com.just.bean.EasySearchResult;
import com.just.bean.Evaluation;
import com.just.bean.IllegalRecord;
import com.just.bean.SaveBook;
import com.just.bean.UserInfo;
import com.just.bean.savePlace;
import com.just.bean.secondBookType;
import com.just.service.BookService;
import com.just.service.UserInfoService;
/**
 * 图书管理controller
 * 
 * @author Administrator
 * 
 */
@Controller
public class ManageBookController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final int PAGESIZE = 2;
	@Resource
	private BookService bookService;
	@Resource
	private UserInfoService userService;

	@RequestMapping("/getPlaceAndBookClass.do")
	@ResponseBody
	public Map<String, List> getPlaceAndBookClass(Integer bookClassId,
			Integer secondClassId, Integer action) {
		Map<String, List> map = new HashMap<String, List>();
		if (action == 0) {
			List<BookType> bookType = bookService.findFirstBookClass();
			List<savePlace> savePlace = bookService.getAllSavePlace();
			List<BookClassInfo> bookClassInfo = bookService.findAllBookType();
			map.put("places", savePlace);
			map.put("bookClasses", bookType);
			map.put("bookClassInfo", bookClassInfo);
		} else if (action == 1) {
			List<secondBookType> secondBookType = bookService
					.findSecondBookClass(bookClassId);
			map.put("secondBookClasses", secondBookType);
		} else if (action == 2) {
			Map<String, Integer> hashmap = new HashMap<String, Integer>();
			hashmap.put("bookClassId", bookClassId);
			hashmap.put("secondClassId", secondClassId);

			map.put("thirdBookClasses", bookService.findThirdBookClass(hashmap));
		}
		return map;
	}

	/**
	 * 添加书籍
	 */
	@RequestMapping("/addBook.do")
	@ResponseBody
	public BaseResult addBook(String bookId, String bookName,
			String bookClassId, Integer bookTypeId, String autor,
			String publishHouse, String publishTime, Double price,
			Integer totalNum, Integer availNum, String description,
			Integer place) {

		BookInfo bookInfo = new BookInfo();
		bookInfo.setBookId(bookId);
		bookInfo.setBookName(bookName);
		bookInfo.setBookClassId(bookClassId);
		bookInfo.setBookTypeId(bookTypeId);
		bookInfo.setAutor(autor);
		bookInfo.setPublishHouse(publishHouse);
		bookInfo.setPublishTime(publishTime);
		bookInfo.setPrice(price);
		bookInfo.setBorrowTimes(0);
		bookInfo.setTotalNum(totalNum);
		bookInfo.setAvailNum(availNum);
		bookInfo.setImgPath(null);
		bookInfo.setDescription(description);
		bookInfo.setAddTime(new Date());
		bookInfo.setPlace(place);
		bookInfo.setSaveTime(0);
		bookService.addBook(bookInfo);
		return new BaseResult(true, null, null);
	}

	/**
	 * 简单检索
	 */
	@RequestMapping("/easySearch.do")
	@ResponseBody
	public BaseResult easySearchBook(String condition1, String condition2,
			Integer type, Integer startPage) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("condition1", condition1);
		map.put("condition3", condition2);
		map.put("type", type);
		map.put("startPage", (startPage - 1) * PAGESIZE);
		map.put("pageSize", PAGESIZE);
		List<EasySearchResult> list = bookService.easySearch(map);
		int totalPage=(int)Math.ceil((list.size()*1.0/PAGESIZE));
		map.put("isLimit",true);
		list=bookService.easySearch(map);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("totalPage", totalPage);
		resultMap.put("result", list);
		return new BaseResult(true, null, resultMap);
	}

	/**
	 * 删除图书
	 */
	@RequestMapping("/deleteBookInfo.do")
	@ResponseBody
	public BaseResult deleteBookInfo(Integer id, String bookId, String bookName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("bookId", bookId);
		map.put("bookName", bookName);
		bookService.deleteBookInfo(map);
		return new BaseResult(true, null, null);
	}

	/**
	 * 根据指定的菜单id查找出菜单的名字
	 */
	@RequestMapping("/findBookClassAllName.do")
	@ResponseBody
	public BaseResult findBookClassAllName(Integer firstClassId,
			Integer secondClassId, Integer thirdClassId) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("firstClassId", firstClassId);
		map.put("secondClassId", secondClassId);
		map.put("thirdClassId", thirdClassId);
		return new BaseResult(true, null, bookService.findBookClassIdName(map));
	}

	/**
	 * 更新bookinfo表
	 */
	@RequestMapping("/modifyBookInfo.do")
	@ResponseBody
	public BaseResult modifyBookInfo(Integer id, String bookId,
			String bookName, String bookClassId, Integer bookTypeId,
			String autor, String publishHouse, String publishTime,
			Double price, Integer totalNum, Integer availNum,
			String description, Integer place) {
		BookInfo bookInfo = new BookInfo();
		bookInfo.setId(id);
		bookInfo.setBookId(bookId);
		bookInfo.setBookName(bookName);
		bookInfo.setBookClassId(bookClassId);
		bookInfo.setBookTypeId(bookTypeId);
		bookInfo.setAutor(autor);
		bookInfo.setPublishHouse(publishHouse);
		bookInfo.setPublishTime(publishTime);
		bookInfo.setPrice(price);
		bookInfo.setTotalNum(totalNum);
		bookInfo.setAvailNum(availNum);
		bookInfo.setDescription(description);
		bookInfo.setPlace(place);
		int n = bookService.modifyBookInfo(bookInfo);
		if (n > 0) {
			return new BaseResult(true, null, null);
		} else {
			return new BaseResult(false, null, null);
		}
	}

	/**
	 * 根据bookId查找出当前书籍的信息
	 */
	@RequestMapping("/findBookUserInfo.do")
	@ResponseBody
	public BaseResult findBookUserInfo(String bookId, Integer operate,
			String loadId) {

		/**
		 * 确定当前用户是否是激活状态
		 */
		UserInfo userinfo = userService.findUserInfoByLoadId(loadId);
		if (userinfo == null) {
			return new BaseResult(false, "此用户不存在", null);
		} else if (userinfo.getIsAvail() == 1) {
			return new BaseResult(false, "此用户账户已被冻结", null);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		// 查找图书相关信息
		map.put("bookId", bookId);
		List<EasySearchResult> list = bookService.findAllBorrowRecord(map);
		//查找当前书籍的预约人数
		map.put("operate", operate);
		List<EasySearchResult> result = bookService.findAllBorrowRecord(map);
		Integer order = result.size();
		/**
		 * 查找出当前用户是否已经借阅此书
		 */
		map.put("operate", 1);
		map.put("loadId", loadId);
		List<EasySearchResult> isBorrow = bookService.findAllBorrowRecord(map);
		if (isBorrow.size() > 0) {
			return new BaseResult(false, "您已借阅" + isBorrow.get(0).getBookName()
					+ "此书", null);
		}
		map.put("operate", 3);
		isBorrow=bookService.findAllBorrowRecord(map);
		if(isBorrow.size()>0){
			return new BaseResult(false, "您已遗失" + isBorrow.get(0).getBookName()
					+ "此书,并且未缴纳相应罚金，无法借阅次数", null);
		}
		
		
		// 查找当前用户是否已经预约过这本书
		map.put("userid", loadId);
		Map<String, Object> mapResult = new HashMap<String, Object>();
		mapResult.put("isOrder", (bookService.checkIsOrder(map) == null));
		mapResult.put("result", list.size() == 0 ? new EasySearchResult()
				: list.get(0));
		mapResult.put("order", order);
		return new BaseResult(true, null, mapResult);
	}

	/**
	 * 确定借阅
	 */
	@Transactional
	@RequestMapping("/modifyBorrowAndBookInfo.do")
	@ResponseBody
	public BaseResult modifyBorrowAndBookInfo(String loadId, Integer id,
			String bookId, String days) {
		/**
		 * 判断当前用户是否已经借阅此书
		 */
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bookId", bookId);
		map.put("loadId", loadId);
		map.put("operate", 1);
		List<EasySearchResult> list = bookService.findAllBorrowRecord(map);
		if (list.size() > 0) {
			return new BaseResult(false, "您已借阅" + list.get(0).getBookName()
					+ "此书", null);
		}
		map.put("operate", 3);
		list=bookService.findAllBorrowRecord(map);
		if(list.size()>0){
			return new BaseResult(false, "您已遗失" + list.get(0).getBookName()
					+ "此书，请先缴纳相应罚金", null);
		}
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, Integer.parseInt(days));
		Date endTime = cal.getTime();
		BorrowRecord record = new BorrowRecord();
		record.setBookId(bookId);
		record.setUserId(loadId);
		record.setBeginTime(date);
		record.setEndTime(endTime);
		record.setOperate(1);
		record.setIsReBorrow(0);
		bookService.addBorrowRecord(record);

		map.put("id", id);
		//添加借阅次数
		bookService.modifyBorrowTime(map);
		//修改可用数目
		bookService.modifyAvailNumSub(bookId);
		return new BaseResult(true, null, null);
	}

	/**
	 * 罚金标准展示和更改
	 * 
	 * @throws IOException
	 */
	@RequestMapping("/punish.do")
	@ResponseBody
	public BaseResult punish(String day, String miss, String avoidDay,
			HttpServletRequest request) throws IOException {
		// 此时是读取properties文件
		Properties prop = new Properties();
		InputStream input = this.getClass().getClassLoader()
				.getResourceAsStream("/pay.properties");
		prop.load(input);
		if (day == null || miss == null || avoidDay == null) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("day", prop.getProperty("day"));
			map.put("miss", prop.getProperty("miss"));
			map.put("isAllow", prop.getProperty("isAllow"));
			return new BaseResult(true, null, map);
		} else {
			prop.setProperty("day", day);
			prop.setProperty("miss", miss);
			prop.setProperty("isAllow", avoidDay);
			FileOutputStream fos = new FileOutputStream(this.getClass()
					.getResource("/pay.properties").getPath());
			prop.store(fos, null);
			return new BaseResult(true, null, null);
		}
	}

	/**
	 * 查询出所有图书的借阅信息
	 */
	@RequestMapping("/searchBookPlace.do")
	@ResponseBody
	public BaseResult searchBookPlace(String condition2, String condition1,
			String condition3, Integer isAll, Integer startPage) {
		Integer totalPage = null;
		Map<String, Object> mapResult = new HashMap<String, Object>();
		// 查找全部书籍去向
		if (isAll == 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			// 实现查询出全部书籍
			List<EasySearchResult> list = bookService.findBookPlace(map);
			totalPage = (int) Math.ceil((double) list.size() / PAGESIZE);
			// 然后进行分页查询
			map.put("isLimit", 1);
			map.put("startPage", (startPage - 1) * PAGESIZE);
			map.put("pageSize", PAGESIZE);
			list = bookService.findBookPlace(map);
			mapResult.put("totalPage", totalPage);
			mapResult.put("result", list);
		} else if (isAll == 1) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("condition1", condition1);
			map.put("condition2", condition2);
			map.put("condition3", condition3);
			List<EasySearchResult> list = bookService.findBookPlace(map);
			totalPage = (int) Math.ceil(list.size() * 1.0 / PAGESIZE);
			map.put("isLimit", 1);
			map.put("startPage", (startPage - 1) * PAGESIZE);
			map.put("pageSize", PAGESIZE);
			list = bookService.findBookPlace(map);
			mapResult = new HashMap<String, Object>();
			mapResult.put("totalPage", totalPage);
			mapResult.put("result", list);

		}
		return new BaseResult(true, null, mapResult);

	}

	// 根据传入的loadId,bookId实现书籍的挂失
	@RequestMapping("/sureMiss.do")
	@ResponseBody
	public BaseResult sureMiss(String loadId, String bookId, Integer type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loadId", loadId);
		map.put("bookId", bookId);
		map.put("operate", 1);
		// 查询书籍信息
		if (type == 1) {
			List<EasySearchResult> list = bookService.findAllBorrowRecord(map);
			if (list.size() == 0) {
				return new BaseResult(false, "您没有借阅次数", null);
			} else {
				return new BaseResult(true, null, list.get(0));
			}
		} else {
			// 挂失操作
			map.put("changeOperate", 3);
			int n = bookService.modifyOperate(map);
			//修改图书的可用数目
			int m=bookService.modifyAvailNumSub(bookId);
			IllegalRecord illegal=new IllegalRecord();
			illegal.setUserId(loadId);
			illegal.setBookId(bookId);
			illegal.setStartTime(new Date());
			illegal.setType(0);
			//增加违约记录
			int q=bookService.addillegalrecord(illegal);
			if (n > 0 && m>0 && q>0) {
				return new BaseResult(true, null, null);
			} else {
				return new BaseResult(false, null, null);
			}
		}
	}

	/**
	 * 查找当期读者的借阅信息(当前和历史)
	 */
	@RequestMapping("/findBorrow.do")
	@ResponseBody
	public BaseResult findBorrow(String loadId, String operate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loadId", loadId);
		map.put("operate", operate);
		List<EasySearchResult> list = bookService.findAllBorrowRecord(map);
		return new BaseResult(true, null, list);
	}

	/**
	 * 续借操作
	 * 
	 * @throws IOException
	 */
	@RequestMapping("/sureReOrder.do")
	@ResponseBody
	public BaseResult updateBorrowEndTime(String loadId, String bookId,
			Integer id, long date) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loadId", loadId);
		map.put("bookId", bookId);
		map.put("id", id);
		// 读取配置文件
		Properties prop = new Properties();
		InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream("/pay.properties");
		prop.load(in);
		int isOrder = Integer.parseInt(prop.getProperty("isOrder"));
		Date endTime = new Date(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(endTime);
		cal.add(Calendar.DAY_OF_YEAR, isOrder);
		map.put("endtime", cal.getTime());
		int n = bookService.updateBorrowTime(map);
		if (n > 0) {
			return new BaseResult(true, null, null);
		} else {
			return new BaseResult(false, null, null);
		}
	}

	/**
	 * 取消预约操作
	 */
	@RequestMapping("/cancleOrder.do")
	@ResponseBody
	public BaseResult cancleOrder(String loadId, String bookId, Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loadId", loadId);
		map.put("bookId", bookId);
		map.put("id", id);
		int n = bookService.deleteOrder(map);
		if (n > 0) {
			return new BaseResult(true, null, null);
		} else {
			return new BaseResult(false, null, null);
		}

	}

	/**
	 * 修改收藏次数
	 */
	@RequestMapping("/updateSaveTime.do")
	@ResponseBody
	public BaseResult updateSaveTimeOperate(String bookId,String userId) {
		//先判断该用户是否以及收藏过该数
		Map<String,String> map=new HashMap<String, String>();
		map.put("bookId", bookId);
		map.put("userId", userId);
		List<EasySearchResult> list=bookService.findSaveBook(map);
		if(list.size()>0){
			return new BaseResult(false,"您已收藏该书",null);
		}
		SaveBook save=new SaveBook();
		save.setBookId(bookId);
		save.setUserId(userId);
		save.setSaveTime(new Date());
		int m=bookService.addSaveBook(save);
		int n = bookService.updateSaveTime(bookId);
		
		//添加收藏记录
		
		if (n > 0 && m>0) {
			return new BaseResult(true, null, null);
		} else {
			return new BaseResult(false, "收藏失败", null);
		}
	}

	/**
	 * 添加修改评论
	 */
	@RequestMapping("/operateEvaluation.do")
	@ResponseBody
	public BaseResult operateEvaluation(String loadId, String bookId,
			Integer star, String description) {
		if (loadId == null) {
			return new BaseResult(false, "请先登录", null);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loadId", loadId);
		map.put("bookId", bookId);
		map.put("star", star);
		// 先判断表中是否有该用户的评论
		Evaluation eval = bookService.findEvaluation(map);
		if (eval == null) {
			// 进行插入操作
			Evaluation newEval = new Evaluation();
			newEval.setBookId(bookId);

			newEval.setUserId(loadId);
			newEval.setDescription(description);
			newEval.setStar(star);
			newEval.setSupport(null);
			newEval.setTime(new Date());
			newEval.setAgainst(null);
			int n = bookService.addEvaluation(newEval);
			if (n > 0) {
				return new BaseResult(true, "insert", null);
			} else {
				return new BaseResult(false, "添加评价失败", null);
			}
		} else {
			int n = bookService.updateEvallucation(map);
			if (n > 0) {
				return new BaseResult(true, "update", null);
			} else {
				return new BaseResult(false, "修改评价失败", null);
			}
		}
	}

	/**
	 * 预约操作，判断用户是否是已经借阅，预约，挂失，否则无法预约
	 */
	@RequestMapping("/sureOrder.do")
	@ResponseBody
	public BaseResult sureOrderOperate(String bookId, String userId) {
		// 先判断该用户是否已经预约
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bookId", bookId);
		map.put("userId", userId);
		map.put("operate", 0);
		/**
		 * 查找当前用户是否存在，或者是否是激活状态
		 */
		UserInfo user=userService.findUserInfoByLoadId(userId);
		
		if(user==null){
			return new  BaseResult(false,"用户不存在",null);
		} else if(user.getIsAvail()==1){
			return new  BaseResult(false,"用户已被冻结",null);
		}
		/**
		 * 判断当期预约书籍是否存在
		 */
		
		BorrowRecord borrow = bookService.checkIsOrder(map);
		if (borrow != null) {
			return new BaseResult(false, "您已预约此书，不需再次预约", null);
		}
		map.put("operate", 1);
		borrow = bookService.checkIsOrder(map);
		if (borrow != null) {
			return new BaseResult(false, "您已借阅此书", null);
		}
		map.put("operate", 3);
		borrow = bookService.checkIsOrder(map);
		if (borrow != null) {
			return new BaseResult(false, "您已挂失此书,请到图书馆缴纳相应费用", null);
		}
		
		
		// 添加预约信息;
		borrow = new BorrowRecord();
		borrow.setBookId(bookId);
		borrow.setUserId(userId);
		borrow.setOperate(0);
		borrow.setBeginTime(new Date());
		int n = bookService.addBorrowRecord(borrow);
		if (n > 0) {
			return new BaseResult(true, null, null);
		} else {
			return new BaseResult(false, "添加预约失败", null);
		}
	}

	/**
	 * 查找所有书籍评价
	 */
	@RequestMapping("/findAllCommit.do")
	@ResponseBody
	public BaseResult findAllCommitOperate(String bookId) {

		List<EasySearchResult> list = bookService.findAllCommits(bookId);
		return new BaseResult(true, null, list);

	}
	/**
	 * 评价
	 */
	@RequestMapping("/updateAllEval.do")
	@ResponseBody
	public BaseResult updateAllEval(String description,String userId,String bookId){
		Evaluation evals=new Evaluation();
		evals.setBookId(bookId);
		evals.setUserId(userId);
		evals.setDescription(description);
		evals.setTime(new Date());
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("loadId", userId);
		map.put("bookId", bookId);
		Evaluation eval=bookService.findEvaluation(map);
		//添加记录
		if(eval==null){
			int n=bookService.addEvaluation(evals);
			if(n>0){
				return new BaseResult(true, null, null);
			} else {
				return new BaseResult(false, null, null);
			}
		}
		int n=bookService.updateAllEval(evals);
		if(n>0){
			return new BaseResult(true,null, null);
		} else {
			return new BaseResult(false, null, null);
		}
	}
	
	/**
	 * 查找借阅数据
	 */
	@RequestMapping("/findBorrowRecordByTime.do")
	@ResponseBody
	public BaseResult findBorrowRecord(String bookId){
		Map<String,String> maps=new HashMap<String, String>();
		maps.put("bookId", bookId);
		List<EasySearchResult> list=bookService.findBorrowRecordByTime(maps);
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy");
		Integer end=Integer.parseInt(sdf.format(date));
		Integer start=end-10;
		//遍历集合,把日期补全
		Map<String,Integer> map=new HashMap<String, Integer>();
		for(EasySearchResult easy : list){
			for(int i=start;i<=end;i++){
				if(easy.getPublishTime().equals(i+"")){
					map.put(i+"", easy.getBorrowTimes());
				} else {
					map.put(i+"", 0);
				}
			}
			
		}
		
		return new BaseResult(true,null,map);
	}
	
	/**
	 * 查找出所有的一级菜单
	 */
	@RequestMapping("/findFirstBookClass.do")
	@ResponseBody
	public BaseResult findFirstBookClass(){
		return new BaseResult(true, null, bookService.findFirstBookClass());
	}
	/**
	 * 查找出热门借阅
	 */
	@RequestMapping("/findBookInfoOrderBorrowTimes.do")
	@ResponseBody
	public BaseResult findBookInfoOrderBorrowTimes(String firstClassId){
		List<EasySearchResult> list=new ArrayList<EasySearchResult>();
		if(firstClassId==null){
			 list=bookService.selectBookInfoOrderByBorrowTimes();
			
		} else {
			list=bookService.selectBookInfoByBookType(firstClassId);
		}
		return new BaseResult(true,null,list);
	}
	
	/**
	 * 查找出热门评价
	 */
	@RequestMapping("/findHotCommit.do")
	@ResponseBody
	public BaseResult findHotCommit(String firstClassId) {
		Map<String,String> map=new HashMap<String, String>();
		map.put("firstClassId", firstClassId);
		List<EasySearchResult> list=bookService.findHotCommits(map);
		return new BaseResult(true, null, list);
	}
	/**
	 * 查询出热门收藏
	 */
	@RequestMapping("/findHotSave.do")
	@ResponseBody
	public BaseResult findHotSave(String firstClassId){
		Map<String,String> map=new HashMap<String, String>();
		map.put("firstClassId",firstClassId);
		List<EasySearchResult> list=bookService.findHotSave(map);
		return new BaseResult(true, null, list);
	}
	/**
	 * 分类浏览树级菜单
	 */
	@RequestMapping("/findTreeView.do")
	@ResponseBody
	public BaseResult findTreeView(){
		Map<String,List> map=new HashMap<String, List>();
		map.put("firstClasses", bookService.findFirstBookClass());
		map.put("secondClasses",bookService.findSecondClass());
		map.put("thirdClasses", bookService.findThridClass());
		return new BaseResult(true,null,map);
		
	}
	/**
	 * 分类浏览,新书通报
	 * @throws IOException 
	 */
	@RequestMapping("/SortAndNewBook.do")
	@ResponseBody
	public BaseResult SortAndNewBook(String classId,Integer startPage) throws IOException{
		//读取配置文件,查询出分页的配置
		Properties prop=new Properties();
		InputStream input=this.getClass().getClassLoader().getResourceAsStream("/pay.properties");
		prop.load(input);
		Integer pagesize=Integer.parseInt(prop.getProperty("PAGESIZE"));
		//先查询出所有的此类的数目
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("classId", classId);
		List<EasySearchResult> list=bookService.selectBookInfoByClassIdOraddTime(map);
		Integer totalPage=list.size();
		//进行分页
		map.put("isLimit", true);
		map.put("startPage",(startPage-1)*pagesize);
		map.put("pageSize",pagesize);
		list=bookService.selectBookInfoByClassIdOraddTime(map);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("totalPage", Math.ceil(totalPage*1.0/pagesize));
		resultMap.put("result", list);
		return new BaseResult(true, null, resultMap);
	}
	
	/**
	 * 查找预约是否到书
	 * @throws IOException 
	 */
	@RequestMapping("/findOrderIsAvail.do")
	@ResponseBody
	public BaseResult findOrderIsAvail(String loadId,Integer startPage) throws IOException{
		//先读取配置文件，查找出pagesize
		Properties prop=new Properties();
		InputStream input=this.getClass().getClassLoader().getResourceAsStream("/pay.properties");
		prop.load(input);
		Integer PAGESIZE=Integer.parseInt(prop.getProperty("PAGESIZE"));
		//查找出所有的预约到书的记录
		Map<String,Object> map=new HashMap<String, Object>();
		List<EasySearchResult> list=bookService.findOrderIsAvail(map);
		int totalPage=(int)Math.ceil((list.size()*1.0/PAGESIZE));
		
		map.put("loadId", loadId);
		map.put("isLimit", true);
		map.put("startPage", (startPage-1)*PAGESIZE);
		map.put("pageSize", PAGESIZE);
		list=bookService.findOrderIsAvail(map);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("totalPage", totalPage);
		resultMap.put("result",list);
		return new BaseResult(true,null,resultMap);
	}
	
	/**
	 * 查询出以及菜单，二级菜单,三级菜单下面有多少书籍
	 */
	@RequestMapping("/findBookNumByClassId.do")
	@ResponseBody
	public BaseResult findBookNumByClassId(Integer sort,Integer  firstClassId){
		 Map<String,List> map=new HashMap<String, List>();
		
		
		 List<Map> resultList=new ArrayList<Map>();
		 if(sort==0){
			 List<BookType> list=bookService.findFirstBookClass();
			 for(BookType type : list) {
				 Map<String,Object> resultMap=new HashMap<String, Object>();
				 int num=bookService.findBookNumByClassId(type.getBookClassId());
				 resultMap.put("ClassId",type.getBookClassId());
				 resultMap.put("ClassName", type.getBookClass());
				 resultMap.put("BookNum", num);
				 resultList.add(resultMap);
			 }
		 }
		 if(sort==1){
			
			 List<secondBookType> secondClasses=bookService.findSecondBookClass(firstClassId);
			 for(secondBookType second : secondClasses){
				 Map<String,Object> resultMap=new HashMap<String, Object>();
			  int num=bookService.findBookNumByClassId(second.getBookClassId()+"|"+second.getSecondClassId());
			 resultMap.put("ClassId",second.getBookClassId());
			 resultMap.put("ClassName",second.getSecondClassName());
			 resultMap.put("BookNum", num);
			 resultList.add(resultMap);
			 }
		 }
		return new BaseResult(true,null,resultList);
		
	}
	
	/**
	 * 对收藏的图书的操作
	 */
	@Transactional
	@RequestMapping("/OperateSaveBook.do")
	@ResponseBody
	public BaseResult OperateSaveBook(Integer type,String loadId,String bookId){
		Map<String,String> map=new HashMap<String, String>();
		//查找收藏记录
		if(type==0){
			map.put("userId", loadId);
			List<EasySearchResult> list=bookService.findSaveBook(map);
			return new BaseResult(true, null, list);
		} else if(type==1){
			map.put("userId",loadId);
			map.put("bookId",bookId);
			int n=bookService.removeSaveBook(map);
			//修改收藏次数
			int m=bookService.reduceSaveTime(bookId);
			if(n>0 && m>0){
				return new  BaseResult(true,"取消收藏成功",null);
			} else {
				return new BaseResult(false, "取消收藏失败，请稍后重试", null);
			}
		}
		return null;
	}
	
	/**
	 * 查找当前用户的星星的等级
	 */
	@RequestMapping("/findOneStar.do")
	@ResponseBody
	public BaseResult findOneSatr(String loadId,String bookId){
		Map<String,String> map=new HashMap<String, String>();
		map.put("userid",loadId);
		map.put("bookid", bookId);
		int n=bookService.findOneStars(map);
		return new BaseResult(true,null,n);
	}
}

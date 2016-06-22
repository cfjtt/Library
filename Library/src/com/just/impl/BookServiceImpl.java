package com.just.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.just.bean.BookAllClassIdAndName;
import com.just.bean.BookClassInfo;
import com.just.bean.BookInfo;
import com.just.bean.BookType;
import com.just.bean.BorrowRecord;
import com.just.bean.EasySearchResult;
import com.just.bean.Evaluation;
import com.just.bean.IllegalRecord;
import com.just.bean.SaveBook;
import com.just.bean.SelectAllBookResult;
import com.just.bean.savePlace;
import com.just.bean.secondBookType;
import com.just.bean.thirdBookType;
import com.just.dao.BookMapperDao;
import com.just.service.BookService;

/**
 * 书籍服务实现类
 * 
 * @author Administrator
 * 
 */
@Component("BookSerivice")
public class BookServiceImpl implements BookService {
	@Resource
	private BookMapperDao bookMapperDao;

	/**
	 * 查找所有书籍分类
	 */
	public List<SelectAllBookResult> getAllBookClass() {
		return bookMapperDao.getAllBookClass();
	}

	/**
	 * 查找图书馆存放书籍的位置
	 */
	public List<savePlace> getAllSavePlace() {

		return bookMapperDao.getAllSavePlace();
	}

	public List<BookType> findFirstBookClass() {

		return bookMapperDao.findFirstBookClass();
	}

	public List<secondBookType> findSecondBookClass(Integer bookClassId) {

		return bookMapperDao.findSecondBookClass(bookClassId);
	}

	public List<thirdBookType> findThirdBookClass(Map<String, Integer> map) {

		return bookMapperDao.findThirdBookClass(map);
	}

	@Transactional
	public void addBook(BookInfo bookInfo) {
		bookMapperDao.addBook(bookInfo);
	}

	/**
	 * 简单搜索
	 */
	public List<EasySearchResult> easySearch(Map<String, Object> map) {

		List<EasySearchResult> list = bookMapperDao.easySearch(map);
		if (list.size() == 1) {
			if (null == list.get(0).getBookName()) {
				return null;
			}
		}
		for (EasySearchResult result : list) {
			if (result.getEvaluationNum() != 0) {
				result.setStar((result.getStar() == null ? 0 : result.getStar())
						/ result.getEvaluationNum());
			}

		}
		return list;
	}

	public List<BookClassInfo> findAllBookType() {

		return bookMapperDao.findAllBookType();
	}

	/**
	 * 删除图书
	 */
	@Transactional
	public void deleteBookInfo(Map<String, Object> map) {
		bookMapperDao.deleteBookInfo(map);

	}

	public int findAllBookInfoNum(Map<String, Object> map) {

		return bookMapperDao.findAllBookInfoNum(map);
	}

	public BookAllClassIdAndName findBookClassIdName(Map<String, Integer> map) {

		return bookMapperDao.findBookClassIdName(map);
	}

	@Transactional
	public int modifyBookInfo(BookInfo bookInfo) {
		int n = bookMapperDao.modifyBookInfo(bookInfo);
		return n;
	}

	/**
	 * 查找当前用户是否预约过这本书
	 */
	public BorrowRecord checkIsOrder(Map<String, Object> map) {
		return bookMapperDao.checkIsOrder(map);
	}

	@Override
	public List<EasySearchResult> findAllBorrowRecord(Map<String, Object> map) {
		return bookMapperDao.findAllBorrowRecord(map);
	}

	// 添加借阅信息
	public int addBorrowRecord(BorrowRecord record) {
		return bookMapperDao.addBorrowRecord(record);
	}

	// 修改bookinfo的borrowTimes字段
	public int modifyBorrowTime(Map<String, Object> map) {
		return bookMapperDao.modifyBorrowTime(map);
	}

	@Transactional
	public int modifyOperate(Map<String, Object> map) {

		return bookMapperDao.modifyOperate(map);
	}

	/**
	 * 查询出所有图书去向,借阅和为借阅的
	 */
	public List<EasySearchResult> findBookPlace(Map<String, Object> map) {

		return bookMapperDao.findBookPlace(map);
	}

	@Transactional
	public int updateBorrowTime(Map<String, Object> map) {
		return bookMapperDao.updateBorrowTime(map);
	}

	@Transactional
	public int deleteOrder(Map<String, Object> map) {
		return bookMapperDao.deleteOrder(map);
	}

	@Transactional
	public int updateSaveTime(String bookId) {
		// TODO Auto-generated method stub
		return bookMapperDao.updateSaveTime(bookId);
	}

	@Override
	public Evaluation findEvaluation(Map<String, Object> map) {

		return bookMapperDao.findEvaluation(map);
	}

	@Transactional
	public int addEvaluation(Evaluation eval) {

		return bookMapperDao.addEvaluation(eval);
	}

	@Transactional
	public int updateEvallucation(Map<String, Object> map) {

		return bookMapperDao.updateEvallucation(map);
	}

	public List<EasySearchResult> findAllCommits(String bookId) {
		return bookMapperDao.findAllCommits(bookId);
	}

	@Transactional
	public int updateAllEval(Evaluation eval) {
		return bookMapperDao.updateAllEval(eval);
	}

	@Transactional
	public int modifyAvailNumSub(String bookId) {
		return bookMapperDao.modifyAvailNumSub(bookId);
	}

	@Transactional
	public int modifyAvailNumAdd(String bookId) {
		return bookMapperDao.modifyAvailNumAdd(bookId);
	}

	/**
	 * 借阅趋势
	 */
	public List<EasySearchResult> findBorrowRecordByTime(Map<String, String> map) {

		return bookMapperDao.findBorrowRecordByTime(map);
	}

	/**
	 * 查询出热门借阅
	 */
	public List<EasySearchResult> selectBookInfoOrderByBorrowTimes() {

		List<EasySearchResult> list = bookMapperDao
				.selectBookInfoOrderByBorrowTimes();
		for (EasySearchResult easy : list) {
			if (easy.getEvaluationNum() != 0 && easy.getEvaluationNum() != null) {
				easy.setStar((easy.getStar() == null ? 0 : easy.getStar())
						/ easy.getEvaluationNum());
			}
		}
		return list;
	}

	/**
	 * 根据第一级菜单查询出所有符合条件的书籍
	 */
	public List<EasySearchResult> selectBookInfoByBookType(String firstClassId) {

		List<EasySearchResult> list = bookMapperDao
				.selectBookInfoByBookType(firstClassId);
		if (list.size() > 0) {
			for (EasySearchResult easy : list) {
				if (easy.getEvaluationNum() != 0
						&& easy.getEvaluationNum() != null) {
					easy.setStar((easy.getStar() == null ? 0 : easy.getStar())
							/ easy.getEvaluationNum());
				}
			}
		}
		return list;
	}

	public List<EasySearchResult> findHotCommits(Map<String, String> map) {

		List<EasySearchResult> list = bookMapperDao.findHotCommits(map);
		if (list.size() > 0) {
			for (EasySearchResult easy : list) {
				if (easy.getEvaluationNum() != 0
						&& easy.getEvaluationNum() != null) {
					easy.setStar((easy.getStar() == null ? 0 : easy.getStar())
							/ easy.getEvaluationNum());
				}
			}
		}
		return list;
	}

	public List<EasySearchResult> findHotSave(Map<String, String> map) {
		List<EasySearchResult> list = bookMapperDao.findHotSave(map);
		if (list.size() > 0) {
			for (EasySearchResult easy : list) {
				if (easy.getEvaluationNum() != 0
						&& easy.getEvaluationNum() != null) {
					easy.setStar((easy.getStar() == null ? 0 : easy.getStar())
							/ easy.getEvaluationNum());
				}
			}
		}
		return list;
	}

	@Override
	public List<secondBookType> findSecondClass() {
		// TODO Auto-generated method stub
		return bookMapperDao.findSecondClass();
	}

	@Override
	public List<thirdBookType> findThridClass() {
		return bookMapperDao.findThridClass();
	}

	@Override
	public List<EasySearchResult> selectBookInfoByClassIdOraddTime(
			Map<String, Object> map) {
		List<EasySearchResult> list = bookMapperDao
				.selectBookInfoByClassIdOraddTime(map);
		if (list.size() > 0) {
			for (EasySearchResult easy : list) {
				if (easy.getEvaluationNum() != 0
						&& easy.getEvaluationNum() != null) {
					easy.setStar((easy.getStar() == null ? 0 : easy.getStar())
							/ easy.getEvaluationNum());
				}
			}
		}
		return list;
	}

	@Transactional
	public int addillegalrecord(IllegalRecord illegal) {
		return bookMapperDao.addillegalrecord(illegal);
	}

	@Transactional
	public List<EasySearchResult> findOrderIsAvail(Map<String, Object> map) {
		List<EasySearchResult> list = bookMapperDao.findOrderIsAvail(map);
		if (list.size() > 0) {
			for (EasySearchResult easy : list) {
				if (easy.getEvaluationNum() != 0
						&& easy.getEvaluationNum() != null) {
					easy.setStar((easy.getStar() == null ? 0 : easy.getStar())
							/ easy.getEvaluationNum());
				}
			}
		}
		return list;
	}

	/**
	 * 查找菜单下面的书籍的数目
	 */
	public int findBookNumByClassId(String classId) {
		return bookMapperDao.findBookNumByClassId(classId);
	}

	/**
	 * 查找收藏信息
	 */
	public List<EasySearchResult> findSaveBook(Map<String, String> map) {
		return bookMapperDao.findSaveBook(map);
	}

	/**
	 * 添加收藏记录
	 */
	@Transactional
	public int addSaveBook(SaveBook save) {
		return bookMapperDao.addSaveBook(save);
	}
	
	/**
	 * 删除收藏记录
	 */
	@Transactional
	public int removeSaveBook(Map<String, String> map) {
		return bookMapperDao.removeSaveBook(map);
	}

	/**
	 * 减少收藏次数
	 */
	@Transactional
	public int reduceSaveTime(String bookId) {
		return bookMapperDao.reduceSaveTime(bookId);
	}

	/**
	 * 查找当前用户的星星等级
	 */
	public int findOneStars(Map<String, String> map) {
		
		return bookMapperDao.findOneStars(map);
	}
	
}

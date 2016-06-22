package com.just.dao;

import java.util.List;
import java.util.Map;

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

/**
 * 书籍有关的数据库映射
 * 
 * @author Administrator
 * 
 */
@Component("BookMapperDao")
public interface BookMapperDao {
	/**
	 * 获得所有书籍分类
	 * 
	 * @return
	 */
	public List<SelectAllBookResult> getAllBookClass();

	/**
	 * 查找图书馆存放书籍的位置
	 */
	public List<savePlace> getAllSavePlace();

	/**
	 * 查找第一层图书
	 */
	public List<BookType> findFirstBookClass();

	/**
	 * 查找第二层图书
	 */
	public List<secondBookType> findSecondBookClass(Integer bookClassId);

	/**
	 * 查找第三层图书
	 */
	public List<thirdBookType> findThirdBookClass(Map<String, Integer> map);

	/**
	 * 添加图书
	 */
	public void addBook(BookInfo bookInfo);

	/**
	 * 简单检索
	 */
	public List<EasySearchResult> easySearch(Map<String, Object> map);

	/**
	 * 查找所有图书类型
	 */
	public List<BookClassInfo> findAllBookType();

	/**
	 * 删除图书
	 */
	public void deleteBookInfo(Map<String, Object> map);

	/**
	 * 简单搜索查找图书所有数目
	 */
	public int findAllBookInfoNum(Map<String, Object> map);

	/**
	 * 检索菜单
	 */
	public BookAllClassIdAndName findBookClassIdName(Map<String, Integer> map);

	/**
	 * 更新
	 */
	public int modifyBookInfo(BookInfo bookInfo);

	/**
	 * 根据bookId，查找出书籍相关信息(预约数量)
	 * 
	 */
	public EasySearchResult findBookUserInfo(Map<String, Object> map);

	/**
	 * 查找当前用户关于这本数的借阅信息()
	 */
	public BorrowRecord checkIsOrder(Map<String, Object> map);

	/**
	 * 根据bookId,以及传入的参数查找图书的相关信息
	 */
	public List<EasySearchResult> findAllBorrowRecord(Map<String, Object> map);

	/**
	 * 添加借阅信息
	 */
	public int addBorrowRecord(BorrowRecord record);

	/**
	 * 修改bookInfo表中的borrowTimes字段
	 */
	public int modifyBorrowTime(Map<String, Object> map);

	/**
	 * 修改表中的borrowrecord的operate字段
	 */
	public int modifyOperate(Map<String, Object> map);

	/**
	 * 查找图书所有的借阅信息
	 */
	public List<EasySearchResult> findBookPlace(Map<String, Object> map);

	/**
	 * 修改borrowRecord中的endTime字段
	 */
	public int updateBorrowTime(Map<String, Object> map);

	/**
	 * 删除borrowRecord表中的预约信息记录
	 */
	public int deleteOrder(Map<String, Object> map);
	
	/**
	 * 添加收藏次数
	 */
	public int updateSaveTime(String bookId);
	/**
	 * 减少收藏次数
	 */
	public int reduceSaveTime(String bookId);
	/**
	 * 查找是否有对此书的评价
	 */
	public Evaluation findEvaluation(Map<String,Object> map);
	/**
	 * 添加评论
	 */
	public int addEvaluation(Evaluation eval);
	/**
	 * 修改评价
	 */
	public int updateEvallucation(Map<String,Object> map);
	/**
	 * 查找所有评论
	 */
	public List<EasySearchResult> findAllCommits(String bookId);
	/**
	 * 修改评论
	 */
	public int updateAllEval(Evaluation eval);
	/**
	 * 修改书籍的可用数目
	 */
	public int modifyAvailNumSub(String bookId);
	/**
	 * 修改书籍的可用数目
	 */
	public int modifyAvailNumAdd(String bookId);
	/**
	 * 查找出借阅趋势,根据时间排序
	 */
	public List<EasySearchResult> findBorrowRecordByTime(Map<String,String> map);
	/**
	 *查询热门借阅,所有书籍以borrowTimes排序
	 */
	public List<EasySearchResult> selectBookInfoOrderByBorrowTimes();
	/**
	 * 根据传入的第一级菜单，查询出所有的符合条件的书籍
	 */
	public List<EasySearchResult> selectBookInfoByBookType(String firstClassId);
	/**
	 * 查找热门评价
	 */
	public List<EasySearchResult> findHotCommits(Map<String,String> map);
	/**
	 * 查询出热门收藏
	 */
	public List<EasySearchResult> findHotSave(Map<String,String> map);
	/**
	 * 查找第二级菜单
	 */
	public List<secondBookType> findSecondClass();
	/**
	 * 查找第三级菜单
	 */
	public List<thirdBookType> findThridClass();
	/**
	 * 查询出图书信息,按菜单查询,分页
	 */
	public List<EasySearchResult> selectBookInfoByClassIdOraddTime(Map<String,Object> map);
	
	/**
	 * 添加违约记录
	 */
	@Transactional
	public int addillegalrecord(IllegalRecord illegal);
	/**
	 * 查找预约是否到书
	 */
	public List<EasySearchResult> findOrderIsAvail(Map<String,Object> map);
	/**
	 * 查找菜单下面有多少本数
	 */
	public int findBookNumByClassId(String classId);
	
	/**
	 * 查找收藏图书信息
	 */
	public List<EasySearchResult> findSaveBook(Map<String,String> map);
	/**
	 * 添加收藏记录
	 */
	public int addSaveBook(SaveBook save);
	/**
	 * 删除收藏记录
	 */
	public int removeSaveBook(Map<String,String> map);
	/**
	 * 查找当前用户的星星等级
	 */
	public int findOneStars(Map<String,String> map);
}

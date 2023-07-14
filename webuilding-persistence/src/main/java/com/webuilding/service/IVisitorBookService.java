package com.webuilding.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.webuilding.entity.VisitorBook;

import java.util.List;
import java.util.Map;

/**
 * 登记记录 服务层
 */
public interface IVisitorBookService
{
    /**
     * 查询预约登记记录
     *
     * @param book 查询条件
     * @return 登记记录集合
     */
    public List<VisitorBook> selectVisitorBookList(VisitorBook book);

    /**
     * 分页查询
     *
     * @param book 查询条件
     * @return 根据条件查询总数
     */
    public int selectCountVisitorBook(VisitorBook book);

    /**
     * 分页查询
     *
     * @param book 查询条件
     * @return 登记记录集合
     */
    public List<VisitorBook> selectVisitorBookPage(VisitorBook book,int currIndex,int pageSize);

    /**
     * 根据 记录id 查询登记记录
     *
     * @param visitorId 记录id
     * @return 登记记录集合
     */
    public VisitorBook selectVisitorBookByVisitorID(String visitorId);

    /**
     * 新增预约记录
     *
     * @param book 要新增的预约登记
     * @return 结果
     */
    public int insertVisitorBook(VisitorBook book);

    /**
     * 更新预约登记记录
     *
     * @param book 更新预约登记
     * @return 结果
     */
    public int updateVisitorBook(VisitorBook book);
}

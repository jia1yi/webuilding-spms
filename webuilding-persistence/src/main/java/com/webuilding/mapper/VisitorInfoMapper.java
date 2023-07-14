package com.webuilding.mapper;

import com.webuilding.entity.VisitorInfo;

import java.util.List;

/**
 * 预约客户信息 操作类型
 */
public interface VisitorInfoMapper {
    /**
     * 查询预约客户信息
     * 
     * @param info 查询条件
     * @return 登记记录集合
     */
    public List<VisitorInfo> selectVisitorInfoList(VisitorInfo info);

    /**
     * 新增客户信息
     * 
     * @param info 要新增的客户信息
     * @return 结果
     */
    public int insertVisitorInfo(VisitorInfo info);

    /**
     * 批量添加客户信息
     *
     * @param list 新增预约登记
     * @return 结果
     */
    public int insertBatchVisitorInfo(List<VisitorInfo> list);

    /**
     * 更新客户信息
     * 
     * @param info 更新客户信息
     * @return 结果
     */
    public int updateVisitorInfo(VisitorInfo info);
}
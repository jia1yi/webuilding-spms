package com.webuilding.service.impl;

import com.webuilding.entity.VisitorInfo;
import com.webuilding.mapper.VisitorInfoMapper;
import com.webuilding.service.IVisitorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 预约客户信息 服务层实现
 * 
 */
@Service
public class VisitorInfoServiceImpl implements IVisitorInfoService {

    @Autowired
    private VisitorInfoMapper visitorInfoMapper;

    /**
     * 查询预约客户信息
     *
     * @param info 查询条件
     * @return 登记记录集合
     */
    public List<VisitorInfo> selectVisitorInfoList(VisitorInfo info){
        return visitorInfoMapper.selectVisitorInfoList(info);
    }

    /**
     * 新增客户信息
     *
     * @param info 要新增的客户信息
     * @return 结果
     */
    public int insertVisitorInfo(VisitorInfo info){
        return visitorInfoMapper.insertVisitorInfo(info);
    }

    /**
     * 批量添加客户信息
     *
     * @param list 新增预约登记
     * @return 结果
     */
    public int insertBatchVisitorInfo(List<VisitorInfo> list){
        return visitorInfoMapper.insertBatchVisitorInfo(list);
    }

    /**
     * 更新客户信息
     *
     * @param info 更新客户信息
     * @return 结果
     */
    public int updateVisitorInfo(VisitorInfo info){
        return visitorInfoMapper.updateVisitorInfo(info);
    }
}

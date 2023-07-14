package com.webuilding.service.impl;

import java.util.List;

import com.webuilding.entity.OperationLog;
import com.webuilding.mapper.OperationLogMapper;
import com.webuilding.service.IOperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 操作日志 服务层实现
 * 
 */
@Service
public class OperationLogServiceImpl implements IOperationLogService
{
    @Autowired
    private OperationLogMapper operationLogMapper;

    /**
     * 查询操作日志列表
     *
     * @param log 操作日志
     * @return 参数操作日志集合
     */
    public List<OperationLog> selectLogList(OperationLog log){
        return operationLogMapper.selectLogList(log);
    }

    /**
     * 新增操作日志
     *
     * @param log 新增操作日志
     * @return 结果
     */
    public int insertOperationLog(OperationLog log){
        return operationLogMapper.insertOperationLog(log);
    }


    /**
     * 批量删除操作日志
     *
     * @param logIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteLogByIds(String[] logIds){
        return operationLogMapper.deleteLogByIds(logIds);
    }
}

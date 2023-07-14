package com.webuilding.mapper;

import com.webuilding.entity.OperationLog;

import java.util.List;

/**
 * 操作日志
 */
public interface OperationLogMapper {
    /**
     * 查询操作日志列表
     * 
     * @param log 操作日志
     * @return 参数操作日志集合
     */
    public List<OperationLog> selectLogList(OperationLog log);

    /**
     * 新增操作日志
     * 
     * @param log 新增操作日志
     * @return 结果
     */
    public int insertOperationLog(OperationLog log);


    /**
     * 批量删除操作日志
     * 
     * @param logIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteLogByIds(String[] logIds);
}
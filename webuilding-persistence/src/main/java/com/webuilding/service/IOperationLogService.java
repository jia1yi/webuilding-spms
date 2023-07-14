package com.webuilding.service;

import java.util.List;
import com.webuilding.entity.OperationLog;

/**
 * 操作日志 服务层
 */
public interface IOperationLogService
{
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

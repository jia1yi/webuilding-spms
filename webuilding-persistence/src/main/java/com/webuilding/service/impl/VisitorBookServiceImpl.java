package com.webuilding.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.webuilding.entity.VisitorBook;
import com.webuilding.mapper.VisitorBookMapper;
import com.webuilding.mapper.VisitorInfoMapper;
import com.webuilding.service.IVisitorBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.naming.ldap.HasControls;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 预约登记记录 服务层实现
 * 
 */
@Service
public class VisitorBookServiceImpl implements IVisitorBookService {

    @Autowired
    private VisitorBookMapper visitorBookMapper;

    @Autowired
    private VisitorInfoMapper visitorInfoMapper;

    /**
     * 查询预约登记记录
     *
     * @param book 查询条件
     * @return 登记记录集合
     */
    public List<VisitorBook> selectVisitorBookList(VisitorBook book){
        return visitorBookMapper.selectVisitorBookList(book);
    }

    /**
     * 根据 记录id 查询登记记录
     *
     * @param visitorId 记录id
     * @return 登记记录集合
     */
    public VisitorBook selectVisitorBookByVisitorID(String visitorId){
        return visitorBookMapper.selectVisitorBookByVisitorID(visitorId);
    }

    /**
     * 分页查询
     *
     * @param book 查询条件
     * @return 根据条件查询总数
     */
    public int selectCountVisitorBook(VisitorBook book){
        return visitorBookMapper.selectCountVisitorBook(book);
    }

    /**
     * 分页查询
     *
     * @param book 查询条件
     * @return 登记记录集合
     */
    public List<VisitorBook> selectVisitorBookPage(VisitorBook book,int currIndex,int pageSize){
        Map<String,Object> map = new HashMap<>();
        map.put("visitorTelephone",book.getVisitorTelephone());
        map.put("visitUserTelephone",book.getVisitUserTelephone());
        map.put("fromType",book.getFromType());
        map.put("visitType",book.getVisitType());
        map.put("approveType",book.getApproveType());
        map.put("approveStatus",book.getApproveStatus());
        map.put("visitStatus",book.getVisitStatus());
        map.put("visitorApprove",book.getVisitorApprove());
        map.put("currIndex",currIndex);
        map.put("pageSize",pageSize);
        return visitorBookMapper.selectVisitorBookPage(map);
    }

    /**
     * 新增预约记录
     *
     * @param book 要新增的预约登记
     * @return 结果
     */
    public int insertVisitorBook(VisitorBook book){
        if(book.getVisitorMsg() != null && book.getVisitorMsg().size() > 0){//有访客信息
            visitorInfoMapper.insertBatchVisitorInfo(book.getVisitorMsg());
        }
        return visitorBookMapper.insertVisitorBook(book);
    }

    /**
     * 更新预约登记记录
     *
     * @param book 更新预约登记
     * @return 结果
     */
    public int updateVisitorBook(VisitorBook book){
        return visitorBookMapper.updateVisitorBook(book);
    }
}

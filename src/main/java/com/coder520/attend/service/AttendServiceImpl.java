package com.coder520.attend.service;

import com.coder520.attend.dao.AttendMapper;
import com.coder520.attend.entity.Attend;
import com.coder520.attend.vo.QueryCondition;
import com.coder520.common.page.PageQueryBean;
import com.coder520.common.utils.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("attendServiceImpl")
public class AttendServiceImpl implements AttendService{

    /**
     * 中午十二点 判定上下午
     */
    private static final int NOON_HOUR = 12;
    private static final int NOON_MUNITE = 00;

    private Log log = LogFactory.getLog(AttendServiceImpl.class);

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Autowired
    private AttendMapper attendMapper;
    @Override
    public void signAttend(Attend attend) throws Exception {
        try {
            Date today = new Date();
            attend.setAttendDate(today);
            attend.setAttendWeek((byte)DateUtils.getTodayWeek());
            //查询当天 此人有没有打卡记录
            Attend todayRecord = attendMapper.selectTodaySignRecord(attend.getUserId());
            Date noon = DateUtils.getDate(NOON_HOUR,NOON_MUNITE);
            if (todayRecord==null){
                //打卡记录还不存在
                if (today.compareTo(noon)<=0){
                    //打卡时间 早于12点 上午打卡
                    attend.setAttendMorning(today);
                }else{
                    //晚上打卡
                    attend.setAttendEvening(today);
                }
                attendMapper.insertSelective(attend);
            }else{
                if (today.compareTo(noon)<=0){
                    //打卡时间 早于12点 上午打卡
                    return;
                }else{
                    //晚上打卡
                    todayRecord.setAttendEvening(today);
                    attendMapper.updateByPrimaryKeySelective(todayRecord);
                }
            }
        }catch (Exception e){
            log.error("用户签到异常",e);
            throw e;
        }
    }

    @Override
    public PageQueryBean listAttend(QueryCondition condition) {
        int count = attendMapper.countByCondition(condition);
        PageQueryBean pageResult = new PageQueryBean();
        if (count>0){
            pageResult.setTotalRows(count);
            pageResult.setCurrentPage(condition.getCurrentPage());
            pageResult.setPageSize(condition.getPageSize());
            List<Attend> attendList = attendMapper.selectAttendPage(condition);
            pageResult.setItems(attendList);
        }
        return pageResult;
    }
}

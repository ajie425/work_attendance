package com.coder520.attend.service;

import com.coder520.attend.dao.AttendMapper;
import com.coder520.attend.entity.Attend;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("attendServiceImpl")
public class AttendServiceImpl implements AttendService{

    private Log log = LogFactory.getLog(AttendServiceImpl.class);

    @Autowired
    private AttendMapper attendMapper;
    @Override
    public void signAttend(Attend attend) throws Exception {
        try {
            attendMapper.insertSelective(attend);
        }catch (Exception e){
            log.error("用户签到异常",e);
            throw e;
        }
    }
}

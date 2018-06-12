package com.coder520.attend.controller;

import com.coder520.attend.entity.Attend;
import com.coder520.attend.service.AttendService;
import com.coder520.attend.vo.QueryCondition;
import com.coder520.common.page.PageQueryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("attend")
public class AttendController {

    @Autowired
    private AttendService attendService;

    @RequestMapping
    public String toAttend(){
        return "attend";
    }

    @RequestMapping("/sign")
    @ResponseBody
    public String signAttend(@RequestBody Attend attend) throws Exception {

        attendService.signAttend(attend);

        return "succ";
    }

    /**
     *@Author Ajie [1134846386@qq.com]
     *@Date 2018/6/12 0012 16:04
     *@Description 考勤数据分页查询
     */
    @RequestMapping("/signList")
    @ResponseBody
    public PageQueryBean listAttend(QueryCondition condition){

        PageQueryBean result = attendService.listAttend(condition);
        return result;
    }
}

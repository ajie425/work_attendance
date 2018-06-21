package com.coder520.workflow.controller;

import com.coder520.workflow.entity.ReAttend;
import com.coder520.workflow.service.ReAttendService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("reAttend")
public class ReAttendController {

    @Autowired
    private ReAttendService reAttendService;

    @RequestMapping("/start")
    public void startReAttendFlow(@RequestBody ReAttend reAttend, HttpSession session){
        reAttend.setReAttendStarter("ajie666");
        reAttendService.startReAttendFlow(reAttend);
    }

    @RequestMapping("/list")
    public List<ReAttend> listReAttendFlow(){
        String userName = "ajie666";
        List<ReAttend> tasks = reAttendService.listTasks(userName);
        return tasks;
    }

    @RequestMapping("/approve")
    public void approveReAttendFlow(@RequestBody ReAttend reAttend){
        reAttendService.approve(reAttend);
    }
}

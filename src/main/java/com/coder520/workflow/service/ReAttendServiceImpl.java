package com.coder520.workflow.service;

import com.coder520.workflow.dao.ReAttendMapper;
import com.coder520.workflow.entity.ReAttend;
import org.activiti.engine.task.Task;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service("reAttendServiceImpl")
public class ReAttendServiceImpl implements ReAttendService{

    /**
     *补签流程状态
     */
    private static final Byte RE_ATTEND_STATUS_ONGOING = 1;
    private static final Byte RE_ATTEND_STATUS_PASS = 2;
    private static final Byte RE_ATTEND_STATUS_REFUSE = 3;

    /**
     * 流程下一步处理人
     */
    private  static final String NEXT_HANDLER = "next_handler";
    /**
     * 任务关联补签数据键
     */
    private static final String RE_ATTEND_SIGN = "re_attend";

    private static final String RE_ATTEND_FLOW_ID = "re_attend";

    @Autowired
    private ReAttendMapper reAttendMapper;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Override
    public void startReAttendFlow(ReAttend reAttend) {
        //从公司组织架构中 查询此人上级领导用户名
        reAttend.setCurrentHander("ajie");
        reAttend.setStatus(RE_ATTEND_STATUS_ONGOING);
        //插入数据库补签表
        reAttendMapper.insertSelective(reAttend);
        Map<String,Object> map = new HashMap<>();
        map.put(RE_ATTEND_SIGN,reAttend);
        map.put(NEXT_HANDLER,reAttend.getCurrentHander());
        //启动补签流程实例
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(RE_ATTEND_FLOW_ID,map);
        //提交用户补签任务
        Task task = taskService.createTaskQuery().processInstanceId(instance.getId()).singleResult();
        taskService.complete(task.getId(),map);
    }

    @Override
    public List<ReAttend> listTasks(String username) {

        List<ReAttend> reAttendList = new ArrayList<>();
        List<Task> taskList = taskService.createTaskQuery().processVariableValueEquals(username).list();
        if (CollectionUtils.isNotEmpty(taskList)){
            for(Task task : taskList){
                Map<String,Object> variable = taskService.getVariables(task.getId());
                ReAttend reAttend = (ReAttend)variable.get(RE_ATTEND_SIGN);
                reAttend.setTaskId(task.getId());
                reAttendList.add(reAttend);
            }
        }
        return reAttendList;
    }

    @Override
    public void approve(ReAttend reattend) {
        Task task = taskService.createTaskQuery().taskId(reattend.getTaskId()).singleResult();
        if(RE_ATTEND_STATUS_PASS.toString().equals(reattend.getApproveFlag())){
            //审批通过 修改补签数据状态
            reattend.setStatus(RE_ATTEND_STATUS_PASS);
            reAttendMapper.updateByPrimaryKeySelective(reattend);
        }else if (RE_ATTEND_STATUS_REFUSE.toString().equals(reattend.getApproveFlag())){
            reattend.setStatus(RE_ATTEND_STATUS_REFUSE);
            reAttendMapper.updateByPrimaryKeySelective(reattend);
        }
        taskService.complete(reattend.getTaskId());
    }
}

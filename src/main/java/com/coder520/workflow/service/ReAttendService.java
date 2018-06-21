package com.coder520.workflow.service;

import com.coder520.workflow.entity.ReAttend;

import java.util.List;

public interface ReAttendService {
    void startReAttendFlow(ReAttend reAttend);

    List<ReAttend> listTasks(String username);

    void approve(ReAttend attend);
}

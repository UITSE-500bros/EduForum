package com.example.eduforum.activity.repository.community;

import java.util.List;
import java.util.Map;

public interface IGetMemberApprovalState {
    void onGetMemberApprovalStateSuccess(Map<String, Boolean> memberApprovalState);
    void onGetMemberApprovalStateFailure(String message);
}

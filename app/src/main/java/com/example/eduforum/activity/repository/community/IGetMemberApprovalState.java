package com.example.eduforum.activity.repository.community;

import java.util.List;

public interface IGetMemberApprovalState {
    void onGetMemberApprovalStateSuccess(List<String> memberApprovalState);
    void onGetMemberApprovalStateFailure(String message);
}

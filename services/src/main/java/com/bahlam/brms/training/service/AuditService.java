package com.bahlam.brms.training.service;

import com.bahlam.brms.training.model.SuspiciousOperation;

public interface AuditService {
    public void notifySuspiciousOperation(SuspiciousOperation operation);
}

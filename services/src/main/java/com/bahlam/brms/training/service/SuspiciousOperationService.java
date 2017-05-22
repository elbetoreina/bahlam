package com.bahlam.brms.training.service;

import java.util.Collection;
import com.bahlam.brms.training.model.SuspiciousOperation;


public interface SuspiciousOperationService {
    public Collection<SuspiciousOperation> getSuspiciousOperationsByCustomer(Long customerId);
}
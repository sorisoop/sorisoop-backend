package com.futurenet.sorisoopbackend.paymentHistory.domain;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentHistoryRepository {
    int insertPaymentHistory(PaymentHistory history);
}

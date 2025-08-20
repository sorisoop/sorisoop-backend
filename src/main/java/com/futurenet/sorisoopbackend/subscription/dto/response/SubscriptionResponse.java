package com.futurenet.sorisoopbackend.subscription.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionResponse {
    private String status;
    private String type;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime startedAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime nextBillingAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime cancelledAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime lastApprovedAt;
}

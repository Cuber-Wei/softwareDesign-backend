package com.l0v3ch4n.oj.model.dto.audit;

import com.l0v3ch4n.oj.model.enums.AuditTypeEnum;
import com.l0v3ch4n.oj.model.enums.ReviewStatusEnum;
import lombok.Data;

@Data
public class AuditRequest {
    private Long id;
    private AuditTypeEnum type;
    private ReviewStatusEnum operation;
}

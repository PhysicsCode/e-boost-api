package org.physicscode.dto.pojo.output.membership;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MembershipPlanDTO {

    private String membershipId;
    private String description;
    private BigDecimal price;

}

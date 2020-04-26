package org.physicscode.dto.pojo.output.membership;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MembershipPlanHolderDTO {

    private List<MembershipPlanDTO> activePlans = new ArrayList<>();
    private List<MembershipPlanDTO> inactivePlans = new ArrayList<>();
}

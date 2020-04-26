package org.physicscode.domain.entity.membership;

import lombok.Data;
import org.bson.types.ObjectId;

import java.math.BigDecimal;

@Data
public class MembershipEntity {

    //private ObjectId objectId;
    private String membershipId;
    private String description;
    private BigDecimal price;


}

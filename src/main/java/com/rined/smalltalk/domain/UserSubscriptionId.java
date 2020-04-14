package com.rined.smalltalk.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.rined.smalltalk.dto.Views;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class UserSubscriptionId implements Serializable {

    @JsonView(Views.Id.class)
    private String channelId;

    @JsonView(Views.Id.class)
    private String subscriberId;

}

package org.mattpayne.simple.model;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MessageDTO {

    private Long id;

    @NotNull
    private String body;

}

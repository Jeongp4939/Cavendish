package com.windows33.cavendish.domain.board.dto.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardModifyFormComponentDto {

    private String nickname;
    private String title;
    private String contents;
    private Integer quotationId;
    private String createDateTime;
    private Integer view;
    private Integer like;

}

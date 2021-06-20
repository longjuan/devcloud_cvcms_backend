package com.cvc.cvcms.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author ZZJ
 * @date 2021/3/30 12:52
 * @desc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class SalesRecordToItem {
    private Integer salesRecordId;
    private Integer salesItemId;
}

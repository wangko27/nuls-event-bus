package io.nuls.eventbus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by wangkun23 on 2018/11/15.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Greeting {
    private String content;
}
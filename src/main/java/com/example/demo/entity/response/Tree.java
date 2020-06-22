package com.example.demo.entity.response;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by more-time on 2019/6/28.
 */

@Data
@ToString
public class Tree {
    private String title;
    private String id;
    boolean checked;
    boolean spread;
    private List<Tree>  children;
}

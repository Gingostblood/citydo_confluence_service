package cn.gingost.system.dto.resp;

import lombok.Data;

import java.util.List;

/**
 * @author:lezzy
 * @Date:2020/7/30 11:25
 */

@Data
public class Tree {
    private Long id;
    protected Long pid;
    private String name;
    private Boolean type;
    private List<Tree> children;
}

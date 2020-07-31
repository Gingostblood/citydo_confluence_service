package cn.gingost.base;

import cn.gingost.annotation.Query;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * @author:lezzy
 * @Date:2020/7/27 11:59
 */

@Data
public class BaseQuery implements Serializable {

    @Query
    private Long id;

    @Query(type = Query.Type.IN,propName = "id")
    private Collection<Long> ids;

    @Query(type = Query.Type.BETWEEN)
    private Collection<Timestamp> createTime;

    @Query(type = Query.Type.BETWEEN)
    private Collection<Timestamp> updateTime;

}

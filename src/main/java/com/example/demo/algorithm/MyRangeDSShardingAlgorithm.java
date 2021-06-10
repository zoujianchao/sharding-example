package com.example.demo.algorithm;

import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author : zoujc
 * @date : 2021/6/10
 * @description : 自定义分片策略
 */
public class MyRangeDSShardingAlgorithm implements RangeShardingAlgorithm<Long> {

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Long> rangeShardingValue) {
        //select * from course where cid between 200 and 300
        Long upperValue = rangeShardingValue.getValueRange().upperEndpoint();//300
        Long lowerValue = rangeShardingValue.getValueRange().lowerEndpoint();//200
        String logicTableName = rangeShardingValue.getLogicTableName();
        //m$->{cid%2+1} m1, m2
        return Arrays.asList("m1", "m2");
    }
}

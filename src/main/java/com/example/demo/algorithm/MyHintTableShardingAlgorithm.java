package com.example.demo.algorithm;

import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author : zoujc
 * @date : 2021/6/10
 * @description :
 */
public class MyHintTableShardingAlgorithm implements HintShardingAlgorithm<Long> {
    //select * from course
    @Override
    public Collection<String> doSharding(Collection<String> collection, HintShardingValue<Long> hintShardingValue) {
        String key = hintShardingValue.getLogicTableName() + "_" + hintShardingValue.getValues().toArray()[0];
        if (collection.contains(key)) {
            return Arrays.asList(key);
        }
        throw new UnsupportedOperationException("route " + key + " is not supported,please check your config");
    }
}

package com.example.demo.algorithm;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.math.BigInteger;
import java.util.Collection;

/**
 * @author : zoujc
 * @date : 2021/6/10
 * @description :
 */
public class MyPreciseTableShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        String logicTableName = preciseShardingValue.getLogicTableName();
        String cid = preciseShardingValue.getColumnName();
        Long cidValue = preciseShardingValue.getValue();
        //实现 course_${cid%2+1}
        BigInteger shardingValueB = BigInteger.valueOf(cidValue);
        BigInteger resB = shardingValueB.mod(new BigInteger("2")).add(new BigInteger("1"));
        String key = logicTableName + "_" + resB;
        if (collection.contains(key)) {
            return key;
        }
        throw new UnsupportedOperationException("route " + key + " is not supported, please check your config");
    }
}

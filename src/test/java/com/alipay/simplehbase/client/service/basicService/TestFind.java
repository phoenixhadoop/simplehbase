package com.alipay.simplehbase.client.service.basicService;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.alipay.simplehbase.client.RowKey;
import com.alipay.simplehbase.myrecord.MyRecord;
import com.alipay.simplehbase.myrecord.MyRecordRowKey;
import com.alipay.simplehbase.myrecord.MyRecordTestBase;

/**
 * @author xinzhi
 */
public class TestFind extends MyRecordTestBase {

    @Test
    public void findObject() {

        MyRecord myRecord = mockSlim(0);
        RowKey rowKey = myRecord.rowKey();

        simpleHbaseClient.putObject(rowKey, myRecord);
        MyRecord resultRecord = simpleHbaseClient.findObject(rowKey,
                MyRecord.class);

        Assert.assertTrue(myRecord.equals(resultRecord));
    }

    @Test
    public void findObjectByHql() {

        putSlim("id=0,name=aaa");

        MyRecord myRecord = simpleHbaseClient.findObject(new MyRecordRowKey(0),
                MyRecord.class);

        Assert.assertEquals(0, myRecord.getId());
        Assert.assertEquals("aaa", myRecord.getName());

        addHql("select where name equal \"aaa\"");
        myRecord = simpleHbaseClient.findObject(new MyRecordRowKey(0),
                MyRecord.class, TestHqlId, null);

        Assert.assertEquals(0, myRecord.getId());
        Assert.assertEquals("aaa", myRecord.getName());

        addHql("select where name equal #name#");
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("name", "aaa");
        myRecord = simpleHbaseClient.findObject(new MyRecordRowKey(0),
                MyRecord.class, TestHqlId, para);
        Assert.assertEquals(0, myRecord.getId());
        Assert.assertEquals("aaa", myRecord.getName());
    }

}

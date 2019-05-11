package com.zappos.backoffice.util;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DateTimeUtilsTests {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void formatIsoUtcDateTime() {
        String isoString = DateTimeUtils.formatIsoUtcDateTime(new Date());
        Assert.assertTrue(StringUtils.contains(isoString, "Z"));
    }

    @Test
    public void parseIsoUtcDateTimeString() throws ParseException {
        Date date = DateTimeUtils.parseIsoUtcString("2018-10-02T07:14:00.0000000Z");
        Assert.assertNotNull(date);
    }
}

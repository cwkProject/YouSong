package com.yousong.yousong;

import android.support.test.runner.AndroidJUnit4;
import android.util.Base64;
import android.util.Log;

import org.cwk.android.library.architecture.preferences.SymmetricCipherUtil;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.security.Key;

/**
 * 测试des加密
 *
 * @author 超悟空
 * @version 1.0 2017/3/2
 * @since 1.0 2017/3/2
 **/
@RunWith(AndroidJUnit4.class)
public class TestDesCipher {

    @Test
    public void createKey() throws Exception {

        Key key = SymmetricCipherUtil.createKey("DES" , 56);
        String base64Key = Base64.encodeToString(key.getEncoded() , Base64.DEFAULT);

        Log.v("TestDesCipher" , "key:" + base64Key);
    }
}

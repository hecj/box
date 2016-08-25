package com.boxamazing.webfront.upload;

import static junit.framework.TestCase.assertEquals;

import org.junit.Test;

import com.boxamazing.service.common.PasswordUtil;
import com.boxamazing.webfront.controller.upload.UploadHelper;

/**
 * Created by jhl on 15/8/20. 
 */
public class UploadHelperTest {

    @Test
    public void getRandomPicName() {
        System.out.println(UploadHelper.genRandomPicName("admin"));
    }

    @Test
    public void genRandomName() {
        System.out.println(UploadHelper.genRandomName("admin", ".zip"));
    }

    @Test
    public void getSuffix() {
        String test = "asdjfasodijfasodjfasdf.jpg";
        String result = ".jpg";
        assertEquals(UploadHelper.getSuffix(test), result);

        test = "asd.jfas.odijfas.odjfasdf.jpg";
        result = ".jpg";
        assertEquals(UploadHelper.getSuffix(test), result);


        test = ".asdjfasodijfasodjfasdf.jpg";
        result = ".jpg";
        assertEquals(UploadHelper.getSuffix(test), result);

        test = ".jpg";
        result = ".jpg";
        assertEquals(UploadHelper.getSuffix(test), result);

        test = "/root/admin/file.jpg";
        result = ".jpg";
        assertEquals(UploadHelper.getSuffix(test), result);
    }
    
    @Test
    public void test01(){
    	System.out.println(PasswordUtil.encryptPassword("123123"));
    }
}

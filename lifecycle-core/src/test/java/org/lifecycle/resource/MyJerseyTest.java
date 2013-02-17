package org.lifecycle.resource;

import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.test.framework.JerseyTest;
import org.junit.Ignore;
import org.junit.Test;

public class MyJerseyTest extends JerseyTest{

    public MyJerseyTest() throws Exception{
        super("org.lifecycle.resource");
    }

    @Test
    @Ignore
    public void test_tmp(){
        WebResource webResource = resource();
        String s = webResource.path("/ticket").get(String.class);
        System.out.print(s);
    }
}

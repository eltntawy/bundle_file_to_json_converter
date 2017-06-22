package com.bundle.converter;

//import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
        import java.io.File;
import java.io.FileInputStream;
        import java.util.Properties;

/**
 * Created by mohamedrefaat on 6/22/17.
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class TestJacksonMapper {

    public static void main(String args[]) {

        try {

            //JavaPropsMapper mapper = new JavaPropsMapper();
            // and then read/write data as usual
            ClassLoader classLoader = TestJacksonMapper.class.getClassLoader();
            File file = new File(classLoader.getResource("property2.properties").toURI());

            Properties prop = new Properties();
            prop.load(new FileInputStream(file));


            //Object obj = mapper.readValue(prop,Object.class );
            //String asText = mapper.writeValueAsString(obj);



        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

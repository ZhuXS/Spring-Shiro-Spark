package com.zhuxs.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Array;
import java.net.URI;


/**
 * Created by shusesshou on 2017/9/11.
 */
public class ApplicationUtil {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationUtil.class);

    public static HttpHeaders getHttpHeaders(UriComponentsBuilder uriComponentsBuilder,String uri,Object... uriVariableValues){
        UriComponents uriComponents = uriComponentsBuilder.path(uri).buildAndExpand(uriVariableValues);
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.setLocation(new URI(uriComponents.getPath()));
        }catch (Exception e){
            logger.error(e.getStackTrace().toString());
        }
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}

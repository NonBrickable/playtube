package com.playtube.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

//消息转换器配置
@Configuration
public class JsonHttpMessageConverterConfig {

    @Bean
    @Primary
    public HttpMessageConverters fastJsonHttpMessageConverts(){
      FastJsonHttpMessageConverter fastJsonHttpMessageConverter=new FastJsonHttpMessageConverter();
      FastJsonConfig fastJsonConfig=new FastJsonConfig();
      fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");//设置时间通用格式
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteNullStringAsEmpty,//null字段返回为空的字符串
                SerializerFeature.WriteNullListAsEmpty,//空list返回为空的字符串
                SerializerFeature.WriteMapNullValue,//空map返回为空的字符串
                SerializerFeature.MapSortField,//对key进行升序排序
                SerializerFeature.DisableCircularReferenceDetect//禁用循环引用
        );
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        return new HttpMessageConverters(fastJsonHttpMessageConverter);
    }
}

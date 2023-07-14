package com.webuilding.config;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.support.config.FastJsonConfig;
import com.alibaba.fastjson2.support.spring.http.converter.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 */
@Configuration
public class HttpMessageConverterConfig implements WebMvcConfigurer {

    //引入Fastjson解析json，不使用默认的jackson
    //必须在pom.xml引入fastjson的jar包，并且版必须大于1.2.10
    /*@Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {

        //1、定义一个convert转换消息的对象
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

        //2、添加fastjson的配置信息
        FastJsonConfig fastJsonConfig = new FastJsonConfig();

        SerializerFeature[] serializerFeatures = new SerializerFeature[]{
                //    输出key是包含双引号
//                SerializerFeature.QuoteFieldNames,
                //    是否输出为null的字段,若为null 则显示该字段
                SerializerFeature.WriteMapNullValue,
                //    数值字段如果为null，则输出为0
                SerializerFeature.WriteNullNumberAsZero,
                //     List字段如果为null,输出为[],而非null
                SerializerFeature.WriteNullListAsEmpty,
                //    字符类型字段如果为null,输出为"",而非null
//                SerializerFeature.WriteNullStringAsEmpty,
                //    Boolean字段如果为null,输出为false,而非null
                SerializerFeature.WriteNullBooleanAsFalse,
                //    Date的日期转换器
                SerializerFeature.WriteDateUseDateFormat,
                //    循环引用
                SerializerFeature.DisableCircularReferenceDetect,
        };

        fastJsonConfig.setSerializerFeatures(serializerFeatures);
        fastJsonConfig.setCharset(Charset.forName("UTF-8"));

        //3、在convert中添加配置信息
        fastConverter.setFastJsonConfig(fastJsonConfig);

        //4、将convert添加到converters中
        HttpMessageConverter<?> converter = fastConverter;

        return new HttpMessageConverters(converter);
    }*/
    /**
     * 配置fastjson输出格式
     **/
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 1. 配置fastjson
        FastJsonConfig config = new FastJsonConfig();

        config.setDateFormat("yyyy-MM-dd HH:mm:ss");
        config.setCharset(StandardCharsets.UTF_8);
        config.setWriterFeatures(
                JSONWriter.Feature.WriteNullListAsEmpty,
                //json格式化
                JSONWriter.Feature.PrettyFormat,
                //输出map中value为null的数据
                JSONWriter.Feature.WriteMapNullValue,
                //输出boolean 为 false
                JSONWriter.Feature.WriteNullBooleanAsFalse,
                //输出list 为 []
                JSONWriter.Feature.WriteNullListAsEmpty,
                //输出number 为 0
                JSONWriter.Feature.WriteNullNumberAsZero,
                //输出字符串 为 ""
                JSONWriter.Feature.WriteNullStringAsEmpty,
                //对map进行排序
                JSONWriter.Feature.MapSortField
        );

        // 2. 添加fastjson转换器
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        List<MediaType> supportedMediaTypes = new ArrayList<>();

        // 3. 添加支持的媒体类型
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
        supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
        supportedMediaTypes.add(MediaType.APPLICATION_PDF);
        supportedMediaTypes.add(MediaType.APPLICATION_RSS_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XML);
        supportedMediaTypes.add(MediaType.IMAGE_GIF);
        supportedMediaTypes.add(MediaType.IMAGE_JPEG);
        supportedMediaTypes.add(MediaType.IMAGE_PNG);
        supportedMediaTypes.add(MediaType.TEXT_EVENT_STREAM);
        supportedMediaTypes.add(MediaType.TEXT_HTML);
        supportedMediaTypes.add(MediaType.TEXT_MARKDOWN);
        supportedMediaTypes.add(MediaType.TEXT_PLAIN);
        supportedMediaTypes.add(MediaType.TEXT_XML);

        converter.setSupportedMediaTypes(supportedMediaTypes);

        //4 将convert添加到converters
        converter.setFastJsonConfig(config);
        converters.add(0,converter);
    }
}

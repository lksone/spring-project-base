package com.lks.common.dataDesensitization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.lks.common.dataDesensitization.pojo.Person;

import java.io.IOException;

/**
 * @author lks
 * @description todo
 * @e-mail 1056224715@qq.com
 * @date 2023/11/12 21:57
 */
public class MobileSerializer extends JsonSerializer<String> {

    /**
     * 自定义序列化方法，用于对手机号进行脱敏处理并写入JSON生成器。
     *
     * @param mobile             要序列化的手机号
     * @param jsonGenerator      JSON生成器，用于写入序列化后的数据
     * @param serializerProvider 序列化提供者，提供序列化所需的服务
     * @throws IOException 如果在序列化过程中发生I/O错误
     */
    @Override
    public void serialize(String mobile, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            // 检查手机号是否为null或者是否是有效的手机号
        if (mobile == null || !isValidMobile(mobile)) {
            // 如果手机号无效，直接输出原始手机号
            jsonGenerator.writeString(mobile);
            return;
        }
        // 调用脱敏工具类对手机号进行脱敏处理
        String mobileDesensitized = DesensitizedUtil.mobilePhone(mobile);
        // 将脱敏后的手机号字符串写入到JSON生成器jsonGenerator中
        jsonGenerator.writeString(mobileDesensitized);
    }

    /**
     * 检查手机号的合法性
     *
     * @param mobile 手机号
     * @return 如果手机号合法则返回true，否则返回false
     */
    private boolean isValidMobile(String mobile) {
        // 在此处添加对手机号合法性的检查逻辑
        return true;
    }
}

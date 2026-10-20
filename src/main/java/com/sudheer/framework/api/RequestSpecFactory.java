package com.sudheer.framework.api;

import com.sudheer.framework.config.ConfigReader;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class RequestSpecFactory {

    private RequestSpecFactory() { }

    public static RequestSpecification base() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigReader.get("apiBaseUrl"))
                .setConfig(RestAssuredConfig.config().encoderConfig(
                        EncoderConfig.encoderConfig()
                                .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .setContentType(ContentType.JSON)
                .addHeader("Accept", "application/json")
                .addFilter(new AllureRestAssured())
                .build();
    }
}
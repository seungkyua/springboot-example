package com.ask.example.controller.resolver;

import com.ask.example.controller.ClientInfo;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class ClientInfoArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String HEADER_CHANNEL = "X-APP-CHANNEL";
    private static final String HEADER_CLIENT_IP = "X-FORWARD-FOR";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return ClientInfo.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(@Nullable MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        String channel = webRequest.getHeader(HEADER_CHANNEL);
        String clientAddress = webRequest.getHeader(HEADER_CLIENT_IP);
        return new ClientInfo(channel, clientAddress);
    }
}

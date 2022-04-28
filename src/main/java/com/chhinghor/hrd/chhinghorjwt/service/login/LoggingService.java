package com.chhinghor.hrd.chhinghorjwt.service.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
class LoggingService implements ILoggingService {

    private ObjectMapper objectMapper;

    @Autowired
    void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void logRequest(HttpServletRequest httpServletRequest, Object body) {

        StringBuilder sb = new StringBuilder();
        Map<String, String> parameters = buildParametersMap(httpServletRequest);

        sb.append("\n[Request]");
        sb.append("\n[Url] [").append(httpServletRequest.getMethod());
        sb.append(" ").append(httpServletRequest.getRequestURI()).append("] ");
        sb.append("\n[Header] [").append(buildHeadersMap(httpServletRequest)).append("] ");

        if (!parameters.isEmpty()) {
            sb.append("\n[Parameter] [").append(parameters).append("] ");
        }

        if (body != null) {
            sb.append("\n[Body] [");

            try {
                sb.append(objectMapper.writeValueAsString(body));
            } catch (JsonProcessingException e) {
                sb.append(body);
            }

            sb.append("]");
        }

        log.info(sb.toString());
        log.info(sb.toString());
    }

    @Override
    public void logResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object body) {
        StringBuilder sb = new StringBuilder();

        sb.append("\n[Response]");
        sb.append("\n[Url] [").append(httpServletRequest.getMethod());
        sb.append(" ").append(httpServletRequest.getRequestURI()).append("] ");
        sb.append("\n[Header] [").append(buildHeadersMap(httpServletResponse)).append("] ");

        if (body != null)
            sb.append("\n[Body] [");

        try {
            sb.append(objectMapper.writeValueAsString(body));
        } catch (JsonProcessingException e) {
            sb.append(body);
        }

        sb.append("]");

        log.info(sb.toString());
    }

    private Map<String, String> buildParametersMap(HttpServletRequest httpServletRequest) {
        Map<String, String> resultMap = new HashMap<>();
        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            String value = httpServletRequest.getParameter(key);
            resultMap.put(key, value);
        }

        return resultMap;
    }

    private Map<String, String> buildHeadersMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }

    private Map<String, String> buildHeadersMap(HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();

        Collection<String> headerNames = response.getHeaderNames();
        for (String header : headerNames) {
            map.put(header, response.getHeader(header));
        }

        return map;
    }
}
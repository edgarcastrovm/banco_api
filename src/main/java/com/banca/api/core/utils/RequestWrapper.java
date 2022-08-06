package com.banca.api.core.utils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class RequestWrapper {

    public Object body;

}

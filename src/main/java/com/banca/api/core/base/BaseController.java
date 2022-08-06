package com.banca.api.core.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseController<C> {

    protected final Logger LOG;

    public BaseController(Class<C> clazz){
        LOG = LoggerFactory.getLogger(clazz);
    }
}

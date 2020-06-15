package com.knewton.planner.faraday.core.interceptor;

import com.knewton.planner.faraday.config.MappingProperties;
import com.knewton.planner.faraday.core.http.ResponseData;

public class NoOpPostForwardResponseInterceptor implements PostForwardResponseInterceptor {
    @Override
    public void intercept(ResponseData data, MappingProperties mapping) {

    }
}

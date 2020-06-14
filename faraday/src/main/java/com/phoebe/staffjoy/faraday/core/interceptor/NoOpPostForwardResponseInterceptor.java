package com.phoebe.staffjoy.faraday.core.interceptor;

import com.phoebe.staffjoy.faraday.config.MappingProperties;
import com.phoebe.staffjoy.faraday.core.http.ResponseData;

public class NoOpPostForwardResponseInterceptor implements PostForwardResponseInterceptor {
    @Override
    public void intercept(ResponseData data, MappingProperties mapping) {

    }
}

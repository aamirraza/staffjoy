package com.knewton.planner.faraday.core.interceptor;

import com.knewton.planner.faraday.config.MappingProperties;
import com.knewton.planner.faraday.config.MappingProperties;
import com.knewton.planner.faraday.core.http.ResponseData;

public interface PostForwardResponseInterceptor {
    void intercept(ResponseData data, MappingProperties mapping);
}

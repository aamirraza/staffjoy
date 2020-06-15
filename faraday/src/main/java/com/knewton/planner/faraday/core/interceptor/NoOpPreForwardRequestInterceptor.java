package com.knewton.planner.faraday.core.interceptor;

import com.knewton.planner.faraday.config.MappingProperties;
import com.knewton.planner.faraday.core.http.RequestData;
import com.knewton.planner.faraday.config.MappingProperties;
import com.knewton.planner.faraday.core.http.RequestData;

public class NoOpPreForwardRequestInterceptor implements PreForwardRequestInterceptor {
    @Override
    public void intercept(RequestData data, MappingProperties mapping) {

    }
}

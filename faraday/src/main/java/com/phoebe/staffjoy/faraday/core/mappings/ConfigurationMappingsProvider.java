package com.phoebe.staffjoy.faraday.core.mappings;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import com.phoebe.staffjoy.faraday.config.FaradayProperties;
import com.phoebe.staffjoy.faraday.config.MappingProperties;
import com.phoebe.staffjoy.faraday.core.http.HttpClientProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigurationMappingsProvider extends MappingsProvider {

    public ConfigurationMappingsProvider(
            ServerProperties serverProperties,
            FaradayProperties faradayProperties,
            MappingsValidator mappingsValidator,
            HttpClientProvider httpClientProvider
    ) {
        super(serverProperties, faradayProperties,
                mappingsValidator, httpClientProvider);
    }


    @Override
    protected boolean shouldUpdateMappings(HttpServletRequest request) {
        return false;
    }

    @Override
    protected List<MappingProperties> retrieveMappings() {
        return faradayProperties.getMappings().stream()
                .map(MappingProperties::copy)
                .collect(Collectors.toList());
    }
}

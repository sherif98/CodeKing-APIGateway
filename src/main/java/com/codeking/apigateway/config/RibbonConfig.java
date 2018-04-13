package com.codeking.apigateway.config;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.WeightedResponseTimeRule;
import org.springframework.context.annotation.Bean;

public class RibbonConfig {

    @Bean
    public IRule ribbonRule(final IClientConfig clientConfig) {
        return new WeightedResponseTimeRule();
    }
}

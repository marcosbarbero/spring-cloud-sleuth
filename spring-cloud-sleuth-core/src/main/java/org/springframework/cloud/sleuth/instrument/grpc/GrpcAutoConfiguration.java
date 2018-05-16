package org.springframework.cloud.sleuth.instrument.grpc;

import brave.Tracer;
import brave.Tracing;
import brave.grpc.GrpcTracing;
import io.grpc.ClientInterceptor;
import io.grpc.ServerInterceptor;
import org.lognet.springboot.grpc.GRpcGlobalInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnBean(Tracing.class)
@ConditionalOnClass(Tracer.class)
@EnableConfigurationProperties(SleuthGrpcProperties.class)
// NOTE: align with org.lognet:grpc-spring-boot-starter property name or implement the whole mechanism from scratch.
@ConditionalOnProperty(value = "spring.sleuth.grpc.enabled", havingValue = "true")
public class GrpcAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public GrpcTracing sleuthGrpcTracing(Tracing braveTracing) {
        return GrpcTracing.create(braveTracing);
    }

    @Bean
    @ConditionalOnMissingBean
    public ClientInterceptor sleuthClientInterceptor(GrpcTracing grpcTracing) {
        return grpcTracing.newClientInterceptor();
    }

    @Bean
    @GRpcGlobalInterceptor
    public ServerInterceptor sleuthTracingInterceptor(GrpcTracing grpcTracing) {
        return grpcTracing.newServerInterceptor();
    }

}

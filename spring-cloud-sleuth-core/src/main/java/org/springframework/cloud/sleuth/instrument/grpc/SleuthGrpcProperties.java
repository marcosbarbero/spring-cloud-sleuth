package org.springframework.cloud.sleuth.instrument.grpc;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.sleuth.grpc")
public class SleuthGrpcProperties {

    private boolean enabled;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

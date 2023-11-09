package com.example.eshop.service.rate_limiter;

import com.example.eshop.domain.entity.User;
import com.example.eshop.service.UserService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.Refill;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class RateLimiterService {
    private final UserService userService;
    private final ProxyManager<String> proxyManager;

    public Bucket resolveBucket(String key) {
        Supplier<BucketConfiguration> configSupplier = getConfigSupplierForUser(key);

        return proxyManager.builder().build(key, configSupplier);
    }

    private Supplier<BucketConfiguration> getConfigSupplierForUser(String key) {
        long userId = Long.parseLong(key);
        userService.findById(userId);

        Refill refill = Refill.greedy(2, Duration.ofSeconds(1));
        Bandwidth limit = Bandwidth.classic(2, refill);
        return () -> (BucketConfiguration.builder()
                .addLimit(limit)
                .build());
    }
}

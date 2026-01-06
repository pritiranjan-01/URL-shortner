package com.url.shortner.service;

import com.url.shortner.entity.UrlShortner;
import com.url.shortner.repository.UrlShortnerRepository;
import com.url.shortner.util.ShortCodeGenerator;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UrlShortnerService {

    private final UrlShortnerRepository urlMappingRepository;
    private final ShortCodeGenerator shortCodeGenerator;

    public UrlShortnerService(
            UrlShortnerRepository urlMappingRepository, ShortCodeGenerator shortCodeGenerator) {
        this.urlMappingRepository = urlMappingRepository;
        this.shortCodeGenerator = shortCodeGenerator;
    }

    @Transactional
    public UrlShortner createShortUrl(String originalUrl) {
        // If URL already exists, return existing mapping
        Optional<UrlShortner> existing = urlMappingRepository.findByOriginalUrl(originalUrl);
        if (existing.isPresent()) {
            return existing.get();
        }

        // Generate unique short code with collision handling
        String shortCode;
        int attempts = 0;
        do {
            shortCode = shortCodeGenerator.generate();
            attempts++;
        } while (urlMappingRepository.findByShortCode(shortCode).isPresent() && attempts < 10);

        if (urlMappingRepository.findByShortCode(shortCode).isPresent()) {
            throw new IllegalStateException("Could not generate unique short code after several attempts");
        }

        UrlShortner mapping = new UrlShortner();
        mapping.setOriginalUrl(originalUrl);
        mapping.setShortCode(shortCode);
        // createdAt will be set by @PrePersist; expireAt can be null or set later
        mapping.setExpireAt(null);

        return urlMappingRepository.save(mapping);
    }

    @Transactional(readOnly = true)
    public Optional<String> getOriginalUrl(String shortCode) {
        return urlMappingRepository
                .findByShortCode(shortCode)
                .filter(m -> m.getExpireAt() == null || m.getExpireAt().isAfter(LocalDateTime.now()))
                .map(UrlShortner::getOriginalUrl);
    }
}



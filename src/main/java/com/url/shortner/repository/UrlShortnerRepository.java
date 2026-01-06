package com.url.shortner.repository;

import com.url.shortner.entity.UrlShortner;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlShortnerRepository extends JpaRepository<UrlShortner, Long> {
    Optional<UrlShortner> findByShortCode(String shortCode);
    Optional<UrlShortner> findByOriginalUrl(String originalUrl);
}


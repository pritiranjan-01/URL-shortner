package com.url.shortner.controller;

import com.url.shortner.entity.UrlShortner;
import com.url.shortner.service.UrlShortnerService;
import java.net.URI;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
public class HomeController {

    private final UrlShortnerService urlShortnerService;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");

    public HomeController(UrlShortnerService urlShortnerService) {
        this.urlShortnerService = urlShortnerService;
    }

    @GetMapping("/")
    public String showForm(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", error);
        }
        return "index";
    }

    @PostMapping("/shorten")
    public String processForm(@RequestParam("url") String url, Model model) {
        // Validate URL
        if (url == null || url.trim().isEmpty()) {
            model.addAttribute("errorMessage", "URL cannot be empty");
            return "index";
        }
        
        url = url.trim();
        
        // Basic URL validation
        try {
            new URI(url).toURL();
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Invalid URL format. Please enter a valid URL (e.g., https://example.com)");
            model.addAttribute("urlValue", url);
            return "index";
        }
        
        try {
            UrlShortner shortenUrlCode = urlShortnerService.createShortUrl(url);

            String shortUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/")
                    .path(shortenUrlCode.getShortCode())
                    .toUriString();

            model.addAttribute("shortCode", shortenUrlCode.getShortCode());
            model.addAttribute("shortUrl", shortUrl);
            model.addAttribute("originalUrl", shortenUrlCode.getOriginalUrl());
            model.addAttribute("createdAt", shortenUrlCode.getCreatedAt() != null ? 
                    shortenUrlCode.getCreatedAt().format(DATE_FORMATTER) : "");
            model.addAttribute("expireAt", shortenUrlCode.getExpireAt() != null ? 
                    shortenUrlCode.getExpireAt().format(DATE_FORMATTER) : null);

            return "result";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            model.addAttribute("urlValue", url);
            return "index";
        }
    }

    @GetMapping("/{shortCode:[a-zA-Z0-9]{6}}")
    public String redirectToOriginal(@PathVariable String shortCode) {
        return urlShortnerService.getOriginalUrl(shortCode)
                .map(url -> "redirect:" + url)
                .orElse("redirect:/?error=Short URL not found");
    }
}

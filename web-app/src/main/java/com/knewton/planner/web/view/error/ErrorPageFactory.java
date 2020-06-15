package com.knewton.planner.web.view.error;

import com.knewton.planner.web.config.AssetLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ErrorPageFactory {

    @Autowired
    AssetLoader assetLoader;

    public ErrorPage buildNotFoundPage() {
        return ErrorPage.builder()
                .title("Oops! The page you were looking for doesn't exist.")
                .explanation("You may have mistyped the address, or the page may have been moved.")
                .headerCode(HttpStatus.NOT_FOUND.value())
                .linkText("Click here to go back to Staffjoy")
                .linkHref("https://www.planner.xyz")
                .imageBase64(assetLoader.getImageBase64())
                .build();
    }

    public ErrorPage buildInternalServerErrorPage() {
        return ErrorPage.builder()
                .title("Internal Server Error")
                .explanation("Oops! Something broke. We're paging our engineers to look at it immediately.")
                .headerCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .linkText("Click here to check out our system status page")
                .linkHref("https://status.planner.xyz")
                .imageBase64(assetLoader.getImageBase64())
                .build();
    }
}

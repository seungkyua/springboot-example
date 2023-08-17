package com.ask.example.controller;

import com.ask.example.controller.validator.AppValidator;
import com.ask.example.domain.BadRequestException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
@RestController
public class AppController {

    @InitBinder
    void initBinder(WebDataBinder binder) {
        binder.addValidators(new AppValidator());
    }

//    @GetMapping(path = "/app/{appId}")
//    public AppResponse getApps(
//            @PathVariable(value = "appId") Long appId,
//            @RequestParam(value = "appName") String appName) {
//
//        return AppResponse.createAppResponse(appId, appName);
//    }

//    @ExceptionHandler(BadRequestException.class)
//    public ResponseEntity<ErrorResponse> handleException(BadRequestException ex) {
//        return new ResponseEntity<>(
//                new ErrorResponse(ex.getErrorMessage()),
//                HttpStatus.BAD_REQUEST);
//    }

    @GetMapping(path = "/apps/{appId}/errors")
    public ResponseEntity<CommonResponse> getErrors(
        @PathVariable(value = "appId") Long appId) {

        log.info("Unknown bad request error.\nappId = {}", appId);
        throw new BadRequestException("Unknown bad request error.\nappId = " + appId);
    }

    @GetMapping(path = "/apps/{appId}")
//    public ResponseEntity<AppResponse> getApps(
    public ResponseEntity<CommonResponse> getApps(
            ClientInfo clientInfo,
            @PathVariable(value = "appId") Long appId,
            @RequestParam(value = "appName") String appName) {

        System.out.println(clientInfo);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//        headers.add("Content-Type", "application/json");
//        headers.add("Content-Type", "charset=utf-8");
        headers.add("Custom-Header", "value1");
        headers.add("Custom-Header", "value2");

//        return new ResponseEntity<>(
//                AppResponse.createAppResponse(appId, appName),
//                headers,
//                HttpStatus.OK);
          return new ResponseEntity<>(
                new CommonResponse(AppResponse.createAppResponse(appId, appName)),
                headers,
                HttpStatus.OK);
//        return new ResponseEntity<>(
//                new CommonResponse(null),
//                headers,
//                HttpStatus.OK);
    }

    @GetMapping(path = "/apps/{appId}/tasks/{taskId}")
    public ResponseEntity<AppResponse.Task> getTask(
            ClientInfo clientInfo,
            @PathVariable(value = "appId") Long appId,
            @PathVariable(value = "taskId") Long taskId) {

        System.out.println(clientInfo);
        System.out.println("appId: " + appId);

        return new ResponseEntity<>(
                AppResponse.createTask(taskId, "v1"),
                HttpStatus.OK);
    }

    @PostMapping(path = "/apps")
    public ResponseEntity<String> createApps(
            @RequestHeader Map<String, String> requestHeaders,
            @Valid @RequestBody AppRequest appRequest,
            BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            String errorMessage = "validation error." +
                    " field: " + Objects.requireNonNull(fieldError).getField() +
                    ", code: " + fieldError.getCode() +
                    ", message: " + fieldError.getDefaultMessage();

            return new ResponseEntity<>(
                    errorMessage,
                    HttpStatus.BAD_REQUEST);
        }

        requestHeaders.forEach((key, value) -> {
            System.out.printf("Request Header '%s' = %s%n", key, value);
        });

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Custom-Header", "value1");
        headers.add("Custom-Header", "value2");

//        headers.forEach((key, value) -> {
//            System.out.printf("Response Header '%s' = %s%n", key, value);
//        });

        return new ResponseEntity<>(
                appRequest.createApp(appRequest.getAppName()).toString(),
                headers,
                HttpStatus.CREATED);
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<FileUploadResponse> uploadFile(
            @RequestParam("file")MultipartFile multipartFile) {

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        long size = multipartFile.getSize();

        String fileGenerateName  = saveFile(fileName, multipartFile);

        FileUploadResponse response = new FileUploadResponse(
                fileName,
                "/downloadRile/" + fileGenerateName,
                size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/downloadFile/{fileGenerateName}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileGenerateName") String fileGenerateName) {
        Resource resource = getFileAsResource(fileGenerateName);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @GetMapping(
            value = "/downloadFile2/{fileId}",
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public void downloadFile(
            @PathVariable("fileId") String fileId,
            HttpServletResponse response) {

        String directory = "upload_files";
        String fileName = "abcdefg-coursera.png";
        fileId = fileName;
        Path filePath = Paths.get(directory).resolve(fileName);

        try (InputStream is = Files.newInputStream(filePath);
             OutputStream os = response.getOutputStream();) {

            response.setStatus(HttpStatus.OK.value());
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");

            StreamUtils.copy(is, os);
        } catch (Throwable th) {
            th.printStackTrace();
            throw new FileDownloadException("file download error.");
        }
    }

    private String saveFile(String fileName, MultipartFile multipartFile) {
        String directory = "upload_files";
        String fileGenerateName = "abcdefg";

        Path uploadPath = Paths.get(directory);
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException ioe) {
                throw new FileUploadException("Create directory error");
            }
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileGenerateName + "-" + fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new FileUploadException("Could not save file: " + fileName);
        }

        return fileGenerateName;
    }

    private Resource getFileAsResource(String fileGenerateName) {
        String directory = "upload_files";

        String found = Stream.of(Objects.requireNonNull(new File(directory).list()))
                .filter(file -> file.startsWith(fileGenerateName))
                .findFirst()
                .orElse(null);

        if (Objects.isNull(found)) {
            throw new BadRequestException("File not found. file = [" + fileGenerateName + "]");
        }

        Path foundFile = Paths.get(directory).resolve(Paths.get(Objects.requireNonNull(found)));

        try {
            return new UrlResource(foundFile.toUri());
        } catch (MalformedURLException e) {
            throw new FileDownloadException("Internal Server Error.");
        }
    }
}

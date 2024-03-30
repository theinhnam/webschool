package com.namndt.webschool.proxy;

import com.namndt.webschool.config.ProjectSecurityConfig;
import com.namndt.webschool.model.Contact;
import feign.Headers;
import lombok.NoArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "contact", url = "http://localhost:8080/api/contact",
    configuration = ProjectSecurityConfig.class)
public interface ContactProxy {

    @RequestMapping(method = RequestMethod.GET, value = "/getAllMsgByStatus")
    @Headers(value = "Content-Type: application/json")
    public List<Contact> findByStatus(@RequestParam String status);
}

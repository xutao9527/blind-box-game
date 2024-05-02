package com.bbg.admin.third.zbt;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@FeignClient(name = "zbt-data",url = "https://www.zbt.com/",configuration = ZBTHttpService.ZBTInterceptor.class)
public interface ZBTHttpService {


    @GetMapping("api/gw/steamtrade/sga/item-search/v1/list?appId=730&appid=730&orderBy=0&sellType=1")
    String getCsgoGoodPageData(@RequestParam("limit") int limit, @RequestParam("page") int page);

    class ZBTInterceptor implements RequestInterceptor {

        @Override
        public void apply(RequestTemplate requestTemplate) {
            String formattedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS"));
            String url = """
                   %s: %s
                   """.formatted(formattedDate, requestTemplate.request().url());
            System.out.println(url);
        }
    }
}

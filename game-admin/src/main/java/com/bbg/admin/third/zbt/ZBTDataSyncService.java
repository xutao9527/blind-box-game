package com.bbg.admin.third.zbt;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.bbg.admin.service.csgo.CsgoGoodsService;
import com.bbg.core.annotation.RedisLock;
import com.bbg.core.constants.KeyConst;
import com.bbg.model.csgo.CsgoGoods;
import com.mybatisflex.core.query.QueryMethods;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Component
public class ZBTDataSyncService {
    @Autowired
    CsgoGoodsService csgoGoodsService;

    @Autowired(required = false)
    ZBTHttpService zbtHttpService;

    private boolean isRunning = false;

    @RedisLock(value = "'zbtData'",key = KeyConst.METHOD_SYNC_DATA_LOCK)
    public boolean syncData() {
        boolean isOk = false;
        if (!isRunning) {
            isOk = true;
            isRunning = true;
            CompletableFuture.runAsync(() -> {
                try {
                    int pages = 200;
                    for (int page = 1; page <= pages; page++) {
                        try {
                            String reqData = zbtHttpService.getCsgoGoodPageData(200, page);
                            CsgoGoodPage goodPage = parseData(reqData);
                            pages = goodPage.pages;
                            saveData(goodPage.goods);
                            Thread.sleep((new Random().nextInt(3) + 10) * 1000);
                        } catch (Exception e) {
                            break;
                        }
                    }
                } catch (Exception ignored) {
                } finally {
                    isRunning = false;
                }
            });
        }
        return isOk;
    }

    @Data
    public static class CsgoGoodPage {
        private int total;  // 总记录
        private int page;   // 当前页
        private int pages;  // 总页数
        private List<CsgoGoods> goods;  // 商品集合
    }

    public CsgoGoodPage parseData(String body) {
        JSONObject ret = JSONObject.parseObject(body);
        List<CsgoGoods> csgoGoodsList = new ArrayList<>();
        CsgoGoodPage goodPage = new CsgoGoodPage();
        goodPage.total = ret.getJSONObject("data").getInteger("total");
        goodPage.page = ret.getJSONObject("data").getInteger("page");
        goodPage.pages = ret.getJSONObject("data").getInteger("pages");

        JSONArray skinjsonArray = ret.getJSONObject("data").getJSONArray("list");
        skinjsonArray.forEach(item -> {
            CsgoGoods data = JSON.parseObject(item.toString(), CsgoGoods.class);
            csgoGoodsList.add(data);
        });
        goodPage.goods = csgoGoodsList;
        return goodPage;
    }

    public void saveData(List<CsgoGoods> list) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(QueryMethods.column(CsgoGoods::getId))
                .where(CsgoGoods::getId)
                .in(list.stream().map(CsgoGoods::getId).toList());
        List<CsgoGoods> existList = csgoGoodsService.list(queryWrapper);
        List<CsgoGoods> insertList = list.stream().filter(
                itemA -> existList.stream()
                        .noneMatch(itemB -> itemA.getId().equals(itemB.getId()))
        ).toList();
        List<CsgoGoods> updateList = list.stream().filter(
                itemA -> existList.stream()
                        .anyMatch(itemB -> itemA.getId().equals(itemB.getId()))
        ).toList();
        csgoGoodsService.saveBatch(insertList);
        csgoGoodsService.updateBatch(updateList);
    }

    public String loadLocalData() {
        StringBuilder sb = new StringBuilder();
        String filePath = "C:\\Users\\Administrator\\Desktop\\skin.json";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception ignored) {
        }
        return sb.toString();
    }


}

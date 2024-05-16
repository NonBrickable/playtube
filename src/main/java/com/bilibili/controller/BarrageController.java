package com.bilibili.controller;

import com.bilibili.common.JsonResponse;
import com.bilibili.controller.support.UserSupport;
import com.bilibili.pojo.Barrage;
import com.bilibili.service.BarrageService;
import com.bilibili.service.impl.BarrageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BarrageController {
    private final BarrageService barrageService;
    private final UserSupport userSupport;

    @GetMapping("/barrages")
    public JsonResponse<List<Barrage>> getBarrages(@RequestParam Long videoId,
                                                   String startTime,
                                                   String endTime) throws Exception{
        List<Barrage> list;
        try{
            userSupport.getCurrentUserId();
            list = barrageService.getBarrages(videoId,startTime,endTime);
        }catch (Exception e){
            list = barrageService.getBarrages(videoId,null,null);
        }
        return new JsonResponse<>(list);
    }
}

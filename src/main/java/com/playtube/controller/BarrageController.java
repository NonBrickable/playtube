package com.playtube.controller;

import com.playtube.common.JsonResponse;
import com.playtube.common.UserContext;
import com.playtube.pojo.Barrage;
import com.playtube.service.BarrageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BarrageController {
    private final BarrageService barrageService;

    @GetMapping("/barrages")
    public JsonResponse<List<Barrage>> getBarrages(@RequestParam Long videoId,
                                                   String startTime,
                                                   String endTime) throws Exception{
        List<Barrage> list;
        try{
            UserContext.getUserId();
            list = barrageService.getBarrages(videoId,startTime,endTime);
        }catch (Exception e){
            list = barrageService.getBarrages(videoId,null,null);
        }
        return new JsonResponse<>(list);
    }
}

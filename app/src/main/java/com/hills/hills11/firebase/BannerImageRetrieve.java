package com.hills.hills11.firebase;

import com.hills.hills11.data.CarouselImageLink;

import java.util.List;
import java.util.Map;

public interface BannerImageRetrieve {

    void onCarouselImageLoadSuccess(List<CarouselImageLink> mList);
    //void onFireBaseImageLoadSuccess(Map<String, Object> mapVal);

    void onFireBaseImageLoadFailed(String message);
}

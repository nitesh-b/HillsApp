package com.hills.hills11.firebase;

import java.util.Map;

public interface BannerImageRetrieve {
    void onFireBaseImageLoadSuccess(Map<String, Object> mapVal);

    void onFireBaseImageLoadFailed(String message);
}

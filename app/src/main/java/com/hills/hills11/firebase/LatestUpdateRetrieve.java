package com.hills.hills11.firebase;

import com.hills.hills11.data.LatestUpdateData;
import com.hills.hills11.data.PreferredBrandDetails;
import com.hills.hills11.data.ProductsDetails;

import java.util.List;

public interface LatestUpdateRetrieve {

    void onFireBaseLoadSucccess(List<LatestUpdateData> list);

    void onFirebaseLoadFailed(String message);
}

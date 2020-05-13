package com.hills.hills11.firebase;


import com.hills.hills11.data.PreferredBrandDetails;
import com.hills.hills11.data.ProductsDetails;

import java.util.List;

public interface RetrieveFromFirestore {
    void onFireBaseLoadSucccess(List<ProductsDetails> productList);

    void onFireBaseBrandLoadSucccess(List<PreferredBrandDetails> productList);

    void onFirebaseLoadFailed(String message);

}

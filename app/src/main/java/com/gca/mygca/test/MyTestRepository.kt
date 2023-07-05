package com.gca.mygca.test

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.gca.mygca.retrofit.HomeApiService
import javax.inject.Inject

class MyTestRepository @Inject constructor(val homeApiService: HomeApiService) {
    fun getBanners() = Pager(config = PagingConfig(pageSize = 20, maxSize = 100),
    pagingSourceFactory = {MyTestSource(homeApiService)}).liveData

}
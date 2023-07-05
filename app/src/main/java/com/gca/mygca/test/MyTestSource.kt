package com.gca.mygca.test

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gca.mygca.model.Banner
import com.gca.mygca.model.SliderResponseModel
import com.gca.mygca.retrofit.HomeApiService
import javax.inject.Inject

class MyTestSource (val homeApiService: HomeApiService):PagingSource<Int,Banner>() {
    override fun getRefreshKey(state: PagingState<Int, Banner>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Banner> {
        return try {
            val pageNo = params.key?:1
            val response = homeApiService.getBanners()
            val next = pageNo+1

            LoadResult.Page(data = response.result, prevKey = null, nextKey = next)
        }catch (e:Exception){
            LoadResult.Error(e)
        }

    }
}
package com.gca.mygca.model.response

data class GalleryResponseModel(val status: Int, val result: List<MediaModel>)

data class MediaModel(val id: String,val title: String,val path: String,val type: Int,val timestamp: String?)
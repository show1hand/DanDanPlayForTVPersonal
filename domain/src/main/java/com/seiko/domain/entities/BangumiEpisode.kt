package com.seiko.domain.entities

//BangumiEpisode {
//    episodeId (integer): 剧集ID（弹幕库编号） ,
//    episodeTitle (string, optional): 剧集标题 ,
//    lastWatched (string): 上次观看时间（服务器时间，即北京时间） ,
//    airDate (string): 本集上映时间（当地时间）
//}
data class BangumiEpisode(
    val episodeId: Int,
    val episodeTitle: String,
    val lastWatched: String,
    val airDate: String
)
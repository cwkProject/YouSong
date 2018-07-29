package com.yousong.yousong.work.common

import com.yousong.yousong.value.ValueUrl
import org.cwk.android.library.work.SimpleUploadWorkModel
import org.json.JSONObject
import java.io.File

/**
 * 上传文件的接口
 *
 * @author 超悟空
 * @version 1.0 2018/7/18
 * @since 1.0
 */
class FileUploadWork : SimpleUploadWorkModel<File, String>() {

    override fun onSuccessExtract(jsonResult: JSONObject) = jsonResult.getString(RESULT)

    override fun onTaskUri(vararg parameters: File?) = ValueUrl.URL_FILE_UPLOAD

    override fun onFill(dataMap: MutableMap<String, Any?>, vararg parameters: File?) {
        dataMap["file"] = parameters[0]
    }
}
package moe.muska.ami.spcraft.cartcore.configuration

import com.alibaba.fastjson2.JSONObject
import java.io.File

class JsonConfiguration(
    val file: File? = null,
) {
    val data: JSONObject = JSONObject()
}
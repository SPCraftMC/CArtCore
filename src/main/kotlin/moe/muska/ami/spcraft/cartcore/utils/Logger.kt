package moe.muska.ami.spcraft.cartcore.utils

import moe.muska.ami.spcraft.cartcore.CArtCore
import org.bukkit.plugin.Plugin
import java.util.logging.Logger
import kotlin.collections.ArrayList

interface Logger {

    companion object {

        private val instance: Plugin = CArtCore.plugin
        private val defLgr: Logger = instance.logger

        /**
         * INFO
         * @param a 输出内容
         */
        fun info(a: String) {
            defLgr.info(a)
        }

        /**
         * INFO
         * @param aL 输出内容列表
         */
        fun info(aL: List<String>) {
            for (a in aL) info(a)
        }

        /**
         * WARN
         * @param a 内容
         */
        fun warn(a: String) {
            defLgr.warning(a)
        }

        /**
         * WARN
         * @param aL 输出内容列表
         */
        fun warn(aL: List<String>) {
            for (a in aL) warn(a)
        }

        /**
         * ERROR
         * @param a 内容
         */
        fun error(a: String) {
            defLgr.severe(a)
        }

        /**
         * ERROR
         * @param aL 输出内容列表
         */
        fun error(aL: List<String>) {
            for (a in aL) error(a)
        }

        /**
         * DEBUG
         * @param a 内容
         */
        fun debug(a: String) {
            defLgr.info("[DEBUG] $a")
        }

        /**
         * DEBUG
         * @param aL 输出内容列表
         */
        fun debug(aL: List<String>) {
            for (a in aL) error(a)
        }

        /**
         * 异常
         * @param e 异常
         */
        fun exception(e: Exception) {
            val message = e.message
            val jClass = e.javaClass
            val stackTrace = e.stackTrace

            val stackTraceFormatted = ArrayList<String>()
            for (trace in stackTrace) stackTraceFormatted.add("- 位于 $trace")

            val res = ArrayList<String>()
            res.add("异常：${message}")
            res.add("类：${jClass}")
            res.add("栈追踪信息：")
            res.addAll(stackTraceFormatted)
            this.error(res)
        }

        /**
         * 致命
         * 此操作将直接禁用plugin
         * @param a 输出内容
         * @param e 异常
         */
        fun fatal(a: String, e: Exception? = null) {
            this.error("致命: $a")
            if (e != null) exception(e)
            instance.pluginLoader.disablePlugin(instance)
        }
    }

}
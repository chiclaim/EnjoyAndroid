package com.chiclaim.play.android

import com.chiclaim.play.android.retrofit.Api
import com.chiclaim.play.android.retrofit.WanApi
import com.chiclaim.play.android.task.startTask
import com.chiclaim.play.android.task.startTaskAsync
import com.chiclaim.play.android.task.uiJob
import kotlinx.coroutines.GlobalScope
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CoroutineUnitTest {
    @Test
    fun test() {

        // place your scope
        GlobalScope.uiJob(block = {

            println("start request server..." + Thread.currentThread().name)
            // retrofit and coroutine // 同步
            val result = Api.create(WanApi::class.java).getCollectedArticleList(0)

            // 执行同步任务
            val value = startTask {
                println("sync task start on thread: " + Thread.currentThread().name)
                Thread.sleep(2000)
                20
            }
            println("get sync task value: $value on thread: " + Thread.currentThread().name)

            // 开始执行异步任务1
            val deferred = startTaskAsync(this) {
                println("async task1 start on thread:" + Thread.currentThread().name)
                Thread.sleep(4000)
                null
            }

            // 开始执行异步任务2
            val deferred2 = startTaskAsync(this) {
                println("async task2 start on thread: " + Thread.currentThread().name)
                Thread.sleep(2000)
                if (true) {
                    throw IllegalStateException("my exception2")
                }
                20
            }

            println("get async task1 value:${deferred.await()} on thread: " + Thread.currentThread().name)
            println("get async task2 value:${deferred2.await()} on thread: " + Thread.currentThread().name)

        }, onFailed = { // 处理异常
            println("handle uiJob exception on uiJob: " + Thread.currentThread().name)
            it.printStackTrace()
            //Toast.makeText(this, it.codeMessage(), Toast.LENGTH_SHORT).show()
        })
    }
}
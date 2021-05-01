package com.chiclaim.play.android.task

import com.chiclaim.play.android.exception.toApiException
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 *
 * 封装主线程执行的协程操作。
 *
 * 可以使用同步的编码方式处理异步任务，例如：
 *
 * ```
 *   GlobalScope.uiJob(block = {
 *       // retrofit and coroutine
 *       val data = Api.create(WanApi::class.java).getCollectedArticleList(0)
 *       println(Thread.currentThread().name + " first fetch -> data size = ${data.data?.datas?.size}")
 *
 *       // 执行同步任务
 *       val value = startTask{
 *           println("sync task start on thread: " + Thread.currentThread().name)
 *           Thread.sleep(2000)
 *           20 // return
 *       }
 *       println("get sync task value: $value on thread: " + Thread.currentThread().name)
 *
 *       // 开始执行异步任务1
 *       val deferred = startTaskAsync(this) {
 *           println("async task1 start on thread:" + Thread.currentThread().name)
 *           Thread.sleep(4000)
 *           20 // return
 *       }
 *
 *       // 开始执行异步任务2
 *       val deferred2 = startTaskAsync(this) {
 *       println("async task2 start on thread: " + Thread.currentThread().name)
 *       Thread.sleep(2000)
 *       if (true) {
 *           throw IllegalStateException("my exception2")
 *       }
 *           20 // return
 *       }
 *
 *       println("get async task1 data on thread: " + Thread.currentThread().name)
 *       deferred.await()
 *
 *       println("get async task2 data on thread: " + Thread.currentThread().name)
 *       deferred2.await()
 *
 *   }, onFailed = { // 处理异常
 *       it.printStackTrace()
 *   })
 *
 * ````
 *
 */
fun CoroutineScope.uiJob(timeout: Long = 0L,
                         block: suspend CoroutineScope.() -> Unit,
                         onFailed: (t: Throwable) -> Unit) {
    startJob(this, Dispatchers.Main, timeout, block, onFailed)
}

/**
 * 在 [coroutineContext] 上执行 [block] ，该方法没有返回值(Unit)
 *
 * 并且 [block] 抛出的异常会被 [onFailed] catch 住，所以如果某个任务只管发起，不关心结果时，建议使用本方法
 *
 * 使用例子可以参考 [uiJob]
 *
 * @param coroutineContext Dispatchers.Default
 * @param timeout 超时时间
 * @param block 需要执行的代码块
 * @return Unit
 */
private fun startJob(
        parentScope: CoroutineScope,
        coroutineContext: CoroutineContext = Dispatchers.Default,
        timeout: Long = 0L,
        block: suspend CoroutineScope.() -> Unit,
        onFailed: ((t: Throwable) -> Unit)) {
    parentScope.launch(coroutineContext) {
        supervisorScope {
            if (timeout > 0L) {
                withTimeout(timeout) {
                    try {
                        block()
                    } catch (t: Throwable) {
                        onFailed(t.toApiException())
                    }
                }

            } else {
                try {
                    block()
                } catch (t: Throwable) {
                    onFailed(t.toApiException())
                }
            }
        }
    }

}

/**
 * 在 [coroutineContext] 上执行 [block]，方法返回 [block] 执行后的结果值
 *
 * 使用例子可以参考 [uiJob]
 *
 * @param coroutineContext Dispatchers.Default
 * @param block 需要执行的代码块
 * @param timeout 超时时间
 * @return T from [block]
 */
suspend fun <T> startTask(
        coroutineContext: CoroutineContext = Dispatchers.Default,
        timeout: Long = 0L,
        block: suspend CoroutineScope.() -> T): T {
    return withContext(coroutineContext) {
        return@withContext if (timeout > 0L) {
            withTimeout(timeout) {
                block()
            }
        } else {
            block()
        }
    }
}

/**
 * 在执行 [coroutineContext] 上执行任务，启动多个会并发执行，返回 [Deferred] 对象
 *
 * 使用例子可以参考 [uiJob] 注释
 *
 * @param coroutineContext Dispatchers.Default
 * @param block 需要执行的代码块
 * @param timeout 超时时间
 *
 * @see startJob
 * @return Deferred
 */
suspend fun <T> startTaskAsync(
        parentScope: CoroutineScope,
        coroutineContext: CoroutineContext = Dispatchers.Default,
        timeout: Long = 0L,
        block: suspend CoroutineScope.() -> T
): Deferred<T> {
    return supervisorScope {
        parentScope.async(coroutineContext) {
            if (timeout > 0L) {
                withTimeout(timeout) {
                    block()
                }
            } else {
                block()
            }
        }
    }
}




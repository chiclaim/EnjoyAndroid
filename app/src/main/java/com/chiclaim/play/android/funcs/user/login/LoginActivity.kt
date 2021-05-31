package com.chiclaim.play.android.funcs.user.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.chiclaim.play.android.MainActivity
import com.chiclaim.play.android.R
import com.chiclaim.play.android.base.BaseActivity
import com.chiclaim.play.android.exception.codeMessage
import com.chiclaim.play.android.retrofit.Api
import com.chiclaim.play.android.retrofit.WanApi
import com.chiclaim.play.android.task.startTask
import com.chiclaim.play.android.task.startTaskAsync
import com.chiclaim.play.android.task.uiJob
import kotlinx.coroutines.GlobalScope

/**
 * 登录页面
 *
 * @author chiclaim@google.com
 */
class LoginActivity : BaseActivity() {

    private lateinit var loginViewModel: LoginViewModel

    private lateinit var editUsername: EditText
    private lateinit var editPassword: EditText

    private val wanApi = Api.create(WanApi::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)

        editUsername = findViewById(R.id.edit_username)
        editPassword = findViewById(R.id.edit_password)


        loginViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(LoginViewModel::class.java)


        findViewById<Button>(R.id.btn_login).setOnClickListener {
            loginViewModel.getLoginLiveData(
                editUsername.text.toString(),
                editPassword.text.toString()
            )
                .observe(this) {
                    if (it.isSuccess()) {
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, it.codeMessage(), Toast.LENGTH_SHORT).show()
                    }
                }

        }

        findViewById<Button>(R.id.btn_test).setOnClickListener {

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
                Toast.makeText(this, it.codeMessage(), Toast.LENGTH_SHORT).show()
            })


            /*wanApi.getArticleList(0).enqueue(
                object : retrofit2.Callback<RespBO<PageBO<ArticleBO>>> {
                    override fun onResponse(
                        call: Call<RespBO<PageBO<ArticleBO>>>,
                        response: Response<RespBO<PageBO<ArticleBO>>>
                    ) {
                        val resp = response.body()
                        if (resp?.isSuccess() == true) {
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                resp?.errorMsg,
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }

                    override fun onFailure(call: Call<RespBO<PageBO<ArticleBO>>>, t: Throwable) {
                        t.printStackTrace()
                    }
                }
            )*/

        }

    }


}
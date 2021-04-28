package com.chiclaim.play.android.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.chiclaim.play.android.MainActivity
import com.chiclaim.play.android.R
import com.chiclaim.play.android.base.BaseActivity
import com.chiclaim.play.android.bean.ArticleDO
import com.chiclaim.play.android.bean.PageDO
import com.chiclaim.play.android.bean.RespDO
import com.chiclaim.play.android.bean.UserDO
import com.chiclaim.play.android.source.Api
import com.chiclaim.play.android.source.WanApi
import retrofit2.Call
import retrofit2.Response

/**
 * 登录页面
 *
 * Created by kumu@2dfire.com
 */
class LoginActivity : BaseActivity() {


    private lateinit var editUsername: EditText
    private lateinit var editPassword: EditText

    private val wanApi = Api.create(WanApi::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)

        editUsername = findViewById(R.id.edit_username)
        editPassword = findViewById(R.id.edit_password)


        findViewById<Button>(R.id.btn_login).setOnClickListener {
            wanApi.login(editUsername.text.toString(), editPassword.text.toString()).enqueue(
                object : retrofit2.Callback<RespDO<UserDO>> {
                    override fun onResponse(
                        call: Call<RespDO<UserDO>>,
                        response: Response<RespDO<UserDO>>
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

                    override fun onFailure(call: Call<RespDO<UserDO>>, t: Throwable) {
                        t.printStackTrace()
                    }
                }
            )
        }


        findViewById<Button>(R.id.btn_test).setOnClickListener {
            wanApi.getArticleList(0).enqueue(
                object : retrofit2.Callback<RespDO<PageDO<ArticleDO>>> {
                    override fun onResponse(
                        call: Call<RespDO<PageDO<ArticleDO>>>,
                        response: Response<RespDO<PageDO<ArticleDO>>>
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

                    override fun onFailure(call: Call<RespDO<PageDO<ArticleDO>>>, t: Throwable) {
                        t.printStackTrace()
                    }
                }
            )

        }

    }


}
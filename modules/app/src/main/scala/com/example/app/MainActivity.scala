package com.example.app

import android.app.Activity
import android.os.Bundle

class MainActivity extends Activity with TypedViewHolder {
    override def onCreate(b: Bundle) {
        super.onCreate(b)
        setContentView(R.layout.hello)
        findView(TR.test_textview).setText("Hello again, world!")
    }
}

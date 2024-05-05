package com.example.calculator_core

class NativeLib {

    /**
     * A native method that is implemented by the 'calculator_core' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'calculator_core' library on application startup.
        init {
            System.loadLibrary("calculator_core")
        }
    }
}
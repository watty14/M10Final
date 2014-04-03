package com.example.wallt;

import android.app.Application;

import com.parse.Parse;

/**
 * WALLT Application Class.
 *
 * @author Thomas Harris (tharris7@gatech.edu)
 * @version 1.0
 */
public class WALLTApplication extends Application {

    /**
     * Initializes the application.
     */
    public final void onCreate() {
        Parse.initialize(this, "Cnmqzdi7YSCSGPBixx11Mjvu6tF8mpzTZ9yObsM1",
            "xeJa7O6dzR8oulCfD31cmUwI3frlngCFSY1jSEr8");
    }

}

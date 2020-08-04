package com.mti.component.master;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedSimpleRingProgressComponent {
    @Test
    public void useAppContext() {
        int[] grants = {-1, 100, 0};

        System.out.println("list---" + Arrays.stream(grants).anyMatch(e -> e == -1));
    }
}

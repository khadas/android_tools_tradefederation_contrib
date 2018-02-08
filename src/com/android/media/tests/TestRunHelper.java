/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.media.tests;

import com.android.tradefed.log.LogUtil.CLog;
import com.android.tradefed.result.ITestInvocationListener;
import com.android.tradefed.result.TestDescription;

import java.util.HashMap;
import java.util.Map;

/** Generic helper class for tests */
public class TestRunHelper {

    private long mTestStartTime = -1;
    private long mTestStopTime = -1;
    private ITestInvocationListener mListener;
    private TestDescription mTestId;

    public TestRunHelper(ITestInvocationListener listener, TestDescription testId) {
        mListener = listener;
        mTestId = testId;
    }

    public long getTotalTestTime() {
        return mTestStopTime - mTestStartTime;
    }

    public void reportFailure(String errMsg) throws TestFailureException {
        CLog.e(errMsg);
        mListener.testRunFailed(errMsg);
        mListener.testFailed(mTestId, errMsg);
        mListener.testEnded(mTestId, new HashMap<String, String>());
        throw new TestFailureException();
    }

    /** @param resultDictionary */
    public void endTest(Map<String, String> resultDictionary) {
        mTestStopTime = System.currentTimeMillis();
        mListener.testRunEnded(getTotalTestTime(), resultDictionary);
        mListener.testEnded(mTestId, resultDictionary);
    }

    public void startTest(int numberOfTests) {
        mListener.testRunStarted(mTestId.getTestName(), numberOfTests);
        mListener.testStarted(mTestId);
        mTestStartTime = System.currentTimeMillis();
    }
}

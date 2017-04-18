/*
 * Copyright (C) 2016 The Pure Nexus Project
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

package com.unholy.settings.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.UserHandle;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.PreferenceScreen;
import com.android.internal.logging.MetricsProto.MetricsEvent;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.unholy.settings.utils.Utils;
import com.unholy.settings.preference.SystemSettingSwitchPreference;

import android.hardware.fingerprint.FingerprintManager;

public class LockScreenSettings extends SettingsPreferenceFragment {

    private static final int MY_USER_ID = UserHandle.myUserId();

    private static final String KEYGUARD_TORCH = "keyguard_toggle_torch";
    private static final String FP_UNLOCK_KEYSTORE = "fp_unlock_keystore";

    private FingerprintManager mFingerprintManager;

    private SystemSettingSwitchPreference mLsTorch;
    private SystemSettingSwitchPreference mFpKeystore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.lockscreen_settings);
        PreferenceScreen prefScreen = getPreferenceScreen();

        mLsTorch = (SystemSettingSwitchPreference) findPreference(KEYGUARD_TORCH);
        if (!Utils.deviceSupportsFlashLight(getActivity())) {
            prefScreen.removePreference(mLsTorch);
        }

        mFingerprintManager = (FingerprintManager) getActivity().getSystemService(Context.FINGERPRINT_SERVICE);
        mFpKeystore = (SystemSettingSwitchPreference) findPreference(FP_UNLOCK_KEYSTORE);
        if (!mFingerprintManager.isHardwareDetected()){
            prefScreen.removePreference(mFpKeystore);
        }
    }

    @Override
    protected int getMetricsCategory() {
        return MetricsEvent.UNHOLY_SETTINGS;
    }
}
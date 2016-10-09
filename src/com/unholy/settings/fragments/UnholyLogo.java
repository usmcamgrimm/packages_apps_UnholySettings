/*
 * Copyright (C) 2016 Screw'd AOSP
 * Copyright (C) 2017 Unholy Development
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

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.UserHandle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceGroup;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;
import android.support.v7.preference.PreferenceScreen;
import android.support.v14.preference.PreferenceFragment;
import android.support.v14.preference.SwitchPreference;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import com.android.internal.logging.MetricsProto.MetricsEvent;
import net.margaritov.preference.colorpicker.ColorPickerPreference;

/**
 * Created by cedwards on 6/3/2016.
 * Modified by jl90 on 3/16/2017
 */
public class UnholyLogo extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    private static final String KEY_UNHOLY_LOGO_COLOR = "status_bar_unholy_logo_color";
    //private static final String KEY_UNHOLY_LOGO_STYLE = "status_bar_unholy_logo_style";

    private ColorPickerPreference mUnholyLogoColor;
    //private ListPreference mUnholyLogoStyle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.unholy_logo_settings);

        PreferenceScreen prefSet = getPreferenceScreen();
        ContentResolver resolver = getActivity().getContentResolver();
        /*
        mUnholyLogoStyle = (ListPreference) findPreference(KEY_UNHOLY_LOGO_STYLE);
        int unholyLogoStyle = Settings.System.getIntForUser(resolver,
                Settings.System.STATUS_BAR_UNHOLY_LOGO_STYLE, 0,
                UserHandle.USER_CURRENT);
        mUnholyLogoStyle.setValue(String.valueOf(unholyLogoStyle));
        mUnholyLogoStyle.setSummary(mUnholyLogoStyle.getEntry());
        mUnholyLogoStyle.setOnPreferenceChangeListener(this);
        
        */

        // Aicp logo color
        mUnholyLogoColor =
            (ColorPickerPreference) prefSet.findPreference(KEY_UNHOLY_LOGO_COLOR);
        mUnholyLogoColor.setOnPreferenceChangeListener(this);
        int intColor = Settings.System.getInt(resolver,
                Settings.System.STATUS_BAR_UNHOLY_LOGO_COLOR, 0xffffffff);
        String hexColor = String.format("#%08x", (0xffffffff & intColor));
        mUnholyLogoColor.setSummary(hexColor);
        mUnholyLogoColor.setNewPreviewColor(intColor);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        AlertDialog dialog;
        ContentResolver resolver = getActivity().getContentResolver();
        if (preference == mUnholyLogoColor) {
            String hex = ColorPickerPreference.convertToARGB(
                    Integer.valueOf(String.valueOf(newValue)));
            preference.setSummary(hex);
            int intHex = ColorPickerPreference.convertToColorInt(hex);
            Settings.System.putInt(resolver,
                    Settings.System.STATUS_BAR_UNHOLY_LOGO_COLOR, intHex);
            return true;
        /*} else if (preference == mUnholyLogoStyle) {
            int unholyLogoStyle = Integer.valueOf((String) newValue);
            int index = mUnholyLogoStyle.findIndexOfValue((String) newValue);
            Settings.System.putIntForUser(
                    resolver, Settings.System.STATUS_BAR_UNHOLY_LOGO_STYLE, unholyLogoStyle,
                    UserHandle.USER_CURRENT);
            mUnholyLogoStyle.setSummary(mUnholyLogoStyle.getEntries()[index]);
            return true;*/
        }
        return false;
    }

    @Override
    protected int getMetricsCategory() {
        return MetricsEvent.UNHOLY_SETTINGS;
    }



}
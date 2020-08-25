package org.tensorflow.lite.examples.classification;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.view.View;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import androidx.annotation.Nullable;
import android.widget.BaseAdapter;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends PreferenceFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    SharedPreferences prefs;

    ListPreference soundPreference;
    ListPreference keywordSoundPreference;
    PreferenceScreen keywordScreen;

    private String mParam1;
    private String mParam2;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private ChildEventListener mChild;

    private ListView listView;
    private ArrayAdapter<String> adapter;
    List<Object> Array = new ArrayList<Object>();

    /////////////////////////////////////////////
    /////////////Default constructor/////////////
    /////////////////////////////////////////////
    public ProfileFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        /*
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.setting_preference);
        soundPreference = (ListPreference) findPreference("sound_list");
        keywordSoundPreference = (ListPreference) findPreference("keyword_sound_list");
        keywordScreen = (PreferenceScreen) findPreference("keyword_screen");

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        if (!prefs.getString("sound_list", "").equals("")) {
            soundPreference.setSummary(prefs.getString("sound_list", "카톡"));
        }

        if (!prefs.getString("keyword_sound_list", "").equals("")) {
            keywordSoundPreference.setSummary(prefs.getString("keyword_sound_list", "카톡"));
        }

        if (prefs.getBoolean("keyword", false)) {
            keywordScreen.setSummary("사용");
        }

        prefs.registerOnSharedPreferenceChangeListener(prefListener);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, null);

        listView = (ListView) view.findViewById(R.id.information);
        return view;
    }

    SharedPreferences.OnSharedPreferenceChangeListener prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {

        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals("sound_list")) {
                soundPreference.setSummary(prefs.getString("sound_list", "카톡"));
            }

            if (key.equals("keyword_sound_list")) {
                keywordSoundPreference.setSummary(prefs.getString("keyword_sound_list", "카톡"));
            }

            if (key.equals("keyword")) {

                if (prefs.getBoolean("keyword", false)) {
                    keywordScreen.setSummary("사용");

                } else {
                    keywordScreen.setSummary("사용안함");
                }

                //2뎁스 PreferenceScreen 내부에서 발생한 환경설정 내용을 2뎁스 PreferenceScreen에 적용하기 위한 소스
                ((BaseAdapter) getPreferenceScreen().getRootAdapter()).notifyDataSetChanged();
            }

        }
    };
}
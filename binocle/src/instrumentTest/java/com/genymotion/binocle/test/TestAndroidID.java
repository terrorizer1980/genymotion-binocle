package com.genymotion.binocle.test;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.genymotion.api.GenymotionManager;
import com.genymotion.binocle.AndroidIdSampleFragment;
import com.genymotion.binocle.R;
import com.genymotion.binocle.RadioSampleFragment;
import com.genymotion.binocle.SampleActivity;

import junit.framework.Assert;

/**
 * Created by philippe on 2/6/14.
 */
public class TestAndroidID extends ActivityInstrumentationTestCase2<SampleActivity> {

    AndroidIdSampleFragment fragmentAndroidID;

    public TestAndroidID() {
        super(SampleActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        GenymotionManager genymotion;
        genymotion = GenymotionManager.getGenymotionManager(getInstrumentation().getContext());
        genymotion.getId().setAndroidId("8ec000b646bce009");

        //add parameter to allow activity to start and create fragment GpsSampleFragment.
        Intent androiIdIntent;
        androiIdIntent = new Intent(getInstrumentation().getTargetContext(), SampleActivity.class);
        androiIdIntent.putExtra(SampleActivity.ARG_ITEM_ID, AndroidIdSampleFragment.TAG);
        setActivityIntent(androiIdIntent);

        //create activity and get fragment back
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentAndroidID = (AndroidIdSampleFragment) fragmentManager.findFragmentByTag(AndroidIdSampleFragment.TAG);
    }

    public void testAndroidId() {
        try {
            Thread.sleep(2000); //Android need time to poll sensors and broadcast event.
        } catch (InterruptedException ie) {}

        TextView tvAndroidId = (TextView) fragmentAndroidID.getView().findViewById(R.id.tv_androidId);
        String text = tvAndroidId.getText().toString();
        Assert.assertTrue(text.endsWith("8ec000b646bce009"));
    }
}

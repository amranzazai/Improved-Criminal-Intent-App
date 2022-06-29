package android.bignerdranch.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity {
    //Creating a ViewPager2 object
    private ViewPager2 mViewPager2;
    private List<Crime> mCrimes;
    private static final String EXTRA_CRIME_ID =
            "com.bignerdranch.android.criminalintent.crime_id";

    @Override
    protected void onCreate(Bundle savedInstancestate) {
        super.onCreate(savedInstancestate);
        setContentView(R.layout.activity_crime_pager);

        //setting object to xml layout we made for it
        mViewPager2 = (ViewPager2)findViewById(R.id.crime_view_pager);
        mCrimes = CrimeLab.get(this).getCrimes();
        //two parameters (fragmentManager, l) that we will pass to FragmentStateAdapter
        //we replaced FragmentStatePagerAdapter with FragmentStateAdapter in order for ViewPager2 to work properly
        FragmentManager fragmentManager = getSupportFragmentManager();
        Lifecycle l = getLifecycle();
        mViewPager2.setAdapter(new FragmentStateAdapter(fragmentManager, l) {
            @Override
            public int getItemCount() {
                return mCrimes.size();
            }

            @NonNull
            @Override
            public Fragment createFragment(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getID());
            }
        });

        UUID crimeId = (UUID)getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        for(int i = 0; i < mCrimes.size(); i++) {
            if(mCrimes.get(i).getID().equals(crimeId)) {
                mViewPager2.setCurrentItem(i);
                break;
            }
        }
    }

    public static Intent newIntent(
            Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext,
                CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }
}

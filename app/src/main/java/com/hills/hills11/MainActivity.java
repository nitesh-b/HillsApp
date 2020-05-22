package com.hills.hills11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.hills.hills11.adapters.DrawerAdapter;
import com.hills.hills11.connectiondetector.ConnectionDetector;
import com.hills.hills11.fragments.DummyFragment;
import com.hills.hills11.fragments.LocationFragment;
import com.hills.hills11.fragments.MainFragment;
import com.hills.hills11.fragments.NewsEventsFragment;
import com.hills.hills11.notification.NotificationDetails;
import com.hills.hills11.notification.NotificationSettings;
import com.hills.hills11.notification.NotificationUpdates;
import com.hills.hills11.notification.Notificationchannel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    NavigationView navigationView;
    // private int[] icons = {R.drawable.ic_home_run, R.drawable.ic_resources, R.drawable.ic_event, R.drawable.ic_map, R.drawable.ic_aboutus};
    private DrawerLayout drawerLayout;// notification drawer
    private DrawerLayout menuDrawer; // navigation drawer
    private FirebaseFirestore db;
    private Toolbar mToolbar;
    /*---------------Navigation view objects---------------------------------*/
    private TextView mArticle, mFinance, mPartner, mLegacy, mFaqs, mContact, mSpecialOfferText, mAlwaysLowPrice, mLatestUpdates;
    private ImageView facebook, linkedIn, youtube;
    /*--------------Initialise objects for bottom Navigation------------------------*/
    private BottomNavigationView bottomNavigationView;

    /*--------------Initialise objects for Main Frame------------------------*/
    private FrameLayout mainFrame;

    /*--------------Fragments used in main activity------------------------*/
    private NewsEventsFragment eventFragment;
    private MainFragment mainFragment;
    private LocationFragment locationFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        /*--------------Get id to a view------------------------*/
        mArticle = findViewById ( R.id.articleandvidoes );
        mArticle.setOnClickListener ( this );
        mFinance = findViewById ( R.id.finance );
        mFinance.setOnClickListener ( this );
        mPartner = findViewById ( R.id.partnerProgram );
        mPartner.setOnClickListener ( this );
        mLegacy = findViewById ( R.id.legacyFirmwareDownload );
        mLegacy.setOnClickListener ( this );
        mFaqs = findViewById ( R.id.faqs );
        mFaqs.setOnClickListener ( this );
        mContact = findViewById ( R.id.contactUs );
        mContact.setOnClickListener ( this );
        navigationView = findViewById ( R.id.nav_menuDrawer );

        facebook = findViewById ( R.id.facebookIcon );
        facebook.setOnClickListener ( this );
        linkedIn = findViewById ( R.id.linkedInIcon );
        linkedIn.setOnClickListener ( this );
        youtube = findViewById ( R.id.youtubeIcon );
        youtube.setOnClickListener ( this );

        mSpecialOfferText = findViewById ( R.id.specialOfferText );
        mSpecialOfferText.setOnClickListener ( this );

        mAlwaysLowPrice = findViewById ( R.id.alwaysLowPriceText );
        mAlwaysLowPrice.setOnClickListener ( this );

        mLatestUpdates = findViewById ( R.id.latestUpdatesTextView );
        mLatestUpdates.setOnClickListener ( this );

        bottomNavigationView = findViewById ( R.id.bottomNavigation );
        mainFrame = findViewById ( R.id.mainFrameLayout );
        eventFragment = new NewsEventsFragment ( );
        mainFragment = new MainFragment ( );
        locationFragment = new LocationFragment ( );

        bottomNavigationView.getMenu ( ).getItem ( 1 ).setChecked ( true );
        getSupportFragmentManager ( ).beginTransaction ( ).replace ( R.id.mainFrameLayout , mainFragment ).commit ( );



        /*--------------------------------------------------------------------------------------------------------------------------*/
        db = FirebaseFirestore.getInstance ( ); // initialize firebase

        /*-------------------Create Notification Channel-----------------------------*/
        Notificationchannel notificationchannel = new Notificationchannel ( this );
        notificationchannel.createNotificationChannel ( );
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult> () {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        Log.d ( TAG , "onComplete: token " + token );
                    }
                });
        /*--------------------------------------------------------------------------------------------------------------------------*/
        setUpNotificationDrawer ( );


        /*---------------------------------------------------------------------------*/
        /*left Navigation Drawer Setup*/
        menuDrawer = findViewById ( R.id.drawer );
        mToolbar = findViewById ( R.id.toolbar );
        mToolbar.setTitle ( "" );
        setSupportActionBar ( mToolbar );
        ActionBarDrawerToggle tog = new ActionBarDrawerToggle ( this , menuDrawer , mToolbar , R.string.open , R.string.close );
        menuDrawer.addDrawerListener ( tog );
        tog.setDrawerIndicatorEnabled ( true );
        tog.syncState ( );
        /*---------------------------------------------------------------------------*/

        /*---------------------------------------------------------------------------*/
        /*-------------------Bottom Navigation Drawer------------------------*/
        bottomNavigationView.setOnNavigationItemSelectedListener ( new BottomNavigationView.OnNavigationItemSelectedListener ( ) {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId ( )) {
                    case R.id.eventItem:
                        setFragment ( eventFragment );
                        return true;

                    case R.id.homeItem:
                        setFragment ( mainFragment );
                        return true;

                    case R.id.findusItem:
                        setFragment ( locationFragment );
                        return true;

                    default:
                        return false;


                }
            }
        } );

        /*---------------------------------------------------------------------------*/


    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager ( ).beginTransaction ( );
        fragmentTransaction.replace ( R.id.mainFrameLayout , fragment ).commit ( );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater ( ).inflate ( R.menu.additional_menu , menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if ( item.getItemId ( ) == R.id.notificationIcon ) {
            drawerLayout.openDrawer ( Gravity.RIGHT );
        }
        return super.onOptionsItemSelected ( item );
    }

    @Override
    protected void onStart() {
        super.onStart ( );
        new NotificationSettings ();
        ConnectionDetector detector = new ConnectionDetector ( this );
        if ( detector.isConnected ( ) ) {
            // Toast.makeText(this, "Connected to Internet", Toast.LENGTH_SHORT).show();
        } else {
            final AlertDialog.Builder builder1 = new AlertDialog.Builder ( this );
            builder1.setMessage ( "Please get connected to the Internet for better user experience!" );
            builder1.setCancelable ( false );

            builder1.setPositiveButton (
                    "Yes" ,
                    new DialogInterface.OnClickListener ( ) {
                        public void onClick(DialogInterface dialog , int id) {
                            startActivity ( new Intent ( Settings.ACTION_WIFI_SETTINGS ) );
                        }
                    } );

            builder1.setNegativeButton (
                    "No" ,
                    new DialogInterface.OnClickListener ( ) {
                        public void onClick(DialogInterface dialog , int id) {
                            dialog.dismiss ( );
                        }
                    } );

            AlertDialog alert11 = builder1.create ( );
            alert11.show ( );
        }

    }

    @Override
    public void onBackPressed() {
        if ( menuDrawer.isDrawerOpen ( GravityCompat.START ) ) {
            menuDrawer.closeDrawer ( GravityCompat.START );
        } else {
            super.onBackPressed ( );
        }
    }


    private void setUpNotificationDrawer() {
        /*Creating a channel for notification*/
        RecyclerView drawerRecyclerView = findViewById ( R.id.drawer_recyclerView );
        drawerLayout = findViewById ( R.id.drawer );
        drawerRecyclerView.setHasFixedSize ( true );
        NotificationUpdates details = new NotificationUpdates ( getApplicationContext ( ) );

        /* Log.d(TAG, "setUpNotification: " + dateTime);*/
        drawerRecyclerView.setLayoutManager ( new LinearLayoutManager ( this ) );
        Log.d ( TAG , "setUpNotification: I am here" );
        List<NotificationDetails> notificationList = details.getDetails ( );
        final DrawerAdapter drawerAdapter = new DrawerAdapter ( this , notificationList );
        drawerRecyclerView.setAdapter ( drawerAdapter );
        drawerAdapter.notifyDataSetChanged ( );
        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle ( this , drawerLayout , R.string.open , R.string.close );
        drawerLayout.addDrawerListener ( mToggle );
        mToggle.syncState ( );

        /*Listen for additional document in firebase*/
        db.collection ( "notification" )
                .document ( "history" )
                .collection ( "timestamp" )
                .addSnapshotListener ( new EventListener<QuerySnapshot> ( ) {
                    @Override
                    public void onEvent(@androidx.annotation.Nullable QuerySnapshot queryDocumentSnapshots , @androidx.annotation.Nullable FirebaseFirestoreException e) {
                        if ( e != null ) {
                            Toast.makeText ( getApplicationContext ( ) , "Problem retrieving Data" , Toast.LENGTH_SHORT ).show ( );
                        } else
                            drawerAdapter.notifyDataSetChanged ( );
                    }
                } );

    }

    @Override
    public void onClick(View v) {
        String url = null;
        switch (v.getId ( )) {

            case R.id.drawerLogo:
                url = "https://www.hills.com.au/articles-and-videos";
                startBrowser ( url );
                break;
                /*Log.d ( TAG , "onClick: always low price clicked" );
                startActivity ( new Intent(MainActivity.this, AlwaysLowPrice.class) );
                menuDrawer.closeDrawer ( GravityCompat.START );
                break;*/


            case R.id.alwaysLowPriceText:
                mAlwaysLowPrice.setSelected ( true );
                openActivity ( AlwaysLowPrice.class );
                break;

            case R.id.specialOfferText:
                mSpecialOfferText.setSelected ( true );
                openActivity ( SpecialProduct.class );
                break;

            case R.id.latestUpdatesTextView:
                mLatestUpdates.setSelected ( true );
                openActivity ( LatestUpdatesActivity.class );
                break;

            case R.id.articleandvidoes:
                url = "https://www.hills.com.au/articles-and-videos";
                startBrowser ( url );
                break;
            case R.id.finance:
                url = "https://www.hills.com.au/finance";
                startBrowser ( url );
                break;
            case R.id.partnerProgram:
                url = "https://www.hills.com.au/partner-program";
                startBrowser ( url );
                break;
            case R.id.legacyFirmwareDownload:
                url = "http://cdn-tp3.mozu.com/24421-m3/cms/files/5a126d89-9064-4cd3-999a-0996f0c8b653";
                startBrowser ( url );
                break;
            case R.id.faqs:
                mFaqs.setSelected ( true );
                menuDrawer.closeDrawer ( GravityCompat.START );
                url = "https://www.hills.com.au/faq-page";
                startBrowser ( url );
                /* startActivity ( new Intent ( this, FaqActivity.class ) );*/
                break;

            case R.id.contactUs:
                findViewById ( R.id.contactUs ).setSelected ( true );
                menuDrawer.closeDrawer ( GravityCompat.START );
                url = "https://www.hills.com.au/contactus";
                startBrowser ( url );
                break;

            case R.id.facebookIcon:
                facebook.setSelected ( true );
                menuDrawer.closeDrawer ( GravityCompat.START );
                url = "https://www.facebook.com/HillsLtd/";
                startBrowser ( url );
                break;
            case R.id.linkedInIcon:
                linkedIn.setSelected ( true );
                menuDrawer.closeDrawer ( GravityCompat.START );
                url = "https://www.linkedin.com/company/hills-holdings-ltd/?originalSubdomain=in";
                startBrowser ( url );
                break;
            case R.id.youtubeIcon:
                linkedIn.setSelected ( true );
                menuDrawer.closeDrawer ( GravityCompat.START );
                url = "https://www.youtube.com/channel/UCfDQtHUtyG0nIU3a_R0ooDw";
                startBrowser ( url );
                break;


        }

    }

    private void openActivity(Class activity) {
        startActivity ( new Intent ( MainActivity.this , activity ) );
        menuDrawer.closeDrawer ( GravityCompat.START );
    }

    private void startBrowser(String url) {
        Intent browse = new Intent ( Intent.ACTION_VIEW , Uri.parse ( url ) );
        startActivity ( browse );
    }


}

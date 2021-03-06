package teammemes.tritonbudget;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import teammemes.tritonbudget.Menus.Menu64Degrees_Main;
import teammemes.tritonbudget.Menus.Menu64North_Main;
import teammemes.tritonbudget.Menus.MenuBistro_Main;
import teammemes.tritonbudget.Menus.MenuCafeVentanas_Main;
import teammemes.tritonbudget.Menus.MenuCanyonVista_Main;
import teammemes.tritonbudget.Menus.MenuClubMed_Main;
import teammemes.tritonbudget.Menus.MenuFoodworx_Main;
import teammemes.tritonbudget.Menus.MenuGoodys_Main;
import teammemes.tritonbudget.Menus.MenuOceanView_Main;
import teammemes.tritonbudget.Menus.MenuPines_Main;
import teammemes.tritonbudget.Menus.MenuRoots_Main;

/* Author: Andy Lum */

public class DiningHallSelection extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //Subtext Size (colleges)
    final float SUBSIZE = (float) .85;
    //Restaurant Name Lenghts
    final int SIXFOUR = 11;
    final int CAFEV = 13;
    final int CV = 12;
    final int FOODWORX = 8;
    final int OVT = 9;
    final int PINES = 5;
    final int BISTRO = 10;
    final int CLUBMED = 8;
    final int FLAVOR = 20;
    final int GOODYS = 7;
    final int GOODYS2 = 20;
    final int ROOTS = 6;
    final int SIXFOURNO = 9;
    String  source;
    //Nav Drawers
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private User usr;

    public DiningHallSelection() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_dhselection);

        usr = User.getInstance(getApplicationContext());

        //Creates the toolbar to the one defined in nav_action
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Dining Hall Selection");

        //Create the Drawer layout and the toggle
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_dhselection);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        //Create the navigationView and add a listener to listen for menu selections
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navHeaderView= navigationView.getHeaderView(0);
        TextView usrName = (TextView) navHeaderView.findViewById(R.id.header_name);
        usrName.setText(usr.getName());

        //This is meant for the college locations ie subtext
        RelativeSizeSpan relsubsize = new RelativeSizeSpan(SUBSIZE);
        ForegroundColorSpan relcolor = new ForegroundColorSpan(Color.GRAY);

        /* Get IDs */
        //Dining Halls
        TextView sixtyfour = (TextView) findViewById(R.id.DH_TextView_64);
        TextView cafev = (TextView) findViewById(R.id.DH_TextView_CafeV);
        TextView canyonv = (TextView) findViewById(R.id.DH_TextView_CanyonV);
        TextView foodworx = (TextView) findViewById(R.id.DH_TextView_FoodWorx);
        TextView ovt = (TextView) findViewById(R.id.DH_TextView_OVT);
        TextView pines = (TextView) findViewById(R.id.DH_TextView_Pines);

        //FT & Specialities
        TextView bistro = (TextView) findViewById(R.id.DH_TextView_Bistro);
        TextView clubmed = (TextView) findViewById(R.id.DH_TextView_ClubMed);
//        TextView flavorstruck = (TextView) findViewById(R.id.DH_TextView_FlavorFT);
        TextView goodys = (TextView) findViewById(R.id.DH_TextView_Goodys);
//        TextView goodysFT = (TextView) findViewById(R.id.DH_TextView_GoodysFT);
        TextView roots = (TextView) findViewById(R.id.DH_TextView_Roots);
        TextView sixtyfournorth = (TextView) findViewById(R.id.DH_TextView_64North);

        TextView[] dtv = {sixtyfour, cafev, canyonv, foodworx, ovt, pines, bistro, clubmed,
                goodys, roots, sixtyfournorth};
        String[] diningHalls = getResources().getStringArray(R.array.Dining_Halls_Array);
        int[] dnamelenghts = {SIXFOUR, CAFEV, CV, FOODWORX, OVT, PINES, BISTRO, CLUBMED,
                GOODYS, ROOTS, SIXFOURNO};

        /*Set Text for TextView Objects*/
        String text;
        SpannableString format;
        for (int i = 0; i < 11; i++) {
            text = diningHalls[i];
            format = new SpannableString(text);
            format.setSpan(relsubsize, dnamelenghts[i], text.length(), 0);
            format.setSpan(relcolor, dnamelenghts[i], text.length(), 0);
            dtv[i].setText(format);
        }
        source=getIntent().getExtras().getString("FROM");
        /*Listeners*/
        /*TODO: This can be optimized with a for-loop. It is currently left like this for back-end
        to tinker with*/

        //Dining Halls
        sixtyfour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(source.equals("PURCHASE"))
                {
                    Intent intent = new Intent(DiningHallSelection.this,PurchaseMenu.class);
                    intent.putExtra("FROM","64 Degrees");
                    startActivity(intent);
                }
                else {
                    //Attempt to open another activity (Dining Hall Menu)
                    Intent intent = new Intent(DiningHallSelection.this, Menu64Degrees_Main.class);
                    startActivity(intent);
                }
            }
        });
        cafev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(source.equals("PURCHASE"))
                {
                    Intent intent = new Intent(DiningHallSelection.this,PurchaseMenu.class);
                    intent.putExtra("FROM","Cafe Ventanas");
                    startActivity(intent);
                }
                else {
                    //Attempt to open another activity (Dining Hall Menu)
                    Intent intent = new Intent(DiningHallSelection.this, MenuCafeVentanas_Main.class);
                    startActivity(intent);
                }
            }
        });
        canyonv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(source.equals("PURCHASE"))
                {
                    Intent intent = new Intent(DiningHallSelection.this,PurchaseMenu.class);
                    intent.putExtra("FROM","Canyon Vista");
                    startActivity(intent);
                }
                else {
                    //Attempt to open another activity (Dining Hall Menu)
                    Intent intent = new Intent(DiningHallSelection.this, MenuCanyonVista_Main.class);
                    startActivity(intent);
                }
            }
        });
        foodworx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(source.equals("PURCHASE"))
                {
                    Intent intent = new Intent(DiningHallSelection.this,PurchaseMenu.class);
                    intent.putExtra("FROM","Foodworx");
                    startActivity(intent);
                }
                else {
                    //Attempt to open another activity (Dining Hall Menu)
                    Intent intent = new Intent(DiningHallSelection.this, MenuFoodworx_Main.class);
                    startActivity(intent);
                }
            }
        });
        ovt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(source.equals("PURCHASE"))
                {
                    Intent intent = new Intent(DiningHallSelection.this,PurchaseMenu.class);
                    intent.putExtra("FROM","Oceanview Terrace");
                    startActivity(intent);
                }
                else {
                    //Attempt to open another activity (Dining Hall Menu)
                    Intent intent = new Intent(DiningHallSelection.this, MenuOceanView_Main.class);
                    startActivity(intent);
                }
            }
        });
        pines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(source.equals("PURCHASE"))
                {
                    Intent intent = new Intent(DiningHallSelection.this,PurchaseMenu.class);
                    intent.putExtra("FROM","Pines");
                    startActivity(intent);
                }
                else {
                    //Attempt to open another activity (Dining Hall Menu)
                    Intent intent = new Intent(DiningHallSelection.this, MenuPines_Main.class);
                    startActivity(intent);
                }
            }
        });

        //FT and Specialities
        bistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(source.equals("PURCHASE"))
                {
                    Intent intent = new Intent(DiningHallSelection.this,PurchaseMenu.class);
                    intent.putExtra("FROM","The Bistro");
                    startActivity(intent);
                }
                else {
                    //Attempt to open another activity (Dining Hall Menu)
                    Intent intent = new Intent(DiningHallSelection.this, MenuBistro_Main.class);
                    startActivity(intent);
                }
            }
        });
        clubmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(source.equals("PURCHASE"))
                {
                    Intent intent = new Intent(DiningHallSelection.this,PurchaseMenu.class);
                    intent.putExtra("FROM","Club Med");
                    startActivity(intent);
                }
                else {
                //Attempt to open another activity (Dining Hall Menu)
                Intent intent = new Intent(DiningHallSelection.this, MenuClubMed_Main.class);
                startActivity(intent);
                }
            }
        });
        /*flavorstruck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(source.equals("PURCHASE"))
                {
                    Intent intent = new Intent(DiningHallSelection.this,PurchaseMenu.class);
                    intent.putExtra("FROM","The Bistro");
                    startActivity(intent);
                }
                else {
                    //Attempt to open another activity (Dining Hall Menu)
                    Intent intent = new Intent(DiningHallSelection.this, testClick.class);
                    startActivity(intent);
                }
            }
        });*/
        goodys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(source.equals("PURCHASE"))
                {
                    Intent intent = new Intent(DiningHallSelection.this,PurchaseMenu.class);
                    intent.putExtra("FROM","Goodys");
                    startActivity(intent);
                }
                else {
                    //Attempt to open another activity (Dining Hall Menu)
                    Intent intent = new Intent(DiningHallSelection.this, MenuGoodys_Main.class);
                    startActivity(intent);
                }
            }
        });
        /*goodysFT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(source.equals("PURCHASE"))
                {
                    Intent intent = new Intent(DiningHallSelection.this,PurchaseMenu.class);
                    intent.putExtra("FROM","The Bistro");
                    startActivity(intent);
                }
                else {
                    //Attempt to open another activity (Dining Hall Menu)
                    Intent intent = new Intent(DiningHallSelection.this, testClick.class);
                    startActivity(intent);
                }
            }
        });*/
        roots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(source.equals("PURCHASE"))
                {
                    Intent intent = new Intent(DiningHallSelection.this,PurchaseMenu.class);
                    intent.putExtra("FROM","Roots");
                    startActivity(intent);
                }
                else {
                    //Attempt to open another activity (Dining Hall Menu)
                    Intent intent = new Intent(DiningHallSelection.this, MenuRoots_Main.class);
                    startActivity(intent);
                }
            }
        });
        sixtyfournorth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(source.equals("PURCHASE"))
                {
                    Intent intent = new Intent(DiningHallSelection.this,PurchaseMenu.class);
                    intent.putExtra("FROM","Sixty-Four North");
                    startActivity(intent);
                }
                else {
                    //Attempt to open another activity (Dining Hall Menu)
                    Intent intent = new Intent(DiningHallSelection.this, Menu64North_Main.class);
                    startActivity(intent);
                }
            }
        });
    }//End of onCreate

    //This method is used to listen for the user clicking the menu button, and opens
    //the drawer up
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //This method is used to see if the back button was pressed while the drawer was open.
    //If it is open and the back button is pressed, then close the drawer.
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            Intent back = new Intent(this, HomeScreen.class);
            startActivity(back);
            //super.onBackPressed();
        }
    }

    // This method is used to react when the user presses one of the options in the drawer
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Gets the id of the item that was selected
        int id = item.getItemId();
        Intent nextScreen;

        //Reacts to the item selected depending on which was pressed
        //Creates a new Intent for the new page and starts that activity
        switch (id) {
            case R.id.nav_home:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                nextScreen = new Intent(this, HomeScreen.class);
                nextScreen.putExtra("FROM", "Menu");
                startActivity(nextScreen);
                return true;
            case R.id.nav_history:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                nextScreen = new Intent(this, History.class);
                nextScreen.putExtra("FROM", "Menu");
                startActivity(nextScreen);
                return true;
            case R.id.nav_statistics:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                nextScreen = new Intent(this, Statistics.class);
                nextScreen.putExtra("FROM", "Menu");
                startActivity(nextScreen);
                return true;
            case R.id.nav_menus:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_settings:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                nextScreen = new Intent(this, Settings.class);
                nextScreen.putExtra("FROM", "Menu");
                startActivity(nextScreen);
                return true;
            case R.id.nav_help:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                nextScreen = new Intent(this, Help.class);
                nextScreen.putExtra("FROM", "Menu");
                startActivity(nextScreen);
                return true;
            default:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
        }

    }
}

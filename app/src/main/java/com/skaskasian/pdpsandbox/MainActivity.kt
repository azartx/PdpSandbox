package com.skaskasian.pdpsandbox

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.skaskasian.pdpsandbox.presentation.screens.algo.AlgoFragment
import com.skaskasian.pdpsandbox.presentation.screens.helloworld.HelloWorldFragment
import com.skaskasian.pdpsandbox.presentation.screens.animations.AnimationsFragment
import com.skaskasian.pdpsandbox.presentation.screens.composeanim.ComposeAnimFragment
import com.skaskasian.pdpsandbox.presentation.screens.contentlist.ContentListFragment
import com.skaskasian.pdpsandbox.presentation.screens.drawableanimations.ImageAnimFragment
import com.skaskasian.pdpsandbox.presentation.screens.customview.CustomViewsFragment
import com.skaskasian.pdpsandbox.presentation.screens.lottieanima.LottieAnimFragment
import com.skaskasian.pdpsandbox.presentation.screens.motionlayoutanim.MotionLayoutAnimationFragment
import com.skaskasian.pdpsandbox.presentation.screens.motionlayoutanim.MotionLayoutFragmentsChangingFragment
import com.skaskasian.pdpsandbox.presentation.screens.patterns.PatternsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews(isConfigurationChanged = savedInstanceState != null)
    }

    private fun setupViews(isConfigurationChanged: Boolean) {
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        if (!isConfigurationChanged) {
            showInitialFragment(toolbar)
        }
    }

    private fun showInitialFragment(toolbar: Toolbar) {
        toolbar.setNavigationOnClickListener { supportFragmentManager.popBackStack() }
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container_main, HelloWorldFragment::class.java, null)
            .addToBackStack(ContentListFragment::class.java.simpleName)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_pagination -> navigate<ContentListFragment>()
            R.id.action_animation -> navigate<AnimationsFragment>()
            R.id.action_animation_compose -> navigate<ComposeAnimFragment>()
            R.id.action_image_animation -> navigate<ImageAnimFragment>()
            R.id.action_motion_layout -> navigate<MotionLayoutAnimationFragment>()
            R.id.action_motion_layout2 -> navigate<MotionLayoutFragmentsChangingFragment>()
            R.id.action_lottie_anim -> navigate<LottieAnimFragment>()
            R.id.action_patterns -> navigate<PatternsFragment>()
            R.id.action_algos -> navigate<AlgoFragment>()
            R.id.action_custom_views -> navigate<CustomViewsFragment>()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private inline fun <reified FRAGMENT : Fragment> navigate(): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_main, FRAGMENT::class.java, null)
            .addToBackStack(FRAGMENT::class.java.simpleName)
            .commit()
        return true
    }
}
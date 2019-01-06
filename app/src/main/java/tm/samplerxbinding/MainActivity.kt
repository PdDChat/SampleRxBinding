package tm.samplerxbinding

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.Observables
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mDisposable : Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val idChanges = profileId.textChanges()
        val pwChanges = profilePw.textChanges()

        mDisposable = Observables.combineLatest(idChanges, pwChanges)
        {id, pw -> id.isNotEmpty() && pw.isNotEmpty()}
            .subscribe { isValid ->
                profileSubmit.isEnabled = isValid
                profileSubmit.setBackgroundResource(
                    if (isValid == true) {
                        R.color.black
                    } else {
                        R.color.gray
                    }
                )
                profileSubmit.setText(
                    if (isValid == true) {
                        R.string.enable_tapping
                    } else {
                        R.string.disable_tapping
                    }
                )
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        mDisposable.dispose()
    }
}

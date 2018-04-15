package bleizing.riva;

import android.view.View;

/**
 * Created by Bleizing on 3/13/2018.
 */

public interface MyClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}

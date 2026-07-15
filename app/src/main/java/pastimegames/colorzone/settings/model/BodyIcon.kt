package pastimegames.colorzone.settings.model

import androidx.annotation.DrawableRes
import pastimegames.colorzone.R

enum class BodyIcon(@DrawableRes val drawableRes: Int) {
    RightHand(R.drawable.right_hand),
    LeftHand(R.drawable.left_hand),
    RightFoot(R.drawable.right_foot),
    LeftFoot(R.drawable.left_foot),
    BothHands(R.drawable.both_hands),
    BothFeet(R.drawable.both_feet),
}

package pastimegames.colorzone.settings.model

import androidx.annotation.DrawableRes
import pastimegames.colorzone.R

enum class BodyIcon(@DrawableRes val drawableRes: Int) {
    LeftHand(R.drawable.left_hand),
    RightHand(R.drawable.right_hand),
    BothHands(R.drawable.both_hands),
    LeftFoot(R.drawable.left_foot),
    RightFoot(R.drawable.right_foot),
    BothFeet(R.drawable.both_feet),
}

package rs.rbt.giftwishlist.widget

/**
 * Created by Nemanja Stosic on 12/25/18.
 */
 abstract class SwipeControllerActions {
    open fun onLeftClicked(position: Int) {}
    open fun onRightClicked(position: Int) {}
}
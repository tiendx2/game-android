package Bom.gioi.BaseAndengine;

import org.anddev.andengine.input.touch.controller.BaseTouchController;

import android.view.MotionEvent;

public class MultiTouchController extends BaseTouchController{

	 // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    // ===========================================================
    // Constructors
    // ===========================================================
    
    public MultiTouchController(){
            if(MultiTouch.isSupportedByAndroidVersion() == false) {
                    try {
						throw new Exception();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            }
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    public boolean onHandleMotionEvent(final MotionEvent pMotionEvent) {
            final int action = pMotionEvent.getAction() & MotionEvent.ACTION_MASK;
            switch(action) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:
                            return this.onHandleTouchAction(MotionEvent.ACTION_DOWN, pMotionEvent);
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                            return this.onHandleTouchAction(MotionEvent.ACTION_UP, pMotionEvent);
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_OUTSIDE:
                            return this.onHandleTouchAction(action, pMotionEvent);
                    case MotionEvent.ACTION_MOVE:
                            return this.onHandleTouchMove(pMotionEvent);
                    default:
                            throw new IllegalArgumentException("Invalid Action detected: " + action);
            }
    }

    // ===========================================================
    // Methods
    // ===========================================================

    private boolean onHandleTouchMove(final MotionEvent pMotionEvent) {
            boolean handled = false;
            for(int i = pMotionEvent.getPointerCount() - 1; i >= 0; i--) {
                    final int pointerIndex = i;
                    final int pointerID = pMotionEvent.getPointerId(pointerIndex);
                    final boolean handledInner = this.fireTouchEvent(pMotionEvent.getX(pointerIndex), pMotionEvent.getY(pointerIndex), MotionEvent.ACTION_MOVE, pointerID, pMotionEvent);
                    handled = handled || handledInner;
            }
            return handled;
    }
    
    private boolean onHandleTouchAction(final int pAction, final MotionEvent pMotionEvent) {
            final int pointerIndex = this.getPointerIndex(pMotionEvent);
            final int pointerID = pMotionEvent.getPointerId(pointerIndex);
            return this.fireTouchEvent(pMotionEvent.getX(pointerIndex), pMotionEvent.getY(pointerIndex), pAction, pointerID, pMotionEvent);
    }

    private int getPointerIndex(final MotionEvent pMotionEvent) {
            return (pMotionEvent.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
